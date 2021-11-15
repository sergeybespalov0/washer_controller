package com.mygdx.game.android.Elements;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.android.Data.Textures;

/**
 * class which groups button and description text to this button, forming switch
 */
public class SwitchElement extends Button {

    private float _width_position;
    private float _height_position;
    private float _width;
    private float _height;
    private float _description_width_position;
    private float _description_height_position;

    private String _description_text;
    private BitmapFont _description_textFont;
    private GlyphLayout _description_glyphLayout;
    private Button _switch;

    void SwitchElement() {

    }

    /**
     * switch initializer
     *
     * @param width_position      - width position of a switch
     * @param height_position     - height position of a switch
     * @param width               - width of a switch
     * @param height              - height of a switch
     * @param textFontFileHandler - style(font) of a description text
     * @param textColor           - color of a description text
     * @param description_text    - description of a button
     */
    public void Initialize(float width_position, float height_position, float width, float height,
                           FileHandle textFontFileHandler, Color textColor, String description_text) {
        if (textFontFileHandler == null || textColor == null) {
            throw new NullPointerException("SwitchElement initialization parameters can't be null");
        }
        _width_position = width_position;
        _height_position = height_position;
        _height = height;
        _description_text = description_text;
        _width = width;
        _description_glyphLayout = new GlyphLayout();
        _description_textFont = new BitmapFont(textFontFileHandler);
        _description_textFont.setColor(textColor);
        _description_glyphLayout.setText(_description_textFont, _description_text);

        _description_width_position = _width_position + _width / 2 - _description_glyphLayout.width / 2;
        _description_height_position = _height_position + _height + _description_glyphLayout.height + 10;

        _switch = new Button();
        _switch.initialize(_width_position, _height_position, _width, _height,
                Textures.get_switch_on(),
                Textures.get_switch_off(),
                Textures.get_switch_on_disabled(),
                Textures.get_switch_off_disabled(),
                "", Color.BLACK, textFontFileHandler);
        /**
         *
         */
    }

    /**
     * switch initializer, used if u want to set custom textures of a button
     *
     * @param width_position      - width position of a button
     * @param height_position     - height position of a texture
     * @param width               - width of a texture
     * @param height              - height of a texture
     * @param switch_on           - texture of switch on
     * @param switch_off          - texture of switch off
     * @param switch_on_disabled  - texture of switch on disabled
     * @param switch_off_disabled - texture of switch off disabled
     * @param textFontFileHandler - style(font) of a description text
     * @param textColor           - color of a description text
     * @param description_text    - description of a button
     */
    public void Initialize(float width_position, float height_position, float width, float height,
                           Texture switch_on, Texture switch_off,
                           Texture switch_on_disabled, Texture switch_off_disabled,
                           FileHandle textFontFileHandler, Color textColor, String description_text) {
        if (textFontFileHandler == null || textColor == null ||
                switch_on == null || switch_off == null || switch_on_disabled == null || switch_off_disabled == null) {
            throw new NullPointerException("SwitchElement initialization parameters can't be null");
        }
        _width_position = width_position;
        _height_position = height_position;
        _height = height;
        _description_text = description_text;
        _width = width;
        _description_glyphLayout = new GlyphLayout();
        _description_textFont = new BitmapFont(textFontFileHandler);
        _description_textFont.setColor(textColor);
        _description_glyphLayout.setText(_description_textFont, _description_text);
        _description_width_position = _width_position + _width / 2 - _description_glyphLayout.width / 2;
        _description_height_position = _height_position + _height + _description_glyphLayout.height + 10;

        _switch = new Button();
        _switch.initialize(_width_position, _height_position, _width, _height,
                switch_on,
                switch_off,
                switch_on_disabled,
                switch_off_disabled,
                "", Color.BLACK, textFontFileHandler);
        /**
         *
         */
    }

    /**
     * draw switch
     *
     * @param spriteBatch - batch, in which we draw
     */
    public void draw(SpriteBatch spriteBatch) {
        if (spriteBatch == null) {
            throw new NullPointerException("SwitchElement draw parameters can't be null");
        }
        _switch.draw(spriteBatch);

        _description_textFont.draw(spriteBatch, _description_glyphLayout,
                _description_width_position,
                _description_height_position);
    }

    /**
     * draw 1px rectangle at the edges of switch *
     *
     * @param renderer - camera of main class, used to switch to shapeRenderer
     */
    public void drawRect(ShapeRenderer renderer) {
        if (renderer == null) {
            throw new NullPointerException("SwitchElement drawRect parameters can't be null");
        }
        _switch.drawRect(renderer);
    }

    /**
     * @return true if switch state is pushed
     */
    public boolean getState() {
        return _switch.getState();
    }

    /**
     * enables switch and changes its texture to enabled
     */
    public void enable() {
        _switch.enable();
    }

    /**
     * disables switch and changes its texture to disabled
     */
    public void disable() {
        _switch.disable();
    }

    /**
     * @return true if switch is off
     */
    public boolean isDisabled() {
        return _switch.isDisabled();
    }

    /**
     * dispose switch
     */
    public void dispose() {
        _switch.dispose();
        _description_textFont.dispose();
    }

    /**
     * @param cam is used to capture last touched coordinates
     * @return false if switch is disabled or not pushed, true if coordinates hits the switch
     */
    public boolean isTouched(OrthographicCamera cam) {
        if (cam == null) {
            throw new NullPointerException("SwitchElement isTouched parameters can't be null");
        }
        return _switch.isTouched(cam);
    }

    /**
     * set fixed description width position
     *
     * @param width - new width position
     */
    public void setFixedDescriptionWidthPosition(float width) {
        _description_width_position = width;
    }

    /**
     * set fixed description height position
     *
     * @param height - new height position
     */
    public void setFixedDescriptionHeightPosition(float height) {
        _description_height_position = height;
    }

    /**
     * set fixed description height and width position
     *
     * @param width  - new width
     * @param height - new height
     */
    public void setFixedDescriptionPosition(float width, float height) {
        setFixedDescriptionWidthPosition(width);
        setFixedDescriptionHeightPosition(height);
    }

    /**
     * disable fixed description position
     */
    public void disableFixedDescriptionPosition() {
        _description_width_position = _width_position + _width / 2 - _description_glyphLayout.width / 2;
        _description_height_position = _height_position + _height + _description_glyphLayout.height + 10;
    }

    /**
     * reverse state of switch
     * on -> off
     * off -> on
     */
    public void changeState() {
        _switch.changeState();
    }

    public float get_description_width() {
        return _description_glyphLayout.width;
    }

    public float get_description_height() {
        return _description_glyphLayout.height;
    }

    /**
     * updating state of a switch and change switch texture, depends on class values
     *
     * @param state - new state
     */
    public void update(boolean state) {
        _switch.updateState(state);
    }

    public float get_width_position() {
        return _width_position;
    }

    public float get_height_position() {
        return _height_position;
    }

    public float get_height() {
        return _height;
    }

    public float get_width() {
        return _width;
    }
}
