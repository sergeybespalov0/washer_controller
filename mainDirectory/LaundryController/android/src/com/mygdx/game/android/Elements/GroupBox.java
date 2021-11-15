package com.mygdx.game.android.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.android.Data.Textures;

/**
 * allows to group elements close in meaning
 */
public class GroupBox {


    private float _start_width_position;
    private float _start_height_position;
    private float _end_width_position;
    private float _end_height_position;
    private boolean _hidden;

    private Sprite _font_sprite;
    private Sprite _font_tinting;


    /**
     * class initializer, draws texture in a specified square from start positions, to end positions
     *
     * @param start_width_position  -
     * @param start_height_position -
     * @param end_width_position    -
     * @param end_height_position   -
     */
    public GroupBox(float start_width_position, float start_height_position,
                    float end_width_position, float end_height_position) {
        _hidden = false;
        _start_width_position = start_width_position;
        _end_width_position = end_width_position;
        _start_height_position = start_height_position;
        _end_height_position = end_height_position;

        _font_sprite = new Sprite(Textures.get_main_background());
        _font_sprite.setPosition(_start_width_position, _start_height_position);
        _font_sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _font_sprite.setSize(_end_width_position - _start_width_position,
                _end_height_position - _start_height_position);


        _font_tinting = new Sprite(new Texture("backgrounds/background_tint.png"));
        _font_tinting.setPosition(_start_width_position, _start_height_position);
        _font_tinting.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _font_tinting.setSize(_end_width_position - _start_width_position,
                _end_height_position - _start_height_position);
    }

    public void drawNoRect(SpriteBatch batch) {
        if (batch == null) {
            throw new NullPointerException("GroupBox draw no rect parameters can't be null");
        }
        if (!_hidden) {
            _font_sprite.draw(batch);
            _font_tinting.draw(batch);
        }
    }

    public void drawRect(ShapeRenderer renderer) {
        if (renderer == null) {
            throw new NullPointerException("GroupBox draw rect parameters can't be null");
        }
        if (!_hidden) {
            renderer.rect(_start_width_position, _start_height_position,
                    _end_width_position - _start_width_position,
                    _end_height_position - _start_height_position);
        }
    }

    /**
     * @param cam is used to capture last touched coordinates
     * @return false if button is disabled or not pushed, true if last touched coordinates hits the button
     */
    public boolean isTouched(OrthographicCamera cam) {
        if (cam == null)
            throw new NullPointerException("GroupBox isTouched parameters can't be null!");

        if (_hidden) return false;
        Vector3 _TouchedCoordinates = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return ((_TouchedCoordinates.y < (_start_height_position + _end_height_position - _start_height_position)) && (_TouchedCoordinates.y > (_start_height_position)))
                &&
                ((_TouchedCoordinates.x > _start_width_position) && (_TouchedCoordinates.x < _start_width_position + _end_width_position - _start_width_position));
    }

    /**
     * shows element
     */
    public void show() {
        _hidden = true;
    }

    /**
     * hides element
     */
    public void hide() {
        _hidden = false;
    }

    public void dispose() {
        _font_sprite.getTexture().dispose();
        _font_sprite = null;
        _font_tinting.getTexture().dispose();
        _font_tinting = null;
    }

    /**
     * @return true, if element is hidden
     */
    public boolean isHidden() {
        return _hidden;
    }

    /**
     * updating texture position and size to given values
     *
     * @param start_width_position  -
     * @param start_height_position -
     * @param end_width_position    -
     * @param end_height_position   -
     */
    public void updatePosition(float start_width_position, float start_height_position,
                               float end_width_position, float end_height_position) {
        _start_width_position = start_width_position;
        _end_width_position = end_width_position;
        _start_height_position = start_height_position;
        _end_height_position = end_height_position;

        _font_sprite.setPosition(_start_width_position, _start_height_position);

        _font_sprite.setSize(_end_width_position - _start_width_position,
                _end_height_position - _start_height_position);

        _font_tinting.setPosition(_start_width_position, _start_height_position);

        _font_tinting.setSize(_end_width_position - _start_width_position,
                _end_height_position - _start_height_position);
    }

    public float get_start_width_position() {
        return _start_width_position;
    }

    public float get_start_height_position() {
        return _start_height_position;
    }

    public float get_end_width_position() {
        return _end_width_position;
    }

    public float get_end_height_position() {
        return _end_height_position;
    }
}

