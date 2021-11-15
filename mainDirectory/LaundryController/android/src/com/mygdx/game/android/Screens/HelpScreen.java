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
import com.mygdx.game.android.Elements.GroupBox;
import com.mygdx.game.android.Elements.RichTextBox;
import com.mygdx.game.android.Main;

/**
 * Help screen with fixed text on it, used to explain user various nuances
 */
public class HelpScreen implements Screen, InputProcessor {
    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    private GroupBox _textBox_groupBox;
    private RichTextBox _textBox;

    private Main _main;
    private float _timer;

    /**
     * Help screen initializer
     *
     * @param main     - main class
     * @param batch    - sprite batch, used to draw elements
     * @param renderer - shape renderer, used to draw rectangles around elements
     * @param cam      - orthographic camera
     */
    public HelpScreen(Main main, SpriteBatch batch, ShapeRenderer renderer,
                      OrthographicCamera cam) {
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _cam = cam;
        _timer = 0;

        _textBox = new RichTextBox(Main.SCREEN_WIDTH / 20,
                Main.SCREEN_HEIGHT - _main._status.get_height() * 2,
                Main.SCREEN_WIDTH / 10 * 8,
                Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 4,
                TextValues.helpScreenData.desc_for_laundrySetupScreen +
                        TextValues.helpScreenData.desc_for_programUpdate +
                        TextValues.helpScreenData.desc_for_ServiceScreen +
                        TextValues.helpScreenData.desc_for_connectionSetupScreen,
                Color.BLACK,
                Main._little_font_fileHandle);

        _textBox_groupBox = new GroupBox(_textBox.get_width_position() - _main._status.get_height() / 2,
                _textBox.get_height_position() + _main._status.get_height() / 2,
                _textBox.get_width_position() + _textBox.get_width() + _main._status.get_height() / 2,
                _textBox.get_height_position() - _textBox.get_height() - _main._status.get_height() / 2);

    }

    @Override
    public void render(float delta) {
/**
 * update
 */

        _cam.update();
        if (_timer < _main._delay) _timer += Gdx.graphics.getDeltaTime();
/**
 * clear and draw background
 */
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _batch.begin();
        _main.backgroundSprite.draw(_batch);
        _textBox_groupBox.drawNoRect(_batch);
/**
 * draw
 */
        _textBox.draw(_batch);
        _batch.end();

        _renderer.begin(ShapeType.Line);
        _textBox_groupBox.drawRect(_renderer);
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
        if (_main._status.returnButton_touched(_cam)) {
            _main.ShowPrimaryScreen();
        }
    }

    /**
     * handle long touch events
     */
    private void handleLongPressing() {

    }

    @Override
    public void resize(int width, int height) {
        _cam.viewportWidth = Main.SCREEN_WIDTH;
        _cam.viewportHeight = Main.SCREEN_HEIGHT;
        _cam.update();
    }

    @Override
    public void show() {
        _main._status.updateNavigation(TextValues.navigation.helpScreen);
        _timer = 0;
        /**
         * code for throwing exceptions, used for testing
         */
        /*
        ArrayList<WasherProgram> _programs = null;
        _programs.clear();
        */
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        _textBox.dispose();
        _textBox_groupBox.dispose();
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
                handleLongPressing();
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