package com.mygdx.game.android.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Interfaces.ISeveralStates;

/**
 * used to create return button
 */
public class ReturnButton implements ISeveralStates {


    private Texture _texture_pushed;
    private Texture _texture_not_pushed;
    private Sprite _sprite;
    private boolean _pushed;
    private float _size;
    private float _width_position;
    private float _height_position;

    /**
     * initialize return button with its own static texture with size w=150, h=150
     *
     * @param width_position  - width position of return button
     * @param height_position - height position of return button
     */
    public ReturnButton(float width_position, float height_position, float size) {

        _height_position = height_position;
        _width_position = width_position;
        _size = size;
        _texture_pushed = Textures.get_return_button_pushed();
        _texture_pushed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _texture_not_pushed = Textures.get_return_button_not_pushed();
        _texture_not_pushed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _pushed = false;
        _sprite = new Sprite(_texture_not_pushed);
        _sprite.setPosition(_width_position, _height_position);
        _sprite.setSize(_size, _size);
    }

    /**
     * draw return button
     *
     * @param spriteBatch - batch, in which we draw
     */
    public void draw(SpriteBatch spriteBatch) {
        if (spriteBatch == null) {
            throw new NullPointerException("ReturnButton draw parameters can't be null");
        }
        _sprite.draw(spriteBatch);
    }

    /**
     * @param cam - used to get last touched coordinates
     * @return true, if last touched coordinates hits the button
     */
//// TODO: 2016-02-04 add quadrant selection from width_pos + 7px to isTouched method to avoid incorrect work of this function
    public boolean IsTouched(OrthographicCamera cam) {
        if (cam == null)
            throw new NullPointerException("ReturnButton isTouched parameters can't be null!");

        Vector3 _TouchedCoordinates = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        return (Math.sqrt(Math.pow(_width_position + _size - _TouchedCoordinates.x, 2) +
                Math.pow(_height_position - _TouchedCoordinates.y, 2)) < _size);
    }

    /**
     * disposing return button
     */
    public void dispose() {
        _texture_pushed.dispose();
        _texture_not_pushed.dispose();
        _sprite.getTexture().dispose();
    }

    /**
     * method not used
     *
     * @param state
     */
    @Override
    public void updateState(boolean state) {
        if (_pushed == state) return;
        _pushed = state;

        if (_pushed) {
            _sprite.setTexture(_texture_pushed);
        } else {
            _sprite.setTexture(_texture_not_pushed);
        }
    }

    /**
     * method not used
     *
     * @return false
     */
    @Override
    public boolean getState() {
        return false;
    }

    /**
     * updating position of return button
     *
     * @param width  - width position
     * @param height - height position
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
        return 150;
    }

    @Override
    public float get_height() {
        return 150;
    }
}
