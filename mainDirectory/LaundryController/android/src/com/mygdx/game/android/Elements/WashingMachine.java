package com.mygdx.game.android.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * used to visualize the state of washing machine
 */
public class WashingMachine {
    private WashingMachineAction _current_action;
    private WashingMachineAction _previous_action;
    private float _angle;
    private Texture _WMachineTexture;
    private Sprite _WMachineSprite;

    private float _height_position;
    private float _width_position;
    private float _width;
    private float _height;
    private float _current_rotation_speed;
    private float _max_rotation_speed;

    /**
     * initialize whe washing machine sing
     *
     * @param width_position   - width position of washing machine
     * @param height_position- height position of washing machine
     * @param size-            size of washing machine, width and height are the same
     * @param WMachineTexture- texture of washing machine, IT MUST BE CIRCULAR
     */
    public WashingMachine(float width_position, float height_position, float size,
                          Texture WMachineTexture) {

        if (WMachineTexture == null) {
            throw new NullPointerException("WashingMachine initialization parameters can't be null!");
        }
        _angle = 0;
        _previous_action = WashingMachineAction.action_stop;
        _current_action = WashingMachineAction.action_stop;
        _current_rotation_speed = 0;
        _max_rotation_speed = 0;
        _height_position = height_position;
        _width_position = width_position;
        _height = size;
        _width = size;
        _WMachineTexture = WMachineTexture;
        _WMachineTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _WMachineSprite = new Sprite(_WMachineTexture);
        _WMachineSprite.setPosition(_width_position, _height_position);
        _WMachineSprite.setSize(_width, _height);
        _WMachineSprite.setOriginCenter();
    }

    /**
     * draw the washing machine action symbolizer
     *
     * @param spriteBatch - sprite batch, in which we draw
     */
    public void draw(SpriteBatch spriteBatch) {
        if (spriteBatch == null) {
            throw new NullPointerException("WashingMachine draw parameters can't be null");
        }
        _WMachineSprite.draw(spriteBatch);
    }

    /**
     * updating state of washing machine
     *
     * @param delta - delta time between screen renders
     */
    public void update(float delta) {
        /**
         * do action, depending on current state of washing machine
         */
        switch (_current_action) {
            case action_washLeft: {
                action_washLeft(delta);
                break;
            }
            case action_washRight: {
                action_washRight(delta);
                break;
            }
            case action_spin1: {
                action_spin1(delta);
                break;
            }
            case action_spin2: {
                action_spin2(delta);
                break;
            }
            case action_stop: {
                action_stopWashingMachine(delta);
                break;
            }
            default: {
                action_stopWashingMachine(delta);
            }
        }
        /**
         * rotation direction, added to show correct rotation direction when rotation to the left or
         * stopping after rotation to the left
         */
        if (_current_action.equals(WashingMachineAction.action_washLeft) ||
                ((_previous_action.equals(WashingMachineAction.action_washLeft)) && _current_action.equals(WashingMachineAction.action_stop))) {
            _angle += _current_rotation_speed * delta;
        } else {
            _angle -= _current_rotation_speed * delta;
        }
        /**
         * reduce the angle value in order to reduce the cost of performance of the angle setting function
         */
        if (_angle > 361.0f) _angle -= 360.0f;
        if (_angle < -1.0f) _angle += 360.0f;
        /**
         * rotate sprite with given angle
         */
        _WMachineSprite.setRotation(_angle);

    }

    /**
     * symbolizes washing to the left side
     */
    private void action_washLeft(float delta) {

        if (_current_rotation_speed < _max_rotation_speed) {
            _current_rotation_speed += 40f * delta;
        } else {
            if (_current_rotation_speed > _max_rotation_speed) {
                _current_rotation_speed = _max_rotation_speed;
            }
        }
    }

    /**
     * symbolizes washing to the right side
     */
    private void action_washRight(float delta) {
        if (_current_rotation_speed < _max_rotation_speed) {
            _current_rotation_speed += 40f * delta;
        } else {
            if (_current_rotation_speed > _max_rotation_speed) {
                _current_rotation_speed = _max_rotation_speed;
            }
        }
    }

    /**
     * symbolizes spin
     */
    private void action_spin1(float delta) {
        if (_current_rotation_speed < _max_rotation_speed) {
            _current_rotation_speed += 40f * delta;
        } else {
            if (_current_rotation_speed > _max_rotation_speed) {
                _current_rotation_speed = _max_rotation_speed;
            }
        }
    }

    /**
     * symbolizes fast spin
     */
    private void action_spin2(float delta) {
        if (_current_rotation_speed < _max_rotation_speed) {
            _current_rotation_speed += 40f * delta;
        } else {
            if (_current_rotation_speed > _max_rotation_speed) {
                _current_rotation_speed = _max_rotation_speed;
            }
        }
    }

    /**
     * symbolizes deceleration of washing machine drum
     */
    private void action_stopWashingMachine(float delta) {
        if (_current_rotation_speed > 0) {
            _current_rotation_speed -= 120f * delta;
        }
        if (_current_rotation_speed < 0) {
            _current_rotation_speed = 0;
        }
    }

    /**
     * created for the explicit definition of the action.
     */
    public enum WashingMachineAction {
        action_spin2,
        action_spin1,
        action_washLeft,
        action_washRight,
        action_stop
    }

    /**
     * used to drop rotation speed,
     * used when show's screen with washing machine visual status (this class.draw method)
     */
    public void dropRotationSpeed() {
        _current_rotation_speed = 0f;
    }

    /**
     * switching between actions
     *
     * @param action - is Washing machine action, pre defined in code above
     */
    public void set_current_action(WashingMachineAction action) {
        if (action == null) {
            throw new NullPointerException("Current washing machine action can't be null");
        }
        if (_current_action.equals(action)) {
            return;
        }
        /**
         * remember previous action, used to define direction for deceleration after left rotation
         */
        _previous_action = _current_action;

        _current_action = action;
        /**
         * set maximum rotation speed, depending on current action
         */
        switch (_current_action) {
            case action_washRight: {
                _max_rotation_speed = 240f;
                break;
            }
            case action_washLeft: {
                _max_rotation_speed = 240f;
                break;
            }
            case action_spin1: {
                _max_rotation_speed = 360f;
                break;
            }
            case action_spin2: {
                _max_rotation_speed = 720f;
                break;
            }
            case action_stop: {
                _max_rotation_speed = 0;
                break;
            }
            default: {
                _max_rotation_speed = 0;
            }
        }
    }

    /**
     * changing size of washing machine
     *
     * @param width  - new width
     * @param height - new height
     */
    public void changeSize(float width, float height) {
        _width = width;
        _height = height;
        _WMachineSprite.setSize(_width, _height);
        _WMachineSprite.setOriginCenter();
    }

    /**
     * method returns true, if element is enabled and last touched coordinates hits
     * the circle in center of element
     *
     * @param camera is used to capture last touched coordinates
     */
    public boolean isTouched(OrthographicCamera camera) {
        if (camera == null)
            throw new NullPointerException("WashingMachine touchedInRadius parameters can't be null!");
        Vector3 _TouchedCoordinates = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return Math.sqrt(Math.pow(_width_position + _width / 2 - _TouchedCoordinates.x, 2) +
                Math.pow(_height_position + _height / 2 - _TouchedCoordinates.y, 2)) < _width / 2;
    }

    /**
     * disposing washing machine symbolizer
     */
    public void dispose() {
        _WMachineSprite.getTexture().dispose();
        _WMachineTexture.dispose();
    }

    /**
     * update position of washing machine isShowed symbolizer
     *
     * @param width  - new width position
     * @param height - new height position
     */
    public void updatePosition(float width, float height) {
        _width_position = width;
        _height_position = height;
        _WMachineSprite.setPosition(_width_position, _height_position);
    }

    public float get_width_position() {
        return _width_position;
    }

    public float get_height_position() {
        return _height_position;
    }

    public float get_width() {
        return _width;
    }

    public float get_height() {
        return _height;
    }
}
