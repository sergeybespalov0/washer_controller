package com.mygdx.game.android.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Interfaces.ISeveralStates;
import com.mygdx.game.android.Main;

/**
 * used to create and draw element, called spinner, used on primary screen,
 * <p/>
 * do not use size different from 4 until u change this class to support some additional actions
 */
public class Spinner implements ISeveralStates {
    private Main _main;
    public static BitmapFont _medium_font = new BitmapFont(Main._medium_font_fileHandle);
    private float _spinner_width;
    private float _spinner_height;
    private float _height_position;
    private float _width_position;
    private float _center_width;
    private float _center_height;
    private float _rotationSpeed;
    private float _round_step;
    private float _spinnerAngle;
    private float timer;
    private float deltaTime;
    private float _radius;
    private float[] _round_angles;
    private float[] _round_anglesTranslated;
    private float[] _buttonDirectionCoordinates;
    protected float _lastCoordinates;


    private Texture _spinnerCenter_texture;
    private Texture[] _quadrantTextures;
    private Texture _defaultQuadrantTextures;

    private Button[] _radialButtons;

    private Sprite _spinnerCenter_sprite;
    private Sprite _tinting;

    private ShapeRenderer _shapeRenderer;

    private String[] _states;

    private int maxWidth;


    private Color _separators_color;
    private Sprite _radialMenu_sprite;

    private OrthographicCamera _cam;

    /**
     * spinner initializer
     *
     * @param width_position  - width position of a texture
     * @param height_position - height position of a texture
     * @param width           - width of a texture
     * @param height          - height of a texture
     * @param cam             - orthographic camera, get used when drawing lines
     * @param States          - button text
     */
    public Spinner(Main main, float width_position, float height_position,
                   float width, float height, OrthographicCamera cam,
                   String[] States) {
        if (main == null || States.length == 0 || cam == null) {
            throw new NullPointerException("Spinner initialization parameters can't be null");
        }
        _main = main;
        _cam = cam;
        _spinner_height = height;
        _spinner_width = width;
        _height_position = height_position;
        _width_position = width_position;
        _center_width = _width_position + _spinner_width / 2;
        _center_height = _height_position + _spinner_height / 2;
        maxWidth = 0;
        deltaTime = (float) 0.1;//Gdx.graphics.getDeltaTime();

        _radius = (((Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8) / 12) * 5) / 2;

        _separators_color = new Color(Color.valueOf("4e4b4b"));
        _defaultQuadrantTextures = new Texture("spinner/quadrants.png");
        _defaultQuadrantTextures.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _quadrantTextures = new Texture[4];
        _quadrantTextures[0] = new Texture("spinner/quadrants1.png");
        _quadrantTextures[0].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _quadrantTextures[1] = new Texture("spinner/quadrants2.png");
        _quadrantTextures[1].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _quadrantTextures[2] = new Texture("spinner/quadrants3.png");
        _quadrantTextures[2].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _quadrantTextures[3] = new Texture("spinner/quadrants4.png");
        _quadrantTextures[3].setFilter(TextureFilter.Linear, TextureFilter.Linear);

        _radialMenu_sprite = new Sprite(_quadrantTextures[1]);
        _radialMenu_sprite.setPosition(_center_width - _radius, _center_height - _radius);
        _radialMenu_sprite.setSize(_radius * 2, _radius * 2);
        _tinting = new Sprite(new Texture("tint1.png"));
        _tinting.setPosition(_center_width - _radius, _center_height - _radius);
        _tinting.setSize(_radius * 2, _radius * 2);


        _shapeRenderer = new ShapeRenderer();
        if (States.length > 14) {
            _states = new String[10];
            System.out.println("ERROR, WRONG STATES DATA, TOO HIGH");
        } else if (States.length < 0) {
            _states = new String[2];
            System.out.println("ERROR, WRONG STATES DATA, TOO LOW");
        } else {
            _states = new String[States.length];
            _states = States;
        }

        _round_step = 360 / _states.length;
        _round_angles = new float[_states.length];
        for (int i = 0; i < _round_angles.length; i++) {
            _round_angles[i] = i * _round_step;
        }


        _buttonDirectionCoordinates = new float[_states.length];
        _round_anglesTranslated = new float[_states.length];

        for (int i = 0; i < _round_anglesTranslated.length; i++) {
            _round_anglesTranslated[i] = 90 - i * _round_step;
            _buttonDirectionCoordinates[i] = _round_anglesTranslated[i] - _round_step / 2;
            if (_buttonDirectionCoordinates[i] < 0) {
                _buttonDirectionCoordinates[i] += 360;
            }
            if (_buttonDirectionCoordinates[i] == 0) {
                _buttonDirectionCoordinates[i] = 360;
            }
        }

        _spinnerAngle = _buttonDirectionCoordinates[0];
        _lastCoordinates = _buttonDirectionCoordinates[0];
        _rotationSpeed = 180f;

        _spinnerCenter_texture = new Texture("spinner/center_knob.png");
        _spinnerCenter_texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        _spinnerCenter_sprite = new Sprite(_spinnerCenter_texture);
        _spinnerCenter_sprite.setSize(_radius * 2 / 3, _radius * 2 / 3);
        _spinnerCenter_sprite.setPosition(_center_width - _spinnerCenter_sprite.getWidth() / 2,
                _center_height - _spinnerCenter_sprite.getHeight() / 2);
        _spinnerCenter_sprite.setOriginCenter();

////////////radial buttons initialization
        _radialButtons = new Button[_states.length];
        for (int i = 0; i < _radialButtons.length; i++) {
            GlyphLayout _glyphLayout = new GlyphLayout();
            _glyphLayout.setText(_medium_font, _states[i]);
            if (_glyphLayout.width > maxWidth) maxWidth = (int) _glyphLayout.width;
        }
        maxWidth += 10;


        for (int i = 0; i < _radialButtons.length; i++) {
            Button button = new Button();
            button.initialize(_center_width, _center_height, maxWidth, _radius * 2 / 3,
                    Textures.get_main_button_pushed(),
                    Textures.get_main_button_not_pushed(),
                    Textures.get_main_button_pushed_disabled(),
                    Textures.get_main_button_not_pushed_disabled(),
                    _states[i], Color.BLACK, Main._medium_font_fileHandle);
            _radialButtons[i] = button;
        }
        _radialButtons[1].updateState(true);
        _radialButtons[1].setTextColor(Color.WHITE);
        _lastCoordinates = _buttonDirectionCoordinates[1];
    }


    /**
     * rotates the spinner towards the assigned coordinates
     *
     * @param finalPoint - vector, which has coordinates to rotate
     */
    public void update(Vector3 finalPoint) {
        if (finalPoint == null) {
            throw new NullPointerException("Spinner update parameters can't be null");
        }
        //float deltaTime = Gdx.graphics.getDeltaTime();
        ///создаём вектор, в который передаём координаты центра
        Vector3 turret = new Vector3(_spinnerCenter_sprite.getX(), _spinnerCenter_sprite.getY(), 0);
        //отнимаем вектор координат центра от значений координат, на которых дотронулись до экрана
        Vector3 turretTargetCopy = new Vector3(finalPoint);
        Vector3 target = new Vector3(turretTargetCopy.sub(turret));
        //формирование необходимого угла
        float turretTargetAngel = (float) Math.atan2(target.y, target.x);
        //перевод угла из градусов в радианы
        float dTurretTargetAngel = (float) (turretTargetAngel / Math.PI * 180f);

        //angle left
        float deltaAngleD = _spinnerAngle - dTurretTargetAngel;
        //fix for (-180 -> 180) bug
        if (deltaAngleD > 180f) {
            deltaAngleD = -360f + deltaAngleD;
        } else if (deltaAngleD < -180) {
            deltaAngleD = 360f + deltaAngleD;
        }

        //rotate with given rotation speed
        if (Math.abs(deltaAngleD) < _rotationSpeed * Gdx.graphics.getDeltaTime()) {
            _spinnerAngle -= deltaAngleD;
        } else if (deltaAngleD > 0) {
            _spinnerAngle -= _rotationSpeed * Gdx.graphics.getDeltaTime();
        } else {
            _spinnerAngle += _rotationSpeed * Gdx.graphics.getDeltaTime();
        }

        if (_spinnerAngle < -270) {
            _spinnerAngle += 360;
        }
        if (_spinnerAngle > 90) {
            _spinnerAngle -= 360;
        }

        //set angle
        _spinnerCenter_sprite.setRotation(_spinnerAngle);

    }

    /**
     * rotates the spinner towards the assigned angle
     *
     * @param angle - angle in degrees
     */
    public void rotateAngle(float angle) {
        //angle left
        float deltaAngleD = _spinnerAngle - angle;
        //fix for (-180 -> 180) bug
        if (deltaAngleD > 180f) {
            deltaAngleD = -360f + deltaAngleD;
        } else if (deltaAngleD < -180) {
            deltaAngleD = 360f + deltaAngleD;
        }
        //rotate with given rotation speed
        if (Math.abs(deltaAngleD) < _rotationSpeed * Gdx.graphics.getDeltaTime()) {
            _spinnerAngle -= deltaAngleD;
        } else if (deltaAngleD > 0) {
            _spinnerAngle -= _rotationSpeed * Gdx.graphics.getDeltaTime();
        } else {
            _spinnerAngle += _rotationSpeed * Gdx.graphics.getDeltaTime();
        }

        if (_spinnerAngle < -270) {
            _spinnerAngle += 360;
        }
        if (_spinnerAngle > 90) {
            _spinnerAngle -= 360;
        }
        _spinnerCenter_sprite.setRotation(_spinnerAngle);

    }

    /**
     * draw spinner
     *
     * @param spriteBatch - batch, in which we draw
     */
    public void draw(SpriteBatch spriteBatch) {
        if (spriteBatch == null) {
            throw new NullPointerException("Spinner draw parameters can't be null");
        }
        drawSeparators();
        spriteBatch.setProjectionMatrix(_cam.combined);
        spriteBatch.begin();
        _radialMenu_sprite.draw(spriteBatch);
        _tinting.draw(spriteBatch);
        _spinnerCenter_sprite.draw(spriteBatch);
        for (Button _radialButton : _radialButtons) {
            _radialButton.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    public void drawRect(ShapeRenderer renderer) {
        if (renderer == null) {
            throw new NullPointerException("Spinner drawRect parameters can't be null");
        }
        for (Button _radialButton : _radialButtons) {
            _radialButton.drawRect(renderer);
        }
    }

    /**
     * draw lines towards buttons
     */
    public void drawSeparators() {
        _shapeRenderer.setProjectionMatrix(_cam.combined);
        _shapeRenderer.begin(ShapeType.Filled);
        _shapeRenderer.setColor(_separators_color);
        float startX, startY, endX, endY;
        //_shapeRenderer.circle(_center_width, _center_height, separators_length);


        for (int i = 0; i < _round_angles.length; i++) {
///drawing circle separators
        /*endX = (float) (_center_width + _radius *
                Math.sin((_round_angles[i] * Math.PI / 180)));
        endY = (float) (_center_height + _radius *
                Math.cos((_round_angles[i] * Math.PI / 180)));
        _shapeRenderer.line(_center_width, _center_height, endX, endY);*/
            //drawing lines toward buttons
            float current_angle = _round_angles[i] + _round_step / 2;
            startX = (float) (_center_width + _radius *
                    Math.sin((current_angle * Math.PI / 180)));
            startY = (float) (_center_height + _radius *
                    Math.cos((current_angle * Math.PI / 180)));
            endX = (float) (_center_width + _radius * 1.5 *
                    Math.sin((current_angle * Math.PI / 180)));
            endY = (float) (_center_height + _radius * 1.5 *
                    Math.cos((current_angle * Math.PI / 180)));

            _shapeRenderer.rectLine(startX, startY, endX, endY, 3f);
            if (endX > _center_width) {
                _shapeRenderer.rectLine(endX, endY,
                        _width_position + _spinner_width - maxWidth, endY, 3f);
                _radialButtons[i].updatePosition(_width_position + _spinner_width - maxWidth,
                        endY - _radialButtons[i].get_height() / 2);
            } else {
                _shapeRenderer.rectLine(endX, endY, _width_position + maxWidth, endY, 3f);
                _radialButtons[i].updatePosition(_width_position, endY - _radialButtons[i].get_height() / 2);
            }
        }
        _shapeRenderer.end();
    }

    /**
     * set transmitted button on and set end degree to spinner
     */
    private void setRadialButtonPushed(int identifier) {

        /////checking identifier

        if (identifier < 0) {
            System.out.println("ERROR WHILE SETTING RADIAL BUTTONS PUSHED, IDENTIFIER TOO LOW");
            return;
        } else if (identifier >= _radialButtons.length) {
            System.out.println("ERROR WHILE SETTING RADIAL BUTTONS PUSHED, IDENTIFIER TOO HIGH");
            return;
        } else {
            ////switching off all radial buttons
            for (Button _radialButton : _radialButtons) {
                _radialButton.updateState(false);
                _radialButton.setTextColor(Color.BLACK);
            }
            ///switching on one radial button
            _radialButtons[identifier].updateState(true);
            _radialButtons[identifier].setTextColor(Color.WHITE);
            _lastCoordinates = _buttonDirectionCoordinates[identifier];
            _radialMenu_sprite.setTexture(_quadrantTextures[identifier]);

        }

    }

    /**
     * old
     *
     * @return
     */
    private int checkRadialButtonPushed() {

        for (int i = 0; i < _radialButtons.length; i++) {
            if (_radialButtons[i].getState()) return i;
        }
        return 0;
    }

    /**
     * check spinner degree and switch buttons relatively on spinner
     */
    public void checkSpinner() {
        float angle = _spinnerCenter_sprite.getRotation();
        float startAngle, endAngle;
        for (int i = 0; i < _radialButtons.length - 1; i++) {
            startAngle = _round_anglesTranslated[i];
            endAngle = _round_anglesTranslated[i + 1];
            if (startAngle > angle && angle >= (endAngle)) {
                setRadialButtonPushed(i);
                return;
            }
        }
        startAngle = _round_anglesTranslated[_round_anglesTranslated.length - 1];
        endAngle = _round_anglesTranslated[_round_anglesTranslated.length - 1] - _round_step;

        if ((startAngle > angle &&
                angle >= endAngle)) {
            setRadialButtonPushed(_radialButtons.length - 1);
            //return;
        }
    }

    /**
     * handle long touch
     */
    public void handleLongPressing() {
        if (Gdx.input.isTouched()) {
            timer += deltaTime;
            if (timer >= 1)
                if (touchedInRadius(_width_position + _spinner_width / 2,
                        _height_position + _spinner_height / 2,
                        _radius)) {
                    update(_cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
                    checkSpinner();
                }
        } else {
            rotateAngle(_lastCoordinates);
            timer = 0;
        }
    }

    /**
     * handle single touch
     */
    public void handleSingleInput() {
        if (Gdx.input.justTouched()) {
            for (int i = 0; i < _radialButtons.length; i++) {
                if (_radialButtons[i].isTouched(_cam)) {
                    setRadialButtonPushed(i);
                    //_execute.switchToScreenId(i);
                }
            }
            //////checking for top right corner
            if ((isTouched(_center_width, _center_height, _radius, _radius))
                    && (touchedInRadius(_width_position + _spinner_width / 2,
                    _height_position + _spinner_height / 2, _radius))
                    ) {
                setRadialButtonPushed(0);
            }
            //////checking for bot right corner
            if ((isTouched(_center_width, _center_height - _radius, _radius, _radius))
                    && (touchedInRadius(_width_position + _spinner_width / 2,
                    _height_position + _spinner_height / 2,
                    _radius))) {
                setRadialButtonPushed(1);
            }
            //////checking for bot left corner
            if ((isTouched(_center_width - _radius, _center_height - _radius, _radius, _radius))
                    && (touchedInRadius(_width_position + _spinner_width / 2,
                    _height_position + _spinner_height / 2,
                    _radius))) {
                setRadialButtonPushed(2);
            }

            //////checking for top left corner
            if ((isTouched(_center_width - _radius, _center_height, _radius, _radius))
                    && (touchedInRadius(_width_position + _spinner_width / 2,
                    _height_position + _spinner_height / 2,
                    _radius))) {
                setRadialButtonPushed(3);
            }
        }
    }

    /**
     * updating spinner position and angle to rotate
     *
     * @param state not used
     */
    @Override
    public void updateState(boolean state) {
        _spinnerAngle = _lastCoordinates;
        _spinnerCenter_sprite.setRotation(_lastCoordinates);
    }


    /**
     * not used
     *
     * @return
     */
    @Override
    public boolean getState() {
        return false;
    }

    /**
     * update position of spinner
     *
     * @param width  - new width position
     * @param height - new height position
     */
    @Override
    public void updatePosition(float width, float height) {
        _width_position = width;
        _height_position = height;

    }

    /**
     * check for touching in a given radius
     *
     * @param width_position  - width position of circle center
     * @param height_position - height position of circle center
     * @param radius          - radius of circle
     * @return true if last touched coordinates hits the circle
     */
    private boolean touchedInRadius(float width_position, float height_position, float radius) {
        Vector3 _TouchedCoordinates = _cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        if (Math.sqrt(Math.pow(width_position - _TouchedCoordinates.x, 2) +
                Math.pow(height_position - _TouchedCoordinates.y, 2)) < radius) return true;
        return false;
    }

    /**
     * check last touched coordinates for hitting the rectangle
     *
     * @param width_position  - width position of a rectangle
     * @param height_position - height position of a rectangle
     * @param width           - width of a rectangle
     * @param height          - height of a rectangle
     * @return true, if last touched coordinates hits the given rectangle
     */
    private boolean isTouched(float width_position, float height_position, float width, float height) {
        Vector3 _TouchedCoordinates = _cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        if (((_TouchedCoordinates.y < (height_position + height)) && (_TouchedCoordinates.y > (height_position)))
                &&
                ((_TouchedCoordinates.x > width_position) && (_TouchedCoordinates.x < width_position + width)))
            return true;
        else return false;
    }

    public boolean isTouched(int button_id) {
        if (button_id < 0 || button_id > _radialButtons.length) return false;
        return _radialButtons[button_id].isTouched(_cam);
    }


    public Button get_button(int button_id) {
        if (button_id < 0 || button_id >= _radialButtons.length) return null;
        return _radialButtons[button_id];
    }

    /**
     * dispose spinner
     */
    public void dispose() {
        _shapeRenderer.dispose();
        _spinnerCenter_texture.dispose();
        _spinnerCenter_texture.dispose();
        _defaultQuadrantTextures.dispose();
        for (Texture _quadrantTexture : _quadrantTextures) {
            _quadrantTexture.dispose();
        }
        for (Button button : _radialButtons) {
            button.dispose();
        }
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
        return _spinner_width;
    }

    @Override
    public float get_height() {
        return _spinner_height;
    }
}
