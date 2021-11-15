package com.mygdx.game.android.Elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Main;

/**
 * used to show warning message to user to confirm some actions
 */

public class ConfirmBoxBig {

    private OrthographicCamera _cam;
    private Sprite _background;
    private Button _button_no;
    private Button _button_yes;

    private GlyphLayout _description_glyphLayout;
    private GlyphLayout _header_glyphLayout;
    private BitmapFont _description_font;
    private BitmapFont _header_font;
    private String _headerText;
    private String _descriptionText;

    private float indent = (Main.SCREEN_HEIGHT / 8 / 2) * 3;
    private boolean _showWarningWindow;
    private boolean _finished;

    /**
     * initializer of a class
     *
     * @param header          - action or a value that the user must confirm
     * @param descriptionText - question, which asks user to confirm
     * @param cam             - orthographic camera from initial screen
     */
    public ConfirmBoxBig(String header, String descriptionText, OrthographicCamera cam) {
        if (descriptionText.isEmpty() || cam == null) {
            throw new NullPointerException("WarningMessage initialization parameters can't be null");
        }
        _showWarningWindow = false;
        _headerText = header;
        _descriptionText = descriptionText;
        _cam = cam;
        _finished = true;
        /////////////////text
        _description_glyphLayout = new GlyphLayout();
        _description_font = new BitmapFont(Main._medium_font_fileHandle);
        _description_font.setColor(Color.BLACK);
        _description_glyphLayout.setText(_description_font, _descriptionText);

        _header_glyphLayout = new GlyphLayout();
        _header_font = new BitmapFont(Main._huge_font_fileHandle);
        _header_font.setColor(Color.BLACK);
        _header_glyphLayout.setText(_header_font, _headerText);


        _background = new Sprite(Textures.get_warning_message_background());
        _background.setPosition(indent / 6 * 5, indent / 6 * 5);
        _background.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _background.setSize(Main.SCREEN_WIDTH - (indent / 6 * 10), Main.SCREEN_HEIGHT - (indent / 6 * 10));


        /*_confirm = new Button();
        _confirm.initialize(_background_width_position + _background.getWidth() / 4,
                _background_height_position + _background.getHeight() / 16,
                _background.getWidth() / 2, _background.getHeight() / 8,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.elements.ok_uppercase, Color.BLACK, Main._medium_font_fileHandle);*/
        float _button_width, _button_height;
        _button_width = _background.getWidth() / 5;
        _button_height = _background.getHeight() / 6;


        _button_no = new Button();
        _button_no.initialize(_background.getX() + _button_width,
                _background.getY() + _button_height / 2,
                _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.elements.no_uppercase, Color.BLACK, Main._medium_font_fileHandle);
        _button_yes = new Button();
        _button_yes.initialize(_button_no.get_width_position() + _button_width * 2,
                _background.getY() + _button_height / 2,
                _button_width, _button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.elements.yes_uppercase, Color.BLACK, Main._medium_font_fileHandle);

    }

    /**
     * drawing class content
     *
     * @param batch - spriteBatch from initial screen
     */
    public void draw(SpriteBatch batch, ShapeRenderer renderer) {
        if (batch == null || renderer == null) {
            throw new NullPointerException("WarningMessage draw parameters can't be null");
        }
        if (_showWarningWindow) {
            batch.begin();
            _background.draw(batch);

            _header_font.draw(batch, _header_glyphLayout,
                    indent + _background.getWidth() / 2 - _header_glyphLayout.width / 2,
                    indent + _background.getHeight() - _background.getHeight() / 10 - _header_glyphLayout.height);

            _description_font.draw(batch, _description_glyphLayout,
                    (indent + _background.getWidth() / 2)
                            - _description_glyphLayout.width / 2,
                    indent + _background.getHeight()
                            - _background.getHeight() / 4);

            _button_no.draw(batch);
            _button_yes.draw(batch);
            batch.end();
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.rect(_background.getX(),
                    _background.getY() + 1,
                    _background.getWidth() - 1,
                    _background.getHeight() - 1);
            _button_no.drawRect(renderer);
            _button_yes.drawRect(renderer);
            renderer.end();
        }
    }


    public void dePressButtons() {
        _button_no.updateState(false);
        _button_yes.updateState(false);
    }

    public void dispose() {
        _button_no.dispose();
        _button_yes.dispose();
        _description_font.dispose();
        _header_font.dispose();
        _background.getTexture().dispose();
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
     * @param header - action or a value that the user must confirm
     */
    public void updateHeader(String header) {
        if (header == null) {
            throw new NullPointerException("WarningMessage updateHeader parameters can't be null");
        }
        _headerText = header;
        _header_glyphLayout.setText(_header_font, _headerText);
    }

    /**
     * updating new question to ask to user
     *
     * @param description - new string to ask
     */
    public void updateDescription(String description) {
        if (description == null) {
            throw new NullPointerException("WarningMessage updateDescription parameters can't be null");
        }
        _descriptionText = description;
        _description_glyphLayout.setText(_description_font, _descriptionText);
    }


    /**
     * method handles single touch
     */
    public void handleSingleInput() {
        if (_button_yes.isTouched(_cam)) {
            _finished = true;
            hide();
        }
        if (_button_no.isTouched(_cam)) {
            hide();
        }
    }


    /**
     * method handles long pressing
     */
    public void handleLongTermDepression() {
        /////////////// drawing 'no' button touched
        if (_button_no.isTouched(_cam)) {
            _button_no.updateState(true);
            return;
        }
        /////////////// drawing 'no' button touched
        if (_button_yes.isTouched(_cam)) {
            _button_yes.updateState(true);
            return;
        }
        dePressButtons();
    }

    /**
     *
     */
    public void setNotFinished() {
        _finished = false;
    }

    /**
     * @return true, if user confirmed action or value
     */
    public boolean isFinished() {
        return _finished;
    }


}

