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
import com.mygdx.game.android.Elements.Button;
import com.mygdx.game.android.Elements.TextBox;
import com.mygdx.game.android.Interfaces.IDataTransfer;
import com.mygdx.game.android.Main;

/**
 * class used to enter integer values
 */
public class NumericEnterScreen implements IDataTransfer, InputProcessor {

    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    //private ConfirmBox _confirm;
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
    private Button _eraseSymbol;
    private Button _enter;
    private Button _clear;
    private TextBox _textBox_content;


    private boolean _incorrect_data = false;

    private String _content;

    private static int _maximum;
    private static int _minimum;
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
    public NumericEnterScreen(Main main, SpriteBatch batch, ShapeRenderer renderer,
                              OrthographicCamera camera) {
        if (main == null || batch == null || renderer == null || camera == null) {
            throw new NullPointerException("NumericEnterScreen initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = camera;

        _timer = 0;
        _content = "";


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
     * @param lastScreen     - last screen to return to
     * @param textToEnter    - description of value to enter
     * @param minimum        - minimum value to enter
     * @param maximum        - maximum value to enter
     * @param content_length - maximum length of value
     */
    public void Initialize(IDataTransfer lastScreen, String textToEnter,
                           int minimum, int maximum, int content_length) {
        _minimum = minimum;
        _maximum = maximum;
        _content = lastScreen.getBuffer();
        _lastScreen = lastScreen;
        if ((content_length < 2) || (6 < content_length)) _length = 6;
        else _length = content_length;
        if (_content.length() > _length) {
            _content = _content.substring(0, _length);
        }
        _textToEnter = textToEnter;
        _textToEnter_glyphLayout.setText(_textToEnter_font, _textToEnter);
    }

    /**
     * initialize numeric buttons
     */
    private void initializeButtons() {
        float _textBox_weight_x = Main.SCREEN_WIDTH / 10;
        float _textBox_weight_y = Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8 / 2 - (Main.SCREEN_HEIGHT / 8 + Main.SCREEN_HEIGHT / 8 / 5);

        ////////////////normal
        _textBox_content = new TextBox();
        _textBox_content.Initialize(_textBox_weight_x, _textBox_weight_y,
                (float) (Main.SCREEN_WIDTH * 0.8), Main.SCREEN_HEIGHT / 8, 1, _content, "",
                Textures.get_numericEnter_textBox_background(), Color.BLACK,
                Main._little_font_fileHandle);

        float _space_between_buttons_width = _textBox_content.get_width() / 13;
        float _space_between_buttons_height = (_textBox_content.get_height_position() - Main.SCREEN_WIDTH / 16) / 13;
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
                Color.BLACK, Main._little_font_fileHandle);
        _enter = new Button();
        _enter.initialize(_table4, _row4, _button_width, _button_height * 2 + _space_between_buttons_height,
                Textures.get_green_button_pushed(),
                Textures.get_green_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.confirm,
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
        updateBeforeDraw();
/**
 * clear and draw background
 */
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _batch.begin();
        _main.backgroundSprite.draw(_batch);
    /*_batch.end();
    _numeric_groupBox.draw(_batch, _cam);
    _actions_groupBox.draw(_batch, _cam);
    _batch.begin();*/
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

        //_confirm.updateContent(_content);
    }

    /**
     * draw elements
     */
    private void draw() {
        _textBox_content.draw(_batch);
        _textToEnter_font.draw(_batch, _textToEnter_glyphLayout,
                _textBox_content.get_width_position() + _textBox_content.get_width() / 20,

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
        _clear.drawRect(_renderer);
        _eraseSymbol.drawRect(_renderer);
        _enter.drawRect(_renderer);

        _renderer.end();
        //_confirm.draw(_batch, _cam);

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
     * handle single touch events
     */
    private void handleSingleInput() {
        if (_main._status.is_error()) return;
//////////////// this method handles button pressing
        //if (Gdx.input.justTouched()) {
        if (_main._status.returnButton_touched(_cam)) {
            _main.setScreen(_lastScreen);
        }

        /////////// numeric 0 touched
        if (_numeric_0.isTouched(_cam)) {
            if (Integer.parseInt(_content) == 0) {
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "0";
                    _incorrect_data = false;
                }
            }
        }
        /////////// numeric 1 touched
        if (_numeric_1.isTouched(_cam)) {

            if (Integer.parseInt(_content) == 0) {
                _content = "1";
                _incorrect_data = false;
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "1";
                    _incorrect_data = false;
                }
            }
        }
        /////////// numeric 2 touched
        if (_numeric_2.isTouched(_cam)) {

            if (Integer.parseInt(_content) == 0) {
                _content = "2";
                _incorrect_data = false;
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "2";
                    _incorrect_data = false;
                }
            }
        }
        /////////// numeric 3 touched
        if (_numeric_3.isTouched(_cam)) {
            if (Integer.parseInt(_content) == 0) {
                _content = "3";
                _incorrect_data = false;
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "3";
                    _incorrect_data = false;
                }
            }
        }
        /////////// numeric 4 touched
        if (_numeric_4.isTouched(_cam)) {
            if (Integer.parseInt(_content) == 0) {
                _content = "4";
                _incorrect_data = false;
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "4";
                    _incorrect_data = false;
                }
            }
        }
        /////////// numeric 5 touched
        if (_numeric_5.isTouched(_cam)) {
            if (Integer.parseInt(_content) == 0) {
                _content = "5";
                _incorrect_data = false;
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "5";
                    _incorrect_data = false;
                }
            }
        }
        /////////// numeric 6 touched
        if (_numeric_6.isTouched(_cam)) {
            if (Integer.parseInt(_content) == 0) {
                _content = "6";
                _incorrect_data = false;
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "6";
                    _incorrect_data = false;
                }
            }
        }
        /////////// numeric 7 touched
        if (_numeric_7.isTouched(_cam)) {
            if (Integer.parseInt(_content) == 0) {
                _content = "7";
                _incorrect_data = false;
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "7";
                    _incorrect_data = false;
                }
            }
        }
        /////////// numeric 8 touched
        if (_numeric_8.isTouched(_cam)) {
            if (Integer.parseInt(_content) == 0) {
                _content = "8";
                _incorrect_data = false;
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "8";
                    _incorrect_data = false;
                }
            }
        }
        /////////// numeric 9 touched
        if (_numeric_9.isTouched(_cam)) {
            if (Integer.parseInt(_content) == 0) {
                _content = "9";
                _incorrect_data = false;
            } else {
                if (_content.length() >= _length) {
                } else {
                    _content += "9";
                    _incorrect_data = false;
                }
            }
        }
        /////////// erase touched
        if (_eraseSymbol.isTouched(_cam)) {
            if (_content.length() == 1) {
                _content = "0";
                _incorrect_data = false;
            } else {
                _content = _content.substring(0, _content.length() - 1);
                _incorrect_data = false;
            }
        }
        /////////// clear touched
        if (_clear.isTouched(_cam)) {
            _content = "0";
            _incorrect_data = false;
        }
        /////////// enter touched
        if (_enter.isTouched(_cam))
            if (checkContent()) {
                //_exit = true;
                setBuffer(_content);
                _main.changeScreen(_lastScreen);
            } else {
                _incorrect_data = true;
            }

    }

    /**
     * de press all buttons
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
        _enter.updateState(false);
        _clear.updateState(false);
        _eraseSymbol.updateState(false);

    }

    /**
     * method handles long pressing
     */
    private void handleLongTermDepression() {
        if (_main._status.is_error()) return;
        //if (Gdx.input.isTouched()) {

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
        dePressButtons();

    }

    /**
     * draw incorrect data indicator
     *
     * @param error boolean value, which defines true if we need to draw
     * @param batch - batch, in which we draw
     */
    private void drawIncorrectDataIndicator(boolean error, SpriteBatch batch) {
        if (error) {
            _incorrectText_indicator.draw(batch);
        }
    }

    /**
     * check content for correct value
     *
     * @return true if correct
     */
    private boolean checkContent() {
        return (Integer.parseInt(_content) < _maximum) && (Integer.parseInt(_content) > _minimum);
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
        _main._status.updateNavigation(TextValues.navigation.numericEnterScreen);
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