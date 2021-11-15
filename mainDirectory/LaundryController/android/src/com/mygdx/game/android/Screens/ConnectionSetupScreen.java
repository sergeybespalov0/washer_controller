package com.mygdx.game.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Elements.GroupBox;
import com.mygdx.game.android.Elements.Button;
import com.mygdx.game.android.Elements.TextBox;
import com.mygdx.game.android.Interfaces.IDataTransfer;
import com.mygdx.game.android.Main;

/**
 * screen, used to set up the connection settings
 */
public class ConnectionSetupScreen implements IDataTransfer, InputProcessor {
    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    private GroupBox _textBox_groupBox;
    private GroupBox _actions_groupBox;
    private TextBox _ip_textBox;
    private TextBox _port_textBox;

    private Button _disconnect_button;
    private Button _connect_button;
    private Main _main;

    private float _timer;

    private float _client_timer = 0;
    private String _buffer;

    public ConnectionSetupScreen(Main main, SpriteBatch batch, ShapeRenderer renderer,
                                 OrthographicCamera cam) {
        if (main == null || batch == null || renderer == null || cam == null) {
            throw new NullPointerException("ConnectionScreen initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = cam;
        _timer = 0;
        _buffer = "";

        float _space_between_elements_height = (Main.SCREEN_HEIGHT - main._status.get_height() * 2) / 11;
        float _elements_height = _space_between_elements_height;
        float _space_between_elements_width = (main._status.get_status_width() / 5);
        float _elements_width = (float) (main._status.get_status_width() * 0.8);


        _ip_textBox = new TextBox();
        _ip_textBox.Initialize(Main.SCREEN_WIDTH / 2 - _elements_width / 2,
                Main.SCREEN_HEIGHT - _main._status.get_height() - _elements_height - _space_between_elements_height,
                _elements_width, _elements_height,
                4,
                _main.get_ip(),
                TextValues.globalValues.ip,
                Textures.get_textBox_wide_background(),
                Color.BLACK,
                Main._medium_font_fileHandle);

        _port_textBox = new TextBox();
        _port_textBox.Initialize(_ip_textBox.get_width_position(),
                _ip_textBox.get_height_position() - _elements_height - _space_between_elements_height,
                _elements_width, _elements_height,
                4,
                String.valueOf(_main.get_port()),
                TextValues.globalValues.port,
                Textures.get_textBox_wide_background(),
                Color.BLACK,
                Main._medium_font_fileHandle);

        _ip_textBox.setFixedDescriptionWidthPosition(_space_between_elements_width / 2);
        _port_textBox.setFixedDescriptionWidthPosition(_space_between_elements_width / 2);


        _connect_button = new Button();
        _connect_button.initialize(_ip_textBox.get_width_position() + _ip_textBox.get_width() + _space_between_elements_width,
                _port_textBox.get_height_position(),
                _elements_width,
                _ip_textBox.get_height() + _space_between_elements_height + _port_textBox.get_height(),
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.connect, Color.BLACK, Main._medium_font_fileHandle);

        _disconnect_button = new Button();
        _disconnect_button.initialize(_connect_button.get_width_position(),
                _connect_button.get_height_position() - _elements_height - _space_between_elements_height,
                _connect_button.get_width(), _elements_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.disconnect, Color.BLACK, Main._medium_font_fileHandle);

        _textBox_groupBox = new GroupBox(_space_between_elements_width / 4,
                _port_textBox.get_height_position() - _space_between_elements_height / 2,
                _port_textBox.get_width_position() + _port_textBox.get_width() + _space_between_elements_width / 4,
                _ip_textBox.get_height_position() + _ip_textBox.get_height() + _space_between_elements_height / 2);

        _actions_groupBox = new GroupBox(_connect_button.get_width_position() - _space_between_elements_width / 4,
                _disconnect_button.get_height_position() - _space_between_elements_height / 2,
                _connect_button.get_width_position() + _connect_button.get_width() + _space_between_elements_width / 4,
                _connect_button.get_height_position() + _connect_button.get_height() + _space_between_elements_height / 2);
    }

    @Override
    public void render(float delta) {
        _cam.update();
        if (_timer < _main._delay) _timer += delta;

        if (_client_timer >= 0) {
            _client_timer -= delta;
        }
        // clear and draw background
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        draw();
    }

    /**
     * draw elements
     */
    private void draw() {
        _batch.begin();
        _main.backgroundSprite.draw(_batch);
        _textBox_groupBox.drawNoRect(_batch);
        _actions_groupBox.drawNoRect(_batch);
        _ip_textBox.draw(_batch);
        _port_textBox.draw(_batch);
        _disconnect_button.draw(_batch);
        _connect_button.draw(_batch);
        _batch.end();

        _renderer.begin(ShapeType.Line);
        _textBox_groupBox.drawRect(_renderer);
        _actions_groupBox.drawRect(_renderer);
        _connect_button.drawRect(_renderer);
        _disconnect_button.drawRect(_renderer);
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
        if (_main._status.returnButton_touched(_cam)) {
            _main.ShowPrimaryScreen();
        }
        if (_ip_textBox.isTouched(_cam)) {
            _main.EnterIP(this);
        }
        if (_port_textBox.isTouched(_cam)) {
            _main.EnteringPort(this);
        }

        if (_client_timer < 0) {
            if (_disconnect_button.isTouched(_cam)) {
                //System.out.println("stopping client");
                _main.stopClient();
                //_main._status.updateConnectionIndicator(false);

                //_client_timer = 5;
            }
            if (_connect_button.isTouched(_cam)) {
                //System.out.println("starting client");
                _main.startClient();
                //_client_timer = 5;
            }
        }
    }

    /**
     * handling long touch events
     */
    private void handleLongPressing() {
        if (_disconnect_button.isTouched(_cam)) {
            _disconnect_button.updateState(true);
            return;
        } else {
            _disconnect_button.updateState(false);
        }
        if (_connect_button.isTouched(_cam)) {
            _connect_button.updateState(true);
        } else {
            _connect_button.updateState(false);
        }
    }

    /**
     * check data for ip or port
     *
     * @param data - string to check
     */
    private void checkData(String data) {
        if (data.equals("")) {
            return;
        }
        /**
         * check string for ip-address
         */
        try {
            if (Main.CheckForIP(data)) {
                _main.setClientConnectionData(data, _main.get_port());
                _ip_textBox.updateContent(_main.get_ip());
                _main.writeToFile(_ip_textBox.get_textBoxContent(), _port_textBox.get_textBoxContent());
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /**
         * check string for port
         */
        try {
            if (Main.CheckForPort(data)) {
                _main.setClientConnectionData(_main.get_ip(), Integer.valueOf(data));
                _port_textBox.updateContent(String.valueOf(_main.get_port()));
                _main.writeToFile(_ip_textBox.get_textBoxContent(), _port_textBox.get_textBoxContent());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        _main._status.updateNavigation(TextValues.navigation.connectionSetupScreen);
        _timer = 0;
        checkData(_buffer);
        _buffer = "";
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
        _textBox_groupBox.dispose();
        _actions_groupBox.dispose();
        _ip_textBox.dispose();
        _port_textBox.dispose();

        _disconnect_button.dispose();
        _connect_button.dispose();
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
                _disconnect_button.updateState(false);
                _connect_button.updateState(false);
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