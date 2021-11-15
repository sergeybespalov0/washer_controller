package com.mygdx.game.android.Elements.inheritable;


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
import com.mygdx.game.android.Elements.Button;
import com.mygdx.game.android.Main;

public class InformationMessage_warnBeforeLaundryStart {
    private float _background_width_position;
    private float _background_height_position;

    private OrthographicCamera _cam;
    private Sprite _background;
    private Button _confirm;

    private GlyphLayout _description_glyphLayout;
    private GlyphLayout _actionsTodo_glyphLayout;

    private BitmapFont _description_font;
    private BitmapFont _actionsTodo_font;

    private GlyphLayout _warn_glyphLayout;
    private BitmapFont _warn_font;

    private boolean _showWarningWindow;

    /**
     * initializer of a class
     *
     * @param cam - orthographic camera from initial screen
     */
    public InformationMessage_warnBeforeLaundryStart(OrthographicCamera cam) {
        if (cam == null) {
            throw new NullPointerException("WarningMessage initialization parameters can't be null");
        }
        _showWarningWindow = false;
        _cam = cam;
        /////////////////text
        _actionsTodo_glyphLayout = new GlyphLayout();
        _actionsTodo_font = new BitmapFont(Main._huge_font_fileHandle);
        _actionsTodo_font.setColor(Color.BLACK);
        _actionsTodo_glyphLayout.setText(_actionsTodo_font, "Загрузите белье в барабан, моющее средство в лотки\n" +
                "и нажмите ОК.\n");

        _warn_glyphLayout = new GlyphLayout();
        _warn_font = new BitmapFont(Main._huge_font_fileHandle);
        _warn_font.setColor(Color.BLACK);
        _warn_glyphLayout.setText(_actionsTodo_font, TextValues.globalValues.warning);


        _description_glyphLayout = new GlyphLayout();
        _description_font = new BitmapFont(Main._medium_font_fileHandle);
        _description_font.setColor(Color.BLACK);
        _description_glyphLayout.setText(_description_font, TextValues.globalValues.beforeStartMessage);


        _background_width_position = Main.SCREEN_WIDTH / 100;
        _background_height_position = Main.SCREEN_HEIGHT / 16 + (Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8) / 100 * 2;

        _background = new Sprite(Textures.get_warning_message_background());
        _background.setPosition(_background_width_position, _background_height_position);
        _background.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _background.setSize(Main.SCREEN_WIDTH - Main.SCREEN_WIDTH / 100 * 2, Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8 - (Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8) / 100 * 4);


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
            throw new NullPointerException("WarningMessage draw parameters can't be null");
        }
        if (_showWarningWindow) {
            _background.draw(batch);

            _actionsTodo_font.draw(batch, _actionsTodo_glyphLayout,
                    _background_width_position + _background.getWidth() / 2 - _actionsTodo_glyphLayout.width / 2,
                    _background_height_position + _background.getHeight() - _background.getHeight() / 10);

            _warn_font.draw(batch, _warn_glyphLayout,
                    _background_width_position + _background.getWidth() / 2 - _actionsTodo_glyphLayout.width / 2,
                    _background_height_position + _background.getHeight() - _background.getHeight() / 3);

            _description_font.draw(batch, _description_glyphLayout,
                    _background_width_position + _background.getWidth() / 2 - _actionsTodo_glyphLayout.width / 2,
                    _background_height_position + _background.getHeight() - _background.getHeight() / 2);

            _confirm.draw(batch);

        }
    }

    public void drawRect(ShapeRenderer renderer) {
        if (renderer == null) {
            throw new NullPointerException("WarningMessage drawRect parameters can't be null");
        }
        if (_showWarningWindow) {
            renderer.rect(_background.getX(),
                    _background.getY() + 1,
                    _background.getWidth() - 1,
                    _background.getHeight() - 1);
            _confirm.drawRect(renderer);
        }
    }

    public void dePressButtons() {
        _confirm.updateState(false);
    }

    public void dispose() {
        _confirm.dispose();
        _description_font.dispose();
        _actionsTodo_font.dispose();
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
            hide();
        }
    }

    /**
     * update position of error box message
     *
     * @param width_position  - new width position
     * @param height_position - new height position
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
        } else {
            _confirm.updateState(false);
        }
    }
}