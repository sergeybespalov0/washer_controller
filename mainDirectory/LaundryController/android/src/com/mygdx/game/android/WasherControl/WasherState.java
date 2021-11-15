package com.mygdx.game.android.WasherControl;

import java.nio.ByteBuffer;

public class WasherState {

	// execute

	private boolean _execute_emergency;
	private boolean _execute_pause;
	private boolean _execute_program;

	// outputs

	private boolean _out_brake;
	private boolean _out_electrical_heat;
	private boolean _out_spin2;
	private boolean _out_spin1;
	private boolean _out_engine_rotation_l;
	private boolean _out_engine_rotation_r;
	private boolean _out_deceleration;
	private boolean _out_steam_heat;

	private boolean _out_power;
	private boolean _out_unlock_door;
	private boolean _out_water_valve;
	private boolean _out_tray1;
	private boolean _out_tray2;
	private boolean _out_tray3;
	private boolean _out_lockup;
	private boolean _out_sink;

	// inputs

	private boolean _in_emergency;
	private boolean _in_shock;
	private boolean _in_dirty_unlocked;
	private boolean _in_dirty_open;
	private boolean _in_clean_unlocked;
	private boolean _in_clean_open;
	private boolean _in_rotation_turn_off_switch;
	private boolean _in_water_consumption_sensor;

	private boolean _in_beats;
	private boolean _in_water_level_0;
	private boolean _in_water_level_1;
	private boolean _in_water_level_2;
	private boolean _in_water_level_3;
	private boolean _in_water_level_4;
	private boolean _in_extraction_position;

	// other

	private float _other_temperature_current;
	private float _other_temperature_maximum;
	private float _other_water_consumption;
	private float _other_drum_rpm;

	// programs

	private int _program_stage_current;
	private int _program_stage_maximum;
	private int _program_current;
	private int _program_seconds_current;
	private int _program_seconds_maximum;
	private int _program_client;
	private int _program_weight;

	private float _electric_voltage;
	private float _electric_current;
	private float _electric_active_power;
	private float _electric_energy_total;


	// class service data

	private boolean _isValid;

	public WasherState() {
		_isValid = false;
	}

	public WasherState(byte[] array) {
		if (array != null && array.length == 94) {
			fromArray(array);
		} else {
			_isValid = false;
		}
	}

	public WasherState(ByteBuffer buffer) {
		if (buffer != null) {
			fromArray(buffer.array());
		} else {
			_isValid = false;
		}
	}

	public boolean isExecuteEmergency() {
		return _execute_emergency;
	}

	public boolean isExecutePause() {
		return _execute_pause;
	}

	public boolean isExecuteProgram() {
		return _execute_program;
	}

	public boolean isOutBrake() {
		return _out_brake;
	}

	public boolean isOutElectricalHeat() {
		return _out_electrical_heat;
	}

	public boolean isOutSpin2() {
		return _out_spin2;
	}

	public boolean isOutSpin1() {
		return _out_spin1;
	}

	public boolean isOutEngineRotationL() {
		return _out_engine_rotation_l;
	}

	public boolean isOutEngineRotationR() {
		return _out_engine_rotation_r;
	}

	public boolean isOutDeceleration() {
		return _out_deceleration;
	}

	public boolean isOutSteamHeat() {
		return _out_steam_heat;
	}

	public boolean isOutPower() {
		return _out_power;
	}

	public boolean isOutUnlockDoor() {
		return _out_unlock_door;
	}

	public boolean isOutWaterValve() {
		return _out_water_valve;
	}

	public boolean isOutTray1() {
		return _out_tray1;
	}

	public boolean isOutTray2() {
		return _out_tray2;
	}

	public boolean isOutTray3() {
		return _out_tray3;
	}

	public boolean isOutLockUp() {
		return _out_lockup;
	}

	public boolean isOutSink() {
		return _out_sink;
	}

	public boolean isInEmergency() {
		return _in_emergency;
	}

	public boolean isInShock() {
		return _in_shock;
	}

	public boolean isInDirtyUnlocked() {
		return _in_dirty_unlocked;
	}

	public boolean isInDirtyOpen() {
		return _in_dirty_open;
	}

	public boolean isInCleanUnlocked() {
		return _in_clean_unlocked;
	}

	public boolean isInCleanOpen() {
		return _in_clean_open;
	}

	public boolean isInRotationTurnOffSwitch() {
		return _in_rotation_turn_off_switch;
	}

	public boolean isInWaterConsumptionSensor() {
		return _in_water_consumption_sensor;
	}

	public boolean isInBeats() {
		return _in_beats;
	}

	public boolean isInWaterLeven0() {
		return _in_water_level_0;
	}

	public boolean isInWaterLevel1() {
		return _in_water_level_1;
	}

	public boolean isInWaterLevel2() {
		return _in_water_level_2;
	}

	public boolean isInWaterLevel3() {
		return _in_water_level_3;
	}

	public boolean isInWaterLevel4() {
		return _in_water_level_4;
	}

	public boolean isInExtractionPosition() {
		return _in_extraction_position;
	}

	public float getOtherTemperatureCurrent() {
		return _other_temperature_current;
	}

	public float getOtherTemperatureMaximum() {
		return _other_temperature_maximum;
	}

	public float getOtherWaterConsumption() {
		return _other_water_consumption;
	}

	public float getOtherDrumRPM() {
		return _other_drum_rpm;
	}

	public int getProgramStageCurrent() {
		return _program_stage_current;
	}

	public int getProgramStageMaximum() {
		return _program_stage_maximum;
	}

	public int getProgramCurrent() {
		return _program_current;
	}

	public int getProgramSecondsCurrent() {
		return _program_seconds_current;
	}

	public int getProgramSecondsMaximum() {
		return _program_seconds_maximum;
	}

	public int getProgramClient() {
		return _program_client;
	}

	public int getProgramWeight() {
		return _program_weight;
	}

	public float getElectricVoltage() {
		return _electric_voltage;
	}

	public float getElectricCurrent() {
		return _electric_current;
	}

	public float getElectricActivePower() {
		return _electric_active_power;
	}

	public float getElectricEnergyTotal() {
		return _electric_energy_total;
	}

	public boolean isValid() {
		return _isValid;
	}

	public void set_execute_1(boolean _execute_1) {
		this._execute_emergency = _execute_1;
	}

	public void set_execute_2(boolean _execute_2) {
		this._execute_pause = _execute_2;
	}

	public void set_execute_3(boolean _execute_3) {
		this._execute_program = _execute_3;
	}

	public void set_out_1(boolean _out_1) {
		this._out_brake = _out_1;
	}

	public void set_out_2(boolean _out_2) {
		this._out_electrical_heat = _out_2;
	}

	public void set_out_3(boolean _out_3) {
		this._out_spin2 = _out_3;
	}

	public void set_out_4(boolean _out_4) {
		this._out_spin1 = _out_4;
	}

	public void set_out_5(boolean _out_5) {
		this._out_engine_rotation_l = _out_5;
	}

	public void set_out_6(boolean _out_6) {
		this._out_engine_rotation_r = _out_6;
	}

	public void set_out_7(boolean _out_7) {
		this._out_deceleration = _out_7;
	}

	public void set_out_8(boolean _out_8) {
		this._out_steam_heat = _out_8;
	}

	public void set_out_9(boolean _out_9) {
		this._out_power = _out_9;
	}

	public void set_out_10(boolean _out_10) {
		this._out_unlock_door = _out_10;
	}

	public void set_out_11(boolean _out_11) {
		this._out_water_valve = _out_11;
	}

	public void set_out_12(boolean _out_12) {
		this._out_tray1 = _out_12;
	}

	public void set_out_13(boolean _out_13) {
		this._out_tray2 = _out_13;
	}

	public void set_out_14(boolean _out_14) {
		this._out_tray3 = _out_14;
	}

	public void set_out_15(boolean _out_15) {
		this._out_lockup = _out_15;
	}

	public void set_out_16(boolean _out_16) {
		this._out_sink = _out_16;
	}

	public void set_in_1(boolean _in_1) {
		this._in_emergency = _in_1;
	}

	public void set_in_2(boolean _in_2) {
		this._in_shock = _in_2;
	}

	public void set_in_3(boolean _in_3) {
		this._in_dirty_unlocked = _in_3;
	}

	public void set_in_4(boolean _in_4) {
		this._in_dirty_open = _in_4;
	}

	public void set_in_5(boolean _in_5) {
		this._in_clean_unlocked = _in_5;
	}

	public void set_in_6(boolean _in_6) {
		this._in_clean_open = _in_6;
	}

	public void set_in_7(boolean _in_7) {
		this._in_rotation_turn_off_switch = _in_7;
	}

	public void set_in_8(boolean _in_8) {
		this._in_water_consumption_sensor = _in_8;
	}

	public void set_in_9(boolean _in_9) {
		this._in_beats = _in_9;
	}

	public void set_in_10(boolean _in_10) {
		this._in_water_level_0 = _in_10;
	}

	public void set_in_11(boolean _in_11) {
		this._in_water_level_1 = _in_11;
	}

	public void set_in_12(boolean _in_12) {
		this._in_water_level_2 = _in_12;
	}

	public void set_in_13(boolean _in_13) {
		this._in_water_level_3 = _in_13;
	}

	public void set_in_14(boolean _in_14) {
		this._in_water_level_4 = _in_14;
	}

	public void set_in_15(boolean _in_15) {
		this._in_extraction_position = _in_15;
	}

	public void set_other_1(float _other_1) {
		this._other_temperature_current = _other_1;
	}

	public void set_other_2(float _other_2) {
		this._other_temperature_maximum = _other_2;
	}

	public void set_other_3(float _other_3) {
		this._other_water_consumption = _other_3;
	}

	public void set_other_4(float _other_4) {
		this._other_drum_rpm = _other_4;
	}

	public void set_program_1(int _program_1) {
		this._program_stage_current = _program_1;
	}

	public void set_program_2(int _program_2) {
		this._program_stage_maximum = _program_2;
	}

	public void set_program_3(int _program_3) {
		this._program_current = _program_3;
	}

	public void set_program_4(int _program_4) {
		this._program_seconds_current = _program_4;
	}

	public void set_program_5(int _program_5) {
		this._program_seconds_maximum = _program_5;
	}

	public void set_program_6(int _program_6) {
		this._program_client = _program_6;
	}

	public void set_program_7(int _program_7) {
		this._program_weight = _program_7;
	}

	public void set_isValid(boolean _isValid) {
		this._isValid = _isValid;
	}

	public void set_electric_1(int _electric_1) {
		this._electric_voltage = _electric_1;
	}

	public void set_electric_2(int _electric_2) {
		this._electric_current = _electric_2;
	}

	public void set_electric_3(int _electric_3) {
		this._electric_active_power = _electric_3;
	}

	public void set_electric_4(int _electric_4) {
		this._electric_energy_total = _electric_4;
	}


	private boolean isRun = false;

	/**
	 * Parse data from array.
	 * @param array - data.
	 */
	private void fromArray(byte[] array) {
		/*
		if (array.length != 70) {
			return;
		}
		*/

		ByteBuffer buffer = ByteBuffer.wrap(array);

		_execute_emergency = (buffer.get() != 0) ? true : false;
		_execute_pause = (buffer.get() != 0) ? true : false;
		_execute_program = (buffer.get() != 0) ? true : false;

		_out_brake = (buffer.get() != 0) ? true : false;
		_out_electrical_heat = (buffer.get() != 0) ? true : false;
		_out_spin2 = (buffer.get() != 0) ? true : false;
		_out_spin1 = (buffer.get() != 0) ? true : false;
		_out_engine_rotation_l = (buffer.get() != 0) ? true : false;
		_out_engine_rotation_r = (buffer.get() != 0) ? true : false;
		_out_deceleration = (buffer.get() != 0) ? true : false;
		_out_steam_heat = (buffer.get() != 0) ? true : false;

		_out_power = (buffer.get() != 0) ? true : false;
		_out_unlock_door = (buffer.get() != 0) ? true : false;
		_out_water_valve = (buffer.get() != 0) ? true : false;
		_out_tray1 = (buffer.get() != 0) ? true : false;
		_out_tray2 = (buffer.get() != 0) ? true : false;
		_out_tray3 = (buffer.get() != 0) ? true : false;
		_out_lockup = (buffer.get() != 0) ? true : false;
		_out_sink = (buffer.get() != 0) ? true : false;

		_in_emergency = (buffer.get() != 0) ? true : false;
		_in_shock = (buffer.get() != 0) ? true : false;
		_in_dirty_unlocked = (buffer.get() != 0) ? true : false;
		_in_dirty_open = (buffer.get() != 0) ? true : false;
		_in_clean_unlocked = (buffer.get() != 0) ? true : false;
		_in_clean_open = (buffer.get() != 0) ? true : false;
		_in_rotation_turn_off_switch = (buffer.get() != 0) ? true : false;
		_in_water_consumption_sensor = (buffer.get() != 0) ? true : false;

		_in_beats = (buffer.get() != 0) ? true : false;
		_in_water_level_0 = (buffer.get() != 0) ? true : false;
		_in_water_level_1 = (buffer.get() != 0) ? true : false;
		_in_water_level_2 = (buffer.get() != 0) ? true : false;
		_in_water_level_3 = (buffer.get() != 0) ? true : false;
		_in_water_level_4 = (buffer.get() != 0) ? true : false;
		_in_extraction_position = (buffer.get() != 0) ? true : false;

		_other_temperature_current = buffer.getInt() / 100f;
		_other_temperature_maximum = buffer.getInt() / 100f;
		_other_water_consumption = buffer.getInt() / 100f;
		_other_drum_rpm = buffer.getInt() / 100f;

		_program_stage_current = buffer.getInt();
		_program_stage_maximum = buffer.getInt();
		_program_current = buffer.getInt();
		_program_seconds_current = buffer.getInt();
		_program_seconds_maximum = buffer.getInt();
		_program_client = buffer.getInt();
		_program_weight = buffer.getInt();

		_electric_voltage = buffer.getInt() / 100f;
		_electric_current = buffer.getInt() / 100f;
		_electric_active_power = buffer.getInt() / 100f;
		_electric_energy_total = buffer.getInt() / 100f;
	}

	/**
	 * Parse from CSV-form, where symbol ':' is a delimiter.
	 */
	public void fromString(String str) {
		String[] values = str.split(":");

		_execute_emergency = Boolean.parseBoolean(values[0]);
		_execute_pause = Boolean.parseBoolean(values[1]);
		_execute_program = Boolean.parseBoolean(values[2]);

		_out_brake = Boolean.parseBoolean(values[3]);
		_out_electrical_heat = Boolean.parseBoolean(values[4]);
		_out_spin2 = Boolean.parseBoolean(values[5]);
		_out_spin1 = Boolean.parseBoolean(values[6]);
		_out_engine_rotation_l = Boolean.parseBoolean(values[7]);
		_out_engine_rotation_r = Boolean.parseBoolean(values[8]);
		_out_deceleration = Boolean.parseBoolean(values[9]);
		_out_steam_heat = Boolean.parseBoolean(values[10]);

		_out_power = Boolean.parseBoolean(values[11]);
		_out_unlock_door = Boolean.parseBoolean(values[12]);
		_out_water_valve = Boolean.parseBoolean(values[13]);
		_out_tray1 = Boolean.parseBoolean(values[14]);
		_out_tray2 = Boolean.parseBoolean(values[15]);
		_out_tray3 = Boolean.parseBoolean(values[16]);
		_out_lockup = Boolean.parseBoolean(values[17]);
		_out_sink = Boolean.parseBoolean(values[18]);

		_in_emergency = Boolean.parseBoolean(values[19]);
		_in_shock = Boolean.parseBoolean(values[20]);
		_in_dirty_unlocked = Boolean.parseBoolean(values[21]);
		_in_dirty_open = Boolean.parseBoolean(values[22]);
		_in_clean_unlocked = Boolean.parseBoolean(values[23]);
		_in_clean_open = Boolean.parseBoolean(values[24]);
		_in_rotation_turn_off_switch = Boolean.parseBoolean(values[25]);
		_in_water_consumption_sensor = Boolean.parseBoolean(values[26]);

		_in_beats = Boolean.parseBoolean(values[27]);
		_in_water_level_0 = Boolean.parseBoolean(values[28]);
		_in_water_level_1 = Boolean.parseBoolean(values[29]);
		_in_water_level_2 = Boolean.parseBoolean(values[30]);
		_in_water_level_3 = Boolean.parseBoolean(values[31]);
		_in_water_level_4 = Boolean.parseBoolean(values[32]);
		_in_extraction_position = Boolean.parseBoolean(values[33]);

		_other_temperature_current = Float.parseFloat(values[34]);
		_other_temperature_maximum = Float.parseFloat(values[35]);
		_other_water_consumption = Float.parseFloat(values[36]);
		_other_drum_rpm = Float.parseFloat(values[37]);

		_program_stage_current = Integer.parseInt(values[38]);
		_program_stage_maximum = Integer.parseInt(values[39]);
		_program_current = Integer.parseInt(values[40]);
		_program_seconds_current = Integer.parseInt(values[41]);
		_program_seconds_maximum = Integer.parseInt(values[42]);
		_program_client = Integer.parseInt(values[43]);
		_program_weight = Integer.parseInt(values[44]);

		_electric_voltage = Float.parseFloat(values[45]);
		_electric_current = Float.parseFloat(values[46]);
		_electric_active_power = Float.parseFloat(values[47]);
		_electric_energy_total = Float.parseFloat(values[48]);
	}


	/**
	 * Convert to CSV-form, with symbol ':' as delimiter.
	 */
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		out.append(_execute_emergency);
		out.append(':');
		out.append(_execute_pause);
		out.append(':');
		out.append(_execute_program);
		out.append(':');

		out.append(_out_brake);
		out.append(':');
		out.append(_out_electrical_heat);
		out.append(':');
		out.append(_out_spin2);
		out.append(':');
		out.append(_out_spin1);
		out.append(':');
		out.append(_out_engine_rotation_l);
		out.append(':');
		out.append(_out_engine_rotation_r);
		out.append(':');
		out.append(_out_deceleration);
		out.append(':');
		out.append(_out_steam_heat);
		out.append(':');

		out.append(_out_power);
		out.append(':');
		out.append(_out_unlock_door);
		out.append(':');
		out.append(_out_water_valve);
		out.append(':');
		out.append(_out_tray1);
		out.append(':');
		out.append(_out_tray2);
		out.append(':');
		out.append(_out_tray3);
		out.append(':');
		out.append(_out_lockup);
		out.append(':');
		out.append(_out_sink);
		out.append(':');

		out.append(_in_emergency);
		out.append(':');
		out.append(_in_shock);
		out.append(':');
		out.append(_in_dirty_unlocked);
		out.append(':');
		out.append(_in_dirty_open);
		out.append(':');
		out.append(_in_clean_unlocked);
		out.append(':');
		out.append(_in_clean_open);
		out.append(':');
		out.append(_in_rotation_turn_off_switch);
		out.append(':');
		out.append(_in_water_consumption_sensor);
		out.append(':');

		out.append(_in_beats);
		out.append(':');
		out.append(_in_water_level_0);
		out.append(':');
		out.append(_in_water_level_1);
		out.append(':');
		out.append(_in_water_level_2);
		out.append(':');
		out.append(_in_water_level_3);
		out.append(':');
		out.append(_in_water_level_4);
		out.append(':');
		out.append(_in_extraction_position);
		out.append(':');

		out.append(_other_temperature_current);
		out.append(':');
		out.append(_other_temperature_maximum);
		out.append(':');
		out.append(_other_water_consumption);
		out.append(':');
		out.append(_other_drum_rpm);
		out.append(':');

		out.append(_program_stage_current);
		out.append(':');
		out.append(_program_stage_maximum);
		out.append(':');
		out.append(_program_current);
		out.append(':');
		out.append(_program_seconds_current);
		out.append(':');
		out.append(_program_seconds_maximum);
		out.append(':');
		out.append(_program_client);
		out.append(':');
		out.append(_program_weight);
		out.append(':');

		out.append(_electric_voltage);
		out.append(':');
		out.append(_electric_current);
		out.append(':');
		out.append(_electric_active_power);
		out.append(':');
		out.append(_electric_energy_total);

		return out.toString();
	}

	@Override
	public boolean equals(Object obj) {
		try {
			if (obj == null) {
				return false;
			}

			WasherState other = (WasherState) obj;

			if (_execute_emergency != other._execute_emergency) {
				return false;
			}

			if (_execute_pause != other._execute_pause) {
				return false;
			}

			if (_execute_program != other._execute_program) {
				return false;
			}

			//

			if (_out_brake != other._out_brake) {
				return false;
			}

			if (_out_electrical_heat != other._out_electrical_heat) {
				return false;
			}

			if (_out_spin2 != other._out_spin2) {
				return false;
			}

			if (_out_spin1 != other._out_spin1) {
				return false;
			}

			if (_out_engine_rotation_l != other._out_engine_rotation_l) {
				return false;
			}

			if (_out_engine_rotation_r != other._out_engine_rotation_r) {
				return false;
			}

			if (_out_deceleration != other._out_deceleration) {
				return false;
			}

			if (_out_steam_heat != other._out_steam_heat) {
				return false;
			}

			//

			if (_out_power != other._out_power) {
				return false;
			}

			if (_out_unlock_door != other._out_unlock_door) {
				return false;
			}

			if (_out_water_valve != other._out_water_valve) {
				return false;
			}

			if (_out_tray1 != other._out_tray1) {
				return false;
			}

			if (_out_tray2 != other._out_tray2) {
				return false;
			}

			if (_out_tray3 != other._out_tray3) {
				return false;
			}

			if (_out_lockup != other._out_lockup) {
				return false;
			}

			if (_out_sink != other._out_sink) {
				return false;
			}

			//

			if (_in_emergency != other._in_emergency) {
				return false;
			}

			if (_in_shock != other._in_shock) {
				return false;
			}

			if (_in_dirty_unlocked != other._in_dirty_unlocked) {
				return false;
			}

			if (_in_dirty_open != other._in_dirty_open) {
				return false;
			}

			if (_in_clean_unlocked != other._in_clean_unlocked) {
				return false;
			}

			if (_in_clean_open != other._in_clean_open) {
				return false;
			}

			if (_in_rotation_turn_off_switch != other._in_rotation_turn_off_switch) {
				return false;
			}

			if (_in_water_consumption_sensor != other._in_water_consumption_sensor) {
				return false;
			}

			//

			if (_in_beats != other._in_beats) {
				return false;
			}

			if (_in_water_level_0 != other._in_water_level_0) {
				return false;
			}

			if (_in_water_level_1 != other._in_water_level_1) {
				return false;
			}

			if (_in_water_level_2 != other._in_water_level_2) {
				return false;
			}

			if (_in_water_level_3 != other._in_water_level_3) {
				return false;
			}

			if (_in_water_level_4 != other._in_water_level_4) {
				return false;
			}

			if (_in_extraction_position != other._in_extraction_position) {
				return false;
			}

			//

			final float EPSILON = 0.01f;

			if (Math.abs(_other_temperature_current - other._other_temperature_current) > EPSILON) {
				return false;
			}

			if (Math.abs(_other_temperature_maximum - other._other_temperature_maximum) > EPSILON) {
				return false;
			}

			if (Math.abs(_other_water_consumption - other._other_water_consumption) > EPSILON) {
				return false;
			}

			if (Math.abs(_other_drum_rpm - other._other_drum_rpm) > EPSILON) {
				return false;
			}

			//

			if (_program_stage_current != other._program_stage_current) {
				return false;
			}

			if (_program_stage_maximum != other._program_stage_maximum) {
				return false;
			}

			if (_program_current != other._program_current) {
				return false;
			}

			if (_program_seconds_current != other._program_seconds_current) {
				return false;
			}

			if (_program_seconds_maximum != other._program_seconds_maximum) {
				return false;
			}

			if (_program_client != other._program_client) {
				return false;
			}

			if (_program_weight != other._program_weight) {
				return false;
			}

			//

			if (Math.abs(_electric_voltage - other._electric_voltage) > EPSILON) {
				return false;
			}

			if (Math.abs(_electric_current - other._electric_current) > EPSILON) {
				return false;
			}

			if (Math.abs(_electric_active_power - other._electric_active_power) > EPSILON) {
				return false;
			}

			if (Math.abs(_electric_energy_total - other._electric_energy_total) > EPSILON) {
				return false;
			}

		}
		catch (Exception ex) {
			return false;
		}

		return true;
	}
}
