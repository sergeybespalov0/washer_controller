package com.mygdx.game.android.Application;


import com.mygdx.game.android.Network.Client;
import com.mygdx.game.android.Network.ClientCommand;
import com.mygdx.game.android.Network.ServerReply;
import com.mygdx.game.android.Utility.Utility;
import com.mygdx.game.android.WasherControl.WasherProgram;
import com.mygdx.game.android.WasherControl.WasherState;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;




/**
 * Handles connection, communication, state, etc.
 * @author alexey
 */
public class Controller {

	private WasherState _currentState;
	private ArrayList<WasherProgram> _programs;

	private Queue<String> _errors;
	String _lastError;
	private Queue<String> _messages;
	String _lastMessage;

	private Client _client;
	private Thread _connectionThread;
	private Thread _processRepliesThread;

	private final Runnable _connectionThreadRunnable;
	private final Runnable _processRepliesThreadRunnable;

	private ServerReply _currentReply;
	private ClientCommand _currentCommand;

	private String _hostname;
	private ArrayList<String> _hostnames;
	private int _port;

	private int _clientID;

	private boolean _isShutdown;


	/**
	 * Creates new instance of Controller. Address is set to 127.0.0.1:3334
	 */
	public Controller() {
		_isShutdown = false;
		_programs = new ArrayList<>();
		_errors = new LinkedList<>();
		_messages = new LinkedList<>();

		_hostnames = new ArrayList<>();
		_hostnames.add("172.16.11.234");
		_hostnames.add("192.168.1.107");
		_hostnames.add("172.16.11.163");
		_hostnames.add("192.168.0.55");

		_hostname = "192.168.0.55";
		_port = 1056;

		_client = new Client(_hostname, _port);

		//THIS DOES NOT AUTOSTART CONNECTION

		_connectionThreadRunnable = new Runnable() {
			@Override
			public void run() {
				ClientCommand commandGetPrograms = new ClientCommand(ClientCommand.TYPE.GET.getValue(), ClientCommand.TYPE.GET.PROGRAM_INFO, new byte[]{0});
				_client.addCommand(commandGetPrograms);

				ClientCommand commandGetID = new ClientCommand(ClientCommand.TYPE.GET.getValue(), ClientCommand.TYPE.GET.CLIENT_NUMBER, new byte[]{0});
				_client.addCommand(commandGetID);

				_client.start();
			}
		};

		//
		_processRepliesThreadRunnable = new Runnable() {
			@Override
			public void run() {
				while (!_isShutdown) {
					processReplies();

					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		_currentState = new WasherState();
	}


	/**
	 * Continuously read incoming commands and replies using infinite loop and process them.
	 * Handles one reply and one command at a time.
	 */
	private void processReplies() {
		synchronized(_currentState) {
			_currentReply = _client.getReply();

			if (_currentCommand != null) {
				_client.addCommand(_currentCommand);
				_currentCommand = null;
			}

			//if any reply available
			if (_currentReply != null && _currentReply.isValid()) {

				//STATE
				if (_currentReply.getType() == ServerReply.TYPE.STATE.getValue()) {
					WasherState newState = new WasherState(_currentReply.getData());

					if (!newState.equals(_currentState)) {
						_currentState = newState;
					}
				}

				//GET
				if (_currentReply.getType() == ServerReply.TYPE.GET.getValue()) {
					System.out.println("Received reply of type \"GET\"");
					return;
				}

				//ERROR

				if (_currentReply.getType() == ServerReply.TYPE.ERROR.getValue()) {

					synchronized (_errors) {
						String currentError = null;

						if (_currentReply.getOption() == ServerReply.TYPE.ERROR.WRONG_VALUE) {

							currentError = _currentReply.getDataAsUTF8();
						}

						if (_currentReply.getOption() == ServerReply.TYPE.ERROR.WRONG_COMMAND) {
							currentError = _currentReply.getDataAsUTF8();
						}

						if (_currentReply.getOption() == ServerReply.TYPE.ERROR.IO_I2C) {
							currentError = "Нет соединения с входами/выходами.";
						}

						if (_currentReply.getOption() == ServerReply.TYPE.ERROR.IO_TTL) {
							currentError = "Нет соединения с датчиком расхода воды.";
						}

						if (_currentReply.getOption() == ServerReply.TYPE.ERROR.IO_1WIRE) {
							currentError = "Нет соединения с датчиком температуры.";
						}

						if (_lastError != currentError)
						{
							_errors.add(currentError);
							_lastError = currentError;
						}


						return;
					}

				}

				//PROGRAM
				if (_currentReply.getType() == ServerReply.TYPE.PROGRAM.getValue()) {

					if (_currentReply.getOption() == ServerReply.TYPE.PROGRAM.FINISHED) {
						synchronized (_messages) {
							_messages.add("Выполение программы стирки завершено.");
						}
					}

					if (_currentReply.getOption() == ServerReply.TYPE.PROGRAM.STOPPED) {
						synchronized (_messages) {
							_messages.add("Выполение программы стирки прервано.");
						}
					}

					if (_currentReply.getOption() == ServerReply.TYPE.PROGRAM.SAVED) {
						synchronized (_messages) {
							//do nothing
						}
					}

					if (_currentReply.getOption() == ServerReply.TYPE.PROGRAM.INFO) {
						WasherProgram program = new WasherProgram();
						program.fromByteArray(_currentReply.getData());

						for (int i = 0; i < program.getStages().size(); ++i) {
							System.out.println(program.getStages().get(i).toString());
						}

						if (program.isValid()) {
							synchronized (_programs) {

								if (!_programs.contains(program)) {
									_programs.add(program);
								}
							}
						}

						return;
					}
				}

				//OTHER
				if (_currentReply.getType() == ServerReply.TYPE.OTHER.getValue()) {

					if (_currentReply.getOption() == ServerReply.TYPE.OTHER.CLIENT_NUMBER) {
						_clientID = ByteBuffer.wrap(_currentReply.getData()).getInt();
					}

					return;
				}
			}
		}
	}


	/**
	 * Connect to the first available server and test if it's a "Washer Controller Program"
	 * Not completely and correctly implemented.
	 * @param port
	 * @return
	 */
	private boolean tryConnectIPv4(int port) {
		try {
			String currentAddress = Utility.getIPAddress(true);
			String[] values = currentAddress.split(Pattern.quote("."));
			for (int i = 1; i < 254; ++i) {
				Client testClient = new Client(values[0] + "." + values[1] + "." + values[2] + "." + Integer.toString(i), port);

				//TODO
			}

			return true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}


	/**
	 * Add command to queue of commands.
	 * @param str - command, represented as string.
	 * @return true if command has been parsed successfully and will be send by client, false if not.
	 */
	private boolean addCommandString(String str) {
		if (str == null) {
			return false;
		}

		if (str.isEmpty()) {
			return false ;
		}

		ClientCommand command = new ClientCommand(str);

		if (command.isValid()) {
			_client.addCommand(command);

			return true;
		}

		return false;
	}

	/**
	 * Send command to washer machine. Throws corresponding exception if any error occurs.
	 * @param str - client command, represented as string.
	 */
	public void addCommand(String str) throws IOException, IllegalAccessException {
		if (!isConnected()) {
			throw new IOException("Нет подключения к стиральной машине.");
		}

		if (!addCommandString(str)) {
			throw new IllegalAccessException("Неверная командая.");
		}
	}


	/**
	 * Send command to washer machine. Throws corresponding exception if any error occurs.
	 * @param command - client command.
	 */
	public void addCommand(ClientCommand command) throws IOException, IllegalAccessException {
		if (!isConnected()) {
			throw new IOException("Нет подключения к стиральной машине.");
		}

		if (command == null || !command.isValid()) {
			throw new IllegalAccessException("Неверная командая.");
		}

		_client.addCommand(command);
	}


	/**
	 * Holds arguments for "void switchOutput(SwitchOutputArgs output)" method.
	 */
	public static enum RequestOutputSwitchArgs {
		out_brake,
		out_electricalHeat,
		out_spin2,
		out_spin1,
		out_engineRotateL,
		out_engineRotateR,
		out_deceleration,
		out_steamHeat,
		out_lockDirtyDoor,
		out_lockCleanDoor,
		out_waterValve,
		out_tray1,
		out_tray2,
		out_tray3,
		out_lockup,
		out_sink
	}


	/**
	 * Send command to invert current value of specified output of washer machine.
	 * @param output - specifies output that needs to be inverted.
	 * @return true on success, false on failure.
	 */
	public boolean requestOutputSwitch(RequestOutputSwitchArgs output) {
		if (!_client.isConnected()) {
			return false;
		}

		byte[] value = new byte[1];
		byte option = 0;

		switch (output) {
		case out_brake:
			value[0] = (byte)(!_currentState.isOutBrake() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_BRAKE;
			break;
		case out_electricalHeat:
			value[0] = (byte)(!_currentState.isOutElectricalHeat() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_ELECTRICAL_HEAT;
			break;
		case out_spin2:
			value[0] = (byte)(!_currentState.isOutSpin2() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_SPIN2;
			break;
		case out_spin1:
			value[0] = (byte)(!_currentState.isOutSpin1() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_SPIN1;
			break;
		case out_engineRotateL:
			value[0] = (byte)(!_currentState.isOutEngineRotationL() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_ENGINE_ROTATE_L;
			break;
		case out_engineRotateR:
			value[0] = (byte)(!_currentState.isOutEngineRotationR() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_ENGINE_ROTATE_R;
			break;
		case out_deceleration:
			value[0] = (byte)(!_currentState.isOutDeceleration() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_DECELERATION;
			break;
		case out_steamHeat:
			value[0] = (byte)(!_currentState.isOutSteamHeat() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_STEAM_HEAT;
			break;
		case out_lockDirtyDoor:
			value[0] = (byte)(!_currentState.isOutPower() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_POWER;
			break;
		case out_lockCleanDoor:
			value[0] = (byte)(!_currentState.isOutUnlockDoor() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_UNLOCK_DOOR;
			break;
		case out_waterValve:
			value[0] = (byte)(!_currentState.isOutWaterValve() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_WATER_VALVE;
			break;
		case out_tray1:
			value[0] = (byte)(!_currentState.isOutTray1() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_TRAY1;
			break;
		case out_tray2:
			value[0] = (byte)(!_currentState.isOutTray2() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_TRAY2;
			break;
		case out_tray3:
			value[0] = (byte)(!_currentState.isOutTray3() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_TRAY3;
			break;
		case out_lockup:
			value[0] = (byte)(!_currentState.isOutLockUp() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_LOCKUP;
			break;
		case out_sink:
			value[0] = (byte)(!_currentState.isOutSink() ? 1 : 0);
			option = ClientCommand.TYPE.SET.OUT_SINK;
			break;
		}

		ClientCommand command = new ClientCommand(ClientCommand.TYPE.SET.getValue(), option, value);

		if (command.isValid() && _client.isConnected()) {
			_client.addCommand(command);
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 * Send request to update info about washing programs. Server should respond with reply (replies) that contain info about washing programs.
	 * @return true on success, false on failure.
	 */
	public boolean requestProgramsUpdate() {
		ClientCommand command = new ClientCommand(ClientCommand.TYPE.GET.getValue(), ClientCommand.TYPE.GET.PROGRAM_INFO, new byte[]{0});

		if (_client.isConnected()) {

			_client.addCommand(command);
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 * Send command to start program execution.
	 * @id - id of the program that will be started.
	 * @return true if command was successfully send, false if not.
	 */
	public boolean requestProgramStart(int id) {
		return requestProgramStart(id, 0, 0);
	}


	/**
	 * Send command to start program execution.
	 * @id - id of the program that will be started.
	 * @return true if command was successfully send, false if not.
	 * @weight - for statistics
	 * @clientID - for statistics
	 */
	public boolean requestProgramStart(int id, int weight, int clientID) {
		try {
			synchronized (_currentState) {
				if (_currentState.isExecuteProgram()) {
					return false;
				}
			}

			if (weight < 0) {
				return false;
			}


			byte type = ClientCommand.TYPE.EXECUTE.getValue();
			byte option = ClientCommand.TYPE.EXECUTE.PROGRAM_START;
			ByteBuffer additional = ByteBuffer.allocate(12); //3 integers = 12 bytes
			additional.putInt(id);
			additional.putInt(weight);
			additional.putInt(clientID);


			ClientCommand command = new ClientCommand(type, option, additional.array());

			_client.addCommand(command);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}


	/**
	 * Send command to abort program execution.
	 * @return true if command was successfully send, false if not.
	 */
	public boolean requestProgramStop() {
		try {
			synchronized (_currentState) {
				if (!_currentState.isExecuteProgram()) {
					return false;
				}
			}

			byte type = ClientCommand.TYPE.EXECUTE.getValue();
			byte option = ClientCommand.TYPE.EXECUTE.PROGRAM_STOP;
			ClientCommand command = new ClientCommand(type, option, new byte[1]);

			_client.addCommand(command);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}


	/**
	 *
	 */
	public boolean requestProgramResume() {
		try {
			synchronized (_currentState) {
				if (_currentState.isExecuteProgram() || _currentState.isExecuteEmergency() || _currentState.isInEmergency() || !isProgramResumeAvailable()) {
					return false;
				}
			}

			byte type = ClientCommand.TYPE.EXECUTE.getValue();
			byte option = ClientCommand.TYPE.EXECUTE.PROGRAM_RESUME;
			ClientCommand command = new ClientCommand(type, option, new byte[1]);

			_client.addCommand(command);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}


	/**
	 * Returns object that contains values that describe current state of washer machine.
	 */
	public synchronized WasherState getState() {
		return _currentState;
	}


	/**
	 * Partially thread-safe.
	 * @return reference to instance of a class that holds data about washing programs.
	 */
	public synchronized ArrayList<WasherProgram> getPrograms() {
		return _programs;
	}


	/**
	 * Partially thread-safe.
	 * @return reference to ArrayList of frequently used hosts.
	 */
	public synchronized ArrayList<String> getFrequentlyUsedHosts() {
		return _hostnames;
	}


	/**
	 * Thread-safe way to determine amount of washing programs that were successfully received.
	 * @return
	 */
	public int getProgramsAmount() {
		int size;

		synchronized (_programs) {
			size = _programs.size();
		}

		return size;
	}


	/**
	 * Get description of error that happened on server (washing machine).
	 * @return String with the description or null if no data available.
	 */
	public synchronized String getError() {
		return _errors.poll();
	}


	/**
	 * Get message from server (washing machine).
	 * @return String with the description or null if no data available.
	 */
	public synchronized String getMessage() {
		return _messages.poll();
	}


	/**
	 * Partially thread-safe.
	 * @return true if connection has been established and is alive now, false if not.
	 */
	public synchronized boolean isConnected() {
		return _client.isConnected();
	}


	/**
	 * Indicates emergency
	 */
	public synchronized boolean isEmergency() {
		boolean result;
		synchronized (_currentState) {
			result = _currentState.isInEmergency();
		}

		return result;
	}


	/**
	 * Allow
	 * @return true if resume command is available, false in not.
	 */
	public synchronized boolean isProgramResumeAvailable() {
		boolean result;

		synchronized (_currentState) {
			result = (!_currentState.isExecuteProgram() && _currentState.getProgramCurrent() != 0 && _currentState.getProgramStageCurrent() != 0);
		}

		return result;
	}


	/**
	 * Set address of washer machine.
	 * If provided values are invalid, they will not be saved.
	 * @param hostname - IP address or hostname.
	 * @param port
	 * @return true if arguments are valid, false if not.
	 */
	public boolean setAddress(String hostname, int port) {
		try {
			if (hostname == null) {
				return false;
			}

			_client.setSocketAddress(hostname, port);

			_hostname = hostname;
			_port = port;

			return true;
		} catch (Exception ex) {
			return false;
		}
	}


	/**
	 * @return current address of the server (IP-address or hostname).
	 */
	public String getHost() {
		return new String(_hostname);
	}


	/**
	 * @return current port number of the server.
	 */
	public int getPort() {
		return _port;
	}


	/**
	 * Unique ID that has been assigned to currentConnection by server.
	 * @return 0 if connection is not established, not 0 if connection is established.
	 */
	public int getClientID() {
		if (isConnected()) {
			return _clientID;
		} else {
			return 0;
		}
	}


	/**
	 * Enable communication controller.
	 * This method will block.
	 * Also tries to update programs.
	 */
	public void start() {
		_connectionThread = new Thread(_connectionThreadRunnable);
		_connectionThread.start();

		_processRepliesThread = new Thread(_processRepliesThreadRunnable);
		_processRepliesThread.start();
	}


	/**
	 * Disable communication controller.
	 * This method will not block.
	 */
	public void stop() {
		_isShutdown = true;
		_client.stop();

		try {
			_connectionThread.join();
			_processRepliesThread.join();
			_isShutdown = false;
		}
		catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}


	/**
	 * Restart connection. Clear all buffers.
	 * This method will block.
	 */
	public void restart() {
		stop();
		_errors.clear();
		_currentCommand = null;
		_currentReply = null;
		start();
	}

}
