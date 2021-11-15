package com.mygdx.game.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Elements.Button;
import com.mygdx.game.android.Elements.ConfirmBoxBig;
import com.mygdx.game.android.Elements.GroupBox;
import com.mygdx.game.android.Elements.LoadingMessage;
import com.mygdx.game.android.Elements.TextBox;
import com.mygdx.game.android.Elements.TexturePlurality;
import com.mygdx.game.android.Elements.inheritable.InformationMessage_warnBeforeLaundryStart;
import com.mygdx.game.android.Interfaces.IDataTransfer;
import com.mygdx.game.android.Interfaces.IProgramTransfer;
import com.mygdx.game.android.Main;
import com.mygdx.game.android.WasherControl.WasherProgram;

/**
 * class used to set up laundry values before starting the wash
 */
public class LaundrySetupScreen implements IProgramTransfer, IDataTransfer, InputProcessor {

    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    private Main _main;
    private ConfirmBoxBig _confirmBox;
    private int _action = 1;

    private TextBox _textBox_weight;
    private TextBox _textBox_clientID;
    private TextBox _textBox_laundryProgram;
    private TextBox _textBox_preWash_temperature;
    private TextBox _textBox_mainWash_temperature;

    private Button _launchButton;
    private Button _resetData;

    private WasherProgram _program;
    private TexturePlurality[] _dataInputIndicator;

    private boolean _laundryWeight_set;
    private boolean _laundryClient_set;
    private boolean _laundryProgram_set;

    private String _laundryWeight;
    private String _clientID;
    private String _laundryProgram;
    private String _preWashTemperature;
    private String _mainWashTemperature;
    private String _buffer;

    private GroupBox _text_groupBox;
    private GroupBox _button_groupBox;

    private InformationMessage_warnBeforeLaundryStart _message;

    private float _elements_height;
    private float _space_between_elements_height;
    private float _elements_width;
    private float _space_between_elements_width;

    private boolean is_programs_downloaded;
    private float _timeBetweenProgramsButtonUpdate = 0;

    private LoadingMessage _loadingMessage;

    private float _deltaTime;
    private float _timer;
    //private float _temperatureSPC;
    private int changed_data_id;

    public LaundrySetupScreen(Main main, SpriteBatch batch, ShapeRenderer renderer, OrthographicCamera camera) {
        if (main == null || batch == null || renderer == null || camera == null) {
            throw new NullPointerException("LaundrySetupScreen initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = camera;
        _timer = 0;
        _laundryWeight_set = false;
        _laundryClient_set = false;
        _laundryProgram_set = false;
        changed_data_id = 0;

        GlyphLayout gl1 = new GlyphLayout();
        gl1.setText(Main._medium_font, " С):");
        //_temperatureSPC = gl1.width;
        _space_between_elements_height = (Main.SCREEN_HEIGHT - _main._status.get_height() * 2) / 11;
        _elements_height = _space_between_elements_height;
        _space_between_elements_width = (_main._status.get_status_width() / 5);
        _elements_width = (float) (_main._status.get_status_width() * 0.8);

        Texture[] plurality = new Texture[3];
        plurality[0] = Textures.get_empty();
        plurality[1] = Textures.get_arrow();
        plurality[2] = Textures.get_check_mark();
        _dataInputIndicator = new TexturePlurality[3];
        for (int i = 0; i < _dataInputIndicator.length; i++) {
            _dataInputIndicator[i] = new TexturePlurality(0, 0, _elements_height / 2, _elements_height / 2,
                    plurality, 1);
        }
        is_programs_downloaded = false;

        /*_message = new InformationMessage(TextValues.globalValues.warning,
                "" , _cam);*/
        _loadingMessage = new LoadingMessage();
        _loadingMessage.show();
        _message = new InformationMessage_warnBeforeLaundryStart(_cam);

        initializeTextBoxes();
        initializeOther();
    }

    /**
     * initialize column of text boxes and their descriptions
     */
    private void initializeTextBoxes() {

        _laundryWeight = "0";
        _textBox_weight = new TextBox();
        _textBox_weight.Initialize(Main.SCREEN_WIDTH / 2 - _elements_width / 2,
                Main.SCREEN_HEIGHT - _main._status.get_height() - _elements_height - _space_between_elements_height,
                _elements_width, _elements_height,
                4, _laundryWeight, TextValues.laundrySetup.weight,
                Textures.get_textBox_wide_background(),
                Color.BLACK,
                Main._medium_font_fileHandle);

        _clientID = "0";
        _textBox_clientID = new TextBox();
        _textBox_clientID.Initialize(_textBox_weight.get_width_position(),
                _textBox_weight.get_height_position() - _elements_height - _space_between_elements_height,
                _elements_width, _elements_height,
                4, _clientID, TextValues.laundrySetup.clientID,
                Textures.get_textBox_wide_background(),
                Color.BLACK,
                Main._medium_font_fileHandle);


        _laundryProgram = TextValues.laundrySetup.choose;
        _textBox_laundryProgram = new TextBox();
        _textBox_laundryProgram.Initialize(_textBox_clientID.get_width_position(),
                _textBox_clientID.get_height_position() - _elements_height - _space_between_elements_height,
                _elements_width, _elements_height,
                4, TextValues.laundrySetup.choose, TextValues.laundrySetup.laundryProgram,
                Textures.get_textBox_wide_background(),
                Color.BLACK,
                Main._medium_font_fileHandle);

        _preWashTemperature = "0";
        _textBox_preWash_temperature = new TextBox();
        _textBox_preWash_temperature.Initialize(_textBox_laundryProgram.get_width_position() + _textBox_laundryProgram.get_width() - _textBox_laundryProgram.get_width() / 3 * 2,
                _textBox_laundryProgram.get_height_position() - _elements_height - _space_between_elements_height,
                _textBox_laundryProgram.get_width() / 3 * 2, _elements_height,
                4, _preWashTemperature, TextValues.laundrySetup.preWashTemperature,
                Textures.get_textBox_grey_background(),
                Color.BLACK,
                Main._medium_font_fileHandle);


        _mainWashTemperature = "0";
        _textBox_mainWash_temperature = new TextBox();
        _textBox_mainWash_temperature.Initialize(_textBox_preWash_temperature.get_width_position(),
                _textBox_preWash_temperature.get_height_position() - _elements_height - _space_between_elements_height,
                _textBox_preWash_temperature.get_width(), _textBox_preWash_temperature.get_height(),
                4, _mainWashTemperature, TextValues.laundrySetup.mainWashTemperature,
                Textures.get_textBox_grey_background(),
                Color.BLACK,
                Main._medium_font_fileHandle);

    }

    /**
     * initialize buttons and fill indicators
     */
    private void initializeOther() {
        _launchButton = new Button();
        _launchButton.initialize(_textBox_weight.get_width_position() + _textBox_weight.get_width() + _space_between_elements_width,
                _textBox_clientID.get_height_position(),
                _elements_width,
                _textBox_clientID.get_height() + _space_between_elements_height + _textBox_weight.get_height(),
                Textures.get_green_button_pushed(),
                Textures.get_green_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.laundrySetup.launchButton, Color.BLACK, Main._huge_font_fileHandle);


        _resetData = new Button();
        _resetData.initialize(_launchButton.get_width_position(),
                _launchButton.get_height_position() - _elements_height - _space_between_elements_height,
                _elements_width,
                _elements_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.laundrySetup.dropData, Color.BLACK, Main._medium_font_fileHandle);

        _textBox_weight.setFixedDescriptionWidthPosition(_space_between_elements_width / 2);
        _textBox_clientID.setFixedDescriptionWidthPosition(_space_between_elements_width / 2);
        _textBox_laundryProgram.setFixedDescriptionWidthPosition(_space_between_elements_width / 2);
        _textBox_preWash_temperature.setFixedDescriptionWidthPosition(_space_between_elements_width / 2);
        _textBox_mainWash_temperature.setFixedDescriptionWidthPosition(_space_between_elements_width / 2);


        _confirmBox = new ConfirmBoxBig(TextValues.globalValues.warning, TextValues.laundrySetup.dropFieldValues, _cam);

        _text_groupBox = new GroupBox(_space_between_elements_width / 4,
                0 + _main._status.get_height() + _space_between_elements_height / 2,
                _textBox_weight.get_width_position() + _textBox_weight.get_width() + _space_between_elements_width / 4,
                Main.SCREEN_HEIGHT - _main._status.get_height() - _space_between_elements_height / 2);

        _button_groupBox = new GroupBox(_resetData.get_width_position() - _space_between_elements_width / 4,
                _resetData.get_height_position() - _space_between_elements_height / 2,
                _launchButton.get_width_position() + _launchButton.get_width() + _space_between_elements_width / 4,
                Main.SCREEN_HEIGHT - _main._status.get_height() - _space_between_elements_height / 2);

        _dataInputIndicator[0].updatePosition(_textBox_weight.get_width_position() - (float) (_dataInputIndicator[0].get_width() * 1.2),
                _textBox_weight.get_height_position() + _textBox_weight.get_height() / 2 - _dataInputIndicator[0].get_height() / 2);

        _dataInputIndicator[1].updatePosition(_textBox_clientID.get_width_position() - (float) (_dataInputIndicator[1].get_width() * 1.2),
                _textBox_clientID.get_height_position() + _textBox_clientID.get_height() / 2 - _dataInputIndicator[1].get_height() / 2);

        _dataInputIndicator[2].updatePosition(_textBox_laundryProgram.get_width_position() - (float) (_dataInputIndicator[2].get_width() * 1.2),
                _textBox_laundryProgram.get_height_position() + _textBox_laundryProgram.get_height() / 2 - _dataInputIndicator[2].get_height() / 2);

    }

    /**
     * check is values set to allow launch button
     */
    //// TODO: 8/2/16 uncomment line below
    private void isReady() {
        if ((_laundryWeight_set && _laundryClient_set && _laundryProgram_set) /*&&
                (!_main._controller.getState().isInCleanOpen() && !_main._controller.getState().isInDirtyOpen())*/
                && _main._controller.isConnected()) {
            _launchButton.enable();
        } else
            _launchButton.disable();
    }

    /**
     * update values before draw
     */
    private void updateBeforeDraw() {
        /**
         * check confirm boxes for finish to do some actions, depend on finished confirm box
         */
        finish();

        /*if (_message_timer > 0) {
            _message_timer -= _deltaTime;
            _dataInputIndicator[0].switchTexture(1);
            _dataInputIndicator[1].switchTexture(1);
            _dataInputIndicator[2].switchTexture(1);
        }*/
        if (_timeBetweenProgramsButtonUpdate <= 0) {
            if (_main._controller.getProgramsAmount() == 0) {
                _timeBetweenProgramsButtonUpdate = 2;
            } else {
                is_programs_downloaded = true;
            }
        }
        isReady();
        _cam.update();
        if (_timer < _main._delay) _timer += _deltaTime;
        _textBox_weight.updateContent(_laundryWeight);
        _textBox_clientID.updateContent(_clientID);

        if (is_programs_downloaded) {
            _textBox_laundryProgram.updateContent(_laundryProgram);
            _textBox_laundryProgram.setTextColor(Color.BLACK);
        } else {
            _textBox_laundryProgram.updateContent(TextValues.laundrySetup.noData);
            _textBox_laundryProgram.setTextColor(Color.RED);
        }


        _textBox_preWash_temperature.updateContent(_preWashTemperature);
        _textBox_mainWash_temperature.updateContent(_mainWashTemperature);
        if (_laundryWeight_set) {
            _dataInputIndicator[0].switchTexture(2);
        }
        if (_laundryClient_set) {
            _dataInputIndicator[1].switchTexture(2);
        }
        if (_laundryProgram_set) {
            _dataInputIndicator[2].switchTexture(2);
        }
    }

    /**
     * check confirm boxes for finish to do some actions, depend on finished confirm box
     */
    private void finish() {
        if (_confirmBox.isFinished())
            switch (_action) {
                case 1: {
                    resetData();
                    _confirmBox.setNotFinished();
                    break;
                }
                case 2: {
                    if (!_confirmBox.isFinished()) return;
                    _main._controller.requestProgramStart(_program.getId(),
                            Integer.valueOf(_laundryWeight), Integer.valueOf(_clientID));
                    //_main.ShowLaundryProcessScreen();
                    _confirmBox.setNotFinished();
                    break;
                }
                default: {
                }
            }
    }

    /**
     * draw elements
     */
    private void draw() {
        _batch.begin();
        _main.backgroundSprite.draw(_batch);

        _text_groupBox.drawNoRect(_batch);
        _button_groupBox.drawNoRect(_batch);
        _textBox_weight.draw(_batch);
        _textBox_clientID.draw(_batch);
        _textBox_laundryProgram.draw(_batch);
        //_textBox_preWash_temperature.draw(_batch);
        //_textBox_mainWash_temperature.draw(_batch);
        _launchButton.draw(_batch);


        _resetData.draw(_batch);

        for (TexturePlurality button : _dataInputIndicator) {
            button.draw(_batch);
        }


        _batch.end();
        _renderer.begin(ShapeType.Line);
        _text_groupBox.drawRect(_renderer);
        _button_groupBox.drawRect(_renderer);

        _launchButton.drawRect(_renderer);
        _resetData.drawRect(_renderer);


        _renderer.end();
        _confirmBox.draw(_batch, _renderer);


        _renderer.begin(ShapeType.Line);

        /*if (!_confirmBox.isShowed()) {
            _renderer.circle(_space_between_elements_width / 2 + _textBox_preWash_temperature.get_description_width() - _temperatureSPC,
                    _textBox_preWash_temperature.get_height_position() + (float) (_textBox_preWash_temperature.get_height() / 4 * 2.4),
                    _textBox_preWash_temperature.get_height() / 8);
            _renderer.circle(_space_between_elements_width / 2 + _textBox_preWash_temperature.get_description_width() - _temperatureSPC,
                    _textBox_preWash_temperature.get_height_position() + (float) (_textBox_preWash_temperature.get_height() / 4 * 2.4),
                    _textBox_preWash_temperature.get_height() / 8 - 2);
        }
        _renderer.circle(_space_between_elements_width / 2 + _textBox_mainWash_temperature.get_description_width() - _temperatureSPC,
                _textBox_mainWash_temperature.get_height_position() + (float) (_textBox_mainWash_temperature.get_height() / 4 * 2.4),
                _textBox_mainWash_temperature.get_height() / 8);
        _renderer.circle(_space_between_elements_width / 2 + _textBox_mainWash_temperature.get_description_width() - _temperatureSPC,
                _textBox_mainWash_temperature.get_height_position() + (float) (_textBox_mainWash_temperature.get_height() / 4 * 2.4),
                _textBox_mainWash_temperature.get_height() / 8 - 2);*/
        _renderer.end();
        _batch.begin();
        _main._status.draw(_batch);
        _batch.end();
        _renderer.begin(ShapeType.Line);
        _main._status.drawRect(_renderer);
        _renderer.end();
        _batch.begin();
        _main._status.drawReturnButton(_batch);
        /*_batch.end();
        _loadingMessage.draw(_batch, _renderer, _cam);
        _batch.begin();*/
        _message.draw(_batch);
        _batch.end();
        _renderer.begin(ShapeType.Line);
        _message.drawRect(_renderer);
        _renderer.end();
    }

    @Override
    public void render(float delta) {
/**
 * update
 */
        _deltaTime = delta;

        if (_timeBetweenProgramsButtonUpdate > 0)
            _timeBetweenProgramsButtonUpdate -= delta;


        updateBeforeDraw();
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
     * handling single touch events
     */
    private void handleSingleInput() {

        if (!_confirmBox.isShowed()) {
            /**
             * check for touching
             * first of all is checking on group boxes, then on elements in this group boxes
             */
            if (_button_groupBox.isTouched(_cam)) {
                if (_resetData.isTouched(_cam)) {
                    _confirmBox.updateHeader(TextValues.globalValues.warning);
                    _confirmBox.updateDescription(TextValues.laundrySetup.dropFieldValues);
                    _action = 1;
                    _confirmBox.show();
                    return;
                }
                if (_launchButton.isTouched(_cam)) {
                    _confirmBox.updateHeader(TextValues.laundrySetup.startTheWash);
                    _confirmBox.updateDescription("       " + TextValues.globalValues.checkInputData + "       \n" +
                            "\n" +
                            "Клиент: " + _clientID + "\n" +
                            "Вес: " + _laundryWeight + " кг" + "\n" +
                            "Программа: " + _program.getName() /*+ "\n" +
                                    TextValues.globalValues.beforeStartMessage*/);
                    _action = 2;
                    _confirmBox.show();
                    _message.show();

                    return;
                }
                if (_launchButton.brutForceTouched(_cam) && _launchButton.isDisabled()) {
                    if (_main._controller.isConnected()) {
                        _main._status.showWarningMessage(TextValues.laundrySetup.userMessage_FillTheFields);
                    } else {
                        if (_laundryClient_set && _laundryWeight_set && _laundryProgram_set) {
                            _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                        } else {
                            _main._status.showWarningMessage(TextValues.laundrySetup.userMessage_FillTheFields);
                        }
                    }
                    return;
                }
            }
            if (_text_groupBox.isTouched(_cam)) {
                if (_textBox_weight.isTouched(_cam)) {
                    setBuffer(_laundryWeight);
                    changed_data_id = 1;
                    _main.EnteringWeight(this, TextValues.globalValues.description_Enter_Weight);
                    return;
                }
                if (_textBox_clientID.isTouched(_cam)) {
                    setBuffer(_clientID);
                    changed_data_id = 2;
                    _main.EnteringClientID(this, TextValues.globalValues.description_Enter_ClientID);
                    return;
                }

                if (_textBox_laundryProgram.isTouched(_cam)) {
                    if (_main._controller.getPrograms().isEmpty()) {
                        if (_main._controller.isConnected()) {
                            _main._controller.requestProgramsUpdate();
                            return;
                        } else {
                            _main._status.showWarningMessage(TextValues.globalValues.description_NO_CONNECTION);
                            return;
                        }

                    } else {
                        _loadingMessage.draw(_batch, _renderer, _cam);
                        System.out.println("draw loading message");
                        setBuffer(_laundryProgram);
                        changed_data_id = 3;
                        _main.ShowLaundryProgramSelectionScreen(this);
                        return;
                    }
                }
            }
            if (_main._status.returnButton_touched(_cam)) {
                resetData();
                _main.ShowPrimaryScreen();
                return;
            }

        } else {
            _confirmBox.handleSingleInput();
        }
        _confirmBox.dePressButtons();
    }

    /**
     * reset the defined settings
     */
    public void resetData() {
        _laundryClient_set = false;
        _clientID = "0";
        _laundryProgram_set = false;
        _laundryProgram = TextValues.laundrySetup.choose;
        _laundryWeight_set = false;
        _laundryWeight = "0";
        _mainWashTemperature = "0";
        _preWashTemperature = "0";
        for (TexturePlurality temp : _dataInputIndicator
                ) {
            temp.switchTexture(1);
        }
        _launchButton.disable();
        _confirmBox.setNotFinished();
    }

    /**
     * handling long touch events
     */
    private void handleLongPressing() {
        if (_main._status.is_error()) return;
        if (!_confirmBox.isShowed()) {
            if (_resetData.isTouched(_cam)) {
                _resetData.updateState(true);
                return;
            } else {
                _resetData.updateState(false);
            }
            if (_launchButton.isTouched(_cam)) {
                _launchButton.updateState(true);
            } else {
                _launchButton.updateState(false);
            }
        } else {
            _confirmBox.handleLongTermDepression();
        }
    }

    /**
     * check transmitted data to set values
     */
    private void update_after_transfer() {
        switch (changed_data_id) {
            case 1: {
                if (!(_buffer.equals("0") || _buffer.equals(""))) {
                    _laundryWeight = _buffer;
                    _laundryWeight_set = true;
                    _buffer = "";
                } else {
                    _laundryWeight_set = false;
                    _buffer = "";
                }
                break;
            }
            case 2: {
                if (!(_buffer.equals("0") || _buffer.equals(""))) {
                    _clientID = _buffer;
                    _laundryClient_set = true;
                    _buffer = "";
                } else {
                    _laundryClient_set = false;
                    _buffer = "";
                }
                break;
            }
            case 3: {
                if (_program != null) {
                    if (_program.getName().length() > 16) {
                        _laundryProgram = _program.getName().substring(0, 13) + "...";
                    } else {
                        _laundryProgram = _program.getName();
                    }
                    _laundryProgram_set = true;

                } else {
                    _laundryProgram_set = false;
                    _program = null;
                    _preWashTemperature = "0";
                    _mainWashTemperature = "0";
                }
                break;
            }
            default: {
            }
        }
        changed_data_id = 0;
        _buffer = "";
    }

    @Override
    public void resize(int width, int height) {
        _cam.viewportWidth = Main.SCREEN_WIDTH;
        _cam.viewportHeight = Main.SCREEN_HEIGHT;
        _cam.update();
    }

    @Override
    public void show() {
        ////////* Called when this screen becomes the current screen */
        update_after_transfer();
        _timer = 0;
        _main._status.updateNavigation(TextValues.navigation.laundrySetupScreen);
        _confirmBox.setNotFinished();
        _main._status.showReturnButton();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        /*_loadingMessage.hide();
        System.out.println("hide loading message");*/
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        _timer = 0;
    }

    @Override
    public void dispose() {
        _confirmBox.dispose();
        _textBox_weight.dispose();
        _textBox_clientID.dispose();
        _textBox_laundryProgram.dispose();
        _textBox_preWash_temperature.dispose();
        _textBox_mainWash_temperature.dispose();
        _loadingMessage.dispose();
        _launchButton.dispose();
        _resetData.dispose();
        _button_groupBox.dispose();
        _text_groupBox.dispose();
        for (TexturePlurality plurality : _dataInputIndicator
                ) {
            plurality.dispose();
        }
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
    public void setProgram(WasherProgram newProgram) {
        _program = newProgram;
    }

    @Override
    public WasherProgram getProgram() {
        return _program;
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
                if (_message.isShowed()) {
                    _message.handleLongTermDepression();
                } else {
                    _main._status.handleLongTermDepression();
                    handleLongPressing();
                }
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
                if (_message.isShowed()) {
                    _message.handleSingleInput();
                } else {
                    _main._status.handleSingleInput();
                    handleSingleInput();
                }
            }
            _message.dePressButtons();
            _launchButton.updateState(false);
            _resetData.updateState(false);
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

    public String get_weight() {
        return _textBox_weight.get_textBoxContent();
    }

    public String get_clientID() {
        return _textBox_clientID.get_textBoxContent();
    }

}