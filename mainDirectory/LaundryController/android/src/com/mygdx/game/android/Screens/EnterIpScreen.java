package com.mygdx.game.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Elements.ConfirmBox;
import com.mygdx.game.android.Elements.Button;
import com.mygdx.game.android.Elements.TextBox;
import com.mygdx.game.android.Interfaces.IDataTransfer;
import com.mygdx.game.android.Main;

/**
 * screen, used to enter ip-address
 */
public class EnterIpScreen implements IDataTransfer, InputProcessor {


    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    private ConfirmBox _error;
    private TextBox _incorrectText_indicator;
    private Main _main;
    private Button _numeric_1;
    private Button _numeric_2;
    private Button _numeric_3;
    private Button _numeric_4;
    private Button _numeric_5;
    private Button _numeric_6;
    private Button _numeric_7;
    private Button _numeric_8;
    private Button _numeric_9;
    private Button _numeric_0;
    private Button _symbol_dot;
    private Button _eraseSymbol;
    private Button _enter;
    private Button _clear;
    private TextBox _textBox_content;

    private final float world_width = Main.SCREEN_WIDTH;
    private final float world_height = Main.SCREEN_HEIGHT;

    private boolean _incorrect_data = false;

    private String _content;
    private static String _ip = "";

    private static int _length;

    private IDataTransfer _lastScreen;
    private BitmapFont _textToEnter_font;
    private GlyphLayout _textToEnter_glyphLayout;
    private static String _textToEnter;

    private float _timer;

    /**
     * main initialization for class usage
     *
     * @param main     - main instance
     * @param batch    - batch to draw in
     * @param renderer - renderer to draw rectangles around elements
     * @param camera   - orthographic camera
     */
    public EnterIpScreen(Main main, SpriteBatch batch, ShapeRenderer renderer, OrthographicCamera camera
    ) {
        if (main == null || batch == null || renderer == null || camera == null) {
            throw new NullPointerException("EnterIpScreen initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = camera;
        _timer = 0;
        _content = " ";
        _error = new ConfirmBox(_content, TextValues.globalValues.confirmEnteredData, _cam);
        _textToEnter = "";
        _textToEnter_glyphLayout = new GlyphLayout();
        _textToEnter_font = Main._little_font;
        _textToEnter_font.setColor(Color.BLACK);
        _textToEnter_glyphLayout.setText(_textToEnter_font, _textToEnter);

        initializeButtons();
        initializeOther();
    }

    /**
     * initialization before usage
     *
     * @param lastScreen - last screen to return to
     */
    public void Initialize(IDataTransfer lastScreen) {
        _content = "";
        _lastScreen = lastScreen;
        _timer = 0;
        _length = 16;
        _ip = "";

        _textToEnter = TextValues.globalValues.ip;
        _textToEnter_glyphLayout.setText(_textToEnter_font, _textToEnter);
    }

    /**
     * initialize numeric buttons
     */
    private void initializeButtons() {
        float _textBox_weight_x = world_width / 10;
        float _textBox_weight_y = world_height - world_height / 8 / 2 - (world_height / 8 + world_height / 8 / 5);

        ////////////////normal
        _textBox_content = new TextBox();
        _textBox_content.Initialize(_textBox_weight_x, _textBox_weight_y,
                (float) (world_width * 0.8), world_height / 8, 1, _content, "",
                Textures.get_numericEnter_textBox_background(), Color.BLACK,
                Main._little_font_fileHandle);

        float _space_between_buttons_width = _textBox_content.get_width() / 13;
        float _space_between_buttons_height = (_textBox_content.get_height_position() - world_width / 16) / 13;
        float _button_width = _space_between_buttons_width * 2;
        float _button_height = _space_between_buttons_height * 2;

        float _table1 = _textBox_weight_x + _space_between_buttons_width;
        float _row1 = _textBox_weight_y - (_space_between_buttons_height + _button_height);
        float _table2 = _table1 + (_space_between_buttons_width + _button_width);
        float _row2 = _row1 - (_space_between_buttons_height + _button_height);
        float _table3 = _table2 + (_space_between_buttons_width + _button_width);
        float _row3 = _row2 - (_space_between_buttons_height + _button_height);
        float _table4 = _table3 + (_space_between_buttons_width + _button_width);
        float _row4 = _row3 - (_space_between_buttons_height + _button_height);


        _numeric_7 = new Button();
        _numeric_7.initialize(_table1, _row1, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "7",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_4 = new Button();
        _numeric_4.initialize(_table1, _row2, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "4",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_1 = new Button();
        _numeric_1.initialize(_table1, _row3, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "1",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_8 = new Button();
        _numeric_8.initialize(_table2, _row1, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "8",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_5 = new Button();
        _numeric_5.initialize(_table2, _row2, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "5",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_2 = new Button();
        _numeric_2.initialize(_table2, _row3, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "2",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_0 = new Button();
        _numeric_0.initialize(_table2, _row4, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "0",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_9 = new Button();
        _numeric_9.initialize(_table3, _row1, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "9",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_6 = new Button();
        _numeric_6.initialize(_table3, _row2, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "6",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_3 = new Button();
        _numeric_3.initialize(_table3, _row3, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "3",
                _main._button_textColor, Main._little_font_fileHandle);
        _clear = new Button();
        _clear.initialize(_table4, _row2, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.drop,
                _main._button_textColor, Main._little_font_fileHandle);
        _eraseSymbol = new Button();
        _eraseSymbol.initialize(_table4, _row1, _button_width, _button_height,
                Textures.get_erase_symbol_pushed(),
                Textures.get_erase_symbol_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "",
                _main._button_textColor, Main._little_font_fileHandle);
        _enter = new Button();
        _enter.initialize(_table4, _row4, _button_width, _button_height * 2 + _space_between_buttons_height,
                Textures.get_green_button_pushed(),
                Textures.get_green_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.confirm,
                _main._button_textColor, Main._little_font_fileHandle);

        _symbol_dot = new Button();
        _symbol_dot.initialize(_numeric_3.get_width_position(),
                _numeric_0.get_height_position(),
                _numeric_0.get_width(), _numeric_0.get_height(),
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                ".",
                _main._button_textColor, Main._little_font_fileHandle);
    }

    /**
     * initialize action buttons
     */
    private void initializeOther() {
        _incorrectText_indicator = new TextBox();
        _incorrectText_indicator.Initialize(_textBox_content.get_width_position() +
                        _textBox_content.get_width() - (float) (_textBox_content.get_width() / 10 * 3.5),
                _textBox_content.get_height_position() + _textBox_content.get_height() / 6 * 2,
                _textBox_content.get_width() / 10 * 3, _textBox_content.get_height() / 6 * 2, 1, TextValues.globalValues.inputError, "",
                Textures.get_stop_button_not_pushed(), Color.WHITE, Main._little_font_fileHandle);
    }

    @Override
    public void render(float delta) {
/**
 * update
 */
        finish();
        updateBeforeDraw();
/**
 * clear and draw background
 */
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _batch.begin();
        _main.backgroundSprite.draw(_batch);
/**
 * draw
 */
        draw();
    }

    /**
     * update values before draw
     */
    private void updateBeforeDraw() {
        if (_timer < _main._delay) _timer += Gdx.graphics.getDeltaTime();
        _cam.update();

        _textBox_content.updateContent(_content);
        _error.updateContent(_content);
    }

    /**
     * draw elements
     */
    private void draw() {
        _textBox_content.draw(_batch);
        _textToEnter_font.draw(_batch, _textToEnter_glyphLayout,
                _textBox_content.get_width_position() + _textBox_content.get_width() / 10,

                _textBox_content.get_height_position() + _textBox_content.get_height() / 2
                        + _textToEnter_glyphLayout.height / 2);
        drawIncorrectDataIndicator(_incorrect_data, _batch);

        _numeric_7.draw(_batch);
        _numeric_4.draw(_batch);
        _numeric_1.draw(_batch);
        _numeric_8.draw(_batch);
        _numeric_5.draw(_batch);
        _numeric_2.draw(_batch);
        _numeric_0.draw(_batch);
        _numeric_9.draw(_batch);
        _numeric_6.draw(_batch);
        _numeric_3.draw(_batch);
        _symbol_dot.draw(_batch);
        _clear.draw(_batch);
        _eraseSymbol.draw(_batch);
        _enter.draw(_batch);

        _batch.end();
        _renderer.begin(ShapeType.Line);
        _numeric_7.drawRect(_renderer);
        _numeric_4.drawRect(_renderer);
        _numeric_1.drawRect(_renderer);
        _numeric_8.drawRect(_renderer);
        _numeric_5.drawRect(_renderer);
        _numeric_2.drawRect(_renderer);
        _numeric_0.drawRect(_renderer);
        _numeric_9.drawRect(_renderer);
        _numeric_6.drawRect(_renderer);
        _numeric_3.drawRect(_renderer);
        _symbol_dot.drawRect(_renderer);
        _clear.drawRect(_renderer);
        _eraseSymbol.drawRect(_renderer);
        _enter.drawRect(_renderer);

        _renderer.end();
        _error.draw(_batch, _cam);

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
     * depress pressed buttons
     */
    private void dePressButtons() {
        _numeric_0.updateState(false);
        _numeric_1.updateState(false);
        _numeric_2.updateState(false);
        _numeric_3.updateState(false);
        _numeric_4.updateState(false);
        _numeric_5.updateState(false);
        _numeric_6.updateState(false);
        _numeric_7.updateState(false);
        _numeric_8.updateState(false);
        _numeric_9.updateState(false);
        _symbol_dot.updateState(false);
        _enter.updateState(false);
        _clear.updateState(false);
        _eraseSymbol.updateState(false);
        _error.dePressButtons();
    }

    /**
     * handle single touch events
     */
    private void handleSingleInput() {
        if (_main._status.returnButton_touched(_cam)) {
            _main.setScreen(_lastScreen);
        }
        if (!_error.isShowed()) {
            /////////// numeric 0 touched
            if (_numeric_0.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "0";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// numeric 1 touched
            if (_numeric_1.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "1";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// numeric 2 touched
            if (_numeric_2.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "2";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// numeric 3 touched
            if (_numeric_3.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "3";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// numeric 4 touched
            if (_numeric_4.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "4";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// numeric 5 touched
            if (_numeric_5.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "5";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// numeric 6 touched
            if (_numeric_6.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "6";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// numeric 7 touched
            if (_numeric_7.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "7";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// numeric 8 touched
            if (_numeric_8.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "8";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// numeric 9 touched
            if (_numeric_9.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += "9";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// erase touched
            if (_eraseSymbol.isTouched(_cam)) {
                if (_content.length() == 1 || _content.length() == 0) {
                    _content = "";
                    _incorrect_data = false;
                    return;
                } else {
                    _content = _content.substring(0, _content.length() - 1);
                    _incorrect_data = false;
                    return;
                }
            }
            if (_symbol_dot.isTouched(_cam)) {
                if (_content.length() < _length) {
                    _content += ".";
                    _incorrect_data = false;
                    return;
                }
            }
            /////////// clear touched
            if (_clear.isTouched(_cam)) {
                _content = "";
                _incorrect_data = false;
                return;
            }
            /////////// enter touched
            if (_enter.isTouched(_cam))
                if (Main.CheckForIP(_content)) {
                    _error.show();
                } else {
                    _incorrect_data = true;
                }
        } else {
            _error.handleSingleInput();
        }

    }

    /**
     * check for finish
     * and if true - set up new value and return to previous screen
     */
    private void finish() {
        if (_error.isFinished()) {
            _ip = _content;
            setBuffer(_ip);
            _main.setScreen(_lastScreen);
        }
    }

    /**
     * handle long touch events
     */
    private void handleLongTermDepression() {

        if (!_error.isShowed()) {
            /////////////// drawing numeric 0 touched
            if (_numeric_0.isTouched(_cam)) {
                _numeric_0.updateState(true);
                return;
            }
            /////////////// drawing numeric 1 touched
            if (_numeric_1.isTouched(_cam)) {
                _numeric_1.updateState(true);
                return;
            }
            /////////////// drawing numeric 2 touched
            if (_numeric_2.isTouched(_cam)) {
                _numeric_2.updateState(true);
                return;
            }
            /////////////// drawing numeric 3 touched
            if (_numeric_3.isTouched(_cam)) {
                _numeric_3.updateState(true);
                return;
            }
            /////////////// drawing numeric 4 touched
            if (_numeric_4.isTouched(_cam)) {
                _numeric_4.updateState(true);
                return;
            }
            /////////////// drawing numeric 5 touched
            if (_numeric_5.isTouched(_cam)) {
                _numeric_5.updateState(true);
                return;
            }
            /////////////// drawing numeric 6 touched
            if (_numeric_6.isTouched(_cam)) {
                _numeric_6.updateState(true);
                return;
            }
            /////////////// drawing numeric 7 touched
            if (_numeric_7.isTouched(_cam)) {
                _numeric_7.updateState(true);
                return;
            }
            /////////////// drawing numeric 8 touched
            if (_numeric_8.isTouched(_cam)) {
                _numeric_8.updateState(true);
                return;
            }
            /////////////// drawing numeric 9 touched
            if (_numeric_9.isTouched(_cam)) {
                _numeric_9.updateState(true);
                return;
            }
            /////////////// drawing erase touched
            if (_eraseSymbol.isTouched(_cam)) {
                _eraseSymbol.updateState(true);
                return;
            }
            /////////////// drawing clear touched
            if (_clear.isTouched(_cam)) {
                _clear.updateState(true);
                return;
            }
            /////////////// drawing enter touched
            if (_enter.isTouched(_cam)) {
                _enter.updateState(true);
                return;
            }
            if (_symbol_dot.isTouched(_cam)) {
                _symbol_dot.updateState(true);
                return;
            }
        } else {
            _error.handleLongTermDepression();
        }
        dePressButtons();

    }

    /**
     * draw incorrect data indicator
     * @param error boolean value, which defines true if we need to draw
     * @param batch - batch, in which we draw
     */
    private void drawIncorrectDataIndicator(boolean error, SpriteBatch batch) {
        if (error) {
            _incorrectText_indicator.draw(batch);
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
        dePressButtons();
        _main._status.updateNavigation(TextValues.navigation.enterIPScreen);
        _timer = 0;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        _error.notFinished();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        _error.dispose();
        _numeric_0.dispose();
        _numeric_1.dispose();
        _numeric_2.dispose();
        _numeric_3.dispose();
        _numeric_4.dispose();
        _numeric_5.dispose();
        _numeric_6.dispose();
        _numeric_7.dispose();
        _numeric_8.dispose();
        _numeric_9.dispose();
        _eraseSymbol.dispose();
        _symbol_dot.dispose();
        _clear.dispose();
        _enter.dispose();
    }

    @Override
    public void setBuffer(String stringToChange) {
        _lastScreen.setBuffer(stringToChange);
    }

    @Override
    public String getBuffer() {
        return _lastScreen.getBuffer();
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
                handleLongTermDepression();
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
                dePressButtons();
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