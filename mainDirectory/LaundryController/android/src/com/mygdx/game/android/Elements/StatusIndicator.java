package com.mygdx.game.android.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.android.Interfaces.ISeveralStates;

/**
 * element which groups indicator and its description
 */
public class StatusIndicator implements ISeveralStates {

    private float _width_position;
    private float _height_position;
    private float _width;
    private float _height;
    private int _align;
    private String _textToOutput;
    private BitmapFont _textFont;
    private GlyphLayout _glyphLayout;
    private Indicator _indicator;

    /**
     * initializer of indicator and its description
     *
     * @param align           - align of a description text
     * @param width_position  - width position of indicator
     * @param height_position - height position of indicator
     * @param size            - size of indicator, height and width
     * @param notActive       - texture of indicator, if its not active
     * @param active          - texture of indicator, if its active
     * @param textFont        - font of description text
     * @param textColor       - color of description text
     * @param description     - description of indicator
     */
    public StatusIndicator(float align, float width_position, float height_position, float size,
                           Texture notActive, Texture active,
                           BitmapFont textFont, Color textColor, String description) {
        if (notActive == null || active == null || textFont == null
                || textColor == null ) {
            throw new NullPointerException("StatusIndicator initialization parameters can't be null");
        }
        if (align == 0) {
            _align = 0;
        } else _align = 1;
        _width_position = width_position;
        _height_position = height_position;

        _height = size;
        _width = size;
        _textToOutput = description;
        _glyphLayout = new GlyphLayout();
        _textFont = textFont;
        _textFont.setColor(textColor);
        _glyphLayout.setText(_textFont, _textToOutput);

        _indicator = new Indicator(_width_position, _height_position, size, _height,
                notActive, active);
    }

    /**
     * draw indicator and its description
     *
     * @param batch - batch, in which we draw
     */
    public void draw(SpriteBatch batch) {
        if (batch == null) {
            throw new NullPointerException("StatusIndicator draw parameters can't be null");
        }
        _indicator.draw(batch);
        if (_align == 0)
            _textFont.draw(batch, _glyphLayout, _indicator.get_width_position() - _glyphLayout.width - 10,
                    _height_position + _indicator.get_height() / 2 + _glyphLayout.height / 2);
        else
            _textFont.draw(batch, _glyphLayout, _indicator.get_width_position() + _indicator.get_width() + 10,
                    _height_position + _indicator.get_height() / 2 + _glyphLayout.height / 2);
    }

    /**
     * @param cam is used to capture last touched coordinates
     * @return true if last touched coordinates hits the button
     */
    public boolean isTouched(OrthographicCamera cam) {
        if (cam == null)
            throw new NullPointerException("StatusIndicator isTouched parameters can't be null!");

        Vector3 _TouchedCoordinates = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return ((_TouchedCoordinates.y < (_height_position + _height)) && (_TouchedCoordinates.y > (_height_position)))
                &&
                ((_TouchedCoordinates.x > _width_position) && (_TouchedCoordinates.x < _width_position + _width));
    }


    public void dispose() {
        _textFont.dispose();
        _indicator.dispose();
    }

    /**
     * updating state if indicator
     *
     * @param state - indicates to switch texture of a sprite
     */
    @Override
    public void updateState(boolean state) {
        _indicator.updateState(state);
    }

    /**
     * @return true, if indicator is active
     */
    @Override
    public boolean getState() {
        return _indicator.getState();
    }

    /**
     * updating indicator position to given values
     *
     * @param width  - new width position
     * @param height - new height position
     */
    @Override
    public void updatePosition(float width, float height) {
        _indicator.updatePosition(width, height);
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
        return (int) (_glyphLayout.width + _indicator.get_width() + 10);
    }
}
