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

public class ErrorMessage {
    private static Main _main;

    private float _background_width_position;
    private float _background_height_position;

    private OrthographicCamera _cam;
    private Sprite _background;
    private Button _confirm;

    //private GlyphLayout _description_glyphLayout;
    private GlyphLayout _header_glyphLayout;
    //private BitmapFont _description_font;
    private BitmapFont _header_font;
    private String _headerText;
    private String _descriptionText;
    private RichTextBox _description_RichTextBox;

    private boolean _showWarningWindow;
    private boolean _finished;

    /**
     * initializer of a class
     *
     * @param header          - action or a value that the user must confirm
     * @param descriptionText - question, which asks user to confirm
     * @param cam             - orthographic camera from initial screen
     */
    public ErrorMessage(Main main, String header, String descriptionText, OrthographicCamera cam) {
        if (main == null || cam == null) {
            throw new NullPointerException("ErrorMessage initialization parameters can't be null!");
        }
        _showWarningWindow = false;
        _headerText = header;
        _descriptionText = descriptionText;
        _cam = cam;
        _main = main;
        _finished = true;
        /////////////////text


        /*_description_glyphLayout = new GlyphLayout();
        _description_font = new BitmapFont(Main._medium_font_fileHandle);
        _description_font.setColor(Color.BLACK);
        _description_glyphLayout.setText(_description_font, _descriptionText);*/


        _header_glyphLayout = new GlyphLayout();
        _header_font = new BitmapFont(Main._huge_font_fileHandle);
        _header_font.setColor(Color.BLACK);
        _header_glyphLayout.setText(_header_font, _headerText);

        _background_width_position = Main.SCREEN_WIDTH / 100;
        _background_height_position = Main.SCREEN_HEIGHT / 16 + (Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8) / 100 * 2;

        _background = new Sprite(Textures.get_error_message_background());
        _background.setPosition(_background_width_position, _background_height_position);
        _background.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _background.setSize(Main.SCREEN_WIDTH - Main.SCREEN_WIDTH / 100 * 2, Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8 - (Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8) / 100 * 4);


        _description_RichTextBox = new RichTextBox(_background_width_position + _background.getWidth() / 10,
                _background_height_position + _background.getHeight() - (float) (_background.getHeight() * 0.15),
                (float) (_background.getWidth() * 0.75),
                (float) (_background.getHeight() * 0.65),
                _descriptionText, Color.BLACK, Main._medium_font_fileHandle);


        _confirm = new Button();
        _confirm.initialize(_background_width_position + _background.getWidth() / 4,
                _background_height_position + _background.getHeight() / 16,
                _background.getWidth() / 2, _background.getHeight() / 8,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.elements.ok_uppercase, Color.BLACK, Main._medium_font_fileHandle);

    }

    /**
     * drawing class content
     *
     * @param batch - spriteBatch from initial screen
     */
    public void draw(SpriteBatch batch) {
        if (batch == null) {
            throw new NullPointerException("ErrorMessage draw parameters can't be null");
        }
        if (_showWarningWindow) {
            _background.draw(batch);

            _header_font.draw(batch, _header_glyphLayout,
                    _background_width_position + _background.getWidth() / 2 - _header_glyphLayout.width / 2,
                    _background_height_position + _background.getHeight() - _background.getHeight() / 10);

            _description_RichTextBox.draw(batch);
            /*_description_font.draw(batch, _description_glyphLayout,
                    (_background_width_position + _background.getWidth() / 2)
                            - _description_glyphLayout.width / 2,
                    _background_height_position + _background.getHeight()
                            - _background.getHeight() / 4);*/

            _confirm.draw(batch);

        }
    }

    public void drawRect(ShapeRenderer renderer) {
        if (renderer == null) {
            throw new NullPointerException("ConfirmBox draw rect parameters can't be null");
        }
        if (_showWarningWindow) {
            renderer.rect(_background.getX(),
                    _background.getY() + 1,
                    _background.getWidth() - 1,
                    _background.getHeight() - 1);
            _confirm.drawRect(renderer);
        }
    }

    public void dispose() {
        _confirm.dispose();
        //_description_font.dispose();
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
            throw new NullPointerException("ConfirmBox updateHeader parameters can't be null");
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
            throw new NullPointerException("ConfirmBox updateDescription parameters can't be null");
        }
        _descriptionText = description;
        _description_RichTextBox.updateText(_descriptionText);
        //_description_glyphLayout.setText(_description_font, _descriptionText);
    }

    /**
     * update state of buttons in errorBox (pushed or not)
     */
    public void update(boolean state) {
        _confirm.updateState(state);
    }

    /**
     * method handles single touch
     */
    public void handleSingleInput() {
        if (_confirm.isTouched(_cam)) {
            _finished = true;
            hide();
            _main.resetDataOnEmergency();
        }
    }

    public void dePressButton() {
        _confirm.updateState(false);
    }

    /**
     * update position of error box message
     *
     * @param width_position
     * @param height_position
     */
    public void updatePosition(float width_position, float height_position) {
        _background_width_position = width_position;
        _background_height_position = height_position;
        _background.setPosition(_background_width_position, _background_height_position);
    }

    /**
     * method handles long pressing
     */
    public void handleLongTermDepression() {
        if (_confirm.isTouched(_cam)) {
            _confirm.updateState(true);
            return;
        }
        _confirm.updateState(false);
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

