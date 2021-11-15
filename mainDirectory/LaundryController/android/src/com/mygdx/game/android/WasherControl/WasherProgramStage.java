package com.mygdx.game.android.WasherControl;

import java.nio.ByteBuffer;

public class WasherProgramStage {

	// data

	private boolean _waterSupplyEnabled;
	private int _waterSupplyMaxLevel; // 0,1,2,3,4
	private int _waterSupplyMaxTime;

	private boolean _trayEnabled;
	private int _trayNumber; // 1,2,3
	private int _trayMaxTime;

	private boolean _drumRotationEnabled;
	private int _drumRotationRightTime;
	private int _drumRotationRightStopTime;
	private int _drumRotationLeftTime;
	private int _drumRotationLeftStopTime;

	private boolean _heatElectricalEnabled;
	private boolean _heatSteamEnabled;
	private int _heatMaxTemperature;
	private int _heatMaxTime;

	private boolean _rinsingEnabled;
	private int _rinsingMaxTime;

	private boolean _spinEnabled;
	private int _spinSpeed; // 0,1,2
	private int _spinMaxTime;
	private int _spinMaxBalanceTries;

	private int _stageMaxTime;

	private int _stageGroup;

	// class service data

	private boolean _isValid;

	// constructor

	public WasherProgramStage() {
		_isValid = false;
	}

	public WasherProgramStage(boolean waterSupplyEnabled, int waterSupplyMaxLevel, int waterSupplyMaxTime,
			boolean trayEnabled, int trayNumber, int trayMaxTime, boolean drumRotationEnabled,
			int drumRotationRightTime, int drumRotationRightStopTime, int drumRotationLeftTime,
			int drumRotationLeftStopTime, boolean heatElectricalEnabled, boolean heatSteamEnabled,int heatMaxTemperature,
			int heatMaxTime, boolean rinsingEnabled, int rinsingMaxTime, boolean spinEnabled, int spinSpeed,
			int spinMaxTime, int spinMaxBalanceTries, int stageMaxTime, int stageGroup) {

		_waterSupplyEnabled = waterSupplyEnabled;
		_waterSupplyMaxLevel = waterSupplyMaxLevel;
		_waterSupplyMaxTime = waterSupplyMaxTime;

		_trayEnabled = trayEnabled;
		_trayNumber = trayNumber;
		_trayMaxTime = trayMaxTime;

		_drumRotationEnabled = drumRotationEnabled;
		_drumRotationRightTime = drumRotationRightTime;
		_drumRotationRightStopTime = drumRotationRightStopTime;
		_drumRotationLeftTime = drumRotationLeftTime;
		_drumRotationLeftStopTime = drumRotationRightStopTime;

		_heatElectricalEnabled = heatElectricalEnabled;
		_heatSteamEnabled = heatSteamEnabled;
		_heatMaxTime = heatMaxTime;
		_heatMaxTemperature = heatMaxTemperature;

		_rinsingEnabled = rinsingEnabled;
		_rinsingMaxTime = rinsingMaxTime;

		_spinEnabled = spinEnabled;
		_spinSpeed = spinSpeed;
		_spinMaxTime = spinMaxTime;
		_spinMaxBalanceTries = spinMaxBalanceTries;

		_stageMaxTime = stageMaxTime;

		_stageGroup = stageGroup;
	}

	public WasherProgramStage(ByteBuffer buffer) {
		if (buffer == null) {
			return;
		}

		fromArray(buffer.array());
		_isValid = check();
	}

	public WasherProgramStage(byte[] array) {
		if (array == null) {
			return;
		}

		fromArray(array);
		_isValid = check();
	}

	public boolean isWaterSupplyEnabled() {
		return _waterSupplyEnabled;
	}

	public int getWaterSupplyMaxLevel() {
		return _waterSupplyMaxLevel;
	}

	public int getWaterSupplyMaxTime() {
		return _waterSupplyMaxTime;
	}

	public boolean isTrayEnabled() {
		return _trayEnabled;
	}

	public int getTrayNumber() {
		return _trayNumber;
	}

	public int getTrayMaxTime() {
		return _trayMaxTime;
	}

	public boolean isDrumRotationEnabled() {
		return _drumRotationEnabled;
	}

	public int getDrumRotationRightTime() {
		return _drumRotationRightTime;
	}

	public int getDrumRotationRightStopTime() {
		return _drumRotationRightStopTime;
	}

	public int getDrumRotationLeftTime() {
		return _drumRotationLeftTime;
	}

	public int getDrumRotationLeftStopTime() {
		return _drumRotationLeftStopTime;
	}

	public boolean isHeatElectricalEnabled() {
		return _heatElectricalEnabled;
	}

	public boolean isHeatSteamEnabled() {
		return _heatSteamEnabled;
	}

	public int getHeatMaxTemperature() {
		return _heatMaxTemperature;
	}

	public int getHeatMaxTime() {
		return _heatMaxTime;
	}

	public boolean isRinsingEnabled() {
		return _rinsingEnabled;
	}

	public int getRinsingMaxTime() {
		return _rinsingMaxTime;
	}

	public boolean isSpinEnabled() {
		return _spinEnabled;
	}

	public int getSpinSpeed() {
		return _spinSpeed;
	}

	public int getSpinMaxTime() {
		return _spinMaxTime;
	}

	public int getSpinMaxBalanceTries() {
		return _spinMaxBalanceTries;
	}

	public int getStageMaxTime() {
		return _stageMaxTime;
	}

	public int getStageGroup() {
		return _stageGroup;
	}

	public boolean isValid() {
		return _isValid;
	}

	public void setWaterSupplyEnabled(boolean waterSupplyEnabled) {
		_waterSupplyEnabled = waterSupplyEnabled;
	}

	public void setWaterSupplyMaxLevel(int waterSupplyMaxLevel) {
		if (waterSupplyMaxLevel < 0 || waterSupplyMaxLevel > 5) {
			return;
		}

		_waterSupplyMaxLevel = waterSupplyMaxLevel;
	}

	public void setWaterSupplyMaxTime(int waterSupplyMaxTime) {
		_waterSupplyMaxTime = waterSupplyMaxTime;
	}

	public void setTrayEnabled(boolean trayEnabled) {
		_trayEnabled = trayEnabled;
	}

	public void setTrayNumber(int trayNumber) {
		if (trayNumber < 1 || trayNumber > 3) {
			return;
		}

		_trayNumber = trayNumber;
	}

	public void setTrayMaxTime(int trayMaxTime) {
		_trayMaxTime = trayMaxTime;
	}

	public void setDrumRotationEnabled(boolean drumRotationEnabled) {
		_drumRotationEnabled = drumRotationEnabled;
	}

	public void setDrumRotationRightTime(int drumRotationRightTime) {
		_drumRotationRightTime = drumRotationRightTime;
	}

	public void setDrumRotationRightStopTime(int drumRotationRightStopTime) {
		_drumRotationRightStopTime = drumRotationRightStopTime;
	}

	public void setDrumRotationLeftTime(int drumRotationLeftTime) {
		_drumRotationLeftTime = drumRotationLeftTime;
	}

	public void setDrumRotationLeftStopTime(int drumRotationLeftStopTime) {
		_drumRotationLeftStopTime = drumRotationLeftStopTime;
	}

	public void setHeatElectricalEnabled(boolean heatElectricalEnabled) {
		_heatElectricalEnabled = heatElectricalEnabled;
	}

	public void setHeatSteamEnabled(boolean heatSteamEnabled) {
		_heatSteamEnabled = heatSteamEnabled;
	}

	public void setHeatMaxTime(int heatMaxTime) {
		_heatMaxTime = heatMaxTime;
	}

	public void setHeatMaxTemperature(int heatMaxTemperature) {
		_heatMaxTemperature = heatMaxTemperature;
	}

	public void setRinsingEnabled(boolean rinsingEnabled) {
		_rinsingEnabled = rinsingEnabled;
	}

	public void setRinsingMaxTime(int rinsingMaxTime) {
		_rinsingMaxTime = rinsingMaxTime;
	}

	public void setSpinEnabled(boolean spinEnabled) {
		_spinEnabled = spinEnabled;
	}

	public void setSpinSpeed(int spinSpeed) {
		if (spinSpeed < 0 || spinSpeed > 2) {
			return;
		}

		_spinSpeed = spinSpeed;
	}

	public void setSpinMaxTime(int spinMaxTime) {
		_spinMaxTime = spinMaxTime;
	}

	public void setSpinMaxBalanceTries(int spinMaxBalanceTries) {
		_spinMaxBalanceTries = spinMaxBalanceTries;
	}

	public void setStageMaxTime(int stageMaxTime) {
		_stageMaxTime = stageMaxTime;
	}

	public void setStageGroup(int stageGroup) {
		_stageGroup = stageGroup;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		try {
			WasherProgramStage other = (WasherProgramStage) obj;

			if (_waterSupplyEnabled != other._waterSupplyEnabled) {
				return false;
			}
			if (_waterSupplyMaxLevel != other._waterSupplyMaxLevel) {
				return false;
			}

			if (_waterSupplyMaxTime != other._waterSupplyMaxTime) {
				return false;
			}


			if (_trayEnabled != other._trayEnabled) {
				return false;
			}

			if (_trayNumber != other._trayNumber) {
				return false;
			}

			if (_trayMaxTime != other._trayMaxTime) {
				return false;
			}


			if (_drumRotationEnabled != other._drumRotationEnabled) {
				return false;
			}
			if (_drumRotationRightTime != other._drumRotationRightTime) {
				return false;
			}

			if (_drumRotationRightStopTime != other._drumRotationRightStopTime) {
				return false;
			}

			if (_drumRotationLeftTime != other._drumRotationLeftTime) {
				return false;
			}

			if (_drumRotationLeftStopTime != other._drumRotationRightStopTime) {
				return false;
			}


			if (_heatElectricalEnabled != other._heatElectricalEnabled) {
				return false;
			}

			if (_heatSteamEnabled != other._heatSteamEnabled) {
				return false;
			}

			if (_heatMaxTime != other._heatMaxTime) {
				return false;
			}

			if (_heatMaxTemperature != other._heatMaxTemperature) {
				return false;
			}


			if (_rinsingEnabled != other._rinsingEnabled) {
				return false;
			}

			if (_rinsingMaxTime != other._rinsingMaxTime) {
				return false;
			}


			if (_spinEnabled != other._spinEnabled) {
				return false;
			}

			if (_spinSpeed != other._spinSpeed) {
				return false;
			}

			if (_spinMaxTime != other._spinMaxTime) {
				return false;
			}

			if (_spinMaxBalanceTries != other._spinMaxBalanceTries) {
				return false;
			}

			if (_stageMaxTime != other._stageMaxTime) {
				return false;
			}

			if (_stageGroup != other._stageGroup) {
				return false;
			}

			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}

	public void fromString(String str) {
		if (str == null) {
			_isValid = false;

			return;
		}

		String[] values = str.split(":");

		if (values.length != 23) {
			_isValid = false;

			return;
		}

		try {
			int index = 0;
			_waterSupplyEnabled = Boolean.parseBoolean(values[index++]);
			_waterSupplyMaxLevel = Integer.parseInt(values[index++]); // 0,1,2,3,4
			_waterSupplyMaxTime = Integer.parseInt(values[index++]);

			_trayEnabled = Boolean.parseBoolean(values[index++]);
			_trayNumber = Integer.parseInt(values[index++]); // 1,2,3
			_trayMaxTime = Integer.parseInt(values[index++]);

			_drumRotationEnabled = Boolean.parseBoolean(values[index++]);
			_drumRotationRightTime = Integer.parseInt(values[index++]);
			_drumRotationRightStopTime = Integer.parseInt(values[index++]);
			_drumRotationLeftTime = Integer.parseInt(values[index++]);
			_drumRotationLeftStopTime = Integer.parseInt(values[index++]);

			_heatElectricalEnabled = Boolean.parseBoolean(values[index++]);
			_heatSteamEnabled = Boolean.parseBoolean(values[index++]);
			_heatMaxTemperature = Integer.parseInt(values[index++]);
			_heatMaxTime = Integer.parseInt(values[index++]);

			_rinsingEnabled = Boolean.parseBoolean(values[index++]);
			_rinsingMaxTime = Integer.parseInt(values[index++]);

			_spinEnabled = Boolean.parseBoolean(values[index++]);
			_spinSpeed = Integer.parseInt(values[index++]); // 0,1,2
			_spinMaxTime = Integer.parseInt(values[index++]);
			_spinMaxBalanceTries = Integer.parseInt(values[index++]);

			_stageMaxTime = Integer.parseInt(values[index++]);

			_stageGroup = Integer.parseInt(values[index]);
		} catch (Exception ex) {
			_isValid = false;
			return;
		}

		_isValid = check();
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		out.append(_waterSupplyEnabled);
		out.append(':');
		out.append(_waterSupplyMaxLevel);
		out.append(':');
		out.append(_waterSupplyMaxTime);
		out.append(':');

		out.append(_trayEnabled);
		out.append(':');
		out.append(_trayNumber);
		out.append(':');
		out.append(_trayMaxTime);
		out.append(':');

		out.append(_drumRotationEnabled);
		out.append(':');
		out.append(_drumRotationRightTime);
		out.append(':');
		out.append(_drumRotationRightStopTime);
		out.append(':');
		out.append(_drumRotationLeftTime);
		out.append(':');
		out.append(_drumRotationLeftStopTime);
		out.append(':');

		out.append(_heatElectricalEnabled);
		out.append(':');
		out.append(_heatSteamEnabled);
		out.append(':');
		out.append(_heatMaxTemperature);
		out.append(':');
		out.append(_heatMaxTime);
		out.append(':');

		out.append(_rinsingEnabled);
		out.append(':');
		out.append(_rinsingMaxTime);
		out.append(':');

		out.append(_spinEnabled);
		out.append(':');
		out.append(_spinSpeed);
		out.append(':');
		out.append(_spinMaxTime);
		out.append(':');
		out.append(_spinMaxBalanceTries);
		out.append(':');

		out.append(_stageMaxTime);
		out.append(':');

		out.append(_stageGroup);

		return out.toString();
	}

	private boolean check() {

		if (_waterSupplyEnabled) {
			if (_waterSupplyMaxLevel < 0 || _waterSupplyMaxLevel > 4) {
				return false;
			}
		}

		if (_drumRotationEnabled) {
			if (_drumRotationLeftTime < 0 || _drumRotationRightTime < 0 || _drumRotationLeftStopTime < 0 || _drumRotationLeftStopTime < 0) {
				return false;
			}
		}


		if (_trayEnabled) {
			if (_trayNumber < 1 || _trayNumber > 3) {
				return false;
			}
		}

		if (_spinEnabled) {
			if (_spinSpeed < 0 || _spinSpeed > 2) {
				return false;
			}
		}

		return true;
	}

	private void fromArray(byte[] array) {
		if (array == null) {
			return;
		}

		ByteBuffer buffer = ByteBuffer.wrap(array);

		_waterSupplyEnabled = (buffer.get() != 0) ? true : false;;
		_waterSupplyMaxLevel = buffer.getInt();
		_waterSupplyMaxTime = buffer.getInt();

		_trayEnabled = (buffer.get() != 0) ? true : false;;
		_trayNumber = buffer.getInt();
		_trayMaxTime = buffer.getInt();

		_drumRotationEnabled = (buffer.get() != 0) ? true : false;;
		_drumRotationRightTime = buffer.getInt();
		_drumRotationRightStopTime = buffer.getInt();
		_drumRotationLeftTime = buffer.getInt();
		_drumRotationLeftStopTime = buffer.getInt();

		_heatElectricalEnabled = (buffer.get() != 0) ? true : false;;
		_heatSteamEnabled = (buffer.get() != 0) ? true : false;;
		_heatMaxTemperature = buffer.getInt();
		_heatMaxTime = buffer.getInt();

		_rinsingEnabled = (buffer.get() != 0) ? true : false;;
		_rinsingMaxTime = buffer.getInt();

		_spinEnabled = (buffer.get() != 0) ? true : false;;
		_spinSpeed = buffer.getInt();
		_spinMaxTime = buffer.getInt();
		_spinMaxBalanceTries = buffer.getInt();

		_stageMaxTime = buffer.getInt();

		_stageGroup = buffer.getInt();
	}
}