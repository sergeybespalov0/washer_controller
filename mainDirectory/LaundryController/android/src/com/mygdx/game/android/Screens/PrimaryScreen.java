package com.mygdx.game.android.Screens;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Elements.Spinner;
import com.mygdx.game.android.Interfaces.IDataTransfer;
import com.mygdx.game.android.Main;

/**
 * primary screen, used to switch to other screens
 */
public class PrimaryScreen implements IDataTransfer, InputProcessor {
    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    private Spinner _spin;
    private Main _main;

    private static String _correct_password = "4554";
    private String _buffer;
    private int _screen_identifier;

    private float _timer;

    public PrimaryScreen(Main main, SpriteBatch batch, ShapeRenderer renderer,
                         OrthographicCamera camera) {
        if (main == null || batch == null || renderer == null || camera == null) {
            throw new NullPointerException("PrimaryScreen initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = camera;
        _timer = 0;
        _buffer = "";
        _screen_identifier = 0;
        int length = 4;
        /**
         * initializing menu names for spinner buttons
         */
        String[] variants = new String[length];
        variants[0] = TextValues.primaryScreen.spinner_serviceButton;
        variants[1] = TextValues.primaryScreen.spinner_helpButton;
        variants[2] = TextValues.primaryScreen.spinner_connectionButton;
        variants[3] = TextValues.primaryScreen.spinner_washButton;
        /**
         * initializing spinner
         */
        _spin = new Spinner(_main, Main.SCREEN_WIDTH / 2 - Main.SCREEN_WIDTH / 10 * 7 / 2,
                Main.SCREEN_HEIGHT / 2 - (Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8 * 2) / 2,
                Main.SCREEN_WIDTH / 10 * 7,
                Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8 * 2,
                _cam, variants);
    }

    @Override
    public void render(float delta) {
/**
 * update
 */
        if (_timer < _main._delay) _timer += delta;
        updateBeforeDraw();
/**
 * draw
 */
        draw();
    }

    /**
     * updating elements before drawing them
     */
    private void updateBeforeDraw() {
        if (_timer >= _main._delay) {
            handleLongPressing();
            _spin.handleSingleInput();
        }
        /**
         * disable certain buttons, depending on emergency state
         */
        if (_main._controller.getState().isInEmergency()) {
            _spin.get_button(0).disable();
            _spin.get_button(3).disable();
        } else {
            _spin.get_button(0).enable();
            _spin.get_button(3).enable();
        }
        _cam.update();
    }

    /**
     * draw screen elements
     */
    private void draw() {
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _batch.begin();
        _main.backgroundSprite.draw(_batch);

        _batch.end();
        _spin.draw(_batch);

        _renderer.begin(ShapeType.Line);
        _spin.drawRect(_renderer);
        _renderer.end();

        _batch.begin();
        _main._status.draw(_batch);
        _batch.end();
        _renderer.begin(ShapeType.Line);
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
        /**
         * return if error message is occurred
         */
        if (_main._status.is_error()) return;
        /**
         * go to help screen if help screen buttons is touched
         */
        if (_spin.isTouched(1)) {
            _main.ShowHelpScreen();
        }
        /**
         * if connection setup or service button is touched define screen to switch to if password is correct
         * and go to password enter screen
         */
        if (_spin.isTouched(2)) {
            _screen_identifier = 2;
            _main.ShowPasswordEnterScreen(this, TextValues.globalValues.description_Enter_password);
        }

        if (_spin.isTouched(0)) {
            _screen_identifier = 1;
            _main.ShowPasswordEnterScreen(this, TextValues.globalValues.description_Enter_password);
        }
        /**
         * go to laundry setup screen if if laundry setup buttons is touched
         */
        if (_spin.isTouched(3)) {
            _main.ShowLaundrySetupScreen();
        }


    }

    /**
     * handle long touch events
     */
    private void handleLongPressing() {
        if (_main._status.is_error()) return;
        _spin.handleLongPressing();
    }

    /**
     * check transmitted data if it's correct
     */
    private void passwordCheck() {
        /**
         * check for correct password
         */
        if (_buffer.equals(_correct_password)) {
            /**
             * switch to screen, depending on screen identifier
             */
            switch (_screen_identifier) {
                case 1: {
                    _screen_identifier = 0;
                    _main.ShowServiceScreen();
                    break;
                }
                case 2: {
                    _screen_identifier = 0;
                    _main.ShowConnectionScreen();
                    break;
                }
                default: {
                    System.out.println("no action");
                }
            }
        }
        _buffer = "";
        _screen_identifier = 0;
    }

    @Override
    public void resize(int width, int height) {
        _cam.viewportWidth = Main.SCREEN_WIDTH;
        _cam.viewportHeight = Main.SCREEN_HEIGHT;
        _cam.update();
    }

    @Override
    public void show() {
        _main._status.hideReturnButton();
        _spin.updateState(true);
        _main._status.updateNavigation(TextValues.navigation.primaryScreen);

        _timer = 0;
        Gdx.input.setInputProcessor(this);
        passwordCheck();
    }

    @Override
    public void hide() {
        _main._status.showReturnButton();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        _spin.dispose();
    }

    @Override
    public void setBuffer(String stringToChange) {
        _buffer = stringToChange;
    }

    @Override
    public String getBuffer() {
        return _buffer;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (_timer >= _main._delay) {
            if (_main._status.is_error()) {
                _main._status.handleLongTermDepressionWarnings();
            } else {
                _main._status.handleLongTermDepression();
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (_timer >= _main._delay) {
            if (_main._status.is_error()) {
                _main._status.handleSingleInputWarnings();
                _main._status.dePressWarnings();
            } else {
                _main._status.handleSingleInput();
                handleSingleInput();
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
