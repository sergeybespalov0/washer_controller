package com.mygdx.game.android.Network;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ServerReply {

	public static final class TYPE {

		/**
		 * Reply to corresponding GET command.
		 * Option stores value type and data stores value itself.
		 */
		public static final class GET {

			static final byte ACTION_EMERGENCY = 1;
			static final byte ACTION_PAUSE = 2;
			static final byte ACTION_PROGRAM_EXECUTE = 3;

			static final byte OUT_BRAKE = 4;
			static final byte OUT_ELECTRICAL_HEAT = 5;
			static final byte OUT_SPIN2 = 6;
			static final byte OUT_SPIN1 = 7;
			static final byte OUT_ENGINE_ROTATE_L = 8;
			static final byte OUT_ENGINE_ROTATE_R = 9;
			static final byte OUT_DECELERATION = 10;
			static final byte OUT_STEAM_HEAT = 11;

			static final byte OUT_POWER = 12;
			static final byte OUT_UNLOCK_DOOR = 13;
			static final byte OUT_WATER_VALVE = 14;
			static final byte OUT_TRAY1 = 15;
			static final byte OUT_TRAY2 = 16;
			static final byte OUT_TRAY3 = 17;
			static final byte OUT_LOCKUP = 18;
			static final byte OUT_SINK = 19;

			static final byte IN_EMERGENCY = 20;
			static final byte IN_SHOCK = 21;
			static final byte IN_DIRTY_UNLOCKED = 22;
			static final byte IN_DIRTY_OPEN = 23;
			static final byte IN_CLEAN_UNLOCKED = 24;
			static final byte IN_CLEAN_OPEN = 25;
			static final byte IN_ROTATION_TURN_OFF_SWITCH = 26;
			static final byte IN_WATER_CONSUMPTION_SENSOR = 27;

			static final byte IN_BEATS = 28;
			static final byte IN_WATERLEVEL0 = 29;
			static final byte IN_WATERLEVEL1 = 30;
			static final byte IN_WATERLEVEL2 = 31;
			static final byte IN_WATERLEVEL3 = 32;
			static final byte IN_WATERLEVEL4 = 33;
			// RESERVED 34
			static final byte IN_EXTRACTION_POSITION = 35;

			static final byte OTHER_TEMPERATURE_CURRENT = 36;
			static final byte OTHER_TEMPERATURE_MAXIMUM = 37;
			static final byte OTHER_WATER_CONSUMPTION = 38;
			static final byte OTHER_DRUM_RPM = 39;

			static final byte PROGRAM_STAGE_CURRENT = 40;
			static final byte PROGRAM_STAGE_MAXIMUM = 41;
			static final byte PROGRAM_CURRENT = 42;

			static final byte PROGRAM_INFO = 43;

			/**
			 * Ask for the current id that has been assigned by server to this client.
			 */
			public static final byte CLIENT_NUMBER = 44;

			public static byte getValue() {
				return 1;
			}

			public static boolean isValid(byte option) {
				return (option >= ACTION_EMERGENCY && option <= PROGRAM_CURRENT);
			}
		}

		/**
		 * Full description of current state of washer machine.
		 */
		public static final class STATE {

			public static byte getValue() {
				return 2;
			}

			public static boolean isValid(byte option) {
				return true;
			}
		}

		/**
		 * Description of one wash program.
		 */
		public static final class PROGRAM {

			/**
			 * Program execution successfully finished.
			 */
			public static final byte FINISHED = 1;

			/**
			 * Data execution has been aborted by user.
			 */
			public static final byte STOPPED = 2;

			/**
			 * NOT IMPLEMENTED
			 */
			public static final byte SAVED = 3;

			/**
			 * Describes one washing program
			 */
			public static final byte INFO = 4;

			public static byte getValue() {
				return 3;
			}

			public static boolean isValid(byte option) {
				return (option >= FINISHED && option <= INFO);
			}
		}

		/**
		 * Signals that an error has occurred.
		 * Option stores error type and data stores error description.
		 */
		public static final class ERROR {

			/**
			 * Received command is completely not recognized.
			 */
			public static final byte WRONG_COMMAND = 1;

			/**
			 * Data field of received command is invalid.
			 */
			public static final byte WRONG_VALUE = 2;

			/**
			 * Issues in I2C interface communication (I/O).
			 */
			public static final byte IO_I2C = 3;

			/**
			 * Issues in serial communication with (Electric Current Measuring device).
			 */
			public static final byte IO_TTL = 4;

			/**
			 * Issues in 1-Wire interface communication (Temperature sensor).
			 */
			public static final byte IO_1WIRE = 5;

			/**
			 * Invalid configuration file.
			 */
			public static final byte WRONG_CONFIG = 6;

			public static byte getValue() {
				return 4;
			}

			public static boolean isValid(byte option) {
				return (option >= WRONG_COMMAND && option <= WRONG_CONFIG);
			}
		}

		/**
		 * Any other data that is accepted as valid.
		 * Implement in case of unexpected upgrades/modifications of current software.
		 * Option and data are both ignored.
		 */
		public static final class OTHER {

			/**
			 * NOT IMPLEMENTED
			 */
			public static final byte PING_OK = 1;

			/**
			 * Indicates the current id that has been assigned by server to this client.
			 */
			public static final byte CLIENT_NUMBER = 2;

			public static byte getValue() {
				return 5;
			}

			public static boolean isValid(byte option) {
				return true;
			}
		}

		public static boolean isValid(byte type) {
			return (type >= STATE.getValue() && type <= OTHER.getValue());
		}
	}

	private byte _type;
	private byte _option;
	private byte[] _data;

	private boolean _isValid;

	public ServerReply() {
		_type = 0;
		_option = 0;
		_data = new byte[0];

		_isValid = false;
	}

	public ServerReply(byte type, byte option, byte[] data) {
		_type = type;
		_option = option;
		_data = data;

		_isValid = check();
	}


	/**
	 * Create new instance of ServerReply using data, provided by ByteBuffer.
	 * @param buffer - instance of ByteBuffer with the data inside.
	 */
	public ServerReply(ByteBuffer buffer) {
		if (buffer == null || buffer.limit() < 3 || buffer.capacity() < 3) {
			_type = 0;
			_option = 0;
			_data = new byte[0];

			_isValid = false;
		}

		buffer.rewind();

		_type = buffer.get();
		_option = buffer.get();
		_data = new byte[buffer.limit() - 2];

		for (int i = 0; i < _data.length; i++) {
			_data[i] = buffer.get();
		}

		_isValid = check();
	}

	/**
	 * Create new instance of ServerReply using by parsing data from byte array.
	 * @param buffer - instance of byte array with the data inside.
	 */
	public ServerReply(byte[] buffer) {
		if (buffer == null || buffer.length < 3) {
			_type = 0;
			_option = 0;
			_data = new byte[0];

			_isValid = false;
		}

		_type = buffer[0];
		_option = buffer[1];

		_data = new byte[buffer.length - 2];
		for (int i = 2; i < buffer.length; ++i) {
			_data[i] = buffer[i];
		}
	}

	public byte getType() {
		return _type;
	}

	public byte getOption() {
		return _option;
	}

	public byte[] getData() {
		return _data;
	}

	public boolean isValid() {
		return _isValid;
	}


	/**
	 * Converts data to raw byte array.
	 * @return new instance of byte array holding raw data or server reply.
	 */
	public byte[] toArray() {
		byte[] array = new byte[2 + _data.length];

		array[0] = _type;
		array[1] = _option;
		for (int i = 2; i < _data.length; ++i) {
			array[i] = _data[i];
		}

		return array;
	}

	/**
	 * @return new instance of ByteBuffer that stores reply.
	 * Capacity and limit are both equal to the length of backing byte array.
	 */
	public ByteBuffer toByteBuffer() {
		ByteBuffer buffer = ByteBuffer.wrap(toArray());

		return buffer;
	}

	/**
	 * Represent data as array of bytes, written in binary form and convert this representation to string.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("[");
		sb.append(Integer.toBinaryString((_type & 0xFF) + 0x100).substring(1));
		sb.append("]");

		sb.append("[");
		sb.append(Integer.toBinaryString((_option & 0xFF) + 0x100).substring(1));
		sb.append("]");

		for (int i = 0; i < _data.length; ++i) {
			sb.append("[");
			sb.append(Integer.toBinaryString((_data[i] & 0xFF) + 0x100).substring(1));
			sb.append("]");
		}

		return sb.toString();
	}


	@Override
	public boolean equals(Object obj) {
		try {
			ServerReply reply = (ServerReply) obj;

			if (_type != reply._type) {
				return false;
			}

			if (_option != reply._option) {
				return false;
			}

			if (_data.length != reply._data.length) {
				return false;
			}

			for (int i = 0; i < _data.length; ++i) {
				if (_data[i] != reply._data[i]) {
					return false;
				}
			}

			if (_isValid != reply._isValid) {
				return false;
			}
		}
		catch (Exception ex) {
			return false;
		}

		return true;
	}


	/**
	 * Check if server reply is correct and makes any sense.
	 * Data field is not checked.
	 * @return true if correct, false if not.
	 */
	private boolean check() {
		/*
		if (!TYPE.isValid(_type)) {
			return false;
		}

		if (_data == null || _data.length < 1) {
			return false;
		}

		if (_type == TYPE.GET.getValue()) {
			if (!TYPE.GET.isValid(_option)) {
				return false;
			}
		}

		if (_type == TYPE.STATE.getValue()) {
			if (!TYPE.STATE.isValid(_option)) {
				return false;
			}
		}

		if (_type == TYPE.PROGRAM.getValue()) {
			if (!TYPE.PROGRAM.isValid(_option)) {
				return false;
			}
		}

		if (_type == TYPE.ERROR.getValue()) {
			if (!TYPE.ERROR.isValid(_option)) {
				return false;
			}
		}

		if (_type == TYPE.OTHER.getValue()) {
			if (!TYPE.OTHER.isValid(_option)) {
				return false;
			}
		}
		*/

		return true;
	}


	/**
	 * Assumes that "data" byte array holds UTF-8 encoded message and converts correspondingly.
	 * @return String with the text message or null if UTF-8 encoding is not supported.
	 */
	public String getDataAsUTF8() {
		try {
			return new String(_data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}


	/**
	 * Assumes that "data" byte array holds US-ASCII encoded message and converts correspondingly.
	 * @return String with the text message or null if US-ASCII encoding is not supported.
	 */
	public String getDataAsASCII() {
		try {
			return new String(_data, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
