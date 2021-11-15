package com.mygdx.game.android.Elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Interfaces.ISeveralStates;

/**
 * class which used to show user the progress of some action
 */
public class ProgressBar implements ISeveralStates {


    private Texture _fontTexture;
    private Texture _fillTexture;
    private Sprite _fontSprite;
    private Sprite[] _fillSprite;
    private float _width;
    private float _height;
    private float _height_position;
    private float _width_position;
    private float _borders;
    private final int _minimum_progress = 0;
    private static int _maximum_progress;
    private int _current_progress;
    private ShapeRenderer _shapeRenderer;

    private boolean _isFull;

    /**
     * progress bar initializer
     *
     * @param width_position   - width position of a progress bar
     * @param height_position  - height position of a progress bar
     * @param width            - width of a progress bar
     * @param height           - height of a progress bar
     * @param maximum_progress - maximum progress
     * @param fontTexture      - font texture of a progress bar
     * @param fillTexture      - texture of an elements, which fills the progress bar
     */
    public ProgressBar(float width_position, float height_position, float width, float height,
                       int maximum_progress, Texture fontTexture, Texture fillTexture) {

        _maximum_progress = maximum_progress;
        _height = height;
        _width = width;
        _fontTexture = fontTexture;
        _fillTexture = fillTexture;
        _height_position = height_position;
        _width_position = width_position;

        _isFull = false;
        _borders = 6;
        _current_progress = _minimum_progress;
        _shapeRenderer = new ShapeRenderer();
        float _indent;
        _indent = (_width - _borders * 2) /
                (_maximum_progress * 9 + (_maximum_progress + 1));


        _fontSprite = new Sprite(_fontTexture);
        _fontSprite.setPosition(_width_position, _height_position);
        _fontSprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _fontSprite.setSize(_width, _height);

        _fillSprite = new Sprite[_maximum_progress];
        float fillPosition = _width_position + _borders + _indent;
        for (int i = 0; i < _fillSprite.length; i++) {
            Sprite temp = new Sprite(_fillTexture);
            _fillSprite[i] = temp;
            _fillSprite[i].setPosition(fillPosition, _height_position + _borders + 2);
            _fillSprite[i].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            _fillSprite[i].setSize(_indent * 9,
                    _height - _borders * 2 - 4);
            fillPosition += _indent * 10;
        }


    }

    /**
     * draw progress bar
     *
     * @param spriteBatch - batch, in which we draw
     */
    public void draw(SpriteBatch spriteBatch) {

        _fontSprite.draw(spriteBatch);
        for (int i = 0; i < _current_progress; i++)
            _fillSprite[i].draw(spriteBatch);


    }

    /**
     * draw 1px rectangle at the edges of progress bar *
     *
     * @param camera - camera of main class, used to switch to shapeRenderer
     */
    public void drawRect(OrthographicCamera camera) {
        _shapeRenderer.setProjectionMatrix(camera.combined);
        _shapeRenderer.begin(ShapeType.Line);
        _shapeRenderer.setColor(Color.BLACK);
        _shapeRenderer.rect(_width_position, _height_position + 1, _width - 1, _height - 1);
        _shapeRenderer.rect(_width_position + 2, _height_position + 3, _width - 5, _height - 5);
        _shapeRenderer.setColor(Color.BROWN);
        _shapeRenderer.rect(_width_position + 1, _height_position + 2, _width - 3, _height - 3);
        _shapeRenderer.end();
    }

    /**
     * increase progress of a progress bar,
     * show appropriate message if progress bar is full
     */
    public void increase_progress() {
        if (_current_progress < _maximum_progress) {
            _current_progress++;
            checkIsFull();
        }
    }

    /**
     * decease progress of a progress bar,
     * show appropriate message if progress bar is empty
     */
    public void decrease_progress() {
        if (_current_progress > 0) {
            _current_progress--;
            checkIsFull();
        }
    }

    /**
     * check progress bar if it is full, and set appropriate values to variables
     */
    private void checkIsFull() {
        if (_current_progress == _maximum_progress) _isFull = true;
        else _isFull = false;
    }

    /**
     * @return true if progress bar is full
     */
    public boolean isFull() {
        return _isFull;
    }

    /**
     * dispose progress bar
     */
    public void dispose() {
        _fillTexture = null;
        _fontTexture = null;
        for (Sprite sprite : _fillSprite
                ) {
            sprite = null;
        }
        _fontSprite = null;
    }

    /**
     * update progress bar state
     *
     * @param state - count of elements
     */
    public void updateState(int state) {
        if (state >= _minimum_progress || state <= _maximum_progress) {
            _current_progress = state;
            checkIsFull();
        }
    }

    public int getProgress() {
        return _current_progress;
    }

    @Override
    public void updateState(boolean state) {
    }

    @Override
    public boolean getState() {
        return _isFull;
    }

    /**
     * updating position of a progress bar
     *
     * @param width  - new width position
     * @param height - new height position
     */
    @Override
    public void updatePosition(float width, float height) {
        _width_position = width;
        _height_position = height;
        _fontSprite.setPosition(_width_position, _height_position);
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

    public int get_maximum_progress() {
        return _maximum_progress;
    }
}
