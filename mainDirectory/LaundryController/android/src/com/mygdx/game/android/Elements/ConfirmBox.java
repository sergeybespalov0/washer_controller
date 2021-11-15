package com.mygdx.game.android.Elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Main;

/**
 * used to show warning message to user to confirm some actions
 */

public class ConfirmBox {
    private static float _button_width;
    private static float _button_height;
    private static float _space_between_buttons_width;
    private static float _space_between_buttons_height;

    private float _textBox_width_position;
    private float _textBox_height_position;


    private OrthographicCamera _cam;
    private TextBox _error_textBox;
    private Button _button_no;
    private Button _button_yes;

    private GlyphLayout _error_glyphLayout;
    private BitmapFont _errorTextBox_font;
    private String _content;
    private String _textToAsk;

    private boolean _showWarningWindow;
    private boolean _finished;
    private ShapeRenderer _renderer;

    /**
     * initializer of a class
     *
     * @param content   - action or a value that the user must confirm
     * @param textToAsk - question, which asks user to confirm
     * @param cam       - orthographic camera from initial screen
     */
    public ConfirmBox(String content, String textToAsk, OrthographicCamera cam) {
        if (cam == null) {
            throw new NullPointerException("ConfirmBox initialization parameters can't be null");
        }

        _showWarningWindow = false;

        _content = content;
        _textToAsk = textToAsk;
        _cam = cam;
        _finished = false;
        /////////////////text
        _error_glyphLayout = new GlyphLayout();
        _errorTextBox_font = Main._huge_font;
        _errorTextBox_font.setColor(Color.BLACK);
        _error_glyphLayout.setText(_errorTextBox_font, _textToAsk);

        _textBox_width_position = (Main.SCREEN_WIDTH / 4) / 2;
        _textBox_height_position = Main.SCREEN_HEIGHT / 4;


        _renderer = new ShapeRenderer();
///////////////////////////////     BUTTONS
        //////////error box
        _error_textBox = new TextBox();
        _error_textBox.Initialize(_textBox_width_position, _textBox_height_position,
                Main.SCREEN_WIDTH / 4 * 3, Main.SCREEN_HEIGHT / 2, 1, _content, "",
                Textures.get_confirm_message_background(),
                Color.BLACK, Main._huge_font_fileHandle);

        _button_width = (float) (_error_textBox.get_width() * 0.15);
        _button_height = (float) (_error_textBox.get_height() * 0.20);
        _space_between_buttons_width = _button_width / 2;
        _space_between_buttons_height = _button_height / 2;

        _button_no = new Button();
        _button_no.initialize(_textBox_width_position + _space_between_buttons_width * 2,
                _textBox_height_position + _space_between_buttons_height,
                _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.elements.no_uppercase, Color.BLACK, Main._little_font_fileHandle);
        _button_yes = new Button();
        _button_yes.initialize((_textBox_width_position + _error_textBox.get_width())
                        - _button_width - _space_between_buttons_width * 2,
                _textBox_height_position + _space_between_buttons_height,
                _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.elements.yes_uppercase, Color.BLACK, Main._little_font_fileHandle);
    }

    /**
     * drawing class content
     *
     * @param batch  - spriteBatch from initial screen
     * @param camera - ortographic camera from initial screen
     */
    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        if (batch == null || camera == null) {
            throw new NullPointerException("ConfirmBox draw parameters can't be null");
        }
        if (_showWarningWindow) {
            batch.begin();
            _error_textBox.draw(batch);

            _errorTextBox_font.draw(batch, _error_glyphLayout,
                    (_error_textBox.get_width_position() + _error_textBox.get_width() / 2)
                            - _error_glyphLayout.width / 2,
                    _error_textBox.get_height_position() + _error_textBox.get_height()
                            - _error_textBox.get_height() / 4);
            _button_no.draw(batch);
            _button_yes.draw(batch);
            batch.end();
            _renderer.setProjectionMatrix(camera.combined);
            _renderer.begin(ShapeType.Line);
            _renderer.setColor(Color.BLACK);
            _renderer.rect(_error_textBox.get_width_position(),
                    _error_textBox.get_height_position() + 1,
                    _error_textBox.get_width() - 1,
                    _error_textBox.get_height() - 1);
            _button_no.drawRect(_renderer);
            _button_yes.drawRect(_renderer);
            _renderer.end();
            _renderer.begin(ShapeType.Filled);
            _renderer.rectLine(
                    (_error_textBox.get_width_position() + _error_textBox.get_width() / 2)
                            - _error_textBox.get_content_width() / 2,
                    _error_textBox.get_height_position() + _error_textBox.get_height() / 2
                            - _error_textBox.get_content_height() / 2 - 12,
                    (_error_textBox.get_width_position() + _error_textBox.get_width() / 2)
                            + _error_textBox.get_content_width() / 2,
                    _error_textBox.get_height_position() + _error_textBox.get_height() / 2
                            - _error_textBox.get_content_height() / 2 - 12
                    , 3);
            _renderer.end();
        }
    }

    /**
     * showing action box
     */
    public void show() {
        _showWarningWindow = true;
    }

    /**
     * hiding action box
     */
    public void hide() {
        _showWarningWindow = false;
    }

    public void dispose() {
        _error_textBox.dispose();
        _button_no.dispose();
        _button_yes.dispose();
    }

    /**
     * showing isShowed of action box
     *
     * @return true if warning window is being showed
     */
    public boolean isShowed() {
        return _showWarningWindow;
    }

    /**
     * set or update description of action or a value that the user must confirm
     *
     * @param content - action or a value that the user must confirm
     */
    public void updateContent(String content) {
        if (content == null) {
            throw new NullPointerException("ConfirmBox content can't be null!");
        }
        _content = content;
        _error_textBox.updateContent(_content);
        //_error_glyphLayout.setText(_errorTextBox_font, _textToAsk);
    }

    /**
     * updating new question to ask to user
     *
     * @param question - new string to ask
     */
    public void updateQuestion(String question) {
        if (question == null) {
            throw new NullPointerException("ConfirmBox question can't be null!");
        }
        _textToAsk = question;
        _error_glyphLayout.setText(_errorTextBox_font, _textToAsk);
    }

    /**
     * method handles single touch
     */
    public void handleSingleInput() {
        //if (Gdx.input.justTouched()) {
        /////////////////////////////////error window
        /////////// error window 'no' button touched
        if (_button_no.isTouched(_cam)) {
            hide();
        }
        /////////// error window 'yes' button touched
        if (_button_yes.isTouched(_cam)) {
            _finished = true;
            hide();
        }
        //}
    }

    /**
     * method handles long pressing
     */
    public void handleLongTermDepression() {

        /////////////// drawing error window 'no' button touched
        if (_button_no.isTouched(_cam)) {
            _button_no.updateState(true);
            return;
        }
        /////////////// drawing error window 'no' button touched
        if (_button_yes.isTouched(_cam)) {
            _button_yes.updateState(true);
            return;
        }
        dePressButtons();
    }

    /**
     *
     */
    public void notFinished() {
        _finished = false;
    }

    /**
     * @return true, if user confirmed action or value
     */
    public boolean isFinished() {
        return _finished;
    }

    /**
     * set button's of confirm box to 'not pushed'
     */
    public void dePressButtons() {
        _button_no.updateState(false);
        _button_yes.updateState(false);
    }


}

