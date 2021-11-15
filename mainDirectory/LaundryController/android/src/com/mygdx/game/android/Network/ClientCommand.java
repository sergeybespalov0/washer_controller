package com.mygdx.game.android.Network;

import com.mygdx.game.android.Utility.Utility;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;


public class ClientCommand {

	/**
	 * Contains constants that represent specific commands.
	 * TYPE stores command values, it's subclasses store corresponding options.
	 */
	public static class TYPE {

		/**
		 * GET type allows client to retrieve value of the variable, specified in option.
		 * Additional data will be ignored.
		 */
		public static class GET {
			public static final byte ACTION_EMERGENCY = 1;
			public static final byte ACTION_PAUSE = 2;
			public static final byte ACTION_PROGRAM_EXECUTE = 3;

			public static final byte OUT_BRAKE = 4;
			public static final byte OUT_ELECTRICAL_HEAT = 5;
			public static final byte OUT_SPIN2 = 6;
			public static final byte OUT_SPIN1 = 7;
			public static final byte OUT_ENGINE_ROTATE_L = 8;
			public static final byte OUT_ENGINE_ROTATE_R = 9;
			public static final byte OUT_DECELERATION = 10;
			public static final byte OUT_STEAM_HEAT = 11;

			public static final byte OUT_POWER = 12;
			public static final byte OUT_UNLOCK_DOOR = 13;
			public static final byte OUT_WATER_VALVE = 14;
			public static final byte OUT_TRAY1 = 15;
			public static final byte OUT_TRAY2 = 16;
			public static final byte OUT_TRAY3 = 17;
			public static final byte OUT_LOCKUP = 18;
			public static final byte OUT_SINK = 19;

			public static final byte IN_EMERGENCY = 20;
			public static final byte IN_SHOCK = 21;
			public static final byte IN_DIRTY_UNLOCKED = 22;
			public static final byte IN_DIRTY_OPEN = 23;
			public static final byte IN_CLEAN_UNLOCKED = 24;
			public static final byte IN_CLEAN_OPEN = 25;
			public static final byte IN_ROTATION_TURN_OFF_SWITCH = 26;
			public static final byte IN_WATER_CONSUMPTION_SENSOR = 27;

			public static final byte IN_BEATS = 28;
			public static final byte IN_WATERLEVEL0 = 29;
			public static final byte IN_WATERLEVEL1 = 30;
			public static final byte IN_WATERLEVEL2 = 31;
			public static final byte IN_WATERLEVEL3 = 32;
			public static final byte IN_WATERLEVEL4 = 33;
			//RESERVED 34
			public static final byte IN_EXTRACTION_POSITION = 35;

			public static final byte OTHER_TEMPERATURE_CURRENT = 36;
			public static final byte OTHER_TEMPERATURE_MAXIMUM = 37;
			public static final byte OTHER_WATER_CONSUMPTION = 38;
			public static final byte OTHER_DRUM_RPM = 39;

			public static final byte PROGRAM_STAGE_CURRENT = 40;
			public static final byte PROGRAM_STAGE_MAXIMUM = 41;
			public static final byte PROGRAM_CURRENT = 42;

			public static final byte PROGRAM_INFO = 43;

			/**
			 * Indicates the current id that has been assigned by server to this client.
			 */
			public static final byte CLIENT_NUMBER = 44;

			public static byte getValue() {
				return 1;
			}

			public static boolean isValid(byte option) {
				return (option >= ACTION_EMERGENCY && option <= CLIENT_NUMBER);
			}

			/**
			 * Convert string to corresponding option.
			 * @param option - value in string form
			 * @return corresponding byte value or 0 if this value is not found
			 */
			public static byte parseOption(String option) {
				String str = option.toUpperCase();

				if (str.equals("ACTION_EMERGENCY") || str.equals(Byte.toString(ACTION_EMERGENCY))) {
					return ACTION_EMERGENCY;
				}

				if (str.equals("ACTION_PAUSE") || str.equals(Byte.toString(ACTION_PAUSE))) {
					return ACTION_PAUSE;
				}

				if (str.equals("ACTION_PROGRAM_EXECUTE") || str.equals(Byte.toString(ACTION_PROGRAM_EXECUTE))) {
					return ACTION_PROGRAM_EXECUTE;
				}


				if (str.equals("OUT_BRAKE") || str.equals(Byte.toString(OUT_BRAKE))) {
					return OUT_BRAKE;
				}

				if (str.equals("OUT_ELECTRICAL_HEAT") || str.equals(Byte.toString(OUT_ELECTRICAL_HEAT))) {
					return OUT_ELECTRICAL_HEAT;
				}

				if (str.equals("OUT_SPIN2") || str.equals(Byte.toString(OUT_SPIN2))) {
					return OUT_SPIN2;
				}

				if (str.equals("OUT_SPIN1") || str.equals(Byte.toString(OUT_SPIN1))) {
					return OUT_SPIN1;
				}

				if (str.equals("OUT_ENGINE_ROTATE_L") || str.equals(Byte.toString(OUT_ENGINE_ROTATE_L))) {
					return OUT_ENGINE_ROTATE_L;
				}

				if (str.equals("OUT_ENGINE_ROTATE_R") || str.equals(Byte.toString(OUT_ENGINE_ROTATE_R))) {
					return OUT_ENGINE_ROTATE_R;
				}

				if (str.equals("OUT_DECELERATION") || str.equals(Byte.toString(OUT_DECELERATION))) {
					return OUT_DECELERATION;
				}

				if (str.equals("OUT_STEAM_HEAT") || str.equals(Byte.toString(OUT_STEAM_HEAT))) {
					return OUT_STEAM_HEAT;
				}

				if (str.equals("OUT_POWER") || str.equals(Byte.toString(OUT_POWER))) {
					return OUT_POWER;
				}

				if (str.equals("OUT_UNLOCK_DOOR") || str.equals(Byte.toString(OUT_UNLOCK_DOOR))) {
					return OUT_UNLOCK_DOOR;
				}

				if (str.equals("OUT_WATER_VALVE") || str.equals(Byte.toString(OUT_WATER_VALVE))) {
					return OUT_WATER_VALVE;
				}

				if (str.equals("OUT_TRAY1") || str.equals(Byte.toString(OUT_TRAY1))) {
					return OUT_TRAY1;
				}

				if (str.equals("OUT_TRAY2") || str.equals(Byte.toString(OUT_TRAY2))) {
					return OUT_TRAY2;
				}

				if (str.equals("OUT_TRAY3") || str.equals(Byte.toString(OUT_TRAY3))) {
					return OUT_TRAY3;
				}

				if (str.equals("OUT_LOCKUP") || str.equals(Byte.toString(OUT_LOCKUP))) {
					return OUT_LOCKUP;
				}

				if (str.equals("OUT_SINK") || str.equals(Byte.toString(OUT_SINK))) {
					return OUT_SINK;
				}


				if (str.equals("IN_EMERGENCY") || str.equals(Byte.toString(IN_EMERGENCY))) {
					return IN_EMERGENCY;
				}

				if (str.equals("IN_SHOCK") || str.equals(Byte.toString(IN_SHOCK))) {
					return IN_SHOCK;
				}

				if (str.equals("IN_DIRTY_UNLOCKED") || str.equals(Byte.toString(IN_DIRTY_UNLOCKED))) {
					return IN_DIRTY_UNLOCKED;
				}

				if (str.equals("IN_DIRTY_OPEN") || str.equals(Byte.toString(IN_DIRTY_OPEN))) {
					return IN_DIRTY_OPEN;
				}

				if (str.equals("IN_CLEAN_UNLOCKED") || str.equals(Byte.toString(IN_CLEAN_UNLOCKED))) {
					return IN_CLEAN_UNLOCKED;
				}

				if (str.equals("IN_CLEAN_OPEN") || str.equals(Byte.toString(IN_CLEAN_OPEN))) {
					return IN_CLEAN_OPEN;
				}

				if (str.equals("IN_ROTATION_TURN_OFF_SWITCH") || str.equals(Byte.toString(IN_ROTATION_TURN_OFF_SWITCH))) {
					return IN_ROTATION_TURN_OFF_SWITCH;
				}

				if (str.equals("IN_WATER_CONSUMPTION_SENSOR") || str.equals(Byte.toString(IN_WATER_CONSUMPTION_SENSOR))) {
					return IN_WATER_CONSUMPTION_SENSOR;
				}

				if (str.equals("IN_BEATS") || str.equals(Byte.toString(IN_BEATS))) {
					return IN_BEATS;
				}

				if (str.equals("IN_WATERLEVEL0") || str.equals(Byte.toString(IN_WATERLEVEL0))) {
					return IN_WATERLEVEL0;
				}

				if (str.equals("IN_WATERLEVEL1") || str.equals(Byte.toString(IN_WATERLEVEL1))) {
					return IN_WATERLEVEL1;
				}

				if (str.equals("IN_WATERLEVEL2") || str.equals(Byte.toString(IN_WATERLEVEL2))) {
					return IN_WATERLEVEL2;
				}

				if (str.equals("IN_WATERLEVEL3") || str.equals(Byte.toString(IN_WATERLEVEL3))) {
					return IN_WATERLEVEL3;
				}

				if (str.equals("IN_WATERLEVEL4") || str.equals(Byte.toString(IN_WATERLEVEL4))) {
					return IN_WATERLEVEL4;
				}

				if (str.equals("IN_EXTRACTION_POSITION") || str.equals(Byte.toString(IN_EXTRACTION_POSITION))) {
					return IN_EXTRACTION_POSITION;
				}


				if (str.equals("OTHER_TEMPERATURE_CURRENT") || str.equals(Byte.toString(OTHER_TEMPERATURE_CURRENT))) {
					return OTHER_TEMPERATURE_CURRENT;
				}

				if (str.equals("OTHER_TEMPERATURE_MAXIMUM") || str.equals(Byte.toString(OTHER_TEMPERATURE_MAXIMUM))) {
					return OTHER_TEMPERATURE_MAXIMUM;
				}

				if (str.equals("OTHER_WATER_CONSUMPTION") || str.equals(Byte.toString(OTHER_WATER_CONSUMPTION))) {
					return OTHER_WATER_CONSUMPTION;
				}

				if (str.equals("OTHER_DRUM_RPM") || str.equals(Byte.toString(OTHER_DRUM_RPM))) {
					return OTHER_DRUM_RPM;
				}


				if (str.equals("PROGRAM_STAGE_CURRENT") || str.equals(Byte.toString(PROGRAM_STAGE_CURRENT))) {
					return PROGRAM_STAGE_CURRENT;
				}

				if (str.equals("PROGRAM_STAGE_MAXIMUM") || str.equals(Byte.toString(PROGRAM_STAGE_MAXIMUM))) {
					return PROGRAM_STAGE_MAXIMUM;
				}

				if (str.equals("PROGRAM_CURRENT") || str.equals(Byte.toString(PROGRAM_CURRENT))) {
					return PROGRAM_CURRENT;
				}

				if (str.equals("PROGRAM_INFO") || str.equals(Byte.toString(PROGRAM_INFO))) {
					return PROGRAM_INFO;
				}

				if (str.equals("CLIENT_NUMBER") || str.equals(Byte.toString(CLIENT_NUMBER))) {
					return CLIENT_NUMBER;
				}

				return 0;
			}
		}

		/**
		 * SET type allows client to set value of the variables, specified in option.
		 * Data field should contain boolean value (0/1, yes/no, true/false).
		 */
		public static class SET {

			public static final byte OUT_BRAKE = 1;
			public static final byte OUT_ELECTRICAL_HEAT = 2;
			public static final byte OUT_SPIN2 = 3;
			public static final byte OUT_SPIN1 = 4;
			public static final byte OUT_ENGINE_ROTATE_L = 5;
			public static final byte OUT_ENGINE_ROTATE_R = 6;
			public static final byte OUT_DECELERATION = 7;
			public static final byte OUT_STEAM_HEAT = 8;

			public static final byte OUT_POWER = 9;
			public static final byte OUT_UNLOCK_DOOR = 10;
			public static final byte OUT_WATER_VALVE = 11;
			public static final byte OUT_TRAY1 = 12;
			public static final byte OUT_TRAY2 = 13;
			public static final byte OUT_TRAY3 = 14;
			public static final byte OUT_LOCKUP = 15;
			public static final byte OUT_SINK = 16;

			public static byte getValue() {
				return 2;
			}

			public static boolean isValid(byte option) {
				return (option >= OUT_BRAKE && option <= OUT_SINK);
			}

			/**
			 * Convert string to corresponding option.
			 * @param option - value in string form
			 * @return corresponding byte value or 0 if this value is not found
			 */
			public static byte parseOption(String option) {
				String str = option.toUpperCase();

				if (str.equals("OUT_BRAKE") || str.equals(Byte.toString(OUT_BRAKE))) {
					return OUT_BRAKE;
				}

				if (str.equals("OUT_ELECTRICAL_HEAT") || str.equals(Byte.toString(OUT_ELECTRICAL_HEAT))) {
					return OUT_ELECTRICAL_HEAT;
				}

				if (str.equals("OUT_SPIN2") || str.equals(Byte.toString(OUT_SPIN2))) {
					return OUT_SPIN2;
				}

				if (str.equals("OUT_SPIN1") || str.equals(Byte.toString(OUT_SPIN1))) {
					return OUT_SPIN1;
				}

				if (str.equals("OUT_ENGINE_ROTATE_L") || str.equals(Byte.toString(OUT_ENGINE_ROTATE_L))) {
					return OUT_ENGINE_ROTATE_L;
				}

				if (str.equals("OUT_ENGINE_ROTATE_R") || str.equals(Byte.toString(OUT_ENGINE_ROTATE_R))) {
					return OUT_ENGINE_ROTATE_R;
				}

				if (str.equals("OUT_DECELERATION") || str.equals(Byte.toString(OUT_DECELERATION))) {
					return OUT_DECELERATION;
				}

				if (str.equals("OUT_STEAM_HEAT") || str.equals(Byte.toString(OUT_STEAM_HEAT))) {
					return OUT_STEAM_HEAT;
				}

				if (str.equals("OUT_POWER") || str.equals(Byte.toString(OUT_POWER))) {
					return OUT_POWER;
				}

				if (str.equals("OUT_UNLOCK_DOOR") || str.equals(Byte.toString(OUT_UNLOCK_DOOR))) {
					return OUT_UNLOCK_DOOR;
				}

				if (str.equals("OUT_WATER_VALVE") || str.equals(Byte.toString(OUT_WATER_VALVE))) {
					return OUT_WATER_VALVE;
				}

				if (str.equals("OUT_TRAY1") || str.equals(Byte.toString(OUT_TRAY1))) {
					return OUT_TRAY1;
				}

				if (str.equals("OUT_TRAY2") || str.equals(Byte.toString(OUT_TRAY2))) {
					return OUT_TRAY2;
				}

				if (str.equals("OUT_TRAY3") || str.equals(Byte.toString(OUT_TRAY3))) {
					return OUT_TRAY3;
				}

				if (str.equals("OUT_LOCKUP") || str.equals(Byte.toString(OUT_LOCKUP))) {
					return OUT_LOCKUP;
				}

				if (str.equals("OUT_SINK") || str.equals(Byte.toString(OUT_SINK))) {
					return OUT_SINK;
				}

				return 0;
			}
		}

		/**
		 * EXECUTE type allows to start different tasks and processes. Option specifies exact task.
		 * Depending on the option, data may be used or ignored.
		 */
		public static class EXECUTE {

			public static final byte EMERGENCY = 1;
			public static final byte PROGRAM_START = 2;
			public static final byte PROGRAM_PAUSE = 3;
			public static final byte PROGRAM_STOP = 4;
			public static final byte PROGRAM_RESUME = 5;

			public static byte getValue() {
				return 3;
			}

			public static boolean isValid(byte option) {
				return (option >= EMERGENCY && option <= PROGRAM_RESUME);
			}

			/**
			 * Convert string to corresponding option.
			 * @param option - value in string form
			 * @return corresponding byte value or 0 if this value is not found
			 */
			public static byte parseOption(String option) {
				String str = option.toUpperCase();

				if (str.equals("EMERGENCY") || str.equals(Byte.toString(EMERGENCY))) {
					return EMERGENCY;
				}

				if (str.equals("PROGRAM_START") || str.equals(Byte.toString(PROGRAM_START))) {
					return PROGRAM_START;
				}

				if (str.equals("PROGRAM_PAUSE") || str.equals(Byte.toString(PROGRAM_PAUSE))) {
					return PROGRAM_PAUSE;
				}

				if (str.equals("PROGRAM_STOP") || str.equals(Byte.toString(PROGRAM_STOP))) {
					return PROGRAM_STOP;
				}

				if (str.equals("PROGRAM_RESUME") || str.equals(Byte.toString(PROGRAM_RESUME))) {
					return PROGRAM_RESUME;
				}

				return 0;
			}
		}

		public static boolean isValid(byte type) {
			return (type >= GET.getValue() && type <= EXECUTE.getValue());
		}

		public static byte parseType(String type) {
			String str = type.toUpperCase();

			if (str.equals("GET") || str.equals(Byte.toString(GET.getValue()))) {
				return GET.getValue();
			}

			if (str.equals("SET") || str.equals(Byte.toString(SET.getValue()))) {
				return SET.getValue();
			}

			if (str.equals("EXECUTE") || str.equals(Byte.toString(EXECUTE.getValue()))) {
				return EXECUTE.getValue();
			}

			return 0;
		}
	}

	private byte _type;
	private byte _option;
	private byte[] _data;

	private boolean _isValid;

	public ClientCommand() {
		_type = 0;
		_option = 0;
		_data = new byte[0];

		_isValid = false;
	}

	public ClientCommand(byte type, byte option, byte[] data) {
		_type = type;
		_option = option;
		_data = data;

		_isValid = check();
	}

	public ClientCommand(ByteBuffer buffer) {
		if (buffer == null || buffer.limit() < 3) {
			_type = 0;
			_option = 0;
			_data = new byte[0];

			_isValid = false;

			return;
		}

		_type = buffer.get();
		_option = buffer.get();
		_data = new byte[buffer.limit() - 2];
		buffer.get(_data, 2, _data.length);

		_isValid = check();
	}

	public ClientCommand(byte[] buffer) {
		if (buffer == null || buffer.length < 3) {
			_type = 0;
			_option = 0;
			_data = new byte[0];

			_isValid = false;

			return;
		}

		_type = buffer[0];
		_option = buffer[1];

		_data = new byte[buffer.length - 2];
		for (int i = 2; i < buffer.length; ++i) {
			_data[i] = buffer[i];
		}
	}

	public ClientCommand(String str) {
		if (parseString(str)) {
			_isValid = check();
		}
		else {
			_isValid = false;
		}

		debugPrint();
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


	public byte[] toArray() {
		byte[] array = new byte[2 + _data.length];

		array[0] = _type;
		array[1] = _option;
		for (int i = 0; i < _data.length; ++i) {
			array[i + 2] = _data[i];
		}

		return array;
	}


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
		if (obj == null) {
			return false;
		}

		try {
			ClientCommand command = (ClientCommand) obj;

			if (_type != command._type) {
				return false;
			}

			if (_option != command._option) {
				return false;
			}

			if (_data.length != command._data.length) {
				return false;
			}

			for (int i = 0; i < _data.length; ++i) {
				if (_data[i] != command._data[i]) {
					return false;
				}
			}

			if (_isValid != command._isValid) {
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

		if (_type == TYPE.SET.getValue()) {
			if (!TYPE.SET.isValid(_option)) {
				return false;
			}
		}

		if (_type == TYPE.EXECUTE.getValue()) {
			if (!TYPE.EXECUTE.isValid(_option)) {
				return false;
			}
		}

		return true;
	}


	private boolean parseString(String str) {
		try {

			String[] commandValues = str.split(";");

			//reset values
			if (commandValues.length != 3) {
				_type = 0;
				_option = 0;
				_data = new byte[0];

				_isValid = false;

				return false;
			}

			_type = TYPE.parseType(commandValues[0]);

			//default
			_data = new byte[1];
			_data[0] = 0;

			if (_type == TYPE.GET.getValue()) {

				_option = TYPE.GET.parseOption(commandValues[1]);

				return (_option != 0) ? true : false;
			}

			if (_type == TYPE.SET.getValue()) {

				_option = TYPE.SET.parseOption(commandValues[1]);

				if (_option != 0) {
					_data[0] = Utility.parseBooleanString(commandValues[2]);

					return true;
				} else {
					return false;
				}

			}

			if (_type == TYPE.EXECUTE.getValue()) {

				_option = TYPE.EXECUTE.parseOption(commandValues[1]);

				if (_option == TYPE.EXECUTE.EMERGENCY) {
					return true;
				}


				//program start relies on 3 additional value - program id, weight and client id
				if (_option == TYPE.EXECUTE.PROGRAM_START) {

					//split string to get additional values
					String[] parameters = commandValues[2].split(":");

					if (parameters.length != 3) {
						return false;
					}

					_data = new byte[12]; //allocate memory (3 int values -> 3*4=12 -> 12 bytes)
					ByteBuffer wrapper =  ByteBuffer.wrap(_data); //wrap to make life a bit easier

					//convert string values to respective integers and put them to array;
					wrapper.putInt(Integer.parseInt(parameters[0]));
					wrapper.putInt(Integer.parseInt(parameters[1]));
					wrapper.putInt(Integer.parseInt(parameters[2]));

					return true;
				}

				if (_option == TYPE.EXECUTE.PROGRAM_PAUSE) {
					return true;
				}

				if (_option == TYPE.EXECUTE.PROGRAM_STOP) {
					return true;
				}
			}

			return false;
		}
		catch (Exception ex) {
			_type = 0;
			_option = 0;
			_data = new byte[0];
			return false;
		}
	}


	/**
	 * Write command data to standard output.
	 */
	public void debugPrint() {
		System.out.print("[" + _type + "][" + _option + "]");

		for (int i = 0; i < _data.length; ++i) {
			System.out.print("[" + _data[i] + "]");
		}

		System.out.print("\n");
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
