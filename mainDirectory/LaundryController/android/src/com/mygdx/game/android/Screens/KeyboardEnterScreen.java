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
 * screen, used to enter text
 */
public class KeyboardEnterScreen implements IDataTransfer, InputProcessor {

    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    private ConfirmBox _error;

    private Main _main;
    /**
     * numeric row
     */
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

    /**
     * row1
     */
    private Button _button_q;
    private Button _button_w;
    private Button _button_e;
    private Button _button_r;
    private Button _button_t;
    private Button _button_y;
    private Button _button_u;
    private Button _button_i;
    private Button _button_o;
    private Button _button_p;
    private Button _button_LeftBracket;
    /**
     * row2
     */
    private Button _button_a;
    private Button _button_s;
    private Button _button_d;
    private Button _button_f;
    private Button _button_g;
    private Button _button_h;
    private Button _button_j;
    private Button _button_k;
    private Button _button_l;
    private Button _button_colon;
    private Button _button_quotes;
    /**
     * row3
     */
    private Button _button_z;
    private Button _button_x;
    private Button _button_c;
    private Button _button_v;
    private Button _button_b;
    private Button _button_n;
    private Button _button_m;
    private Button _button_LeftAngleBracket;
    private Button _button_RightAngleBracket;
    /**
     * other
     */
    private Button _registerChange;
    private Button _space_button;
    private Button _symbol_dot;
    private Button _eraseSymbol;
    private Button _enter;
    private Button _clear;
    private TextBox _textBox_content;

    private static float _button_width;
    private static float _button_height;

    private static int _max_length;

    private String _content;

    private boolean _upperCase;

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
     * @param camera   - ortographic camera
     */
    public KeyboardEnterScreen(Main main, SpriteBatch batch, ShapeRenderer renderer, OrthographicCamera camera) {
        if (main == null || batch == null || renderer == null || camera == null) {
            throw new NullPointerException("KeyboardEnter initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = camera;

        _timer = 0;
        _upperCase = false;

        _content = "";
        _textToEnter = "";
        _error = new ConfirmBox(_content, TextValues.globalValues.confirmEnteredData, _cam);

        _textToEnter_glyphLayout = new GlyphLayout();
        _textToEnter_font = Main._little_font;
        _textToEnter_font.setColor(Color.BLACK);
        _textToEnter_glyphLayout.setText(_textToEnter_font, _textToEnter);

        _textBox_content = new TextBox();
        _textBox_content.Initialize((Main.SCREEN_WIDTH / 10) / 4,
                Main.SCREEN_HEIGHT - _main._status.get_height() - Main.SCREEN_HEIGHT / 8 - _main._status.get_height() / 2,
                (float) (Main.SCREEN_WIDTH * 0.95),
                Main.SCREEN_HEIGHT / 8,
                1, _content, "",
                Textures.get_numericEnter_textBox_background(), Color.BLACK,
                Main._little_font_fileHandle);

        _button_height = Main.SCREEN_HEIGHT / 8;
        _button_width = (float) (Main.SCREEN_WIDTH * 0.95 / 12.5);

        float row1 = _textBox_content.get_height_position() - Main.SCREEN_WIDTH / 16 / 2
                - _button_height / 10 * 8;

        float row2 = row1 - Main.SCREEN_WIDTH / 16 / 2 - (float) (_button_height * 0.8);
        float row3 = row2 - _button_height;
        float row4 = row3 - _button_height;

        float first_button_width_position = (Main.SCREEN_WIDTH / 10) / 4;

        initializeNumericRow(row1, first_button_width_position);
        initializeFirstRow(row2, first_button_width_position);
        initializeSecondRow(row3, first_button_width_position);
        initializeThirdRow(row4, first_button_width_position);

        _space_button = new Button();
        _space_button.initialize(_button_v.get_width_position(),
                row4 - _button_height,
                _button_width * 4,
                (float) (_button_height * 0.8),
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.space,
                _main._button_textColor, Main._little_font_fileHandle);
        _symbol_dot = new Button();
        _symbol_dot.initialize(_button_RightAngleBracket.get_width_position(),
                _space_button.get_height_position(),
                _button_RightAngleBracket.get_width(), _space_button.get_height(),
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                ".",
                _main._button_textColor, Main._little_font_fileHandle);
    }

    /**
     * initialization before usage
     *
     * @param lastScreen - last screen to return to
     */
    public void Initialize(IDataTransfer lastScreen, String textToEnter, int max_content_length) {
        _lastScreen = lastScreen;
        _textToEnter = textToEnter;
        _textToEnter_glyphLayout.setText(_textToEnter_font, _textToEnter);
        _content = lastScreen.getBuffer();
        if ((max_content_length < 5) || (25 < max_content_length)) _max_length = 20;
        else _max_length = max_content_length;
        if (_content.length() > _max_length) {
            _content = _content.substring(0, _max_length);
        }
    }

    /**
     * initialize numeric buttons row
     *
     * @param row         - row height
     * @param buttonWidth - buttons width
     */
    private void initializeNumericRow(float row, float buttonWidth) {

        float numeric_button_width = (float) (Main.SCREEN_WIDTH * 0.95 / 10),
                numeric_button_height = (float) (_button_height * 0.8);

        _numeric_1 = new Button();
        _numeric_1.initialize(buttonWidth, row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "1",
                _main._button_textColor, Main._little_font_fileHandle);

        _numeric_2 = new Button();
        _numeric_2.initialize(_numeric_1.get_width_position() + numeric_button_width,
                row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "2",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_3 = new Button();
        _numeric_3.initialize(_numeric_2.get_width_position() + numeric_button_width,
                row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "3",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_4 = new Button();
        _numeric_4.initialize(_numeric_3.get_width_position() + numeric_button_width,
                row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "4",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_5 = new Button();
        _numeric_5.initialize(_numeric_4.get_width_position() + numeric_button_width,
                row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "5",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_6 = new Button();
        _numeric_6.initialize(_numeric_5.get_width_position() + numeric_button_width,
                row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "6",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_7 = new Button();
        _numeric_7.initialize(_numeric_6.get_width_position() + numeric_button_width,
                row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "7",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_8 = new Button();
        _numeric_8.initialize(_numeric_7.get_width_position() + numeric_button_width,
                row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "8",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_9 = new Button();
        _numeric_9.initialize(_numeric_8.get_width_position() + numeric_button_width,
                row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "9",
                _main._button_textColor, Main._little_font_fileHandle);
        _numeric_0 = new Button();
        _numeric_0.initialize(_numeric_9.get_width_position() + numeric_button_width,
                row, numeric_button_width, numeric_button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "0",
                _main._button_textColor, Main._little_font_fileHandle);
    }

    /**
     * initialize first row of symbolic buttons
     *
     * @param row         - row height
     * @param buttonWidth - buttons width
     */
    private void initializeFirstRow(float row, float buttonWidth) {

        _button_q = new Button();
        _button_q.initialize(buttonWidth, row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_q,
                _main._button_textColor, Main._little_font_fileHandle);

        _button_w = new Button();
        _button_w.initialize(_button_q.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_w,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_e = new Button();
        _button_e.initialize(_button_w.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_e,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_r = new Button();
        _button_r.initialize(_button_e.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_r,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_t = new Button();
        _button_t.initialize(_button_r.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_t,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_y = new Button();
        _button_y.initialize(_button_t.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_y,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_u = new Button();
        _button_u.initialize(_button_y.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_u,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_i = new Button();
        _button_i.initialize(_button_u.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_i,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_o = new Button();
        _button_o.initialize(_button_i.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_o,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_p = new Button();
        _button_p.initialize(_button_o.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_p,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_LeftBracket = new Button();
        _button_LeftBracket.initialize(_button_p.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_LeftBracket,
                _main._button_textColor, Main._little_font_fileHandle);
        _eraseSymbol = new Button();
        _eraseSymbol.initialize(_button_LeftBracket.get_width_position() + _button_width,
                row, _button_width * 1.5f, _button_height,
                Textures.get_erase_symbol_pushed(),
                Textures.get_erase_symbol_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "",
                _main._button_textColor, Main._little_font_fileHandle);
    }

    /**
     * initialize second row of symbolic buttons
     *
     * @param row         - row height
     * @param buttonWidth - buttons width
     */
    private void initializeSecondRow(float row, float buttonWidth) {
        _button_a = new Button();
        _button_a.initialize(buttonWidth, row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_a,
                _main._button_textColor, Main._little_font_fileHandle);

        _button_s = new Button();
        _button_s.initialize(_button_a.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_s,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_d = new Button();
        _button_d.initialize(_button_s.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_d,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_f = new Button();
        _button_f.initialize(_button_d.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_f,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_g = new Button();
        _button_g.initialize(_button_f.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_g,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_h = new Button();
        _button_h.initialize(_button_g.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_h,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_j = new Button();
        _button_j.initialize(_button_h.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_j,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_k = new Button();
        _button_k.initialize(_button_j.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_k,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_l = new Button();
        _button_l.initialize(_button_k.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_l,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_colon = new Button();
        _button_colon.initialize(_button_l.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_colon,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_quotes = new Button();
        _button_quotes.initialize(_button_colon.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_quotes,
                _main._button_textColor, Main._little_font_fileHandle);

        _clear = new Button();
        _clear.initialize(_button_quotes.get_width_position() + _button_width,
                row, _button_width * 1.5f, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed_darker(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.drop,
                _main._button_textColor, Main._little_font_fileHandle);
    }

    /**
     * initialize third row of symbolic buttons
     *
     * @param row         - row height
     * @param buttonWidth - buttons width
     */
    private void initializeThirdRow(float row, float buttonWidth) {
        _registerChange = new Button();
        _registerChange.initialize(buttonWidth, row,
                _button_width, _button_height,
                Textures.get_register_change_pushed(),
                Textures.get_register_change_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                "",
                _main._button_textColor, Main._little_font_fileHandle);

        _button_z = new Button();
        _button_z.initialize(_registerChange.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_z,
                _main._button_textColor, Main._little_font_fileHandle);

        _button_x = new Button();
        _button_x.initialize(_button_z.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_x,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_c = new Button();
        _button_c.initialize(_button_x.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_c,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_v = new Button();
        _button_v.initialize(_button_c.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_v,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_b = new Button();
        _button_b.initialize(_button_v.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_b,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_n = new Button();
        _button_n.initialize(_button_b.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_n,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_m = new Button();
        _button_m.initialize(_button_n.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_m,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_LeftAngleBracket = new Button();
        _button_LeftAngleBracket.initialize(_button_m.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_LeftAngleBracket,
                _main._button_textColor, Main._little_font_fileHandle);
        _button_RightAngleBracket = new Button();
        _button_RightAngleBracket.initialize(_button_LeftAngleBracket.get_width_position() + _button_width,
                row, _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.keyboardEnterButtons.button_RightAngleBracket,
                _main._button_textColor, Main._little_font_fileHandle);
        _enter = new Button();
        _enter.initialize(_button_RightAngleBracket.get_width_position() + _button_width,
                row,
                _button_width * 2.5f, _button_height,
                Textures.get_green_button_pushed(),
                Textures.get_green_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.confirm,
                _main._button_textColor, Main._little_font_fileHandle);
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
     * draw elements
     */
    private void draw() {
        _textBox_content.draw(_batch);
        _textToEnter_font.draw(_batch, _textToEnter_glyphLayout,
                _textBox_content.get_width_position() + _textBox_content.get_width() / 20,
                _textBox_content.get_height_position()
                        + _textBox_content.get_height() / 2 + _textToEnter_glyphLayout.height / 2);

        drawNumericRow();
        drawFirstRow();
        drawSecondRow();
        drawThirdRow();
        _registerChange.draw(_batch);
        _space_button.draw(_batch);
        _symbol_dot.draw(_batch);
        _batch.end();
        _renderer.begin(ShapeType.Line);
        drawNumericRowRect();
        drawFirstRowRect();
        drawSecondRowRect();
        drawThirdRowRect();
        _registerChange.drawRect(_renderer);
        _space_button.drawRect(_renderer);
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
     * draw numeric row of elements
     */
    private void drawNumericRow() {
        _numeric_1.draw(_batch);
        _numeric_2.draw(_batch);
        _numeric_3.draw(_batch);
        _numeric_4.draw(_batch);
        _numeric_5.draw(_batch);
        _numeric_6.draw(_batch);
        _numeric_7.draw(_batch);
        _numeric_8.draw(_batch);
        _numeric_9.draw(_batch);
        _numeric_0.draw(_batch);
    }

    /**
     * draw first row of elements
     */
    private void drawFirstRow() {
        _button_q.draw(_batch);
        _button_w.draw(_batch);
        _button_e.draw(_batch);
        _button_r.draw(_batch);
        _button_t.draw(_batch);
        _button_y.draw(_batch);
        _button_u.draw(_batch);
        _button_i.draw(_batch);
        _button_o.draw(_batch);
        _button_p.draw(_batch);
        _button_LeftBracket.draw(_batch);
        _clear.draw(_batch);
    }

    /**
     * draw second row of elements
     */
    private void drawSecondRow() {
        _button_a.draw(_batch);
        _button_s.draw(_batch);
        _button_d.draw(_batch);
        _button_f.draw(_batch);
        _button_g.draw(_batch);
        _button_h.draw(_batch);
        _button_j.draw(_batch);
        _button_k.draw(_batch);
        _button_l.draw(_batch);
        _button_colon.draw(_batch);
        _button_quotes.draw(_batch);
        _eraseSymbol.draw(_batch);
    }

    /**
     * draw third row of elements
     */
    private void drawThirdRow() {
        _button_z.draw(_batch);
        _button_x.draw(_batch);
        _button_c.draw(_batch);
        _button_v.draw(_batch);
        _button_b.draw(_batch);
        _button_n.draw(_batch);
        _button_m.draw(_batch);
        _button_LeftAngleBracket.draw(_batch);
        _button_RightAngleBracket.draw(_batch);
        _enter.draw(_batch);
    }

    /**
     * draw numeric row of elements borders
     */
    private void drawNumericRowRect() {
        _numeric_1.drawRect(_renderer);
        _numeric_2.drawRect(_renderer);
        _numeric_3.drawRect(_renderer);
        _numeric_4.drawRect(_renderer);
        _numeric_5.drawRect(_renderer);
        _numeric_6.drawRect(_renderer);
        _numeric_7.drawRect(_renderer);
        _numeric_8.drawRect(_renderer);
        _numeric_9.drawRect(_renderer);
        _numeric_0.drawRect(_renderer);
    }

    /**
     * draw first row of elements borders
     */
    private void drawFirstRowRect() {
        _button_q.drawRect(_renderer);
        _button_w.drawRect(_renderer);
        _button_e.drawRect(_renderer);
        _button_r.drawRect(_renderer);
        _button_t.drawRect(_renderer);
        _button_y.drawRect(_renderer);
        _button_u.drawRect(_renderer);
        _button_i.drawRect(_renderer);
        _button_o.drawRect(_renderer);
        _button_p.drawRect(_renderer);
        _button_LeftBracket.drawRect(_renderer);
        _clear.drawRect(_renderer);
    }

    /**
     * draw second row of elements borders
     */
    private void drawSecondRowRect() {
        _button_a.drawRect(_renderer);
        _button_s.drawRect(_renderer);
        _button_d.drawRect(_renderer);
        _button_f.drawRect(_renderer);
        _button_g.drawRect(_renderer);
        _button_h.drawRect(_renderer);
        _button_j.drawRect(_renderer);
        _button_k.drawRect(_renderer);
        _button_l.drawRect(_renderer);
        _button_colon.drawRect(_renderer);
        _button_quotes.drawRect(_renderer);
        _eraseSymbol.drawRect(_renderer);
    }

    /**
     * draw third row of elements borders
     */
    private void drawThirdRowRect() {
        _button_z.drawRect(_renderer);
        _button_x.drawRect(_renderer);
        _button_c.drawRect(_renderer);
        _button_v.drawRect(_renderer);
        _button_b.drawRect(_renderer);
        _button_n.drawRect(_renderer);
        _button_m.drawRect(_renderer);
        _button_LeftAngleBracket.drawRect(_renderer);
        _button_RightAngleBracket.drawRect(_renderer);
        _enter.drawRect(_renderer);
    }

    /**
     * update values before draw
     */
    private void updateBeforeDraw() {
        if (_timer < _main._delay) _timer += Gdx.graphics.getDeltaTime();
        _cam.update();
        _textBox_content.updateContent(_content);
        _registerChange.updateState(_upperCase);
        _error.updateContent(_content);
    }

    /**
     * method handles single touch events
     */
    private void handleSingleInput() {
        if (_main._status.returnButton_touched(_cam)) {
            _main.changeScreen(_lastScreen);
        }
        if (!_error.isShowed()) {
            if (_main.IsTouched(_registerChange.get_width_position(),
                    _registerChange.get_height_position(),
                    (float) (_registerChange.get_width() * 12.5),
                    _registerChange.get_height() * 3)) {

                handleSingleSymbolicEnter();

                if (_registerChange.isTouched(_cam)) {
                    changeRegister();
                    return;
                }

                if (_eraseSymbol.isTouched(_cam)) {
                    if (_content.length() != 0)
                        _content = _content.substring(0, _content.length() - 1);
                    return;
                }
                if (_clear.isTouched(_cam)) {
                    _content = "";
                    return;
                }
                if (_enter.isTouched(_cam)) {
                    _error.show();
                    return;
                }
            }

            if (_main.IsTouched(_numeric_1.get_width_position(),
                    _numeric_1.get_height_position(),
                    (_numeric_1.get_width() * 10),
                    _numeric_1.get_height())) {
                /**
                 * numeric row
                 */
                if (_numeric_1.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_1.get_buttonContent();
                    return;
                }
                if (_numeric_2.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_2.get_buttonContent();
                    return;
                }
                if (_numeric_3.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_3.get_buttonContent();
                    return;
                }
                if (_numeric_4.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_4.get_buttonContent();
                    return;
                }
                if (_numeric_5.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_5.get_buttonContent();
                    return;
                }
                if (_numeric_6.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_6.get_buttonContent();
                    return;
                }
                if (_numeric_7.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_7.get_buttonContent();
                    return;
                }
                if (_numeric_8.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_8.get_buttonContent();
                    return;
                }
                if (_numeric_9.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_9.get_buttonContent();
                    return;
                }
                if (_numeric_0.isTouched(_cam) && _content.length() < _max_length) {
                    _content += _numeric_0.get_buttonContent();
                    return;
                }

            }
            /////////// space button touched
            if (_space_button.isTouched(_cam) && _content.length() < _max_length) {
                _content += " ";
                return;
            }
            if (_symbol_dot.isTouched(_cam) && _content.length() < _max_length) {
                _content += ".";
                return;
            }
        } else {
            _error.handleSingleInput();
        }

    }

    /**
     * handles symbolic buttons touches
     */
    private void handleSingleSymbolicEnter() {


        /**
         * row1
         */
        if (_button_q.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_q.get_buttonContent();
            return;
        }
        if (_button_w.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_w.get_buttonContent();
            return;
        }
        if (_button_e.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_e.get_buttonContent();
            return;
        }
        if (_button_r.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_r.get_buttonContent();
            return;
        }
        if (_button_t.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_t.get_buttonContent();
            return;
        }
        if (_button_y.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_y.get_buttonContent();
            return;
        }
        if (_button_u.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_u.get_buttonContent();
            return;
        }
        if (_button_i.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_i.get_buttonContent();
            return;
        }
        if (_button_o.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_o.get_buttonContent();
            return;
        }
        if (_button_p.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_p.get_buttonContent();
            return;
        }
        if (_button_LeftBracket.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_LeftBracket.get_buttonContent();
            return;
        }
        /**
         * row2
         */
        if (_button_a.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_a.get_buttonContent();
            return;
        }
        if (_button_s.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_s.get_buttonContent();
            return;
        }
        if (_button_d.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_d.get_buttonContent();
            return;
        }
        if (_button_f.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_f.get_buttonContent();
            return;
        }
        if (_button_g.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_g.get_buttonContent();
            return;
        }
        if (_button_h.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_h.get_buttonContent();
            return;
        }
        if (_button_j.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_j.get_buttonContent();
            return;
        }
        if (_button_k.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_k.get_buttonContent();
            return;
        }
        if (_button_l.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_l.get_buttonContent();
            return;
        }
        if (_button_colon.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_colon.get_buttonContent();
            return;
        }
        if (_button_quotes.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_quotes.get_buttonContent();
            return;
        }
        /**
         * row3
         */
        if (_button_z.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_z.get_buttonContent();
            return;
        }
        if (_button_x.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_x.get_buttonContent();
            return;
        }
        if (_button_c.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_c.get_buttonContent();
            return;
        }
        if (_button_v.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_v.get_buttonContent();
            return;
        }
        if (_button_b.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_b.get_buttonContent();
            return;
        }
        if (_button_n.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_n.get_buttonContent();
            return;
        }
        if (_button_m.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_m.get_buttonContent();
            return;
        }
        if (_button_LeftAngleBracket.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_LeftAngleBracket.get_buttonContent();
            return;
        }
        if (_button_RightAngleBracket.isTouched(_cam) && _content.length() < _max_length) {
            _content += _button_RightAngleBracket.get_buttonContent();
            return;
        }
    }

    /**
     * change register of symbolic buttons
     */
    private void changeRegister() {
        if (_upperCase) {
            _button_q.setContent(
                    Character.toString(Character.toLowerCase(_button_q.get_buttonContent().charAt(0))));
            _button_w.setContent(
                    Character.toString(Character.toLowerCase(_button_w.get_buttonContent().charAt(0))));
            _button_e.setContent(
                    Character.toString(Character.toLowerCase(_button_e.get_buttonContent().charAt(0))));
            _button_r.setContent(
                    Character.toString(Character.toLowerCase(_button_r.get_buttonContent().charAt(0))));
            _button_t.setContent(
                    Character.toString(Character.toLowerCase(_button_t.get_buttonContent().charAt(0))));
            _button_y.setContent(
                    Character.toString(Character.toLowerCase(_button_y.get_buttonContent().charAt(0))));
            _button_u.setContent(
                    Character.toString(Character.toLowerCase(_button_u.get_buttonContent().charAt(0))));
            _button_i.setContent(
                    Character.toString(Character.toLowerCase(_button_i.get_buttonContent().charAt(0))));
            _button_o.setContent(
                    Character.toString(Character.toLowerCase(_button_o.get_buttonContent().charAt(0))));
            _button_p.setContent(
                    Character.toString(Character.toLowerCase(_button_p.get_buttonContent().charAt(0))));
            _button_LeftBracket.setContent(
                    Character.toString(Character.toLowerCase(_button_LeftBracket.get_buttonContent().charAt(0))));


            _button_a.setContent(
                    Character.toString(Character.toLowerCase(_button_a.get_buttonContent().charAt(0))));
            _button_s.setContent(
                    Character.toString(Character.toLowerCase(_button_s.get_buttonContent().charAt(0))));
            _button_d.setContent(
                    Character.toString(Character.toLowerCase(_button_d.get_buttonContent().charAt(0))));
            _button_f.setContent(
                    Character.toString(Character.toLowerCase(_button_f.get_buttonContent().charAt(0))));
            _button_g.setContent(
                    Character.toString(Character.toLowerCase(_button_g.get_buttonContent().charAt(0))));
            _button_h.setContent(
                    Character.toString(Character.toLowerCase(_button_h.get_buttonContent().charAt(0))));
            _button_j.setContent(
                    Character.toString(Character.toLowerCase(_button_j.get_buttonContent().charAt(0))));
            _button_k.setContent(
                    Character.toString(Character.toLowerCase(_button_k.get_buttonContent().charAt(0))));
            _button_l.setContent(
                    Character.toString(Character.toLowerCase(_button_l.get_buttonContent().charAt(0))));
            _button_colon.setContent(
                    Character.toString(Character.toLowerCase(_button_colon.get_buttonContent().charAt(0))));
            _button_quotes.setContent(
                    Character.toString(Character.toLowerCase(_button_quotes.get_buttonContent().charAt(0))));


            _button_z.setContent(
                    Character.toString(Character.toLowerCase(_button_z.get_buttonContent().charAt(0))));
            _button_x.setContent(
                    Character.toString(Character.toLowerCase(_button_x.get_buttonContent().charAt(0))));
            _button_c.setContent(
                    Character.toString(Character.toLowerCase(_button_c.get_buttonContent().charAt(0))));
            _button_v.setContent(
                    Character.toString(Character.toLowerCase(_button_v.get_buttonContent().charAt(0))));
            _button_b.setContent(
                    Character.toString(Character.toLowerCase(_button_b.get_buttonContent().charAt(0))));
            _button_n.setContent(
                    Character.toString(Character.toLowerCase(_button_n.get_buttonContent().charAt(0))));
            _button_m.setContent(
                    Character.toString(Character.toLowerCase(_button_m.get_buttonContent().charAt(0))));
            _button_LeftAngleBracket.setContent(
                    Character.toString(Character.toLowerCase(_button_LeftAngleBracket.get_buttonContent().charAt(0))));
            _button_RightAngleBracket.setContent(
                    Character.toString(Character.toLowerCase(_button_RightAngleBracket.get_buttonContent().charAt(0))));

            _upperCase = false;
        } else {
            _button_q.setContent(
                    Character.toString(Character.toUpperCase(_button_q.get_buttonContent().charAt(0))));
            _button_w.setContent(
                    Character.toString(Character.toUpperCase(_button_w.get_buttonContent().charAt(0))));
            _button_e.setContent(
                    Character.toString(Character.toUpperCase(_button_e.get_buttonContent().charAt(0))));
            _button_r.setContent(
                    Character.toString(Character.toUpperCase(_button_r.get_buttonContent().charAt(0))));
            _button_t.setContent(
                    Character.toString(Character.toUpperCase(_button_t.get_buttonContent().charAt(0))));
            _button_y.setContent(
                    Character.toString(Character.toUpperCase(_button_y.get_buttonContent().charAt(0))));
            _button_u.setContent(
                    Character.toString(Character.toUpperCase(_button_u.get_buttonContent().charAt(0))));
            _button_i.setContent(
                    Character.toString(Character.toUpperCase(_button_i.get_buttonContent().charAt(0))));
            _button_o.setContent(
                    Character.toString(Character.toUpperCase(_button_o.get_buttonContent().charAt(0))));
            _button_p.setContent(
                    Character.toString(Character.toUpperCase(_button_p.get_buttonContent().charAt(0))));
            _button_LeftBracket.setContent(
                    Character.toString(Character.toUpperCase(_button_LeftBracket.get_buttonContent().charAt(0))));


            _button_a.setContent(
                    Character.toString(Character.toUpperCase(_button_a.get_buttonContent().charAt(0))));
            _button_s.setContent(
                    Character.toString(Character.toUpperCase(_button_s.get_buttonContent().charAt(0))));
            _button_d.setContent(
                    Character.toString(Character.toUpperCase(_button_d.get_buttonContent().charAt(0))));
            _button_f.setContent(
                    Character.toString(Character.toUpperCase(_button_f.get_buttonContent().charAt(0))));
            _button_g.setContent(
                    Character.toString(Character.toUpperCase(_button_g.get_buttonContent().charAt(0))));
            _button_h.setContent(
                    Character.toString(Character.toUpperCase(_button_h.get_buttonContent().charAt(0))));
            _button_j.setContent(
                    Character.toString(Character.toUpperCase(_button_j.get_buttonContent().charAt(0))));
            _button_k.setContent(
                    Character.toString(Character.toUpperCase(_button_k.get_buttonContent().charAt(0))));
            _button_l.setContent(
                    Character.toString(Character.toUpperCase(_button_l.get_buttonContent().charAt(0))));
            _button_colon.setContent(
                    Character.toString(Character.toUpperCase(_button_colon.get_buttonContent().charAt(0))));
            _button_quotes.setContent(
                    Character.toString(Character.toUpperCase(_button_quotes.get_buttonContent().charAt(0))));


            _button_z.setContent(
                    Character.toString(Character.toUpperCase(_button_z.get_buttonContent().charAt(0))));
            _button_x.setContent(
                    Character.toString(Character.toUpperCase(_button_x.get_buttonContent().charAt(0))));
            _button_c.setContent(
                    Character.toString(Character.toUpperCase(_button_c.get_buttonContent().charAt(0))));
            _button_v.setContent(
                    Character.toString(Character.toUpperCase(_button_v.get_buttonContent().charAt(0))));
            _button_b.setContent(
                    Character.toString(Character.toUpperCase(_button_b.get_buttonContent().charAt(0))));
            _button_n.setContent(
                    Character.toString(Character.toUpperCase(_button_n.get_buttonContent().charAt(0))));
            _button_m.setContent(
                    Character.toString(Character.toUpperCase(_button_m.get_buttonContent().charAt(0))));
            _button_LeftAngleBracket.setContent(
                    Character.toString(Character.toUpperCase(_button_LeftAngleBracket.get_buttonContent().charAt(0))));
            _button_RightAngleBracket.setContent(
                    Character.toString(Character.toUpperCase(_button_RightAngleBracket.get_buttonContent().charAt(0))));

            _upperCase = true;
        }
    }

    /**
     * check for finish
     * transmit set up data to previous screen and switch to it
     */
    private void finish() {
        if (_error.isFinished()) {
            setBuffer(_content);
            _main.changeScreen(_lastScreen);
        }
    }

    /**
     * handles long touch events
     */
    private void handleLongTermDepression() {
        if (!_error.isShowed()) {
            if (_main.IsTouched(_registerChange.get_width_position(),
                    _registerChange.get_height_position(),
                    (float) (_registerChange.get_width() * 12.5),
                    _registerChange.get_height() * 3)) {

                /**
                 * row1
                 */
                if (_button_q.isTouched(_cam)) {
                    _button_q.updateState(true);
                    return;
                }
                if (_button_w.isTouched(_cam)) {
                    _button_w.updateState(true);
                    return;
                }
                if (_button_e.isTouched(_cam)) {
                    _button_e.updateState(true);
                    return;
                }
                if (_button_r.isTouched(_cam)) {
                    _button_r.updateState(true);
                    return;
                }
                if (_button_t.isTouched(_cam)) {
                    _button_t.updateState(true);
                    return;
                }
                if (_button_y.isTouched(_cam)) {
                    _button_y.updateState(true);
                    return;
                }
                if (_button_u.isTouched(_cam)) {
                    _button_u.updateState(true);
                    return;
                }
                if (_button_i.isTouched(_cam)) {
                    _button_i.updateState(true);
                    return;
                }
                if (_button_o.isTouched(_cam)) {
                    _button_o.updateState(true);
                    return;
                }
                if (_button_p.isTouched(_cam)) {
                    _button_p.updateState(true);
                    return;
                }
                if (_button_LeftBracket.isTouched(_cam)) {
                    _button_LeftBracket.updateState(true);
                    return;
                }
                /**
                 * row2
                 */
                if (_button_a.isTouched(_cam)) {
                    _button_a.updateState(true);
                    return;
                }
                if (_button_s.isTouched(_cam)) {
                    _button_s.updateState(true);
                    return;
                }
                if (_button_d.isTouched(_cam)) {
                    _button_d.updateState(true);
                    return;
                }
                if (_button_f.isTouched(_cam)) {
                    _button_f.updateState(true);
                    return;
                }
                if (_button_g.isTouched(_cam)) {
                    _button_g.updateState(true);
                    return;
                }
                if (_button_h.isTouched(_cam)) {
                    _button_h.updateState(true);
                    return;
                }
                if (_button_j.isTouched(_cam)) {
                    _button_j.updateState(true);
                    return;
                }
                if (_button_k.isTouched(_cam)) {
                    _button_k.updateState(true);
                    return;
                }
                if (_button_l.isTouched(_cam)) {
                    _button_l.updateState(true);
                    return;
                }
                if (_button_colon.isTouched(_cam)) {
                    _button_colon.updateState(true);
                    return;
                }
                if (_button_quotes.isTouched(_cam)) {
                    _button_quotes.updateState(true);
                    return;
                }
                /**
                 * row3
                 */
                if (_button_z.isTouched(_cam)) {
                    _button_z.updateState(true);
                    return;
                }
                if (_button_x.isTouched(_cam)) {
                    _button_x.updateState(true);
                    return;
                }
                if (_button_c.isTouched(_cam)) {
                    _button_c.updateState(true);
                    return;
                }
                if (_button_v.isTouched(_cam)) {
                    _button_v.updateState(true);
                    return;
                }
                if (_button_b.isTouched(_cam)) {
                    _button_b.updateState(true);
                    return;
                }
                if (_button_n.isTouched(_cam)) {
                    _button_n.updateState(true);
                    return;
                }
                if (_button_m.isTouched(_cam)) {
                    _button_m.updateState(true);
                    return;
                }
                if (_button_LeftAngleBracket.isTouched(_cam)) {
                    _button_LeftAngleBracket.updateState(true);
                    return;
                }
                if (_button_RightAngleBracket.isTouched(_cam)) {
                    _button_RightAngleBracket.updateState(true);
                    return;
                }
                if (_eraseSymbol.isTouched(_cam)) {
                    _eraseSymbol.updateState(true);
                    return;
                }
                if (_clear.isTouched(_cam)) {
                    _clear.updateState(true);
                    return;
                }
                if (_enter.isTouched(_cam)) {
                    _enter.updateState(true);
                    return;
                }
            }

            if (_main.IsTouched(_numeric_1.get_width_position(),
                    _numeric_1.get_height_position(),
                    (_numeric_1.get_width() * 10),
                    _numeric_1.get_height())) {
                /**
                 * numeric row
                 */
                if (_numeric_1.isTouched(_cam)) {
                    _numeric_1.updateState(true);
                    return;
                }
                if (_numeric_2.isTouched(_cam)) {
                    _numeric_2.updateState(true);
                    return;
                }
                if (_numeric_3.isTouched(_cam)) {
                    _numeric_3.updateState(true);
                    return;
                }
                if (_numeric_4.isTouched(_cam)) {
                    _numeric_4.updateState(true);
                    return;
                }
                if (_numeric_5.isTouched(_cam)) {
                    _numeric_5.updateState(true);
                    return;
                }
                if (_numeric_6.isTouched(_cam)) {
                    _numeric_6.updateState(true);
                    return;
                }
                if (_numeric_7.isTouched(_cam)) {
                    _numeric_7.updateState(true);
                    return;
                }
                if (_numeric_8.isTouched(_cam)) {
                    _numeric_8.updateState(true);
                    return;
                }
                if (_numeric_9.isTouched(_cam)) {
                    _numeric_9.updateState(true);
                    return;
                }
                if (_numeric_0.isTouched(_cam)) {
                    _numeric_0.updateState(true);
                    return;
                }
            }
            /**
             * other
             */
            /////////////// drawing space button touched
            if (_space_button.isTouched(_cam)) {
                _space_button.updateState(true);
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
        /**
         * other
         */
        _space_button.updateState(false);
        _symbol_dot.updateState(false);
        _eraseSymbol.updateState(false);
        _clear.updateState(false);
        _enter.updateState(false);
        _error.dePressButtons();
    }

    /**
     * de press all buttons
     */
    private void dePressButtons() {
        /**
         * numeric row
         */
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
        /**
         *row1
         */
        _button_q.updateState(false);
        _button_w.updateState(false);
        _button_e.updateState(false);
        _button_r.updateState(false);
        _button_t.updateState(false);
        _button_y.updateState(false);
        _button_u.updateState(false);
        _button_i.updateState(false);
        _button_o.updateState(false);
        _button_p.updateState(false);
        _button_LeftBracket.updateState(false);
        /**
         * row2
         */
        _button_a.updateState(false);
        _button_s.updateState(false);
        _button_d.updateState(false);
        _button_f.updateState(false);
        _button_g.updateState(false);
        _button_h.updateState(false);
        _button_j.updateState(false);
        _button_k.updateState(false);
        _button_l.updateState(false);
        _button_colon.updateState(false);
        _button_quotes.updateState(false);
        /**
         * row3
         */
        _button_z.updateState(false);
        _button_x.updateState(false);
        _button_c.updateState(false);
        _button_v.updateState(false);
        _button_b.updateState(false);
        _button_n.updateState(false);
        _button_m.updateState(false);
        _button_LeftAngleBracket.updateState(false);
        _button_RightAngleBracket.updateState(false);

        /**
         * other
         */
        _clear.updateState(false);
        _eraseSymbol.updateState(false);
        _symbol_dot.updateState(false);
        _space_button.updateState(false);
        _enter.updateState(false);

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
        /**
         * other
         */
        _space_button.updateState(false);
        _eraseSymbol.updateState(false);
        _clear.updateState(false);
        _enter.updateState(false);
        _error.dePressButtons();

        _main._status.updateNavigation(TextValues.navigation.keyboardEnterScreen);
        _timer = 0;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        if (_upperCase) changeRegister();
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
        _textToEnter_font.dispose();
        _error.dispose();


        /**
         * numeric row
         */
        _numeric_1.dispose();
        _numeric_2.dispose();
        _numeric_3.dispose();
        _numeric_4.dispose();
        _numeric_5.dispose();
        _numeric_6.dispose();
        _numeric_7.dispose();
        _numeric_8.dispose();
        _numeric_9.dispose();
        _numeric_0.dispose();

        /**
         * row1
         */
        _button_q.dispose();
        _button_w.dispose();
        _button_e.dispose();
        _button_r.dispose();
        _button_t.dispose();
        _button_y.dispose();
        _button_u.dispose();
        _button_i.dispose();
        _button_o.dispose();
        _button_p.dispose();
        _button_LeftBracket.dispose();
        /**
         * row2
         */
        _button_a.dispose();
        _button_s.dispose();
        _button_d.dispose();
        _button_f.dispose();
        _button_g.dispose();
        _button_h.dispose();
        _button_j.dispose();
        _button_k.dispose();
        _button_l.dispose();
        _button_colon.dispose();
        _button_quotes.dispose();
        /**
         * row3
         */
        _button_z.dispose();
        _button_x.dispose();
        _button_c.dispose();
        _button_v.dispose();
        _button_b.dispose();
        _button_n.dispose();
        _button_m.dispose();
        _button_LeftAngleBracket.dispose();
        _button_RightAngleBracket.dispose();
        /**
         * other
         */
        _registerChange.dispose();
        _space_button.dispose();
        _symbol_dot.dispose();
        _eraseSymbol.dispose();
        _enter.dispose();
        _clear.dispose();
        _textBox_content.dispose();
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
                _error.handleLongTermDepression();
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
                _error.dePressButtons();
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