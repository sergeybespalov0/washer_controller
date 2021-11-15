package com.mygdx.game.android.Elements;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Main;

import java.text.DateFormat;
import java.util.Date;

/**
 * class used to draw isShowed bar
 */
public class StatusBar {
    private Main _main;
    private OrthographicCamera _cam;
    private float _height;
    private float _width_position;
    private float _height_position;
    private StatusIndicator _connection_indicator;
    private Sprite _background_Sprite;
    private Button _menu_navigator;
    private InformationMessage _infoMessage;
    private ErrorMessage _error;
    private Button _work_status;
    private Button _connectionStatus;
    private Button _pauseButton;
    private TextView _dateTime;
    private ReturnButton _returnButton;
    private boolean _returnButton_hidden;
    private String _text_buffer = null;
    private String _work_status_text;

    /**
     * isShowed bar initializer
     * isShowed bar is program element to show connection state, current menu,
     * draw stop button and additional text information
     *
     * @param main - instance of a main class
     * @param cam  - instance of orthographic camera of a main class
     */
    public StatusBar(Main main, OrthographicCamera cam) {

        if (main == null || cam == null) {
            throw new NullPointerException("StatusBar initialization parameters can't be null!");
        }
        _returnButton_hidden = false;
        _width_position = 0;
        _height_position = 0;
        //_cam = cam;
        _height = Main.SCREEN_HEIGHT / 8 / 2;
        _main = main;
        _cam = cam;
        _menu_navigator = new Button();
        _menu_navigator.initialize(_width_position, Main.SCREEN_HEIGHT - _height,
                Main.SCREEN_WIDTH, _height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                " ",
                Color.BLACK, Main._little_font_fileHandle);

        _infoMessage = new InformationMessage(" ", " ", _cam);
        _error = new ErrorMessage(_main, " ", " ", _cam);

        Texture _backgroundTexture = Textures.get_statusBar_background();
        _background_Sprite = new Sprite(_backgroundTexture);
        _background_Sprite.setPosition(_width_position, _height_position);
        _background_Sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _background_Sprite.setSize(Main.SCREEN_WIDTH, _height);


        _dateTime = new TextView(_background_Sprite.getX(),
                _menu_navigator.get_height_position(),
                DateFormat.getDateTimeInstance().format(new Date()),
                Main._little_font_fileHandle,
                Color.BLACK);
        _dateTime.updatePosition(_background_Sprite.getX() + _menu_navigator.get_width() / 40,
                _menu_navigator.get_height_position() + _menu_navigator.get_height() / 2 - _dateTime.get_height() / 2);

        _connectionStatus = new Button();
        _connectionStatus.initialize(_width_position,
                _height_position,
                Main.SCREEN_WIDTH / 3,
                _height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.statusBar.connectionStatusDescription, Color.BLACK,
                Main._little_font_fileHandle);

        _connection_indicator = new StatusIndicator(1,
                _connectionStatus.get_width_position() + _connectionStatus.get_width() / 10 * 8,
                _height / 10 * 2,
                _height / 10 * 6,
                Textures.get_indicator_red_2(), Textures.get_indicator_green_2(),
                Main._little_font,
                _main._button_textColor, " ");

        _work_status_text = TextValues.statusBar.status_waiting;
        _work_status = new Button();
        _work_status.initialize(_connectionStatus.get_width_position() +
                        _connectionStatus.get_width(),
                _height_position,
                Main.SCREEN_WIDTH / 3,
                _height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.statusBar.status_description + _work_status_text, Color.BLACK, Main._little_font_fileHandle);


        _pauseButton = new Button();
        _pauseButton.initialize(_work_status.get_width_position() + _work_status.get_width(),
                _height_position,
                Main.SCREEN_WIDTH - ((Main.SCREEN_WIDTH / 3) * 2) - _height * 3,
                _height,
                Textures.get_yellow_button_pushed(),
                Textures.get_yellow_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.pause_button_text, Color.WHITE,
                Main._little_font_fileHandle);
        _returnButton = new ReturnButton(Main.SCREEN_WIDTH - _height * 3,
                _height_position,
                _height * 3);
        _text_buffer = null;
    }

    /**
     * draw isShowed bar
     *
     * @param batch - batch, in which we draw
     */
    public void draw(SpriteBatch batch) {
        if (batch == null) {
            throw new NullPointerException("StatusBar draw parameters can't be null");
        }
        update();

        _background_Sprite.draw(batch);

        _connectionStatus.draw(batch);
        _connection_indicator.draw(batch);
        _work_status.draw(batch);
        _pauseButton.draw(batch);
        _menu_navigator.draw(batch);
        _dateTime.draw(batch);
        _infoMessage.draw(batch);
        _error.draw(batch);
    }

    /**
     * draw return button, separated to draw on top of other elements
     *
     * @param batch - batch, in which we draw
     */
    public void drawReturnButton(SpriteBatch batch) {
        if (batch == null) {
            throw new NullPointerException("StatusBar drawReturnButton parameters can't be null");
        }
        if (!_returnButton_hidden) {
            _returnButton.draw(batch);
        }
    }

    /**
     * draw 1px borders on the edges of all elements of isShowed bar
     *
     * @param renderer -
     */
    public void drawRect(ShapeRenderer renderer) {
        if (renderer == null) {
            throw new NullPointerException("StatusBar drawRect parameters can't be null");
        }
        _connectionStatus.drawRect(renderer);
        _work_status.drawRect(renderer);
        _pauseButton.drawRect(renderer);
        _infoMessage.drawRect(renderer);
        _error.drawRect(renderer);
        _menu_navigator.drawRect(renderer);
    }

    /**
     * hide return button and fill the space it occupies with stop button
     */
    public void hideReturnButton() {
        _returnButton_hidden = true;
        _pauseButton.changeSize(Main.SCREEN_WIDTH - ((Main.SCREEN_WIDTH / 3) * 2), _height);
    }

    /**
     * show return button and change stop button size to default
     */
    public void showReturnButton() {
        _returnButton_hidden = false;
        _pauseButton.changeSize(Main.SCREEN_WIDTH - ((Main.SCREEN_WIDTH / 3) * 2)
                        - _height * 3,
                _height);
    }

    /**
     * @param camera - used to check if button is touched
     * @return true, if return button is touched
     */
    public boolean returnButton_touched(OrthographicCamera camera) {
        if (camera == null) {
            throw new NullPointerException("StatusBar returnButtonTouched parameters can't be null");
        }
        return !_returnButton_hidden && _returnButton.IsTouched(camera);
    }

    /**
     * update isShowed bar elements to their actual state
     */
    private void update() {
        _dateTime.updateContent(DateFormat.getDateTimeInstance().format(new Date()));

        /**
         * go to laundry process screen if program is executing
         * and current screen is not laundry process screen
         */
        if (_main._controller.getState().isExecuteProgram() &&
                !_main.checkForLaundryProcessScreen()) {
            if (_main._controller.isConnected()) {
                _main.ShowLaundryProcessScreen();
            }
        }
        /**
         * update connection indicators, depends on connection
         */
        if (_main.is_Connected()) {
            _connection_indicator.updateState(true);
        } else {
            _connection_indicator.updateState(false);
        }
        /**
         * enable or disable stop button, depends on program execution
         */
        if (_main._controller.getState().isExecuteProgram()) {
            _pauseButton.setTextColor(Color.WHITE);
            _pauseButton.enable();
        } else {
            _pauseButton.setTextColor(Color.BLACK);
            _pauseButton.disable();
        }
        /**
         * show error box if error is occurred
         */
        if (_error.isFinished()) {
            _text_buffer = null;
            _text_buffer = _main._controller.getError();
        }
        if (_text_buffer != null) {
            showError(_text_buffer);
        }
        /**
         * show message box if there is a message
         */
        if (_infoMessage.isFinished()) {
            _text_buffer = null;
            _text_buffer = _main._controller.getMessage();
        }
        if (_text_buffer != null) {
            _infoMessage.setNotFinished();
            _infoMessage.show();
            _infoMessage.updateHeader(TextValues.globalValues.warning);
            _infoMessage.updateDescription(_text_buffer);
        }
        /**
         * update work isShowed, depend on connection, program executing and current screen
         */
        if (_main._controller.isConnected()) {
            if (_main._controller.getState().isExecuteProgram()) {
                _work_status_text = TextValues.statusBar.status_working;
            } else {
                if (_main.checkForLaundryProcessScreen()) {
                    _work_status_text = TextValues.statusBar.status_finished;
                } else {
                    _work_status_text = TextValues.statusBar.status_waiting;
                }
            }
        } else {
            _work_status_text = TextValues.statusBar.status_notConnected;
        }
        _work_status.setContent(TextValues.statusBar.status_description + _work_status_text);
    }

    public void showWarningMessage(String message) {
        if (message != null) {
            _infoMessage.setNotFinished();
            _infoMessage.show();
            _infoMessage.updateHeader(TextValues.globalValues.warning);
            _infoMessage.updateDescription(message);
        }
    }

    public void setInfoMessage(String new_header, String new_description) {
        if ((new_header != null) && (new_description != null)) {
            _infoMessage.setNotFinished();
            _infoMessage.show();
            _infoMessage.updateHeader(new_header);
            _infoMessage.updateDescription(new_description);
        }
    }

    /**
     * handling single touch events for isShowed bar elements
     */
    public void handleSingleInput() {
        if (_pauseButton.isTouched(_cam)) {
            _main._controller.requestProgramStop();
        }
        _pauseButton.updateState(false);
        _returnButton.updateState(false);
    }

    /**
     * handling single touch events for warning box and error box
     */
    public void handleSingleInputWarnings() {
        if (_infoMessage.isShowed())
            _infoMessage.handleSingleInput();
        if (_error.isShowed())
            _error.handleSingleInput();
    }

    /**
     * handle long touch events for isShowed bar elements
     */
    public void handleLongTermDepression() {
        if (_pauseButton.isTouched(_cam)) {
            _pauseButton.updateState(true);
        }
        if (_returnButton.IsTouched(_cam)) {
            _returnButton.updateState(true);
        }
    }

    /**
     * handle long touch events for warning box and error box
     */
    public void handleLongTermDepressionWarnings() {
        if (_infoMessage.isShowed())
            _infoMessage.handleLongTermDepression();
        if (_error.isShowed())
            _error.handleLongTermDepression();
    }

    /**
     * @return true, if error box or warning box is active,
     * used to disable touch events when error and warning boxes is active
     */
    public boolean is_error() {
        return (_error.isShowed() || _infoMessage.isShowed());
    }

    /**
     * depress error box and warning box buttons
     */
    public void dePressWarnings() {
        _error.dePressButton();
        _infoMessage.dePressButtons();
    }

    /**
     * update text of navigation bar (at the top of the screen)
     *
     * @param location - current location
     */
    public void updateNavigation(String location) {
        if (location.isEmpty()) {
            throw new NullPointerException("StatusBar updateNavigation parameters can't be null");
        }
        _menu_navigator.setContent(location);
    }

    /**
     * disposing isShowed bar
     */
    public void dispose() {
        _connectionStatus.dispose();
        _infoMessage.dispose();
        _error.dispose();
        _dateTime = null;
        _work_status.dispose();
        _pauseButton.disable();
        _returnButton.dispose();
        _menu_navigator.dispose();
    }

    /**
     * updating position of isShowed bar
     *
     * @param width  - width position
     * @param height - height position
     */
    public void updatePosition(float width, float height) {
        _width_position = width;
        _height_position = height;
        _menu_navigator.updatePosition(_width_position, Main.SCREEN_HEIGHT - _menu_navigator.get_height());
        _background_Sprite.setPosition(_width_position, _height_position);
        _connectionStatus.updatePosition(_width_position, _height_position);
        _connection_indicator.updatePosition(_connectionStatus.get_width_position()
                + _connectionStatus.get_width() - 40 - 40, _height / 8 / 2);
        _work_status.updatePosition(_connectionStatus.get_width_position() + _connectionStatus.get_width(),
                _height_position);
        _pauseButton.updatePosition(_work_status.get_width_position() + _work_status.get_width(),
                _height_position);
        _returnButton.updatePosition(Main.SCREEN_WIDTH - ((Main.SCREEN_WIDTH / 3) / 3), _height_position);

    }

    /**
     * @return width of work isShowed element (1/3 of screen width)
     */
    public float get_status_width() {
        return _work_status.get_width();
    }

    /**
     * @return height of bottom isShowed bar,
     * navigation bar in tob got the same height
     */
    public float get_height() {
        return _height;
    }

    public String get_work_status_text() {
        return _work_status_text;
    }

    /**
     * show error box if error is occurred
     */
    public void showError(String errorMessage) {

        if (_error.isFinished()) {
            if (errorMessage != null) {
                _error.setNotFinished();
                _error.show();
                _error.updateHeader(TextValues.globalValues.error);
                _error.updateDescription(errorMessage);
            }
        }
    }
}