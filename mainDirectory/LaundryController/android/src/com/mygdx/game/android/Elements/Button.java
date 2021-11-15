package com.mygdx.game.android.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.android.Interfaces.ISeveralStates;


/**
 * used to create button with text and shading
 */
public class Button implements ISeveralStates {

    private boolean _IsPushed;
    private boolean _disabled;

    private Texture _pushed;
    private Texture _notPushed;
    private Texture _pushed_disabled;
    private Texture _notPushed_disabled;
    private Sprite _button_sprite;
    private String _buttonContent;
    private BitmapFont _text_Font;
    private GlyphLayout _text_glyphLayout;

    private float _width;
    private float _height;
    private float _height_position;
    private float _width_position;

    private boolean _visible;


    public Button() {
    }

    /**
     * main button initializer
     *
     * @param width_position     - width position of a texture
     * @param height_position    - height position of a texture
     * @param width              - width of a texture
     * @param height             - height of a texture
     * @param pushed             - texture of pushed button
     * @param notPushed          - texture of not pushed button
     * @param pushed_disabled    - texture of pushed, disabled button
     * @param notPushed_disabled - texture of not pushed, disabled button
     * @param content            - text in a middle of a button
     * @param textColor          - color of this text
     * @param textFontFileHandle - style(font) of this text
     */
    public void initialize(float width_position, float height_position, float width, float height,
                           Texture pushed, Texture notPushed,
                           Texture pushed_disabled, Texture notPushed_disabled,
                           String content, Color textColor, FileHandle textFontFileHandle) {

        if (pushed == null || notPushed == null ||
                pushed_disabled == null || notPushed_disabled == null ||
                textColor == null || textFontFileHandle == null) {
            throw new NullPointerException("Button initialization parameters can't be null!");
        }
        _visible = true;


        _IsPushed = false;
        _disabled = false;
        _height = height;
        _width = width;
        _pushed = pushed;

        _pushed.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _notPushed = notPushed;
        _notPushed.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _pushed_disabled = pushed_disabled;
        _pushed_disabled.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _notPushed_disabled = notPushed_disabled;
        _notPushed_disabled.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _height_position = height_position;
        _width_position = width_position;

        _text_glyphLayout = new GlyphLayout();

        _text_Font = new BitmapFont(textFontFileHandle);
        _text_Font.setColor(textColor);

        _buttonContent = content;
        _text_glyphLayout.setText(_text_Font, _buttonContent);

        _button_sprite = new Sprite(_notPushed);
        _button_sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _button_sprite.setPosition(_width_position, _height_position);

        _button_sprite.setSize(_width, _height);
        _button_sprite.setOriginCenter();
    }

    /**
     * use if u need one texture in all states
     *
     * @param width_position     - width position of a texture
     * @param height_position    - height position of a texture
     * @param width              - width of a texture
     * @param height             - height of a texture
     * @param single             - texture of a button
     * @param content            - text in a middle of a button
     * @param textColor          - color of this text
     * @param textFontFileHandle - style(font) of this text
     */
    public void initialize(float width_position, float height_position, float width, float height,
                           Texture single, String content, Color textColor,
                           FileHandle textFontFileHandle) {

        if (single == null ||
                textColor == null || textFontFileHandle == null) {
            throw new NullPointerException("Button initialization parameters can't be null!");
        }
        _visible = true;

        _IsPushed = false;
        _disabled = false;
        _height = height;
        _width = width;
        _pushed = single;

        _pushed.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _notPushed = single;
        _notPushed.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _pushed_disabled = single;
        _pushed_disabled.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _notPushed_disabled = single;
        _notPushed_disabled.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        _height_position = height_position;
        _width_position = width_position;

        _text_glyphLayout = new GlyphLayout();
        _text_Font = new BitmapFont(textFontFileHandle);
        _text_Font.setColor(textColor);

        _buttonContent = content;
        _text_glyphLayout.setText(_text_Font, _buttonContent);

        _button_sprite = new Sprite(_notPushed);
        _button_sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _button_sprite.setPosition(_width_position, _height_position);
        _button_sprite.setSize(_width, _height);
        _button_sprite.setOriginCenter();
    }

    /**
     * draw button
     *
     * @param spriteBatch - batch, in which we draw
     */
    public void draw(SpriteBatch spriteBatch) {
        if (spriteBatch == null) {
            throw new NullPointerException("Button draw parameters can't be null");
        }
        if (_visible) {
            _button_sprite.draw(spriteBatch);
            _text_Font.draw(spriteBatch, _text_glyphLayout,
                    (_button_sprite.getX() + _width / 2) - _text_glyphLayout.width / 2,
                    (_button_sprite.getY() + _height / 2) + _text_glyphLayout.height / 2);
        }
    }

    /**
     * draw 1px rectangle at the edges of buttons
     *
     * @param renderer - shape renderer of main class,used draw borders
     */
    public void drawRect(ShapeRenderer renderer) {
        if (renderer == null) {
            throw new NullPointerException("Button drawRect parameters can't be null");
        }
        renderer.rect(_width_position, _height_position, _width, _height);
    }

    /**
     * change size of a button
     *
     * @param width  - new width
     * @param height - new height
     */
    public void changeSize(float width, float height) {
        _width = width;
        _height = height;
        _button_sprite.setSize(_width, _height);
        _button_sprite.setOriginCenter();
    }

    /**
     * @param cam is used to capture last touched coordinates
     * @return false if button is disabled or not pushed, true if last touched coordinates hits the button
     */
    public boolean isTouched(OrthographicCamera cam) {
        if (cam == null)
            throw new NullPointerException("Button isTouched parameters can't be null!");

        if (_disabled) return false;
        Vector3 _TouchedCoordinates = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return ((_TouchedCoordinates.y < (_height_position + _height)) && (_TouchedCoordinates.y > (_height_position)))
                &&
                ((_TouchedCoordinates.x > _width_position) && (_TouchedCoordinates.x < _width_position + _width));
    }

    /**
     * used for bypass the disabling the button
     *
     * @param cam is used to capture last touched coordinates
     * @return true if coordinates hits the button, else returns false
     */
    public boolean brutForceTouched(OrthographicCamera cam) {
        if (cam == null)
            throw new NullPointerException("Button brutForceTouched parameters can't be null!");

        Vector3 _TouchedCoordinates = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return ((_TouchedCoordinates.y < (_height_position + _height)) && (_TouchedCoordinates.y > (_height_position)))
                &&
                ((_TouchedCoordinates.x > _width_position) && (_TouchedCoordinates.x < _width_position + _width));
    }

    /**
     * reverse state of button
     * pushed -> not pushed
     * not pushed -> pushed
     */
    public void changeState() {
        updateState(!_IsPushed);
    }

    /**
     * @return button content
     */
    public String get_buttonContent() {
        return _buttonContent;
    }

    /**
     * dispose button
     */
    public void dispose() {
        _text_Font.dispose();
        _pushed.dispose();
        _pushed_disabled.dispose();
        _notPushed.dispose();
        _notPushed_disabled.dispose();
        _button_sprite.getTexture().dispose();
    }

    /**
     * change color of button content text
     *
     * @param textColor - new color
     */
    public void setTextColor(Color textColor) {
        if (textColor == null) {
            throw new NullPointerException("Button setTextColor parameters can't be null");
        }
        _text_Font.setColor(textColor);
        _text_glyphLayout.setText(_text_Font, _buttonContent);
    }

    /**
     * enables a button and changes its texture to enabled
     */
    public void enable() {
        if (!_disabled) return;
        _disabled = false;
        updateState(_IsPushed);
    }

    /**
     * disables button and changes its texture to disabled
     */
    public void disable() {
        if (_disabled) return;
        _disabled = true;
        updateState(_IsPushed);
    }

    /**
     * make button visible
     */
    public void show() {
        _visible = true;
    }

    /**
     * @return true, if button is visible
     */
    public boolean is_visible() {
        return _visible;
    }

    /**
     * make button invisible
     */
    public void hide() {
        _visible = false;
    }

    /**
     * change text in a middle of a button
     *
     * @param newContent - new text
     */
    public void setContent(String newContent) {
        if (newContent == null)
            throw new NullPointerException("Button setContent parameters can't be null!");
        _buttonContent = newContent;
        _text_glyphLayout.setText(_text_Font, _buttonContent);
    }

    /**
     * @return true if button is disabled
     */
    public boolean isDisabled() {
        return _disabled;
    }

    /**
     * updating state of a button and change button texture, depends on class values
     *
     * @param state - new state
     */
    @Override
    public void updateState(boolean state) {

        _IsPushed = state;

        if (_IsPushed) {
            if (_disabled) _button_sprite.setTexture(_pushed_disabled);
            else _button_sprite.setTexture(_pushed);
        } else {
            if (_disabled) _button_sprite.setTexture(_notPushed_disabled);
            else _button_sprite.setTexture(_notPushed);
        }
    }

    /**
     * @return true if button state is pushed
     */
    @Override
    public boolean getState() {
        return _IsPushed;
    }

    /**
     * updating position of a texture
     *
     * @param width  - new width position
     * @param height - new height position
     */
    @Override
    public void updatePosition(float width, float height) {
        _width_position = width;
        _height_position = height;
        _button_sprite.setPosition(_width_position, _height_position);
    }

    @Override
    public float get_width_position() {
        return _width_position;
    }

    @Override
    public float get_height_position() {
        return _height_position;
    }

    @Override
    public float get_width() {
        return _button_sprite.getWidth();
    }

    @Override
    public float get_height() {
        return _button_sprite.getHeight();
    }
}
