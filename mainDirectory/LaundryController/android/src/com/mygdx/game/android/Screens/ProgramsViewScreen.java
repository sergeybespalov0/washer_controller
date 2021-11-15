package com.mygdx.game.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Elements.Button;
import com.mygdx.game.android.Elements.GroupBox;
import com.mygdx.game.android.Elements.RichTextBox;
import com.mygdx.game.android.Interfaces.IProgramTransfer;
import com.mygdx.game.android.Main;
import com.mygdx.game.android.WasherControl.WasherProgram;

import java.util.ArrayList;

/**
 * class used to show a list of laundry programs, which user can choose
 */
public class ProgramsViewScreen implements IProgramTransfer, InputProcessor, GestureDetector.GestureListener {
    private IProgramTransfer _lastScreen;
    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private ShapeRenderer _renderer;
    private Main _main;
    //private ConfirmBox _confirm;
    private InputMultiplexer _im;

    private Texture _up_arrow;
    private Texture _down_arrow;

    private float maxHeightPosition;
    private float minHeightPosition;
    private float _buttonArrayX;
    private float _buttonArrayY;
    private float _timer;

    private ArrayList<WasherProgram> _programs;

    private boolean[] _enabledButtons;
    private boolean _program_selected;
    private boolean _dragged;

    private Button _confirmProgram;
    private Button[] _buttonArray;

    private RichTextBox _program_description;

    private int _button_id;

    private GroupBox _button_groupBox;
    private GroupBox _programs_groupBox;
    private GroupBox _description_groupBox;

    /**
     * main initialization for class usage
     *
     * @param main     - main instance
     * @param batch    - batch to draw in
     * @param renderer - renderer to draw rectangles around elements
     * @param camera   - orthographic camera
     */
    public ProgramsViewScreen(Main main, SpriteBatch batch,
                              ShapeRenderer renderer, OrthographicCamera camera) {
        if (main == null || batch == null || renderer == null || camera == null) {
            throw new NullPointerException("ProgramsViewScreen initialization parameters can't be null");
        }
        this._main = main;
        _batch = batch;
        _renderer = renderer;
        _button_id = 0;
        _program_selected = false;
        _timer = 0;
        _cam = camera;

        _dragged = false;

        _up_arrow = Textures.get_arrow_up();
        _up_arrow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _down_arrow = Textures.get_arrow_down();
        _down_arrow.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _im = new InputMultiplexer();
        //_confirm = new ConfirmBox("", TextValues.globalValues.confirmEnteredData, _cam);
        GestureDetector gd = new GestureDetector(this);
        _im.addProcessor(gd);
        _im.addProcessor(this);
        initializeButtons();

    }

    /**
     * initialization before usage
     *
     * @param lastScreen - last screen to switch to when finish
     * @param programs   - list of programs to choose
     */
    public void Initialize(IProgramTransfer lastScreen, ArrayList<WasherProgram> programs) {
        if (lastScreen == null || programs.isEmpty()) {
            throw new NullPointerException("ProgramViewScreen pre-use initialization parameters can't be null");
        }
        _lastScreen = lastScreen;
        _programs = programs;
        _program_selected = false;
        float max_width = 0;
        String tempProgramName = "";
        for (int i = 0; i < _programs.size(); i++) {
            tempProgramName = _programs.get(i).getName();
            GlyphLayout glyph = new GlyphLayout();
            BitmapFont font = Main._medium_font;
            font.setColor(Color.BLACK);
            if (tempProgramName.length() > 16) {
                tempProgramName = tempProgramName.substring(0, 13) + "...";
            }
            glyph.setText(font, tempProgramName);
            if (max_width < glyph.width)
                max_width = glyph.width;
        }
        _buttonArray = new Button[_programs.size()];
        _enabledButtons = new boolean[_programs.size()];

        _buttonArrayX = Main.SCREEN_WIDTH / 3 / 2;
        _buttonArrayY = Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8 - Main.SCREEN_HEIGHT / 8;
        String temp;
        /**
         * initializing button array
         */
        for (int i = 0; i < _programs.size(); i++) {
            temp = _programs.get(i).getName();

            if (temp.length() > 16) {
                temp = temp.substring(0, 14) + "...";
            }
            _buttonArray[i] = new Button();
            _buttonArray[i].initialize(_buttonArrayX - max_width / 2,
                    (float) (_buttonArrayY - i * ((Main.SCREEN_HEIGHT / 8) * 1.5)),
                    10 + max_width, Main.SCREEN_HEIGHT / 8,
                    Textures.get_main_button_pushed(),
                    Textures.get_main_button_not_pushed(),
                    Textures.get_main_button_pushed_disabled(),
                    Textures.get_main_button_not_pushed_disabled(),
                    temp, Color.BLACK, Main._medium_font_fileHandle);
            _enabledButtons[i] = !(_buttonArray[i].get_height_position() < 0 ||
                    _buttonArray[i].get_height_position() > Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8);
        }
        initializeOther();
    }

    /**
     * initialize buttons for laundry programs
     */
    private void initializeButtons() {
/**
 * action button
 */
        float button_width = _main._status.get_status_width() / 2;
        float button_height = button_width / 2;
        _confirmProgram = new Button();
        _confirmProgram.initialize(Main.SCREEN_WIDTH - button_width - button_height / 2,
                Main.SCREEN_HEIGHT - button_height * 2,
                button_width, button_height,
                Textures.get_main_button_pushed(),
                Textures.get_main_button_not_pushed(),
                Textures.get_main_button_pushed_disabled(),
                Textures.get_main_button_not_pushed_disabled(),
                TextValues.globalValues.confirm, Color.BLACK, Main._huge_font_fileHandle);
        _confirmProgram.disable();
    }

    /**
     * initialize action buttons and group boxes
     */
    private void initializeOther() {
        _program_description = new RichTextBox(_buttonArray[0].get_width_position() + _buttonArray[0].get_width() + _confirmProgram.get_height() / 4 * 3,
                _confirmProgram.get_height_position() + _confirmProgram.get_height(),
                _confirmProgram.get_width_position() - _confirmProgram.get_height() / 4 * 3 - (_buttonArray[0].get_width_position() + _buttonArray[0].get_width() + _confirmProgram.get_height() / 4 * 3),
                _confirmProgram.get_height_position() + _confirmProgram.get_height() - (_main._status.get_height() + _confirmProgram.get_height() / 2),
                "asdf", Color.BLACK,
                Main._tiny_font_fileHandle);
        _button_groupBox = new GroupBox(_confirmProgram.get_width_position() - _confirmProgram.get_height() / 4,
                _confirmProgram.get_height_position() - _confirmProgram.get_height() / 4,
                _confirmProgram.get_width_position() + _confirmProgram.get_width() + _confirmProgram.get_height() / 4,
                _confirmProgram.get_height_position() + _confirmProgram.get_height() + _confirmProgram.get_height() / 4);
        _programs_groupBox = new GroupBox(_buttonArray[0].get_width_position() - _confirmProgram.get_height() / 4,
                _main._status.get_height(),
                _buttonArray[0].get_width_position() +
                        _buttonArray[0].get_width() + _confirmProgram.get_height() / 4,
                Main.SCREEN_HEIGHT - _main._status.get_height());
        _description_groupBox = new GroupBox(_program_description.get_width_position() - _confirmProgram.get_height() / 4,
                _program_description.get_height_position() + _confirmProgram.get_height() / 4,
                _program_description.get_width_position() + _program_description.get_width() + _confirmProgram.get_height() / 4,
                _program_description.get_height_position() - _program_description.get_height() - _confirmProgram.get_height() / 4);

        maxHeightPosition = Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8 - _buttonArray[0].get_height();
        minHeightPosition = Main.SCREEN_HEIGHT / 8;
    }

    /**
     * move buttons
     *
     * @param height - value to move
     */
    private void moveButtons(float height) {
        /**
         * check for moving buttons
         * moving check for height is not higher the highest point.
         */
        if (_buttonArray[0].get_height_position() < maxHeightPosition)
            if (height < 0)
                height = 0;
        /**
         * check for moving buttons
         * moving check for height is not lower the lowest point.
         */
        if (_buttonArray[_buttonArray.length - 1].get_height_position() > minHeightPosition)
            if (height > 0)
                height = 0;
        /**
         * moving all buttons
         */
        for (int i = 0; i < _buttonArray.length; i++) {
            _buttonArray[i].updatePosition(_buttonArray[i].get_width_position(),
                    _buttonArray[i].get_height_position() + height);
            _enabledButtons[i] = !(_buttonArray[i].get_height_position() < 0 ||
                    _buttonArray[i].get_height_position() > Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT / 8);
        }
    }

    @Override
    public void render(float delta) {
/**
 * update
 */
        if (_timer < _main._delay) _timer += delta;

        _cam.update();
/**
 * clear and draw background
 */
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /**
         *draw
         */
        draw();
    }

    /**
     * draw elements
     */
    private void draw() {
        _batch.begin();
        _main.backgroundSprite.draw(_batch);
        _button_groupBox.drawNoRect(_batch);
        _programs_groupBox.drawNoRect(_batch);
        if (_program_selected) {
            _description_groupBox.drawNoRect(_batch);

        }
        //_cam.updateString();
        for (Button a_buttonArray : _buttonArray) {
            a_buttonArray.draw(_batch);
        }


        if (_program_selected) {
            _program_description.draw(_batch);

        }
        _confirmProgram.draw(_batch);

        _main._status.draw(_batch);
        _batch.end();
        _renderer.begin(ShapeType.Line);
        _button_groupBox.drawRect(_renderer);
        _programs_groupBox.drawRect(_renderer);
        if (_program_selected) {
            _description_groupBox.drawRect(_renderer);
        }
        for (Button a_buttonArray : _buttonArray) {
            a_buttonArray.drawRect(_renderer);
        }

        _confirmProgram.drawRect(_renderer);
        _renderer.end();
        //_confirm.draw(_batch, _cam);

        _batch.begin();
        if (_buttonArray[_buttonArray.length - 1].get_height_position() < 0)
            _batch.draw(_down_arrow,
                    0,
                    _main._status.get_height() + _confirmProgram.get_height() / 4,
                    _confirmProgram.get_height() / 2,
                    _confirmProgram.get_height() / 2);
        if (_buttonArray[0].get_height_position() + _buttonArray[0].get_height() > Main.SCREEN_HEIGHT)
            _batch.draw(_up_arrow,
                    0,
                    Main.SCREEN_HEIGHT - _main._status.get_height() - _confirmProgram.get_height() / 4 - _confirmProgram.get_height() / 2,
                    _confirmProgram.get_height() / 2,
                    _confirmProgram.get_height() / 2);
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
        //if (_main._status.is_error()) return;
        //if (Gdx.input.justTouched()) {
        if (_main._status.returnButton_touched(_cam)) {
            _main.ShowLaundrySetupScreen();
        }

        if (_programs_groupBox.isTouched(_cam) && !_dragged) {
            for (int i = 0; i < _buttonArray.length; i++) {
                if (_buttonArray[i].isTouched(_cam) && _enabledButtons[i]) {

                    if ((_button_id >= 0) && _button_id < _buttonArray.length) {
                        _buttonArray[_button_id].updateState(false);
                    }
                    _buttonArray[i].updateState(true);
                    _button_id = i;
                    _program_description.updateText(_programs.get(i).getDescription());
                    _program_selected = true;
                    _confirmProgram.enable();
                    return;
                }
            }

        }
        if (_confirmProgram.isTouched(_cam)) {
            setProgram(_programs.get(_button_id));
            _main.changeScreen(_lastScreen);
        }
        disable_selection();

    }

    /**
     * disabling selection of a program
     */
    private void disable_selection() {
        if (_button_id > -1 && _button_id < _buttonArray.length) {
            _buttonArray[_button_id].updateState(false);
            _button_id = 0;
        }
        _program_selected = false;
        _confirmProgram.disable();
    }

    /**
     * handle long touch events
     */
    private void handleLongTermDepression() {
        if (_main._status.is_error()) return;


        if (_confirmProgram.isTouched(_cam)) {
            _confirmProgram.updateState(true);
        } else {
            _confirmProgram.updateState(false);
        }

    }


    @Override
    public void resize(int width, int height) {
        _cam.viewportWidth = Main.SCREEN_WIDTH;
        _cam.viewportHeight = Main.SCREEN_HEIGHT;
        _cam.update();
    }

    @Override
    public void show() {
        _main._status.updateNavigation(TextValues.navigation.programViewScreen);
        _timer = 0;
        //Gdx.input.setInputProcessor(this);
        Gdx.input.setInputProcessor(_im);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    public void dispose() {
        _im.clear();
        _up_arrow.dispose();
        _down_arrow.dispose();
        _programs = null;

        _confirmProgram.dispose();

        _buttonArray = null;
        _program_description = null;

    }

    @Override
    public void setProgram(WasherProgram program) {
        _lastScreen.setProgram(program);
    }

    @Override
    public WasherProgram getProgram() {
        return _lastScreen.getProgram();
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
                handleLongTermDepression();
                _dragged = false;
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
                _confirmProgram.updateState(false);
                _dragged = false;
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

    ////////////////////gestures
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    /**
     * drag gesture, used to move buttons
     *
     * @param x      - not used
     * @param y      - not used
     * @param deltaX - changed horizontal position
     * @param deltaY - changed vertical position
     * @return false
     */
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (!_main._status.is_error()) {
            if (_button_groupBox.isTouched(_cam)) return false;
            _dragged = true;

            disable_selection();
            moveButtons(-deltaY);

        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
