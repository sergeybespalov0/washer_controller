package com.mygdx.game.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Elements.Button;
import com.mygdx.game.android.Elements.TextBox;
import com.mygdx.game.android.Interfaces.IDataTransfer;
import com.mygdx.game.android.Main;

import static android.hardware.Camera.getNumberOfCameras;

/**
 * This class is used to enter user's USERNAME.
 * It shows only when program is newly installed or it's data has been erased.
 * <p/>
 * IDataTransfer is used to transfer data between screens with buffer
 * <p/>
 * InputProcessor is used to detect touch events.
 */
public class LoginScreen implements Screen, InputProcessor, IDataTransfer {
    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    private Main _main;


    private TextBox _userName_textBox;
    private Button _confirm;
    private String _userName = "";
    private float _timer;


    public LoginScreen(Main main, SpriteBatch batch, ShapeRenderer renderer,
                       OrthographicCamera camera) {
        if (main == null || batch == null || renderer == null || camera == null) {
            throw new NullPointerException("LoginScreen initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = camera;
        _timer = 0;
        _userName = "";
        _userName_textBox = new TextBox();
        _userName_textBox.Initialize(Main.SCREEN_WIDTH / 2 - (float) (Main.SCREEN_WIDTH / 2.5 / 2),
                Main.SCREEN_HEIGHT / 2 + Main.SCREEN_HEIGHT / 8 / 4,
                (float) (Main.SCREEN_WIDTH / 2.5), Main.SCREEN_HEIGHT / 8,
                1, _userName, TextValues.globalValues.description_Enter_User_Name,
                Textures.get_textBox_wide_background(),
                Color.BLACK,
                Main._huge_font_fileHandle);
        _userName_textBox.setFixedDescriptionHeightPosition(_userName_textBox.get_height() + _userName_textBox.get_height_position() + Main.SCREEN_HEIGHT / 8 / 2 + _userName_textBox.get_description_height());
        _confirm = new Button();
        _confirm.initialize(_userName_textBox.get_width_position(),
                _userName_textBox.get_height_position() - _userName_textBox.get_height() - Main.SCREEN_HEIGHT / 8 / 2,
                _userName_textBox.get_width(), _userName_textBox.get_height(),
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.confirm, Color.BLACK, Main._huge_font_fileHandle);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta - delta time between previous render
     */
    @Override
    public void render(float delta) {
        updateBeforeDraw();
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        draw();
    }

    /**
     * update data before draw
     */
    private void updateBeforeDraw() {
        _cam.update();
        if (_timer < _main._delay) {
            _timer += Gdx.graphics.getDeltaTime();
        }
        _userName_textBox.updateContent(_userName);
        if (Main.CheckForName(_userName)) {
            _confirm.enable();
        } else {
            _confirm.disable();
        }
    }

    /**
     * draw elements
     */
    private void draw() {
        _batch.begin();
        _main.backgroundSprite.draw(_batch);
        _userName_textBox.draw(_batch);
        _confirm.draw(_batch);
        _main._status.draw(_batch);
        _batch.end();
        _renderer.begin(ShapeType.Line);
        _confirm.drawRect(_renderer);
        _main._status.drawRect(_renderer);
        _renderer.end();
        _batch.begin();
        _main._status.drawReturnButton(_batch);
        _batch.end();
    }

    /**
     * handle single touch events
     */
    private void handleSingleInput() {
        if (_confirm.isTouched(_cam)) {
            Main.set_username(_userName);
            _main.writeToFile(Main.get_username(), _main.get_ip(), String.valueOf(_main.get_port()));
            _main.ShowPrimaryScreen();
        }
        if (_userName_textBox.isTouched(_cam)) {
            _main.ShowTextEnterScreen(this, TextValues.globalValues.description_Username, 20);
        }
    }

    /**
     * handle long touch events
     */
    private void handleLongPressing() {
        if (_confirm.isTouched(_cam)) {
            _confirm.updateState(true);
        } else {
            _confirm.updateState(false);
        }
    }

    /**
     * resize current screen size to new values
     *
     * @param width  - new width value
     * @param height - new height value
     */
    @Override
    public void resize(int width, int height) {
        _cam.viewportWidth = Main.SCREEN_WIDTH;
        _cam.viewportHeight = Main.SCREEN_HEIGHT;
        _cam.update();
    }

    /**
     * Called when this screen becomes the current screen for a program.
     */
    @Override
    public void show() {
        _main._status.updateNavigation(TextValues.navigation.loginScreen);
        _timer = 0;
        _main._status.hideReturnButton();
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Called when this screen is no longer the current screen for a program.
     */
    @Override
    public void hide() {
    }

    /**
     * called when you switch to another program
     */
    @Override
    public void pause() {
    }

    /**
     * called on resuming
     */
    @Override
    public void resume() {
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        _userName_textBox.dispose();
        _confirm.dispose();
    }


    /**
     * code below is InputProcessor's action's when event is occurred
     */

    /**
     * The first three methods allow you to listen for keyboard events:
     */

    /**
     * Called when a key was pressed down. Reports the key code, as found in Keys.
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Called when a key was lifted. Reports the key code as above.
     */
    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    /**
     * Called when a Unicode character was generated by the keyboard input.
     * This can be used to implement text fields and similar user interface elements.
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    /**
     * The next three methods report mouse/touch events:
     */

    /**
     * Called when a finger went down on the screen or a mouse button was pressed.
     * Reports the coordinates as well as the pointer index and
     * mouse button (always Buttons.LEFT for touch screens).
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (_timer >= _main._delay) {
            handleLongPressing();
        }
        return true;
    }

    /**
     * Called when a finger was lifted from the screen or a mouse button was released.
     * Reports the last known coordinates as well as the pointer index and
     * mouse button (always Buttons.Left for touch screens).
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (_timer >= _main._delay) {
            handleSingleInput();
        }
        return true;
    }

    /**
     * Called when a finger is being dragged over the screen or the mouse is dragged
     * while a button is pressed. Reports the coordinates and pointer index.
     * The button is not reported as multiple buttons could be pressed while the mouse is being dragged.
     * You can use Gdx.input.isButtonPressed() to check for a specific button.
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse is moved over the screen without a mouse button being down.
     * This event is only relevant on the desktop and will never occur on
     * touch screen devices where you only get touchDragged() events.
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the scroll wheel of the mouse was turned.
     * Reports either -1 or 1 depending on the direction of spin.
     * This will never be called for touch screen devices.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    /**
     * code below is IDataTransfer's function's, which is used to transfer data between screens
     */

    /**
     * set buffer value to 'stringToChange'
     *
     * @param stringToChange - value to set
     */
    @Override
    public void setBuffer(String stringToChange) {
        _userName = stringToChange;
    }

    /**
     * get buffer value
     *
     * @return - buffer value (string)
     */
    @Override
    public String getBuffer() {
        return _userName;
    }
}