package com.mygdx.game.android.WasherControl;

import java.util.ArrayList;
import java.util.Arrays;

public class WasherProgram {

	private int _id;
	private String _name;
	private String _description;
	private ArrayList<WasherProgramStage> _stages;
	private ArrayList<String> _stageGroups;

	private boolean _isValid;

	public WasherProgram() {
		reset();
	}

	public WasherProgram(int id, String name, String description, ArrayList<WasherProgramStage> stages, ArrayList<String> stageGroups) {
		_id = id;
		_name = name;
		_description = description;
		_stages = stages;
		_stageGroups = stageGroups;

		_isValid = check();
	}

	/**
	 * Returns TRUE if this program contains valid data, FALSE if not.
	 */
	private boolean check() {
		if (_id == 0) {
			return false;
		}

		if (_name == null) {
			return false;
		}

		if (_name.isEmpty()) {
			return false;
		}

		if (_description == null) {
			return false;
		}

		if (_description.isEmpty()) {
			return false;
		}

		if (_stages == null) {
			return false;
		}

		if (_stages.size() < 1) {
			return false;
		}

		for (WasherProgramStage stage : _stages) {
			if (!stage.isValid()) {
				return false;
			}
		}

		if (_stageGroups == null) {
			return false;
		}

		if (_stageGroups.size() < 1) {
			return false;
		}

		for (String stageGroup : _stageGroups) {
			if (stageGroup == null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Parse program, represented as string.
	 * String must contain data in the following pattern:
	 * id;name;description;stage1;stage2;stage3;...;stageN
	 * @param str - string to parse
	 */
	public void fromString(String str) {
		try {

			String[] values = str.split(";"); //str.split(";");

			if (values.length < 5) {
				throw new Exception("Invalid WasherProgram input. Not enough data to parse."); // exit
			}

			_id = Integer.parseInt(values[0]);
			_name = values[1];
			_description = values[2];

			//_description = new String(values[2].getBytes("US-ASCII"), StandardCharsets.UTF_8);

			for (int i = 3; i < values.length - 1; ++i) {
				WasherProgramStage stage = new WasherProgramStage();
				stage.fromString(values[i]);

				if (stage.isValid()) {

					_stages.add(stage);
				}
			}

			_stageGroups = new ArrayList<String>(Arrays.asList(values[values.length - 1].split(",")));

			_isValid = check();

		} catch (Exception ex) {
			System.err.println(str);

			ex.printStackTrace();

			// reset
			reset();
		}
	}

	@Override
	public String toString() {

		return null;
	}

	public void fromByteArray(byte[] array) {
		//StringBuffer sb = new StringBuffer();

		try {
			/*
			for (int i = 0; i < array.length; ++i) {
				sb.append((char) array[i]);
			}
			*/

			fromString(new String(array, "UTF-8"));//StandardCharsets.UTF_8));

		} catch (Exception ex) {
			ex.printStackTrace();

			// reset
			reset();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		try {
			WasherProgram other = (WasherProgram) obj;

			if (_id != other._id) {
				return false;
			}

			if (!_name.equals(other._name)) {
				return false;
			}

			return true;
		}
		catch (Exception ex) {
			ex.printStackTrace();

			return false;
		}
	}

	public int getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getDescription() {
		return _description;
	}

	public ArrayList<WasherProgramStage> getStages() {
		return _stages;
	}

	public ArrayList<String> getStageGroups() {
		return _stageGroups;
	}

	public boolean isValid() {
		return _isValid;
	}

	public void setId(int id){
		_id = id;

		_isValid = check();
	}

	public void setName(String name) {
		if (name != null) {
			_name = name;

			_isValid = check();
		}
	}

	public void setDescription(String description) {
		if (description != null) {
			_description = description;

			_isValid = check();
		}
	}

	public void setStages(ArrayList<WasherProgramStage> stages) {
		if (stages != null) {
			_stages = stages;

			_isValid = check();
		}
	}

	public void addStage(WasherProgramStage stage) {
		if (stage != null) {
			_stages.add(stage);

			_isValid = check();
		}
	}

	public void setStageGroups(ArrayList<String> stageGroups) {
		if (stageGroups != null) {
			_stageGroups = stageGroups;

			_isValid = check();
		}
	}

	private void reset() {
		_id = 0;
		_name = new String();
		_description = new String();
		_stages = new ArrayList<>();
		_stageGroups = new ArrayList<>();

		_isValid = false;
	}
}
