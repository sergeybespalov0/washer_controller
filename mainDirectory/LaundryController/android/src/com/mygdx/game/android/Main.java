package com.mygdx.game.android;

import android.content.res.Resources;
import android.os.Environment;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.android.Application.Controller;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Elements.StatusBar;
import com.mygdx.game.android.Interfaces.IDataTransfer;
import com.mygdx.game.android.Interfaces.IProgramTransfer;
import com.mygdx.game.android.Screens.ConnectionSetupScreen;
import com.mygdx.game.android.Screens.EnterIpScreen;
import com.mygdx.game.android.Screens.EnterPortScreen;
import com.mygdx.game.android.Screens.HelpScreen;
import com.mygdx.game.android.Screens.KeyboardEnterScreen;
import com.mygdx.game.android.Screens.LaundryProcessScreen;
import com.mygdx.game.android.Screens.LaundrySetupScreen;
import com.mygdx.game.android.Screens.LoadingScreen;
import com.mygdx.game.android.Screens.LoginScreen;
import com.mygdx.game.android.Screens.NumericEnterScreen;
import com.mygdx.game.android.Screens.PasswordEnterScreen;
import com.mygdx.game.android.Screens.PrimaryScreen;
import com.mygdx.game.android.Screens.ProgramsViewScreen;
import com.mygdx.game.android.Screens.ServiceScreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * See [LoginScreen] class in order to see description of the implementation's,
 * mostly used in all screen classes.
 */
public class Main extends Game  {
    /**
     * pattern, used to check string for correct ip address
     */
    private static final Pattern IP_PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    /**
     * pattern, used to check string for correct username
     */
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[a-zA-Zа-яА-Я'][a-zA-Zа-яА-Я-' ]+[a-zA-Zа-яА-Я']?$");
    /*private CameraActivity1 _camera = new CameraActivity1();

    @Override
    public void onCreateCamera(Bundle savedInstanceState) {

        _camera.onCreate(savedInstanceState);
    }

    @Override
    public void takePhoto() {
        _camera.takePhoto();
    }*/


    /**
     * [State] is defined to use in our program
     */
    public enum State {
        PAUSE,
        RUN,
        RESUME,
        STOPPED
    }

    private State state = State.RUN;

    /**
     * define default values for screen resolution (width and height)
     */
    public static float SCREEN_WIDTH = 1280;
    public static float SCREEN_HEIGHT = 800;
    /**
     * define 4 font files
     */
    public static BitmapFont _tiny_font;
    public static BitmapFont _little_font;
    public static BitmapFont _medium_font;
    public static BitmapFont _huge_font;
    /**
     * define FileHandle's for 4 font files
     */
    public static FileHandle _tiny_font_fileHandle;
    public static FileHandle _little_font_fileHandle;
    public static FileHandle _medium_font_fileHandle;
    public static FileHandle _huge_font_fileHandle;
    /**
     * define FileHandle for connection info (ip, port and username)
     */
    private static File _connectionInfoFile;
    /**
     * initialize colors for text
     */
    public final Color _button_textColor = new Color(Color.BLACK);
    /**
     * initialize user name variable
     */
    private static String _username = "";
    private String _fileName = "info" + ".txt";
    /**
     * Define OrthographicCamera, which used in 2D environments only as it implements
     * a parallel (orthographic) projection and there will be no scale factor for the final image
     * regardless where the objects are placed in the world.
     */
    private OrthographicCamera _cam;
    /**
     * Define SpriteBatch, which takes care of all the steps needed to achieve texture mapping
     * and displaying texture mapped rectangles on the screen. It is a convenience class which makes
     * drawing onto the screen extremely easy and it is also optimised.
     */
    private SpriteBatch _batch;
    /**
     * Define ShapeRenderer, which renders points, lines, shape outlines and filled shapes.
     * Used to draw rectangles around elements.
     */
    private ShapeRenderer _renderer;

    /**
     * define screens
     */

    private LoginScreen _loginScreen;
    private LoadingScreen _loadingScreen;

    private PrimaryScreen _primaryScreen;
    private LaundrySetupScreen _laundrySetupScreen;
    private LaundryProcessScreen _laundryProcessScreen;
    private ServiceScreen _serviceScreen;
    private HelpScreen _helpScreen;
    private ConnectionSetupScreen _connectionScreen;

    public final double _delay = 0.3;
    /**
     * Controller handles connection, communication, state, etc.
     */
    public Controller _controller = new Controller();

    private NumericEnterScreen _numericEnterScreen;
    private ProgramsViewScreen _programsViewScreen;
    private PasswordEnterScreen _passwordEnterScreen;
    private KeyboardEnterScreen _keyboardEnterScreen;
    private EnterIpScreen _enterIpScreen;
    private EnterPortScreen _enterPortScreen;

    /**
     * Define StatusBar, which is used to show washing machine isShowed,
     * stop wash if necessary and show current menu name
     */
    public StatusBar _status;
    /**
     * Define backgroundTexture, which will be used as background on all screens
     * Define backgroundSprite, which will draw backgroundTexture
     */
    //private Texture backgroundTexture;
    public Sprite backgroundSprite;
    /**
     * flag, which used to show loading screen after initialization start
     */
    private boolean _initialized = false;
    /**
     * flag, which used to determine, if user is logged in
     */
    private boolean _logged_in = false;

    private float _time = 0;

    @Override
    public void create() {
        /**
         * initialize screen resolution and show it in system log
         */


        if (!"U30GT-H".equals(android.os.Build.MODEL)) {
            SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
            SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
        }
            /*System.out.println("|||||||||NOT STANDARD TABLET||||||||||||||||||||||||");
        } else {
            System.out.println("|||||||||STANDARD TABLET||||||||||||||||||||||||");
        }*/
        System.out.println(SCREEN_WIDTH + "x" + SCREEN_HEIGHT);

        /**
         * initialize FileHandle's for font's, depending on screen resolution
         */
        if ((SCREEN_WIDTH > 1000) && (SCREEN_WIDTH < 2000)) {
            _tiny_font_fileHandle = Gdx.files.internal("fonts/segoe_ui_28.bmf");
            _little_font_fileHandle = Gdx.files.internal("fonts/segoe_ui_32.bmf");
            _medium_font_fileHandle = Gdx.files.internal("fonts/segoe_ui_36.bmf");
            _huge_font_fileHandle = Gdx.files.internal("fonts/segoe_ui_40.bmf");
            //System.out.println("Medium fonts");
        } else {
            if (SCREEN_WIDTH < 1000) {
                _tiny_font_fileHandle = Gdx.files.internal("fonts/segoe_12.bmf");
                _little_font_fileHandle = Gdx.files.internal("fonts/segoe_14.bmf");
                _medium_font_fileHandle = Gdx.files.internal("fonts/segoe_16.bmf");
                _huge_font_fileHandle = Gdx.files.internal("fonts/segoe_18.bmf");
                //System.out.println("Little fonts");
            } else {
                _tiny_font_fileHandle = Gdx.files.internal("fonts/segoe_54.bmf");
                _little_font_fileHandle = Gdx.files.internal("fonts/segoe_58.bmf");
                _medium_font_fileHandle = Gdx.files.internal("fonts/segoe_62.bmf");
                _huge_font_fileHandle = Gdx.files.internal("fonts/segoe_66.bmf");
                //System.out.println("Huge fonts");
            }
        }
        /**
         * SpriteBatch initialization
         */
        _batch = new SpriteBatch();
        /**
         * OrthographicCamera initialization with pre-defined screen resolution
         */
        _cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        _cam.position.set(_cam.viewportWidth / 2f, _cam.viewportHeight / 2f, 0);
        _cam.update();
        /**
         * Sets the projection matrix to be used by this Batch.
         */
        _batch.setProjectionMatrix(_cam.combined);

        /**
         * initialize loading screen before main initialization, and switch to it
         */
        _loadingScreen = new LoadingScreen(_batch, _cam);
        changeScreen(_loadingScreen);
        /**
         * this flag is used to go to SecondaryInitialization in render cycle
         */
        _initialized = false;
        //System.out.println("Main initialization completed");
    }

    /**
     * used immediately after main initialization and showing loading screen
     */
    private void SecondaryInitialization() {
        /**
         * initialize and load textures
         */
        //System.out.println("Loading textures");
        Textures.load();

        /**
         * initialize ShapeRenderer
         */
        _renderer = new ShapeRenderer();
        _renderer.setProjectionMatrix(_cam.combined);
        _renderer.setColor(Color.BLACK);
        /**
         * initialize font's with FileHandle's
         */
        _tiny_font = new BitmapFont(_tiny_font_fileHandle);
        _little_font = new BitmapFont(_little_font_fileHandle);
        _medium_font = new BitmapFont(_medium_font_fileHandle);
        _huge_font = new BitmapFont(_huge_font_fileHandle);
        /**
         * initialize backgroundTexture and backgroundSprite
         */
        //backgroundTexture = Textures.get_main_background();
        backgroundSprite = new Sprite(Textures.get_main_background());
        backgroundSprite.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        /**
         * initialize StatusBar
         */
        _status = new StatusBar(this, _cam);
        //System.out.println("fonts and isShowed bar initialized");
        /**
         * initialize FileHandle for connection info file and read data from it (if it exists)
         */
        //System.out.println("Reading from file");

        _connectionInfoFile = new File(Environment.getExternalStorageDirectory().toString() + "/connection");
        System.out.println(_connectionInfoFile.getPath());
        _connectionInfoFile.mkdirs();

        readFromFile();
        //System.out.println("Reading completed");
        /**
         * initialize screens
         */
        //System.out.println("Loading all screens");
        _loginScreen = new LoginScreen(this, _batch, _renderer, _cam);

        _primaryScreen = new PrimaryScreen(this, _batch, _renderer, _cam);
        _helpScreen = new HelpScreen(this, _batch, _renderer, _cam);
        _programsViewScreen = new ProgramsViewScreen(this, _batch, _renderer, _cam);

        _connectionScreen = new ConnectionSetupScreen(this, _batch, _renderer, _cam);

        _enterIpScreen = new EnterIpScreen(this, _batch, _renderer, _cam);
        _enterPortScreen = new EnterPortScreen(this, _batch, _renderer, _cam);
        _numericEnterScreen = new NumericEnterScreen(this, _batch, _renderer, _cam);
        _passwordEnterScreen = new PasswordEnterScreen(this, _batch, _renderer, _cam);
        _laundrySetupScreen = new LaundrySetupScreen(this, _batch, _renderer, _cam);
        _laundryProcessScreen = new LaundryProcessScreen(this, _batch, _renderer, _cam);
        _serviceScreen = new ServiceScreen(this, _batch, _renderer, _cam);
        _keyboardEnterScreen = new KeyboardEnterScreen(this, _batch, _renderer, _cam);
        //System.out.println("Screens loaded");

        readFromFile();
        /**
         * show screen depend on connection data
         */
        if (_logged_in) {
            changeScreen(_primaryScreen);
        } else {
            changeScreen(_loginScreen);
        }
        /**
         * start controller
         */
        try {
            _controller.start();
        } catch (Exception ex) {
            ex.getMessage();
        }
        _initialized = true;
    }

    /**
     * used to enter ip-address of a server
     *
     * @param invoker- is a class which implements Screen, used to return data to screen,
     *                 which caused him and return to this screen
     */
    public void EnterIP(IDataTransfer invoker) {
        _enterIpScreen.Initialize(invoker);
        changeScreen(_enterIpScreen);
    }

    /**
     * used to enter port of a server
     *
     * @param invoker- is a class which implements Screen, used to return data to screen,
     *                 which caused him and return to this screen
     */
    public void EnteringPort(IDataTransfer invoker) {
        _enterPortScreen.Initialize(invoker);
        changeScreen(_enterPortScreen);
    }

    /**
     * this method used to switch to numeric enter screen to enter int data
     * and return in to the laundry screen
     *
     * @param invoker          - is a class which implements Screen, used to return data to screen,
     *                         which caused him and return to this screen
     * @param hint_text_String - is a text string, which hints user, what he need to enter
     */
    public void EnteringWeight(IDataTransfer invoker, String hint_text_String) {
        //System.out.println("-------------------WEIGHT");
        _numericEnterScreen.Initialize(invoker, hint_text_String, 0, 21, 2);
        changeScreen(_numericEnterScreen);

    }

    /**
     * this method used to switch to numeric enter screen to enter password
     * and return to the previous screen
     *
     * @param invoker          - is a class which implements Screen, used to return data to screen,
     *                         which caused him and return to this screen
     * @param hint_text_String - is a text string, which hints user, what he need to enter
     */
    public void ShowPasswordEnterScreen(IDataTransfer invoker, String hint_text_String) {
        _passwordEnterScreen.Initialize(invoker, hint_text_String, 0, 10000, 6);
        changeScreen(_passwordEnterScreen);
    }

    /**
     * this method used to switch to numeric enter screen to enter password
     * and return to the previous screen
     *
     * @param invoker          - is a class which implements Screen, used to return data to screen,
     *                         which caused him and return to this screen
     * @param hint_text_String - is a text string, which hints user, what he need to enter
     */
    public void ShowTextEnterScreen(IDataTransfer invoker, String hint_text_String, int length) {
        _keyboardEnterScreen.Initialize(invoker, hint_text_String, length);
        changeScreen(_keyboardEnterScreen);
    }

    /**
     * this method used to switch to numeric enter screen to enter int data
     * and return in to the laundry screen
     *
     * @param invoker          - is a class which implements Screen, used to return data to screen,
     *                         which caused him and return to this screen
     * @param hint_text_string - is a text string, which hints user, what he need to enter
     */
    public void EnteringClientID(IDataTransfer invoker, String hint_text_string) {
        //System.out.println("-----------------------CLIENT ID");
        _numericEnterScreen.Initialize(invoker, hint_text_string, 0, 1001, 4);
        changeScreen(_numericEnterScreen);
    }

    /**
     * this method used to switch to screen, which allows user to choose laundry program,
     * he want's to use
     *
     * @param invoker - is a class which implements Screen, used to return data to screen,
     *                which caused him and return to this screen
     */
    public void ShowLaundryProgramSelectionScreen(IProgramTransfer invoker) {
        _programsViewScreen.Initialize(invoker, _controller.getPrograms());
        changeScreen(_programsViewScreen);
    }

    /**
     * this method used to switch screen on laundry set up screen, which allows user to enter
     * initial data to start a wash
     */
    public void ShowLaundrySetupScreen() {
        //System.out.println(_controller.getPrograms().size() + "<------------count of programs");
        if (_controller.isConnected()) _controller.requestProgramsUpdate();
        setScreen(_laundrySetupScreen);
    }

    /**
     * this method used to return to primary screen
     */
    public void ShowPrimaryScreen() {
        setScreen(_primaryScreen);
    }

    /**
     * show connection setup screen
     */
    public void ShowConnectionScreen() {
        setScreen(_connectionScreen);
    }

    /**
     * go to screen, which views isShowed of laundry process
     */
    public void ShowLaundryProcessScreen() {
        setScreen(_laundryProcessScreen);
    }

    /**
     * this method used to go to help screen
     */
    public void ShowHelpScreen() {
        setScreen(_helpScreen);
    }

    /**
     * this method used to go to additional _serviceScreen screen
     */
    public void ShowServiceScreen() {
        setScreen(_serviceScreen);
    }

    /**
     * reset laundry setup data and go to primary screen
     */
    public void resetDataOnEmergency() {
        _laundrySetupScreen.resetData();
        ShowPrimaryScreen();
    }

    /**
     * @return true if program is connected to server
     */
    public boolean is_Connected() {
        return _controller.isConnected();
    }

    /**
     * check string for name
     *
     * @param name - name
     * @return true, if it's OK
     */
    public static boolean CheckForName(final String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    /**
     * check string for ip-address
     *
     * @param ip - ip-address
     * @return true, if it's OK
     */
    public static boolean CheckForIP(final String ip) {
        return IP_PATTERN.matcher(ip).matches();
    }

    /**
     * check string for port
     *
     * @param port - port
     * @return true, if it's OK
     */
    public static boolean CheckForPort(String port) {
        return !port.equals("") && ((Integer.valueOf(port) > 1024) && (Integer.valueOf(port) < 9999));
    }

    /**
     * reset laundry setup data,
     * used to drop settings each time wash is completed
     */
    public void resetLaundrySetupData() {
        _laundrySetupScreen.resetData();
    }

    /**
     * read from local storage connection data if it exists, or create new file
     */
    private void readFromFile() {

        String pathToInfo = _connectionInfoFile.getPath() + "/" + _fileName;


        File file = new File(_connectionInfoFile, _fileName);

        if (!file.exists()) {
            System.out.println("File not exists");
            return;
        }


        String[] temp = new String[0];
        try {
            temp = getStringFromFile(pathToInfo).split("\n");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        String[] loginData = temp[0].split("=");
        if (loginData[0].equals(TextValues.globalValues.name) && Main.CheckForName(loginData[1])) {
            _username = loginData[1];
        } else {
            _logged_in = false;
            return;
        }
        if (Main.CheckForIP(temp[1])) {
            setClientConnectionData(temp[1], get_port());
        } else {
            _logged_in = false;
            return;
        }
        if (Main.CheckForPort(temp[2])) {
            setClientConnectionData(get_ip(), Integer.valueOf(temp[2]));
        } else {
            _logged_in = false;
            return;
        }
        _logged_in = true;
    }


    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    /**
     * write connection data and username to local storage file
     *
     * @param username - name of user
     * @param ip       - last ip address, which is set up in program
     * @param port     - last port, which is set up in program
     */
    public void writeToFile(String username, String ip, String port) {
        /**
         * same as standard writing to file, except setting a username
         */
        _username = username;
        writeToFile(ip, port);
    }

    /**
     * write connection data to local storage file
     *
     * @param ip   - last ip address, used in program
     * @param port - last port, used in program
     */
    public void writeToFile(String ip, String port) {

        /**
         * define the file name and create it if file doesn't exists
         */
        File file = new File(_connectionInfoFile, _fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("==========cannot create file==========");
            }
            return;
        } else {
            file.delete();

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                //System.out.println("==========cannot create file==========");
            }
        }

        /**
         * initialize fileWriter, which allows us append text into file
         */
        FileWriter out = null;
        try {
            out = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            /**
             * write data into file
             */
            out.write(TextValues.globalValues.name + "=" + _username + "\n");
            out.write(ip + "\n");
            out.write(port + "\n");
            /**
             * close file
             */
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete file with last remembered connection settings
     */
    public void deleteConnectionInfo() {
        File file = new File(_connectionInfoFile, _fileName);
        System.out.println("deleting file");
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * set client connection data
     *
     * @param ip   - ip address
     * @param port - port
     */
    public void setClientConnectionData(String ip, int port) {
        _controller.setAddress(ip, port);
    }

    /**
     * start connection attempt
     */
    public void startClient() {
        _controller.restart();
    }

    /**
     * stop connection attempt
     */
    public void stopClient() {
        _controller.stop();
    }

    /**
     * @return true if last touched coordinates hits the rectangle
     */
    public boolean IsTouched(float width_position, float height_position, float width, float height) {
        if (_cam == null)
            throw new NullPointerException("Transmitted parameters can't be null!");

        Vector3 _TouchedCoordinates = _cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return ((_TouchedCoordinates.y < (height_position + height)) && (_TouchedCoordinates.y > (height_position)))
                &&
                ((_TouchedCoordinates.x > width_position) && (_TouchedCoordinates.x < width_position + width));
    }

    @Override
    public void render() {
        /**
         * this is realization of states, which allows to pause, and resume the program
         */
        switch (state) {
            case RUN:
                /**
                 * this is global renderer for all screens
                 */
                super.render();
                /**
                 * delay, which reduces CPU load
                 */
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * this used to show loading screen before starting to load
                 */
                if (!_initialized && _time > 1) {
                    SecondaryInitialization();
                } else {
                    _time += Gdx.graphics.getDeltaTime();
                }
                break;
            case PAUSE:

                break;
            case RESUME:
                /**
                 * check if textures are loaded, and load them if not
                 */
                try {
                    Textures._manager.finishLoading();
                } catch (Exception ex) {
                    ex.getMessage();
                }
                setGameState(State.RUN);
                break;
            default:
                break;
        }
    }

    /**
     * method used to switch screen to
     *
     * @param screen - screen to switch to
     */
    public void changeScreen(Screen screen) {
        setScreen(screen);
    }

    @Override
    public void resize(int width, int height) {
        _cam.viewportWidth = SCREEN_WIDTH;
        _cam.viewportHeight = SCREEN_HEIGHT;
        _cam.update();
    }

    @Override
    public void resume() {
        this.state = State.RESUME;
    }

    @Override
    public void dispose() {
        Textures.dispose();
        _status.dispose();
        _controller.stop();
        _primaryScreen.dispose();
        _laundrySetupScreen.dispose();
        _laundryProcessScreen.dispose();
        _numericEnterScreen.dispose();
        _loginScreen.dispose();
        _helpScreen.dispose();
        _programsViewScreen.dispose();
        _connectionScreen.dispose();
        _enterIpScreen.dispose();
        _enterPortScreen.dispose();
        _numericEnterScreen.dispose();
        _passwordEnterScreen.dispose();
        _serviceScreen.dispose();
        _keyboardEnterScreen.dispose();
        _loadingScreen.dispose();
        _batch.dispose();
        _renderer.dispose();
    }

    @Override
    public void pause() {
        this.state = State.PAUSE;
    }

    /**
     * used to change state (pause, resume e.t.c.)
     *
     * @param s - new state
     */
    private void setGameState(State s) {
        this.state = s;
    }

    /**
     * @return current server ip-address to connect to
     */
    public String get_ip() {
        return _controller.getHost();
    }

    /**
     * @return current server port to connect to
     */
    public int get_port() {
        return _controller.getPort();
    }

    /**
     * @return current user name
     */
    public static String get_username() {
        return _username;
    }

    /**
     * set up new username
     *
     * @param _username - new user name
     */
    public static void set_username(String _username) {
        Main._username = _username;
    }

    ///////DEPRECATED
    /*public LaundryProcess get_laundryProcess() {
        return _laundryProcess;
    }

    public boolean checkForCurrentScreen(Screen screen) {
        return getScreen().equals(screen);
    }
    */

    /**
     * check if current screen is laundry process screen
     *
     * @return true, if current screen is laundry process screen
     */
    public boolean checkForLaundryProcessScreen() {
        return getScreen().equals(_laundryProcessScreen);
    }

    /**
     * restart program
     */
    public void Restart() {
        /**
         * changing screen to loading screen for user convenience
         */
        changeScreen(_loadingScreen);
        float delay = 1; // seconds
        /**
         * dispose and initialize current class
         */
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                dispose();
                create();
            }
        }, delay);

    }

    /**
     * search program by ID
     *
     * @param id - id of program
     * @return program name, else return null
     */
    public String get_program_name(int id) {
        /**
         * returning null if program array is empty
         */
        if (_controller.getProgramsAmount() == 0) return null;
        /**
         * creating existence flag
         */
        boolean exist = false;
        try {
            /**
             * search program by id in programs array
             */
            int i;
            for (i = 0; i < _controller.getPrograms().size(); i++) {
                if (_controller.getPrograms().get(i).getId() == id) {
                    exist = true;
                    break;
                }
            }
            /**
             * return program name if program with given id exists
             */
            if (exist) {
                return _controller.getPrograms().get(i).getName();
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }

    /**
     * search program by ID
     *
     * @param id - id of program
     * @return program name, else return null
     */
    public String get_program_GroupStage(String programName, int id) {
        if (_controller.getProgramsAmount() == 0) {
            System.out.println("No programs to find current stage group name!");
            return null;
        }
        String name = null;
        /**
         * search program by name and id in programs array
         */
        for (int i = 0; i < _controller.getPrograms().size(); i++) {
            if (_controller.getPrograms().get(i).getName().equals(programName)) {
                name = _controller.getPrograms().get(i).getStageGroups().get(_controller.getPrograms().get(i).getStages().get(id).getStageGroup() - 1);
                break;
            }
        }

        return name;
    }
}