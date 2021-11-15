package com.mygdx.game.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Elements.Button;
import com.mygdx.game.android.Elements.GroupBox;
import com.mygdx.game.android.Elements.ProgressBar;
import com.mygdx.game.android.Elements.TextBox;
import com.mygdx.game.android.Elements.TextView;
import com.mygdx.game.android.Elements.TexturePlurality;
import com.mygdx.game.android.Main;

import java.io.IOException;

/**
 * class used to show laundry process values and isShowed
 */
public class LaundryProcessScreen implements Screen, InputProcessor {
    private Main _main;
    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    //private TextViewCenter _workStatus;
    //private TextViewCenter _workStatusDescription;
    private ProgressBar _laundryProgressBar;
    //private WashingMachine _washing_machine_visualStatus;
    private TexturePlurality _heat;
    private TexturePlurality _water_flow;

    private TextBox _textBox_weight;
    private TextBox _textBox_clientID;
    private TextBox _textBox_program;
    private TextBox _textBox_final_temperature;
    private TextBox _textBox_currentStageGroup;
    //private TextBox _textBox_progress;
    private TextView _text_progress;

    private TextBox _textBox_waterConsumption;
    private TextBox _textBox_electrical_energy_total;
    private TextBox _textBox_electrical_current;
    private Button _chemicals_button;
    private Button _continue_button;
    private Button _finish_button;

    private float _timer;
    private float _description_timer;
    private float _visual_status_update_action_timer;

    private GroupBox _laundryData_groupBox;
    private GroupBox _inputData_groupBox;
    private GroupBox _waterAndElectricity_groupBox;
    private GroupBox _actions_groupBox;

    private boolean _previously_connected = true;
    private boolean _currently_connected = true;
    private float _timerToCheckConnection;
    private final float _connectionCheckTime = 20;

    private String _temp_text = "";

    public LaundryProcessScreen(Main main, SpriteBatch batch, ShapeRenderer renderer, OrthographicCamera camera) {
        if (main == null || batch == null || renderer == null || camera == null) {
            throw new NullPointerException("LaundryProcessScreen initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = camera;
        _description_timer = 0;
        _timer = 0;
        initializeLeftGroup();
        initializeRightGroup();
    }

    /**
     * initialize left column with washer data and isShowed bar
     */
    private void initializeLeftGroup() {
        float element_height = (Main.SCREEN_HEIGHT - _main._status.get_height() * 3) / 17;


        _textBox_weight = new TextBox();
        _textBox_weight.Initialize(Main.SCREEN_WIDTH / 30 + _main._status.get_status_width() * 3 / 2 - _main._status.get_status_width() / 2 - (_main._status.get_status_width() / 2) / 5,
                Main.SCREEN_HEIGHT - (_main._status.get_height() * 3 / 2) - element_height * 2,
                _main._status.get_status_width() / 2, element_height,
                4, "0", TextValues.laundryProcess.weight,
                Textures.get_empty(), Color.BLACK, Main._little_font_fileHandle);
        _textBox_clientID = new TextBox();
        _textBox_clientID.Initialize(_textBox_weight.get_width_position(),
                _textBox_weight.get_height_position() - element_height * 2,
                _main._status.get_status_width() / 2, element_height,
                4, "0", TextValues.laundryProcess.clientID,
                Textures.get_empty(), Color.BLACK, Main._little_font_fileHandle);
        _textBox_program = new TextBox();
        _textBox_program.Initialize(_textBox_clientID.get_width_position(),
                _textBox_clientID.get_height_position() - element_height * 2,
                _main._status.get_status_width() / 2, element_height,
                4, "0", TextValues.laundryProcess.programName,
                Textures.get_empty(), Color.BLACK, Main._little_font_fileHandle);


        _textBox_final_temperature = new TextBox();
        _textBox_final_temperature.Initialize(_textBox_program.get_width_position(),
                _textBox_program.get_height_position() - element_height * 3,
                _main._status.get_status_width() / 2, element_height,
                4, "0", TextValues.laundryProcess.temperature,
                Textures.get_empty(), Color.BLACK, Main._little_font_fileHandle);

        _textBox_currentStageGroup = new TextBox();
        _textBox_currentStageGroup.Initialize(_textBox_final_temperature.get_width_position(),
                _textBox_final_temperature.get_height_position() - element_height * 2,
                _main._status.get_status_width() / 2, element_height,
                4, "этап", TextValues.laundryProcess.currentStageGroup,
                Textures.get_empty(), Color.BLACK, Main._little_font_fileHandle);


        _text_progress = new TextView(Main.SCREEN_WIDTH / 30,
                _textBox_currentStageGroup.get_height_position() - element_height * 2,
                "", Main._little_font_fileHandle, Color.BLACK);

        _laundryProgressBar = new ProgressBar(Main.SCREEN_WIDTH / 30,
                _main._status.get_height() * 3 / 2 + element_height,
                _main._status.get_status_width() * 3 / 2,
                element_height * 2,
                20,
                Textures.get_textBox_wide_background(), Textures.get_progress_bar_fill_element());


        _textBox_weight.setFixedDescriptionWidthPosition(_laundryProgressBar.get_width_position());
        _textBox_clientID.setFixedDescriptionWidthPosition(_laundryProgressBar.get_width_position());
        _textBox_program.setFixedDescriptionWidthPosition(_laundryProgressBar.get_width_position());
        _textBox_final_temperature.setFixedDescriptionWidthPosition(_laundryProgressBar.get_width_position());
        _textBox_currentStageGroup.setFixedDescriptionWidthPosition(_laundryProgressBar.get_width_position());

        _inputData_groupBox = new GroupBox(_laundryProgressBar.get_width_position() - _textBox_weight.get_height() / 2,
                _textBox_program.get_height_position() - _textBox_weight.get_height() / 2,
                _laundryProgressBar.get_width_position() + _laundryProgressBar.get_width() + _textBox_weight.get_height() / 2,
                _textBox_weight.get_height_position() + _textBox_weight.get_height() + _textBox_weight.get_height() / 2);

        _laundryData_groupBox = new GroupBox(_laundryProgressBar.get_width_position() - _textBox_weight.get_height() / 2,
                _laundryProgressBar.get_height_position() - _textBox_weight.get_height() / 2,
                _laundryProgressBar.get_width_position() + _laundryProgressBar.get_width() + _textBox_weight.get_height() / 2,
                _textBox_final_temperature.get_height_position() + _textBox_final_temperature.get_height() + _textBox_final_temperature.get_height() / 2);
    }

    /**
     * initialize right column with washer isShowed and finish button
     */
    private void initializeRightGroup() {
        _finish_button = new Button();
        _finish_button.initialize(Main.SCREEN_WIDTH - _main._status.get_status_width() - _laundryProgressBar.get_height(),
                _laundryProgressBar.get_height_position(),
                _main._status.get_status_width(), _laundryProgressBar.get_height(),
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.laundryProcess.finishButton, Color.BLACK, Main._medium_font_fileHandle);

        _continue_button = new Button();
        _continue_button.initialize(_finish_button.get_width_position(),
                _finish_button.get_height_position() + _finish_button.get_height() + _finish_button.get_height() / 2,
                _finish_button.get_width(),
                _finish_button.get_height(),
                Textures.get_green_button_pushed(),
                Textures.get_green_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.laundryProcess.continue_program, Color.BLACK, Main._medium_font_fileHandle);

        _chemicals_button = new Button();
        _chemicals_button.initialize(_finish_button.get_width_position() + _finish_button.get_width() / 2,
                _continue_button.get_height_position() + _continue_button.get_height() + _continue_button.get_height() / 2,
                _finish_button.get_width() / 2,
                _finish_button.get_height(),
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.laundryProcess.chemicals, Color.BLACK, Main._medium_font_fileHandle);

        _textBox_waterConsumption = new TextBox();
        _textBox_waterConsumption.Initialize(_finish_button.get_width_position() + _finish_button.get_width() - _chemicals_button.get_width() / 2,
                _textBox_weight.get_height_position(),
                _chemicals_button.get_width() / 2, _textBox_weight.get_height(),
                4, "0", TextValues.laundryProcess.waterConsumption,
                Textures.get_empty(), Color.BLACK, Main._little_font_fileHandle);

        _textBox_electrical_energy_total = new TextBox();
        _textBox_electrical_energy_total.Initialize(_textBox_waterConsumption.get_width_position(),
                _textBox_waterConsumption.get_height_position() - _textBox_waterConsumption.get_height() * 2,
                _textBox_waterConsumption.get_width(), _textBox_waterConsumption.get_height(),
                4, "0", TextValues.laundryProcess.electricalEnergyTotal,
                Textures.get_empty(), Color.BLACK, Main._little_font_fileHandle);

        _textBox_electrical_current = new TextBox();
        _textBox_electrical_current.Initialize(_textBox_waterConsumption.get_width_position(),
                _textBox_electrical_energy_total.get_height_position() - _textBox_waterConsumption.get_height() * 2,
                _textBox_waterConsumption.get_width(), _textBox_waterConsumption.get_height(),
                4, "0", TextValues.laundryProcess.electricalActivePower,
                Textures.get_empty(), Color.BLACK, Main._little_font_fileHandle);


        _textBox_waterConsumption.setFixedDescriptionWidthPosition(_finish_button.get_width_position());
        _textBox_electrical_energy_total.setFixedDescriptionWidthPosition(_finish_button.get_width_position());
        _textBox_electrical_current.setFixedDescriptionWidthPosition(_finish_button.get_width_position());


        Texture[] heat_textures = new Texture[2];
        heat_textures[0] = Textures.get_heat_inactive();
        heat_textures[1] = Textures.get_heat_active();

        _heat = new TexturePlurality(_finish_button.get_width_position(),
                _continue_button.get_height_position() + _continue_button.get_height() + _continue_button.get_height() / 2,
                _chemicals_button.get_height(), _chemicals_button.get_height(),
                heat_textures, 1);

        Texture[] water_flow_textures = new Texture[2];
        water_flow_textures[0] = Textures.get_water_flow_inactive();
        water_flow_textures[1] = Textures.get_water_flow_active();
        _water_flow = new TexturePlurality(_heat.get_width_position() + _heat.get_width() + _heat.get_width() / 2,
                _heat.get_height_position(),
                _chemicals_button.get_height(), _chemicals_button.get_height(), water_flow_textures, 1);

        _finish_button.disable();


        _waterAndElectricity_groupBox = new GroupBox(_finish_button.get_width_position() - _laundryProgressBar.get_height() / 4,
                _textBox_electrical_current.get_height_position() - _laundryProgressBar.get_height() / 4,
                _finish_button.get_width_position() + _finish_button.get_width() + _laundryProgressBar.get_height() / 4,
                _textBox_waterConsumption.get_height_position() + _textBox_waterConsumption.get_height() + _laundryProgressBar.get_height() / 4);


        _actions_groupBox = new GroupBox(_finish_button.get_width_position() - _laundryProgressBar.get_height() / 4,
                _laundryData_groupBox.get_start_height_position(),
                _finish_button.get_width_position() + _finish_button.get_width() + _laundryProgressBar.get_height() / 4,
                _laundryData_groupBox.get_end_height_position());

    }

    @Override
    public void render(float delta) {
/**
 * update
 */
        /**
         * delay, used for check touching screen only after few seconds
         */
        if (_timer < _main._delay) _timer += delta;
        if (_visual_status_update_action_timer < 3) _visual_status_update_action_timer += delta;
        updateBeforeDraw(delta);
/**
 * clear and draw background
 */
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /**
         * draw
         */
        draw();
    }

    /**
     * update values before draw
     */
    private void updateBeforeDraw(float delta) {
        /**
         * check program if it is connected and show error box if it is
         */
        if (_timerToCheckConnection >= _connectionCheckTime) {
            _timerToCheckConnection = 0;
            if (!_main._controller.isConnected()) {
                _currently_connected = false;
            } else {
                _currently_connected = true;
            }
            if ((_previously_connected == _currently_connected) && (!_previously_connected)) {
                //// TODO: 01.12.16 add showing error box if not connected on laundry process screen
                //// TODO: 01.12.16 *edited     TEST THIS CODE
                _main._status.showError("Нет соединения с сервером!");
            }
            _previously_connected = _currently_connected;
        } else {
            _timerToCheckConnection += delta;
        }
        /**
         * update description of current program, if laundry is executing
         */


        if (_main._controller.getState().isExecuteProgram()) {
            if (_description_timer < 3) {
                _description_timer += delta;
            } else {
                _textBox_clientID.updateContent(String.valueOf(_main._controller.getState().getProgramClient()));
                _textBox_weight.updateContent(String.valueOf(_main._controller.getState().getProgramWeight()));
                if (String.valueOf(_main.get_program_name(_main._controller.getState().getProgramCurrent())).length() > 18) {
                    _textBox_program.updateContent(String.valueOf(_main.get_program_name(_main._controller.getState().getProgramCurrent())).substring(0, 16) + "...");
                } else {
                    _textBox_program.updateContent(String.valueOf(_main.get_program_name(_main._controller.getState().getProgramCurrent())));
                }


                /**
                 * update additional work isShowed text, depending on already existing work isShowed description text
                 */
                if (String.valueOf(_main.get_program_name(_main._controller.getState().getProgramCurrent())) != null) {
                    _temp_text = _main.get_program_GroupStage(
                            String.valueOf(_main.get_program_name(_main._controller.getState().getProgramCurrent())),
                            _main._controller.getState().getProgramStageCurrent());
                    if (_temp_text != null) {
                        if (_temp_text.length() > 18) {
                            _temp_text = _temp_text.substring(0, 16) + "...";
                            _textBox_currentStageGroup.updateContent(_temp_text);
                        } else {
                            _textBox_currentStageGroup.updateContent(_temp_text);
                        }

                    }
                    _temp_text = "";
                }
                _description_timer = 0;
            }
        }

        if (_main._controller.getState().getProgramSecondsMaximum() != 0 ||
                _main._controller.getState().getProgramSecondsCurrent() != 0) {

            _text_progress.updateContent(TextValues.laundryProcess.progress + "  " + Integer.toString((int) ((_main._controller.getState().getProgramSecondsCurrent() * 100) /
                    _main._controller.getState().getProgramSecondsMaximum())) + "%"
                    + ", " + TextValues.laundryProcess.time_left +
                    (Integer.toString(
                            (int) ((_main._controller.getState().getProgramSecondsMaximum() -
                                    _main._controller.getState().getProgramSecondsCurrent()) / 60)) +
                            TextValues.laundryProcess.minutes)
            );
        }
        /**
         * switch water flow indicator to active, if one of water valve's is open
         */
        if (_main._controller.getState().isOutWaterValve() ||
                _main._controller.getState().isOutTray1() ||
                _main._controller.getState().isOutTray2() ||
                _main._controller.getState().isOutTray3()) {
            _water_flow.switchTexture(1);
        } else {
            _water_flow.switchTexture(0);
        }

        if (_main._controller.isConnected()) {
            /**
             *  enable continue button, depending on if we could continue
             */
            if (_main._controller.isProgramResumeAvailable()) {
                _continue_button.enable();
            } else {
                _continue_button.disable();
            }
        }
        /**
         * if heat is on: switch water flow indicator to active and show final temperatureField1
         */
        if (_main._controller.getState().isOutElectricalHeat() ||
                _main._controller.getState().isOutSteamHeat()) {
            _heat.switchTexture(1);
            _textBox_final_temperature.updateContent(String.valueOf((float) (Math.rint(10.0 * _main._controller.getState().getOtherTemperatureMaximum()) / 10.0)));
        } else {
            _heat.switchTexture(0);
        }


        /**
         * update additional values
         */
        _textBox_waterConsumption.updateContent(String.valueOf((float) (Math.rint(10.0 * _main._controller.getState().getOtherWaterConsumption()) / 10.0)));
        _textBox_electrical_energy_total.updateContent(String.valueOf(_main._controller.getState().getElectricEnergyTotal()));
        _textBox_electrical_current.updateContent(String.valueOf(_main._controller.getState().getElectricCurrent()));

        /**
         * update progress bar, depending on current work time and maximum work time of program
         */
        if (_main._controller.getState().getProgramSecondsMaximum() != 0) {
            _laundryProgressBar.updateState((int) (
                    (_main._controller.getState().getProgramSecondsCurrent() * _laundryProgressBar.get_maximum_progress())
                            / _main._controller.getState().getProgramSecondsMaximum()));
        }
        /**
         * enabling finish button, depend on program execution
         * but if connection is failed, finish button enables
         */
        if (_main._controller.isConnected()) {
            if (!_main._controller.getState().isExecuteProgram()) {
                _finish_button.enable();
            } else {
                _finish_button.disable();
            }
        } else {
            _finish_button.enable();
        }

        _cam.update();
    }

    /**
     * draw elements
     */
    private void draw() {
        _batch.begin();
        _main.backgroundSprite.draw(_batch);
        _laundryData_groupBox.drawNoRect(_batch);
        _inputData_groupBox.drawNoRect(_batch);
        _waterAndElectricity_groupBox.drawNoRect(_batch);
        _actions_groupBox.drawNoRect(_batch);
        _textBox_weight.draw(_batch);
        _textBox_clientID.draw(_batch);
        _textBox_program.draw(_batch);
        if (_main._controller.getState().isOutElectricalHeat() ||
                _main._controller.getState().isOutSteamHeat()) {
            _textBox_final_temperature.draw(_batch);
        }
        _textBox_currentStageGroup.draw(_batch);
        _textBox_waterConsumption.draw(_batch);
        _textBox_electrical_energy_total.draw(_batch);
        _textBox_electrical_current.draw(_batch);
        //_textBox_progress.draw(_batch);
        _text_progress.draw(_batch);
        //_workStatus.draw(_batch);
        //_workStatusDescription.draw(_batch);
        _finish_button.draw(_batch);
        _continue_button.draw(_batch);
        _chemicals_button.draw(_batch);
        _heat.draw(_batch);
        _water_flow.draw(_batch);
        _laundryProgressBar.draw(_batch);
        _batch.end();

        _renderer.begin(ShapeType.Line);
        _laundryData_groupBox.drawRect(_renderer);
        _inputData_groupBox.drawRect(_renderer);
        /*_renderer.line(_laundryData_groupBox.get_start_width_position(), _textBox_program.get_height_position() - _textBox_program.get_height() / 2,
                _laundryData_groupBox.get_end_width_position(), _textBox_program.get_height_position() - _textBox_program.get_height() / 2);*/
        _waterAndElectricity_groupBox.drawRect(_renderer);
        _actions_groupBox.drawRect(_renderer);
        _finish_button.drawRect(_renderer);
        _continue_button.drawRect(_renderer);
        _chemicals_button.drawRect(_renderer);
        _renderer.end();

        _batch.begin();
        _main._status.draw(_batch);
        _batch.end();
        _renderer.begin(ShapeType.Line);
        _main._status.drawRect(_renderer);
        _renderer.end();
        _batch.begin();
        _main._status.drawReturnButton(_batch);
        _batch.end();
    }

    /**
     * handling single touch events
     */
    private void handleSingleInput() {
        if (_finish_button.isTouched(_cam)) {
            _main.resetLaundrySetupData();
            _main.ShowLaundrySetupScreen();
        }
        if (_chemicals_button.isTouched(_cam)) {
            try {
                if (_main._controller.getState().isOutTray1()) {
                    _main._controller.addCommand("set;OUT_TRAY1;0");
                } else {
                    _main._controller.addCommand("set;OUT_TRAY1;1");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (_continue_button.isTouched(_cam)) {
            _main._controller.requestProgramResume();
        }
    }

    /**
     * handling long touch events
     */
    private void handleLongPressing() {
        if (_finish_button.isTouched(_cam)) {
            _finish_button.updateState(true);
        }
        if (_chemicals_button.isTouched(_cam)) {
            _chemicals_button.updateState(true);
        }
        if (_continue_button.isTouched(_cam)) {
            _continue_button.updateState(true);
        }
    }

    @Override
    public void resize(int width, int height) {
        _cam.viewportWidth = Main.SCREEN_WIDTH;
        _cam.viewportHeight = Main.SCREEN_HEIGHT;
        _cam.update();
    }

    @Override
    public void show() {
        _main._status.hideReturnButton();
        _main._status.updateNavigation(TextValues.navigation.laundryProcessScreen);
        _timer = 0;
        _description_timer = 0;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        _main._status.showReturnButton();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        _laundryProgressBar.dispose();
        _heat.dispose();
        _water_flow.dispose();
        _textBox_weight.dispose();
        _textBox_clientID.dispose();
        _textBox_program.dispose();
        _textBox_final_temperature.dispose();

        _textBox_waterConsumption.dispose();

        _finish_button.dispose();
        //_current_status_groupBox.dispose();
        _waterAndElectricity_groupBox.dispose();
        _laundryData_groupBox.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (_timer >= _main._delay) {
            if (_main._status.is_error()) {
                _main._status.handleLongTermDepressionWarnings();
            } else {
                _main._status.handleLongTermDepression();
                handleLongPressing();
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (_timer >= _main._delay) {
            if (_main._status.is_error()) {
                _main._status.handleSingleInputWarnings();
                _main._status.dePressWarnings();
            } else {
                _main._status.handleSingleInput();
                handleSingleInput();
                _finish_button.updateState(false);
                _continue_button.updateState(false);
                _chemicals_button.updateState(false);
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
