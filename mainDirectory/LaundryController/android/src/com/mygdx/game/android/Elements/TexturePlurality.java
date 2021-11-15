package com.mygdx.game.android.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.android.Interfaces.ISeveralStates;

/**
 * class which uses plurality of textures to draw
 */
public class TexturePlurality implements ISeveralStates {


    private boolean _disabled;
    private Texture[] _texturesPlurality;
    private Sprite _texture_sprite;
    private float _width;
    private float _height;
    private float _height_position;
    private float _width_position;
    private boolean _visible;
    private int _currentTexture;
    private int _texturesCount;

    /**
     * initializer of plurality of textures
     *
     * @param width_position     - texture width position
     * @param height_position    - texture height position
     * @param width              - texture width
     * @param height             - texture height
     * @param textures_plurality - array of textures
     * @param currentTexture     - id of current texture
     */
    public TexturePlurality(float width_position, float height_position, float width, float height,
                            Texture[] textures_plurality, int currentTexture) {

        if (textures_plurality.length == 0) {
            throw new NullPointerException("TexturePlurality initialization parameters can't be null!");
        }
        _height = height;
        _width = width;
        _height_position = height_position;
        _width_position = width_position;
        _visible = true;

        _texturesCount = textures_plurality.length;
        _currentTexture = currentTexture;
        _texturesPlurality = new Texture[_texturesCount];
        _texturesPlurality = textures_plurality;
        if (_currentTexture < 0 ||
                _currentTexture > _texturesCount - 1)
            _currentTexture = 0;
        for (Texture temp : _texturesPlurality
                ) {
            temp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        _texture_sprite = new Sprite(_texturesPlurality[_currentTexture]);
        _texture_sprite.setPosition(_width_position, _height_position);

        _texture_sprite.setSize(_width, _height);
        _texture_sprite.setOriginCenter();
    }

    /**
     * draw texture
     *
     * @param spriteBatch - batch, in which we draw
     */
    public void draw(SpriteBatch spriteBatch) {
        if (spriteBatch == null) {
            throw new NullPointerException("TexturePlurality draw parameters can't be null");
        }

        if (_visible) {
            _texture_sprite.draw(spriteBatch);
        }
    }

    /**
     * change size of texture
     *
     * @param width  - new width
     * @param height - new height
     */
    public void changeSize(float width, float height) {
        _width = width;
        _height = height;
        _texture_sprite.setSize(_width, _height);
        _texture_sprite.setOriginCenter();

    }

    /**
     * switch to another texture
     *
     * @param number - number of texture in array
     */
    public void switchTexture(int number) {
        if (number < 0 || number > _texturesCount) return;
        _texture_sprite.setTexture(_texturesPlurality[number]);
    }

    /**
     * @param cam is used to capture last touched coordinates
     * @return false if button is disabled or not pushed, true if coordinates hits the button
     */
    public boolean IsTouched(OrthographicCamera cam) {
        if (cam == null)
            throw new NullPointerException("TexturePlurality isTouched parameters can't be null!");

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
    public boolean BrutForceTouched(OrthographicCamera cam) {
        if (cam == null)
            throw new NullPointerException("TexturePlurality bruteForceTouched parameters can't be null!");

        Vector3 _TouchedCoordinates = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return ((_TouchedCoordinates.y < (_height_position + _height)) && (_TouchedCoordinates.y > (_height_position)))
                &&
                ((_TouchedCoordinates.x > _width_position) && (_TouchedCoordinates.x < _width_position + _width));
    }

    /**
     * dispose plurality of textures
     */
    public void dispose() {
        for (Texture texture : _texturesPlurality
                ) {
            texture.dispose();
        }
        _texture_sprite.getTexture().dispose();
    }

    /**
     * enable isTouched method
     */
    public void enable() {
        _disabled = false;

    }

    /**
     * disable isTouched method
     */
    public void disable() {
        _disabled = true;

    }

    /**
     * set texture visible
     */
    public void show() {
        _visible = true;
    }

    /**
     * set texture invisible
     */
    public void hide() {
        _visible = false;
    }

    /**
     * @return true, if texture is visible
     */
    public boolean is_visible() {
        return _visible;
    }


    /**
     * @return true, if disabled
     */
    public boolean isDisabled() {
        return _disabled;
    }

    /**
     * not used
     *
     * @param state
     */
    @Override
    public void updateState(boolean state) {
    }

    /**
     * unused
     *
     * @return
     */
    @Override
    public boolean getState() {
        return false;
    }

    /**
     * updating texture position
     *
     * @param width  - new width position
     * @param height - new height position
     */
    @Override
    public void updatePosition(float width, float height) {
        _width_position = width;
        _height_position = height;
        _texture_sprite.setPosition(_width_position, _height_position);
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
        return _width;
    }

    @Override
    public float get_height() {
        return _height;
    }
}
