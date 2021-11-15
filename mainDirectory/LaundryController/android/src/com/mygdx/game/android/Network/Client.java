package com.mygdx.game.android.Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class Client {
	private SocketChannel _socketChannel;
	private InetSocketAddress _address;

	private ArrayBlockingQueue<ServerReply> _replies;
	private ArrayBlockingQueue<ClientCommand> _commands;
	private ArrayBlockingQueue<String> _logMessages;

	private Thread _processRepliesThread;
	private Thread _processCommandsThread;

	private Runnable DEPRECATED_processRepliesThreadRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				ByteBuffer bufferReply = null;
				ByteBuffer bufferReplyLength = ByteBuffer.allocate(4);
				int replyLength = 0;

				while (!_isShutdown) {
					bufferReplyLength.clear();

					// read length
					if (_socketChannel.isConnected() && _socketChannel.read(bufferReplyLength) != -1) {
						bufferReplyLength.flip();
						replyLength = bufferReplyLength.getInt();
						bufferReplyLength.flip();
						bufferReply = ByteBuffer.allocate(replyLength);

						//FIX: this delay prevents information loss. (We can't expect several kb's of data to be sent in 1ms)
						if (replyLength > 1000) {
							try {
								Thread.sleep(250);
							} catch (InterruptedException intEx) {

							}
						}


						bufferReply.clear();

						// read data
						if (_socketChannel.read(bufferReply) != -1) {

							// filter same replies
							if (!Arrays.equals(bufferReply.array(), _previousReply)) {
								_previousReply = bufferReply.array();

								bufferReply.flip();
								ServerReply newReply = new ServerReply(bufferReply);

								// add reply to queue
								if (newReply.isValid()) {
									synchronized (_replies) {
										_replies.put(newReply);
									}
								} else {
									System.err.println("Unable to parse received data: " + Arrays.toString(newReply.toArray()));
								}
							}
						} else {
							throw new IOException("Unable to receive data server.");
						}
					} else {
						// brake out of the loop
						throw new IOException("Unable to read data from server.");
					}

					//timeout to prevent 100% CPU usage
					try {
						Thread.sleep(1);
					} catch (InterruptedException intEx) {

					}
				}

				System.out.println("Stopped receiving replies.");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				_isReplyError = true;
				System.out.println("Stopped receiving replies.");
			}
		}
	};


	/**
	 * Reinitialize thread that processes replies from server
	 */
	private Runnable _processRepliesThreadRunnable = new Runnable() {
		@Override
		public void run() {

			try {
				ByteBuffer bufferReply = null;
				ByteBuffer bufferReplyLength = ByteBuffer.allocate(4);
				int replyLength = 0;

				while (!_isShutdown) {
					bufferReplyLength.clear();

					// read length
					if (_socketChannel.isConnected()) {

						//read length
						while (bufferReplyLength.remaining() != 0) {
							if (_socketChannel.read(bufferReplyLength) == -1) {
								throw new IOException("Unable to receive length of data.");
							}
						}

						//set buffers
						bufferReplyLength.flip();
						replyLength = bufferReplyLength.getInt();
						bufferReplyLength.flip();
						bufferReply = ByteBuffer.allocate(replyLength);

						bufferReply.clear();

						//read data
						while (bufferReply.remaining() != 0) {
							if (_socketChannel.read(bufferReply) == -1) {
								throw new IOException("Unable to receive data.");
							}
						}

						// filter same replies
						if (!Arrays.equals(bufferReply.array(), _previousReply)) {

							_previousReply = bufferReply.array();

							bufferReply.flip();
							ServerReply newReply = new ServerReply(bufferReply);

							// add reply to queue
							if (newReply.isValid()) {
								synchronized (_replies) {
									_replies.put(newReply);
								}
							} else {
								System.err.println("Unable to parse received data: " + Arrays.toString(newReply.toArray()));
							}
						}
					} else {
						// brake out of the loop
						throw new IOException("Connection is not established.");
					}

					//timeout to prevent 100% CPU usage
					try {
						Thread.sleep(1);
					} catch (InterruptedException intEx) {

					}
				}

				System.out.println("Stopped receiving replies.");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				_isReplyError = true;
				System.err.println("Stopped receiving replies.");
			}
		}
	};
	/**
	 * Used to reinitialize thread that processes command to server
	 */
	private Runnable _processCommandsThreadRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				ByteBuffer bufferCommand = null;
				ByteBuffer bufferCommandLength = ByteBuffer.allocate(4);

				while (!_isShutdown) {
					// send
					if (_socketChannel.isConnected()) {
						synchronized (_commands) {

							if (_commands.size() > 0) {
								bufferCommand = _commands.poll().toByteBuffer();

								if (bufferCommand != null) {
									bufferCommandLength.putInt(bufferCommand.limit());
									bufferCommandLength.flip();

									// send length
									_socketChannel.write(bufferCommandLength);
									bufferCommandLength = ByteBuffer.allocate(4); // reset

									// send command
									_socketChannel.write(bufferCommand);
									System.out.println("Command sent.");
								}
								else {
									System.err.println("Unable to poll command.");
								}
							}
						}
					}

					//timeout to prevent 100% CPU usage
					try {
						Thread.sleep(1);
					} catch (InterruptedException intEx) {

					}
				}

				System.out.println("Stopped sending commands.");
			}
			catch (Exception ex) {
				ex.printStackTrace();
				_isCommandError = true;
			}
		}
	};

	private boolean _isShutdown;
	private boolean _isConnected;

	private boolean _isReplyError;
	private boolean _isCommandError;

	private byte[] _previousReply;

	/**
	 * Creates a new instance of client at 127.0.0.1:3334
	 */
	public Client() {
		_address = new InetSocketAddress("127.0.0.1", 3334);

		_replies = new ArrayBlockingQueue<>(1000);
		_commands = new ArrayBlockingQueue<>(1000);
		_logMessages = new ArrayBlockingQueue<>(100);

		_previousReply = new byte[0];

		_isShutdown = false;
	}

	/**
	 * Creates a new instance of client at user defined host/port
	 */
	public Client(String host, int port) {
		_address = new InetSocketAddress(host, port);

		_replies = new ArrayBlockingQueue<>(1000);
		_commands = new ArrayBlockingQueue<>(1000);

		_isShutdown = false;
		_isConnected = false;
	}

	/**
	 * Try to connect to the server
	 * DEBUG ONLY
	 */
	public boolean ping(byte[] pingCommand, byte[] pingReply, int timeout) {
		_isShutdown = false;

		try {
			_socketChannel = SocketChannel.open();

			_socketChannel.connect(_address);
			_isConnected = true;

			// initialize variables
			ByteBuffer bufferCommand = ByteBuffer.wrap(pingCommand);
			ByteBuffer bufferReply = null;
			ByteBuffer bufferCommandLength = ByteBuffer.allocate(4);
			ByteBuffer bufferReplyLength = ByteBuffer.allocate(4);


			// send ping command
			bufferCommandLength.putInt(bufferCommand.limit());
			bufferCommandLength.flip();

			// send data without error check
			_socketChannel.write(bufferCommandLength);
			_socketChannel.write(bufferCommand);

			long startTime = System.currentTimeMillis();
			long currentTime = 0;

			// retry until timeout
			while (currentTime - startTime < timeout) {
				// receive
				bufferReplyLength.clear();

				// read length
				if (_socketChannel.read(bufferReplyLength) != -1) {
					bufferReplyLength.flip();
					bufferReply = ByteBuffer.allocate(bufferReplyLength.getInt());
					bufferReplyLength.flip();

					bufferReply.clear();

					// read data
					if (_socketChannel.read(bufferReply) != -1) {

						// filter same replies
						if (!Arrays.equals(bufferReply.array(), _previousReply)) {
							_previousReply = bufferReply.array();

							// if reply equal to ping reply
							if (Arrays.equals(bufferReply.array(), pingReply)) {
								return true;
							}
						}
					} else {
						// brake out of the loop
						return false;
					}
				} else {
					// brake out of the loop
					return false;
				}

				// update time
				currentTime = System.currentTimeMillis();
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Starts communication. This method will block.
	 */
	public void start() {
		System.out.println("Started client.");
		_isShutdown = false;

		while (!_isShutdown) {
			try {
				System.out.println("Connecting.");

				//connect
				_socketChannel = SocketChannel.open();
				_socketChannel.connect(_address);
				_isConnected = true;

				//reset error flags
				_isReplyError = false;
				_isCommandError = false;

				System.out.println("Connected.");

				//start threads
				_processRepliesThread = new Thread(_processRepliesThreadRunnable);
				_processCommandsThread = new Thread(_processCommandsThreadRunnable);
				_processRepliesThread.start();
				_processCommandsThread.start();

				//do not reconnect if connection is alive and error-free
				while (!_isReplyError && !_isCommandError) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException intEx) {

					}
				}

				_isConnected = false;
				_socketChannel.close();
			}
			catch (Exception ex) {

				//reconnect timeout
				try {
					Thread.sleep(5000);
					_isConnected = false;
				} catch (InterruptedException intEx) {

				}
			}
		}

		System.out.println("Connection stopped.");
	}

	/**
	 * Disable connection.
	 */
	public void stop() {
		_isShutdown = true;

		try {
			_socketChannel.close();
			_isConnected = false;
			System.out.println("Socket closed.");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Add command to queue of command, if possible.
	 *
	 * @param command
	 */
	public void addCommand(ClientCommand command) {
		if (!_commands.offer(command)) {
			System.err.println("Unable to add command.");
		}
		else {
			System.out.println("Command accepted.");
		}
	}

	/**
	 * Get server reply.
	 *
	 * @return ServerReply or null if it's not available.
	 */
	public ServerReply getReply() {
		ServerReply reply = null;

		synchronized (_replies) {
			reply = _replies.poll();
		}

		return reply;
	}

	/**
	 * Returns connection status
	 *
	 * @return true if connection was established, false if not or it was
	 *         terminated.
	 */
	public synchronized boolean isConnected() {
		return _isConnected;
	}

	public void setSocketAddress(String host, int port) {
		if (host == null || host.isEmpty()) {
			return;
		}

		if (port <= 0) {
			return;
		}

		InetSocketAddress address = new InetSocketAddress(host, port);

		synchronized (_address) {
			_address = address;
		}
	}
}
