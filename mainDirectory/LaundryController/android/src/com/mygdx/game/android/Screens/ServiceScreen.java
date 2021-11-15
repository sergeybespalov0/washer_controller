package com.mygdx.game.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Application.Controller;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Elements.ConfirmBox;
import com.mygdx.game.android.Elements.GroupBox;
import com.mygdx.game.android.Elements.Button;
import com.mygdx.game.android.Elements.StatusIndicator;
import com.mygdx.game.android.Elements.SwitchElement;
import com.mygdx.game.android.Interfaces.IDataTransfer;
import com.mygdx.game.android.Main;

/**
 * class used to provide service to system
 */
public class ServiceScreen implements IDataTransfer, InputProcessor {

    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    private Main _main;
    private ConfirmBox _confirm;
    private GroupBox _drum_groupBox;
    private GroupBox _water_groupBox;
    private GroupBox _other_groupBox;
    private GroupBox _actions_groupBox;

    private BitmapFont _label_waterConsumption_textFont;
    private BitmapFont _label_temperature_textFont;
    private BitmapFont _label_drumRPM_textFont;

    private GlyphLayout _waterConsumption_glyphLayout;
    private GlyphLayout _temperature_glyphLayout;
    private GlyphLayout _drumRPM_glyphLayout;

    private Button _dropSettings_button;

    private StatusIndicator _drumPositionSensor;
    private StatusIndicator _clear_door_status;
    private StatusIndicator _waterLevel0;
    private StatusIndicator _waterLevel1;
    private StatusIndicator _waterLevel2;
    private StatusIndicator _waterLevel3;
    private StatusIndicator _waterLevel4;

    private SwitchElement _spin1;
    private SwitchElement _spin2;
    private SwitchElement _washRight;
    private SwitchElement _washLeft;
    private SwitchElement _deceleration;
    private SwitchElement _power;

    private SwitchElement _electricalHeat;
    private SwitchElement _steamHeat;

    private SwitchElement _sink;


    private SwitchElement _waterValve;
    private SwitchElement _tray1;
    private SwitchElement _tray2;
    private SwitchElement _tray3;


    /**
     * timers for each button
     * they are used for program to show changing of element after pressing it for 1.5 sec
     * and then update it to states of server
     */
    private float _extraction1_timer;
    private float _extraction2_timer;
    private float _washRight_timer;
    private float _washLeft_timer;
    private float _deceleration_timer;
    private float _power_timer;
    private float _setDrumPosition_timer;

    private float _electricalHeat_timer;
    private float _steamHeat_timer;
    private float _releaseDoor_timer;

    private float _sink_timer;
    private float _lockup_timer;

    private float _waterValve_timer;
    private float _tray1_timer;
    private float _tray2_timer;
    private float _tray3_timer;
    private final float timeToUpdateStates = (float) 1.5;
    private float _timer;

    private float _temperatureSPC;
    private float _button_width;
    private float _button_height;
    private float _space_between_button_width;
    private float _space_between_button_height;

    private int _action = 0;
    private String _buffer = "";

    public ServiceScreen(Main main, SpriteBatch batch, ShapeRenderer renderer, OrthographicCamera camera) {
        if (main == null || batch == null || renderer == null || camera == null) {
            throw new NullPointerException("ServiceScreen initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = camera;
        _timer = 0;
        _confirm = new ConfirmBox(TextValues.serviceScreen.enablePower, TextValues.serviceScreen.confirmAction, _cam);
        _space_between_button_width = Main.SCREEN_WIDTH / 26;
        _button_width = _space_between_button_width * 3;
        _space_between_button_height = (Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8) / 16;
        _button_height = _space_between_button_height;

        float column1 = _space_between_button_width;
        initializeDrumElements(column1);
        float column2 = (float) (_deceleration.get_width_position() + _deceleration.get_width() + _space_between_button_width * 1.5);
        initializeValveElements(column2);
        float column3 = (float) (_tray2.get_width_position() + _tray2.get_width() + _space_between_button_width * 1.5);
        initializeHeatElements(column3);
        initializeOther();
    }

    /**
     * initialize first column of elements
     *
     * @param column - horizontal position
     */
    private void initializeDrumElements(float column) {
        _power = new SwitchElement();
        _power.Initialize(column, Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8 - _space_between_button_height * 2,
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.power);
        _deceleration = new SwitchElement();
        _deceleration.Initialize(_power.get_width_position() + _power.get_width() + _space_between_button_width,
                _power.get_height_position(),
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.deceleration);
        _washRight = new SwitchElement();
        _washRight.Initialize(column,
                _power.get_height_position() - _button_height - _space_between_button_height,
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.washRight);
        _washLeft = new SwitchElement();
        _washLeft.Initialize(_washRight.get_width_position() + _washRight.get_width() + _space_between_button_width,
                _washRight.get_height_position(),
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.washLeft);
        _spin1 = new SwitchElement();
        _spin1.Initialize(column,
                _washRight.get_height_position() - _button_height - _space_between_button_height,
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.spin1);
        _spin2 = new SwitchElement();
        _spin2.Initialize(column,
                _spin1.get_height_position() - _button_height - _space_between_button_height,
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.spin2);

        _drumPositionSensor = new StatusIndicator(1, column,
                _spin2.get_height_position() - _button_height - _space_between_button_height,
                _button_height,
                Textures.get_indicator_white(), Textures.get_indicator_yellow(),
                Main._little_font,
                _main._button_textColor, TextValues.serviceScreen.drumPositionSensor);


        /**
         * drum rpm(rates per minute)
         */
        _drumRPM_glyphLayout = new GlyphLayout();
        _label_drumRPM_textFont = new BitmapFont(Main._little_font_fileHandle);
        _label_drumRPM_textFont.setColor(_main._button_textColor);
        _drumRPM_glyphLayout.setText(_label_drumRPM_textFont, String.valueOf(_main._controller.getState().getOtherDrumRPM()));
    }

    /**
     * initialize second column of elements
     *
     * @param column - horizontal position
     */
    private void initializeValveElements(float column) {
/**
 * water consumption
 * потребление воды
 */
        _waterConsumption_glyphLayout = new GlyphLayout();
        _label_waterConsumption_textFont = new BitmapFont(Main._little_font_fileHandle);
        _label_waterConsumption_textFont.setColor(_main._button_textColor);
        _waterConsumption_glyphLayout.setText(_label_waterConsumption_textFont, String.valueOf(_main._controller.getState().getOtherWaterConsumption()));
        /**
         * drain
         */

        _sink = new SwitchElement();
        _sink.Initialize(column + _button_width + _space_between_button_width / 2 - _button_width / 2,
                _deceleration.get_height_position(),
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.sink);
        /**
         * water
         */
        _tray1 = new SwitchElement();
        _tray1.Initialize(column,
                _sink.get_height_position() - _space_between_button_height - _button_height,
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.tray1);
        _tray2 = new SwitchElement();
        _tray2.Initialize(_tray1.get_width() + _tray1.get_width_position() + _space_between_button_width,
                _tray1.get_height_position(),
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.tray2);
        _tray3 = new SwitchElement();
        _tray3.Initialize(_tray1.get_width_position(),
                _tray1.get_height_position() - _button_height - _space_between_button_height,
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.tray3);
        _waterValve = new SwitchElement();
        _waterValve.Initialize(_tray2.get_width_position(),
                _tray3.get_height_position(),
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.waterValve);


        /**
         * door indicator
         * индикатор двери
         */
        _clear_door_status = new StatusIndicator(1, _tray3.get_width_position(),
                _tray3.get_height_position() - _button_height - _space_between_button_height * 2,
                _button_height,
                Textures.get_indicator_red(), Textures.get_indicator_green(),
                Main._little_font,
                _main._button_textColor, TextValues.serviceScreen.clearDoor);


    }

    /**
     * initialize third column of elements
     *
     * @param column - horizontal position
     */
    private void initializeHeatElements(float column) {
        _electricalHeat = new SwitchElement();
        _electricalHeat.Initialize(column,
                _sink.get_height_position(),
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.electricalHeat);
        _steamHeat = new SwitchElement();
        _steamHeat.Initialize(_electricalHeat.get_width_position() + _electricalHeat.get_width() + _space_between_button_width,
                _electricalHeat.get_height_position(),
                _button_width, _button_height,
                Main._little_font_fileHandle, _main._button_textColor, TextValues.serviceScreen.steamHeat);
        /**
         * waterLevel indicators
         */
        _waterLevel0 = new StatusIndicator(1, _electricalHeat.get_width_position(),
                _electricalHeat.get_height_position() - _space_between_button_height * 2 - _button_height,
                _button_height,
                Textures.get_indicator_white(), Textures.get_indicator_green(),
                Main._little_font,
                _main._button_textColor, TextValues.serviceScreen.waterLevel0);
        _waterLevel1 = new StatusIndicator(1, _waterLevel0.get_width_position(),
                _waterLevel0.get_height_position() - _space_between_button_height / 4 - _button_height,
                _button_height,
                Textures.get_indicator_white(), Textures.get_indicator_green(),
                Main._little_font,
                _main._button_textColor, TextValues.serviceScreen.waterLevel1);
        _waterLevel2 = new StatusIndicator(1, _waterLevel1.get_width_position(),
                _waterLevel1.get_height_position() - _space_between_button_height / 4 - _button_height,
                _button_height,
                Textures.get_indicator_white(), Textures.get_indicator_yellow(),
                Main._little_font,
                _main._button_textColor, TextValues.serviceScreen.waterLevel2);
        _waterLevel3 = new StatusIndicator(1, _waterLevel2.get_width_position(),
                _waterLevel2.get_height_position() - _space_between_button_height / 4 - _button_height,
                _button_height,
                Textures.get_indicator_white(), Textures.get_indicator_yellow(),
                Main._little_font,
                _main._button_textColor, TextValues.serviceScreen.waterLevel3);
        _waterLevel4 = new StatusIndicator(1, _waterLevel3.get_width_position(),
                _waterLevel3.get_height_position() - _space_between_button_height / 4 - _button_height,
                _button_height,
                Textures.get_indicator_white(), Textures.get_indicator_red(),
                Main._little_font,
                _main._button_textColor, TextValues.serviceScreen.waterLevel4);
        /**
         * temperatureField1
         * температура
         */
        _temperature_glyphLayout = new GlyphLayout();
        _label_temperature_textFont = new BitmapFont(Main._little_font_fileHandle);
        _label_temperature_textFont.setColor(_main._button_textColor);
        _temperature_glyphLayout.setText(_label_temperature_textFont, String.valueOf(_main._controller.getState().getOtherTemperatureCurrent()));
        GlyphLayout tempGlyph = new GlyphLayout();
        tempGlyph.setText(_label_temperature_textFont, TextValues.serviceScreen.temperatureField1);
        _temperatureSPC = tempGlyph.width;
    }

    /**
     * initialize other elements
     */
    private void initializeOther() {
        _drum_groupBox = new GroupBox(_power.get_width_position() - _space_between_button_width / 2,
                0 + _main._status.get_height() + _space_between_button_height / 2,
                _deceleration.get_width_position() + _deceleration.get_width() + _space_between_button_width / 2,
                Main.SCREEN_HEIGHT - _main._status.get_height() - _space_between_button_height / 2);
        _water_groupBox = new GroupBox(_tray1.get_width_position() - _space_between_button_width / 2,
                0 + _main._status.get_height() + _space_between_button_height / 2,
                _tray2.get_width_position() + _tray2.get_width() + _space_between_button_width / 2,
                Main.SCREEN_HEIGHT - _main._status.get_height() - _space_between_button_height / 2);
        _other_groupBox = new GroupBox(_waterLevel4.get_width_position() - _space_between_button_width / 2,
                _waterLevel4.get_height_position() - _space_between_button_height / 2,
                _steamHeat.get_width_position() + _steamHeat.get_width() + _space_between_button_width / 2,
                Main.SCREEN_HEIGHT - _main._status.get_height() - _space_between_button_height / 2);
        _dropSettings_button = new Button();
        _dropSettings_button.initialize(_waterLevel4.get_width_position(),
                0 + _main._status.get_height() + _space_between_button_height,
                _button_width / 2 * 3, _button_height * 5 / 2,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.serviceScreen.dropSettings,
                _main._button_textColor, Main._little_font_fileHandle);
        _actions_groupBox = new GroupBox(_dropSettings_button.get_width_position() - _space_between_button_width / 2,
                _dropSettings_button.get_height_position() - _space_between_button_height / 2,
                _dropSettings_button.get_width_position() + _dropSettings_button.get_width() + _space_between_button_width / 2,
                _dropSettings_button.get_height_position() + _dropSettings_button.get_height() + _space_between_button_height / 2);
    }

    /**
     * update values before draw
     */
    private void updateBeforeDraw() {
        /**
         * update elements depending on server data
         */
        getData();
        /**
         * update service values
         */
        _waterConsumption_glyphLayout.setText(_label_waterConsumption_textFont, TextValues.serviceScreen.data_waterConsumption
                + String.valueOf((float) (Math.rint(10.0 * _main._controller.getState().getOtherWaterConsumption()) / 10.0)));
        _temperature_glyphLayout.setText(_label_temperature_textFont, TextValues.serviceScreen.data_temperature +
                String.valueOf((float) (Math.rint(10.0 * _main._controller.getState().getOtherTemperatureCurrent()) / 10.0)));
        _drumRPM_glyphLayout.setText(_label_drumRPM_textFont, TextValues.serviceScreen.data_drumRPM + String.valueOf(_main._controller.getState().getOtherDrumRPM()));


        _cam.update();
        /**
         * do some actions, depending on pushed confirm menu buttons
         */
        if (_confirm.isFinished()) {
            switch (_action) {
                /**
                 * in case of pushing 'yes'
                 * deleting connection info and restarting app
                 */
                case 1: {
                    _main.deleteConnectionInfo();
                    _main.Restart();
                    _action = 0;
                    return;
                }
                /**
                 * in case of pushing 'no'
                 * send data to server and show changes on interface
                 */
                case 2: {
                    //// TODO: 8/22/16 change from [out_brake] to [out_power]
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_lockDirtyDoor);
                    _power.update(true);
                    _power_timer = 0;
                    _confirm.notFinished();
                    _action = 0;
                    return;
                }
                default: {
                }
            }
        }
    }

    /**
     * draw elements
     */
    private void draw() {
        _batch.begin();
        _main.backgroundSprite.draw(_batch);
        _drum_groupBox.drawNoRect(_batch);
        _water_groupBox.drawNoRect(_batch);
        _other_groupBox.drawNoRect(_batch);
        _actions_groupBox.drawNoRect(_batch);

        _clear_door_status.draw(_batch);
        _drumPositionSensor.draw(_batch);
        _waterLevel0.draw(_batch);
        _waterLevel1.draw(_batch);
        _waterLevel2.draw(_batch);
        _waterLevel3.draw(_batch);
        _waterLevel4.draw(_batch);
        _dropSettings_button.draw(_batch);
        /**
         * text
         */
        _label_drumRPM_textFont.draw(_batch, _drumRPM_glyphLayout,
                _drumPositionSensor.get_width_position(),
                _drumPositionSensor.get_height_position() - _space_between_button_height);
        _label_waterConsumption_textFont.draw(_batch, _waterConsumption_glyphLayout,
                _tray3.get_width_position(),
                _tray3.get_height_position() - _space_between_button_height);
        _label_temperature_textFont.draw(_batch, _temperature_glyphLayout,
                _electricalHeat.get_width_position(),
                _electricalHeat.get_height_position() - _space_between_button_height);
        /**
         * draw
         */
        _spin1.draw(_batch);
        _spin2.draw(_batch);
        _washLeft.draw(_batch);
        _washRight.draw(_batch);
        _power.draw(_batch);
        _deceleration.draw(_batch);
        _electricalHeat.draw(_batch);
        _steamHeat.draw(_batch);
        //_setDrumPosition.draw(_batch);
        _sink.draw(_batch);
        _waterValve.draw(_batch);
        _tray1.draw(_batch);
        _tray2.draw(_batch);
        _tray3.draw(_batch);

        _batch.end();
        _renderer.begin(ShapeType.Line);

        _renderer.circle(_electricalHeat.get_width_position() + _temperatureSPC,
                _electricalHeat.get_height_position() - _temperature_glyphLayout.height - _space_between_button_height + (float) (_temperature_glyphLayout.height / 4 * 2.4),
                _temperature_glyphLayout.height / 4);
        _renderer.circle(_electricalHeat.get_width_position() + _temperatureSPC,
                _electricalHeat.get_height_position() - _temperature_glyphLayout.height - _space_between_button_height + (float) (_temperature_glyphLayout.height / 4 * 2.4),
                _temperature_glyphLayout.height / 4 - 1);

        _drum_groupBox.drawRect(_renderer);
        _water_groupBox.drawRect(_renderer);
        _other_groupBox.drawRect(_renderer);
        _actions_groupBox.drawRect(_renderer);

        _spin1.drawRect(_renderer);
        _spin2.drawRect(_renderer);
        _washLeft.drawRect(_renderer);
        _washRight.drawRect(_renderer);
        _power.drawRect(_renderer);
        _deceleration.drawRect(_renderer);
        _electricalHeat.drawRect(_renderer);
        _steamHeat.drawRect(_renderer);
        //_setDrumPosition.drawRect(_renderer);
        _dropSettings_button.drawRect(_renderer);

        _sink.drawRect(_renderer);
        _waterValve.drawRect(_renderer);
        _tray1.drawRect(_renderer);
        _tray2.drawRect(_renderer);
        _tray3.drawRect(_renderer);

        _renderer.end();
        _confirm.draw(_batch, _cam);

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

    @Override
    public void render(float delta) {

        /**
         * updating data
         */
        if (_timer < _main._delay) _timer += delta;

        timerIncrement(delta);
        updateBeforeDraw();
        //checkData();

/**
 *  clear and draw background
 */
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /**
         * indicators
         */
        draw();
    }

    /**
     * lock, used to correct view of elements
     */
    private void timerIncrement(float delta) {
        /**
         * timer increment
         */
        if (_extraction1_timer <= timeToUpdateStates) {
            _extraction1_timer += delta;
        }
        if (_extraction2_timer <= timeToUpdateStates) {
            _extraction2_timer += delta;
        }
        if (_washRight_timer <= timeToUpdateStates) {
            _washRight_timer += delta;
        }
        if (_washLeft_timer <= timeToUpdateStates) {
            _washLeft_timer += delta;
        }
        if (_deceleration_timer <= timeToUpdateStates) {
            _deceleration_timer += delta;
        }
        if (_power_timer <= timeToUpdateStates) {
            _power_timer += delta;
        }
        if (_setDrumPosition_timer <= timeToUpdateStates) {
            _setDrumPosition_timer += delta;
        }
        if (_electricalHeat_timer <= timeToUpdateStates) {
            _electricalHeat_timer += delta;
        }
        if (_steamHeat_timer <= timeToUpdateStates) {
            _steamHeat_timer += delta;
        }
        if (_releaseDoor_timer <= timeToUpdateStates) {
            _releaseDoor_timer += delta;
        }
        if (_sink_timer <= timeToUpdateStates) {
            _sink_timer += delta;
        }
        if (_lockup_timer <= timeToUpdateStates) {
            _lockup_timer += delta;
        }
        if (_waterValve_timer <= timeToUpdateStates) {
            _waterValve_timer += delta;
        }
        if (_tray1_timer <= timeToUpdateStates) {
            _tray1_timer += delta;
        }
        if (_tray2_timer <= timeToUpdateStates) {
            _tray2_timer += delta;
        }
        if (_tray3_timer <= timeToUpdateStates) {
            _tray3_timer += delta;
        }
    }

    /**
     * handle single touch events
     */
    private void handleSingleInput() {

/**
 * choosing what to handle:
 * warning box or else
 */
        //if (Gdx.input.justTouched()) {
        if (!_confirm.isShowed()) {
            if (_dropSettings_button.isTouched(_cam)) {
                _action = 1;
                _confirm.show();
                _confirm.updateContent(TextValues.serviceScreen.drop_all_settings);
                return;
            }
            //synchronized (_main._commands._washer_variables) {
            if (_drum_groupBox.isTouched(_cam))
                rotation();
            if (_water_groupBox.isTouched(_cam))
                valve();
            if (_other_groupBox.isTouched(_cam)) {
                if (_electricalHeat_timer > timeToUpdateStates)
                    if (_electricalHeat.isTouched(_cam)) {
                        if (!_main._controller.isConnected()) {
                            _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                            return;
                        }
                        if (_electricalHeat.getState()) {
                            _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_electricalHeat);
                            _electricalHeat.update(false);
                            _electricalHeat_timer = 0;
                            return;
                        } else {
                            _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_electricalHeat);
                            _electricalHeat.update(true);
                            _electricalHeat_timer = 0;
                            return;
                        }
                    }
                if (_steamHeat_timer > timeToUpdateStates)
                    if (_steamHeat.isTouched(_cam)) {
                        if (!_main._controller.isConnected()) {
                            _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                            return;
                        }
                        if (_steamHeat.getState()) {
                            _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_steamHeat);
                            _steamHeat.update(false);
                            _steamHeat_timer = 0;
                        } else {
                            _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_steamHeat);
                            _steamHeat.update(true);
                            _steamHeat_timer = 0;
                        }
                    }
            }

        } else {
            _confirm.handleSingleInput();
        }

        if (_main._status.returnButton_touched(_cam)) {
            _main.ShowPrimaryScreen();
        }
    }

    /**
     * handle valve elements touch
     */
    private void valve() {
        /**
         * handling valve elements
         * each element get's handled no more frequently than every 1.5 seconds.
         */

        if (_sink_timer > timeToUpdateStates)
            if (_sink.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_sink.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_sink);
                    _sink.update(false);
                    _sink_timer = 0;
                    return;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_sink);
                    _sink.update(true);
                    _sink_timer = 0;
                    return;
                }
            }
        if (_waterValve_timer > timeToUpdateStates)
            if (_waterValve.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_waterValve.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_waterValve);
                    _waterValve.update(false);
                    _waterValve_timer = 0;
                    return;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_waterValve);
                    _waterValve.update(true);
                    _waterValve_timer = 0;
                    return;
                }
            }
        if (_tray1_timer > timeToUpdateStates)
            if (_tray1.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_tray1.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_tray1);
                    _tray1.update(false);
                    _tray1_timer = 0;
                    return;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_tray1);
                    _tray1.update(true);
                    _tray1_timer = 0;
                    return;
                }
            }
        if (_tray2_timer > timeToUpdateStates)
            if (_tray2.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_tray2.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_tray2);
                    _tray2.update(false);
                    _tray2_timer = 0;
                    return;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_tray2);
                    _tray2.update(true);
                    _tray2_timer = 0;
                    return;
                }
            }
        if (_tray3_timer > timeToUpdateStates)
            if (_tray3.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_tray3.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_tray3);
                    _tray3.update(false);
                    _tray3_timer = 0;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_tray3);
                    _tray3.update(true);
                    _tray3_timer = 0;
                }
            }
    }

    /**
     * handle drum rotation elements touch
     */
    private void rotation() {
        /**
         * handling rotation elements
         * each element get's handled no more frequently than every 1.5 seconds.
         */
        /**
         * handle of deceleration button
         */
        if (_deceleration_timer > timeToUpdateStates)
            if (_deceleration.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_deceleration.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_deceleration);
                    _deceleration.update(false);
                    _deceleration_timer = 0;
                    return;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_deceleration);
                    _deceleration.update(true);
                    _deceleration_timer = 0;
                    return;
                }
            }
        /**
         * handle of power button
         */
        if (_power_timer > timeToUpdateStates)
            if (_power.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_power.getState()) {
                    _confirm.notFinished();
                    //// TODO: 8/22/16 set from [out_brake] to [out_power]
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_lockDirtyDoor);
                    _power.update(false);
                    _power_timer = 0;
                    return;
                } else {
                    _action = 2;
                    _confirm.show();
                    _confirm.updateContent(TextValues.serviceScreen.enablePower);
                    return;
                }
            }
        /**
         * handles of rotation buttons
         */
        if (_washLeft_timer > timeToUpdateStates)
            if (_washLeft.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_washLeft.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_engineRotateL);
                    _washLeft.update(false);
                    _washLeft_timer = 0;
                    return;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_engineRotateL);
                    _washLeft.update(true);
                    _washLeft_timer = 0;
                    return;
                }
            }
        if (_washRight_timer > timeToUpdateStates)
            if (_washRight.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_washRight.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_engineRotateR);
                    _washRight.update(false);
                    _washRight_timer = 0;
                    return;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_engineRotateR);
                    _washRight.update(true);
                    _washRight_timer = 0;
                    return;
                }
            }
        if (_extraction1_timer > timeToUpdateStates)
            if (_spin1.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_spin1.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_spin1);
                    _spin1.update(false);
                    _extraction1_timer = 0;
                    return;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_spin1);
                    _spin1.update(true);
                    _extraction1_timer = 0;
                    return;
                }
            }
        if (_extraction2_timer > timeToUpdateStates)
            if (_spin2.isTouched(_cam)) {
                if (!_main._controller.isConnected()) {
                    _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                    return;
                }
                if (_spin2.getState()) {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_spin2);
                    _spin2.update(false);
                    _extraction2_timer = 0;
                } else {
                    _main._controller.requestOutputSwitch(Controller.RequestOutputSwitchArgs.out_spin2);
                    _spin2.update(true);
                    _extraction2_timer = 0;
                }
            }
    }

    /**
     * handling long touch events
     */
    private void handleLongPressing() {
        if (_dropSettings_button.isTouched(_cam)) {
            _dropSettings_button.updateState(true);
        } else {
            _dropSettings_button.updateState(false);
        }
    }

    /**
     * getting data from additional variables class and apply them to elements
     */
    private void getData() {
        /**
         * update elements if their timers are OK
         * update depends on server values
         */
        if (_power_timer > timeToUpdateStates)
            _power.update(_main._controller.getState().isOutPower());
        if (_deceleration_timer > timeToUpdateStates)
            _deceleration.update(_main._controller.getState().isOutDeceleration());
        if (_washRight_timer > timeToUpdateStates)
            _washRight.update(_main._controller.getState().isOutEngineRotationR());
        if (_washLeft_timer > timeToUpdateStates)
            _washLeft.update(_main._controller.getState().isOutEngineRotationL());
        if (_extraction1_timer > timeToUpdateStates)
            _spin1.update(_main._controller.getState().isOutSpin1());
        if (_extraction2_timer > timeToUpdateStates)
            _spin2.update(_main._controller.getState().isOutSpin2());

        if (_sink_timer > timeToUpdateStates)
            _sink.update(_main._controller.getState().isOutSink());
        if (_tray1_timer > timeToUpdateStates)
            _tray1.update(_main._controller.getState().isOutTray1());
        if (_tray2_timer > timeToUpdateStates)
            _tray2.update(_main._controller.getState().isOutTray2());
        if (_tray3_timer > timeToUpdateStates)
            _tray3.update(_main._controller.getState().isOutTray3());
        if (_waterValve_timer > timeToUpdateStates)
            _waterValve.update(_main._controller.getState().isOutWaterValve());

        if (_electricalHeat_timer > timeToUpdateStates)
            _electricalHeat.update(_main._controller.getState().isOutElectricalHeat());
        if (_steamHeat_timer > timeToUpdateStates)
            _steamHeat.update(_main._controller.getState().isOutSteamHeat());

        _drumPositionSensor.updateState(_main._controller.getState().isInExtractionPosition());
        _waterLevel0.updateState(_main._controller.getState().isInWaterLeven0());
        _waterLevel1.updateState(_main._controller.getState().isInWaterLevel1());
        _waterLevel2.updateState(_main._controller.getState().isInWaterLevel2());
        _waterLevel3.updateState(_main._controller.getState().isInWaterLevel3());
        _waterLevel4.updateState(_main._controller.getState().isInWaterLevel4());
        _clear_door_status.updateState(_main._controller.getState().isInCleanOpen());
    }

    @Override
    public void resize(int width, int height) {
        _cam.viewportWidth = Main.SCREEN_WIDTH;
        _cam.viewportHeight = Main.SCREEN_HEIGHT;
        _cam.update();
    }

    @Override
    public void show() {
        _main._status.updateNavigation(TextValues.navigation.serviceScreen);
        _timer = 0;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        _drum_groupBox.dispose();
        _water_groupBox.dispose();
        _other_groupBox.dispose();
        _actions_groupBox.dispose();

        _label_waterConsumption_textFont.dispose();
        _label_temperature_textFont.dispose();
        _label_drumRPM_textFont.dispose();

        _dropSettings_button.dispose();

        _drumPositionSensor.dispose();
        _clear_door_status.dispose();
        _waterLevel0.dispose();
        _waterLevel1.dispose();
        _waterLevel2.dispose();
        _waterLevel3.dispose();
        _waterLevel4.dispose();

        _spin1.dispose();
        _spin2.dispose();
        _washRight.dispose();
        _washLeft.dispose();
        _deceleration.dispose();
        _power.dispose();

        _electricalHeat.dispose();
        _steamHeat.dispose();

        _sink.dispose();

        _waterValve.dispose();
        _tray1.dispose();
        _tray2.dispose();
        _tray3.dispose();
    }

    @Override
    public void setBuffer(String stringToChange) {
        _buffer = stringToChange;
    }

    @Override
    public String getBuffer() {
        return _buffer;
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
                _dropSettings_button.updateState(false);
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