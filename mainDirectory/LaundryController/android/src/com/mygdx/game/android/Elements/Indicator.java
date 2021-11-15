package com.mygdx.game.android.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.android.Interfaces.ISeveralStates;

/**
 * used to indicate something
 */
public class Indicator implements ISeveralStates {

    private boolean _isActive;
    private Texture _active;
    private Texture _notActive;
    private Sprite _sprite;
    private float _width;
    private float _height;
    private float _height_position;
    private float _width_position;

    /**
     * class initializer
     *
     * @param width_position  - width position of texture
     * @param height_position - height position of texture
     * @param width           - width of texture
     * @param height          - height of texture
     * @param notActive       - texture, used if indicator not active
     * @param active          - texture, used if indicator is active
     */
    public Indicator(float width_position, float height_position, float width, float height,
                     Texture notActive, Texture active) {
        if (active == null || notActive == null) {
            throw new NullPointerException("Indicator initialization parameters can't be null");
        }
        _isActive = false;
        _height = height;
        _width = width;
        _active = active;
        _notActive = notActive;
        _height_position = height_position;
        _width_position = width_position;

        _sprite = new Sprite(_notActive);
        _sprite.setPosition(_width_position, _height_position);
        _sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _sprite.setSize(_width, _height);
    }

    /**
     * method to draw indicator in transmitted sprite
     *
     * @param spriteBatch - sprite, in which we raw
     */
    public void draw(SpriteBatch spriteBatch) {
        if (spriteBatch == null) {
            throw new NullPointerException("Indicator draw parameters can't be null");
        }
        _sprite.draw(spriteBatch);
    }

    /**
     * used to check if sprite was touched
     *
     * @param cam - orthographic camera, used to get last touched coordinates
     * @return true, if last touched coordinates was in used sprite
     */
    public boolean isTouched(OrthographicCamera cam) {
        if (cam == null) {
            throw new NullPointerException("Indicator isTouched parameters can't be null");
        }
        Vector3 _TouchedCoordinates = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        if (((_TouchedCoordinates.y < (_height_position + _height)) && (_TouchedCoordinates.y > (_height_position)))
                &&
                ((_TouchedCoordinates.x > _width_position) && (_TouchedCoordinates.x < _width_position + _width)))
            return true;
        else return false;
    }

    /**
     * dispose the indicator
     */
    public void dispose() {
        _active.dispose();
        _notActive.dispose();
        _sprite.getTexture().dispose();
    }

    /**
     * updating state if indicator
     *
     * @param state - indicates to switch texture of a sprite
     */
    @Override
    public void updateState(boolean state) {
        _isActive = state;
        if (_isActive) {
            _sprite.setTexture(_active);
        } else {
            _sprite.setTexture(_notActive);
        }
    }

    /**
     * method to get state of a sprite
     *
     * @return true, if indicator is disabled
     */
    @Override
    public boolean getState() {
        return _isActive;
    }

    /**
     * updating texture position to given values
     *
     * @param width  - new width position
     * @param height - new height position
     */
    @Override
    public void updatePosition(float width, float height) {
        _width_position = width;
        _height_position = height;
        _sprite.setPosition(_width_position, _height_position);
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
