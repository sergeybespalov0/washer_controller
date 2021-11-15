package com.mygdx.game.android.Data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * class, used to load textures
 */
public class Textures {

    public static AssetManager _manager;

    private static String _empty =
            "empty.png";

    /**
     * main Strings
     */
    private static String _main_background =
            "backgrounds/main_background.png";
    private static String _confirm_message_background =
            "backgrounds/confirm_message_background.png";
    private static String _error_message_background =
            "backgrounds/error_message_background.png";
    private static String _warning_message_background =
            "backgrounds/warning_message_background.png";

    private static String _textBox_background =
            "backgrounds/textBox_background.png";
    private static String _textBox_grey_background =
            "backgrounds/textBox_grey_background.png";
    private static String _textBox_empty_background =
            "backgrounds/textBox_empty_background.png";
    private static String _textBox_wide_background =
            "backgrounds/textBox_wide_background.png";
    private static String _textBox_wide_grey_background =
            "backgrounds/textBox_wide_grey_background.png";
    private static String _navigation_background =
            "backgrounds/navigation_background.png";
    private static String _ticker_background =
            "backgrounds/ticker_background.png";
    private static String _statusBar_background =
            "backgrounds/statusBar_background.png";
    private static String _work_status_background =
            "backgrounds/work_status_background.png";
    private static String _transparent_background =
            "backgrounds/transparent_background.png";
    private static String _numericEnter_textBox_background =
            "backgrounds/numericEnter_textBox_background.png";
    private static String _table_background =
            "backgrounds/table_background.png";
    private static String _table_tint =
            "backgrounds/table_tinting.png";
    private static String _error_background =
            "backgrounds/error_message_background.png";
    private static String _checkBox_background =
            "backgrounds/checkBox_background.png";
    private static String _checkBox_disabled_background =
            "backgrounds/checkBox_disabled_background.png";


    /**
     * progress bar
     */
    private static String _progress_bar_fill_element =
            "progressBar/progress_bar_fill_element.png";
    private static String _progress_bar_font =
            "progressBar/progress_bar_font.png";
    /**
     * button Strings
     */

    private static String _green_button_not_pushed =
            "buttons/green_button_not_pushed.png";
    private static String _green_button_pushed =
            "buttons/green_button_pushed.png";
    private static String _main_button_not_pushed_darker =
            "buttons/main_button_not_pushed_darker.png";


    private static String _main_button_pushed =
            "buttons/main_button_pushed.png";
    private static String _main_button_pushed_disabled =
            "buttons/main_button_pushed_disabled.png";
    private static String _main_button_not_pushed =
            "buttons/main_button_not_pushed.png";
    private static String _main_button_not_pushed_disabled =
            "buttons/main_button_not_pushed_disabled.png";
    private static String _return_button_pushed =
            "buttons/return_button_pushed.png";
    private static String _return_button_not_pushed =
            "buttons/return_button_not_pushed.png";
    private static String _stop_button_not_pushed =
            "buttons/stop_button_not_pushed.png";
    private static String _stop_button_not_pushed_disabled =
            "buttons/stop_button_not_pushed_disabled.png";
    private static String _stop_button_pushed =
            "buttons/stop_button_pushed.png";
    private static String _stop_button_pushed_disabled =
            "buttons/stop_button_pushed_disabled.png";
    private static String _yellow_button_pushed =
            "buttons/yellow_button_pushed.png";
    private static String _yellow_button_not_pushed =
            "buttons/yellow_button_not_pushed.png";
    /**
     * keyboard Strings
     */
    private static String _register_change_not_pushed =
            "keyboard/register_change_not_pushed.png";
    private static String _register_change_pushed =
            "keyboard/register_change_pushed.png";
    private static String _erase_symbol_not_pushed =
            "keyboard/erase_symbol_not_pushed.png";
    private static String _erase_symbol_pushed =
            "keyboard/erase_symbol_pushed.png";
    /**
     * Strings for switches
     */
    private static String _switch_off =
            "switch/switch_off.png";
    private static String _switch_off_red =
            "switch/switch_off_red.png";
    private static String _switch_off_disabled =
            "switch/switch_off_disabled.png";
    private static String _switch_on =
            "switch/switch_on.png";
    private static String _switch_on_green =
            "switch/switch_on_green.png";
    private static String _switch_on_disabled =
            "switch/switch_on_disabled.png";


    /**
     * indicators
     */

    private static String _indicator_white =
            "indicators/indicator_white.png";
    private static String _indicator_green =
            "indicators/indicator_green.png";
    private static String _indicator_yellow =
            "indicators/indicator_yellow.png";
    private static String _indicator_red =
            "indicators/indicator_red.png";
    private static String _indicator_white_2 =
            "indicators/indicator_white_2.png";
    private static String _indicator_green_2 =
            "indicators/indicator_green_2.png";
    private static String _indicator_yellow_2 =
            "indicators/indicator_yellow_2.png";
    private static String _indicator_red_2 =
            "indicators/indicator_red_2.png";
    private static String _connected_status =
            "indicators/connected_status.png";
    private static String _disconnected_status =
            "indicators/disconnected_status.png";
    private static String _water_flow_active =
            "indicators/water_flow_active.png";
    private static String _water_flow_inactive =
            "indicators/water_flow_inactive.png";
    private static String _heat_active =
            "indicators/heat_active.png";
    private static String _heat_inactive =
            "indicators/heat_inactive.png";


    private static String _arrow =
            "indicators/arrow.png";
    private static String _arrow_up =
            "indicators/arrow_up.png";
    private static String _arrow_down =
            "indicators/arrow_down.png";
    private static String _check_mark =
            "indicators/check_mark.png";

    public static void load() {
        _manager = new AssetManager();
        _manager.load(_empty, Texture.class);
        _manager.load(_main_background, Texture.class);
        _manager.load(_confirm_message_background, Texture.class);
        _manager.load(_error_message_background, Texture.class);
        _manager.load(_warning_message_background, Texture.class);
        _manager.load(_textBox_background, Texture.class);
        _manager.load(_textBox_grey_background, Texture.class);
        _manager.load(_textBox_empty_background, Texture.class);
        _manager.load(_textBox_wide_background, Texture.class);
        _manager.load(_textBox_wide_grey_background, Texture.class);
        _manager.load(_navigation_background, Texture.class);
        _manager.load(_ticker_background, Texture.class);
        _manager.load(_statusBar_background, Texture.class);
        _manager.load(_work_status_background, Texture.class);
        _manager.load(_transparent_background, Texture.class);
        _manager.load(_numericEnter_textBox_background, Texture.class);
        _manager.load(_table_background, Texture.class);
        _manager.load(_table_tint, Texture.class);
        _manager.load(_error_background, Texture.class);
        _manager.load(_checkBox_background, Texture.class);
        _manager.load(_checkBox_disabled_background, Texture.class);

        _manager.load(_progress_bar_fill_element, Texture.class);
        _manager.load(_progress_bar_font, Texture.class);

        _manager.load(_green_button_not_pushed, Texture.class);
        _manager.load(_green_button_pushed, Texture.class);
        _manager.load(_main_button_not_pushed_darker, Texture.class);
        _manager.load(_main_button_pushed, Texture.class);
        _manager.load(_main_button_pushed_disabled, Texture.class);
        _manager.load(_main_button_not_pushed, Texture.class);
        _manager.load(_main_button_not_pushed_disabled, Texture.class);
        _manager.load(_return_button_pushed, Texture.class);
        _manager.load(_return_button_not_pushed, Texture.class);
        _manager.load(_stop_button_not_pushed, Texture.class);
        _manager.load(_stop_button_not_pushed_disabled, Texture.class);
        _manager.load(_stop_button_pushed, Texture.class);
        _manager.load(_stop_button_pushed_disabled, Texture.class);
        _manager.load(_yellow_button_pushed, Texture.class);
        _manager.load(_yellow_button_not_pushed, Texture.class);

        _manager.load(_register_change_not_pushed, Texture.class);
        _manager.load(_register_change_pushed, Texture.class);
        _manager.load(_erase_symbol_not_pushed, Texture.class);
        _manager.load(_erase_symbol_pushed, Texture.class);
        _manager.load(_switch_off, Texture.class);
        _manager.load(_switch_off_red, Texture.class);
        _manager.load(_switch_off_disabled, Texture.class);
        _manager.load(_switch_on, Texture.class);
        _manager.load(_switch_on_green, Texture.class);
        _manager.load(_switch_on_disabled, Texture.class);

        _manager.load(_indicator_white, Texture.class);
        _manager.load(_indicator_green, Texture.class);
        _manager.load(_indicator_red, Texture.class);
        _manager.load(_indicator_yellow, Texture.class);
        _manager.load(_indicator_white_2, Texture.class);
        _manager.load(_indicator_green_2, Texture.class);
        _manager.load(_indicator_red_2, Texture.class);
        _manager.load(_indicator_yellow_2, Texture.class);
        _manager.load(_connected_status, Texture.class);
        _manager.load(_disconnected_status, Texture.class);
        _manager.load(_water_flow_active, Texture.class);
        _manager.load(_water_flow_inactive, Texture.class);
        _manager.load(_heat_active, Texture.class);
        _manager.load(_heat_inactive, Texture.class);
        _manager.load(_arrow, Texture.class);
        _manager.load(_arrow_up, Texture.class);
        _manager.load(_arrow_down, Texture.class);
        _manager.load(_check_mark, Texture.class);


        //noinspection StatementWithEmptyBody
        while (!_manager.update()) {
            //

        }
        System.out.println("Loaded: " + (int) (_manager.getProgress() * 100) + "%");
    }

    public static Boolean isLoaded() {
        return _manager.getProgress() >= 1;
    }

    public static void dispose() {
        _manager.dispose();
        _manager = null;
    }

    //////////////////
    public static Texture get_empty() {
        return _manager.get(_empty);
    }

    public static Texture get_textBox_background() {
        return _manager.get(_textBox_background);
    }

    public static Texture get_textBox_grey_background() {
        return _manager.get(_textBox_grey_background);
    }

    public static Texture get_textBox_empty_background() {
        return _manager.get(_textBox_empty_background);
    }

    public static Texture get_main_background() {
        return _manager.get(_main_background);
    }

    public static Texture get_confirm_message_background() {
        return _manager.get(_confirm_message_background);
    }

    public static Texture get_navigation_background() {
        return _manager.get(_navigation_background);
    }

    public static Texture get_main_button_not_pushed() {
        return _manager.get(_main_button_not_pushed);
    }

    public static Texture get_main_button_not_pushed_disabled() {
        return _manager.get(_main_button_not_pushed_disabled);
    }

    public static Texture get_return_button_pushed() {
        return _manager.get(_return_button_pushed);
    }

    public static Texture get_return_button_not_pushed() {
        return _manager.get(_return_button_not_pushed);
    }

    public static Texture get_switch_off() {
        return _manager.get(_switch_off);
    }

    public static Texture get_switch_off_disabled() {
        return _manager.get(_switch_off_disabled);
    }

    public static Texture get_switch_on() {
        return _manager.get(_switch_on);
    }

    public static Texture get_switch_on_disabled() {
        return _manager.get(_switch_on_disabled);
    }

    public static Texture get_indicator_white() {
        return _manager.get(_indicator_white);
    }

    public static Texture get_indicator_green() {
        return _manager.get(_indicator_green);
    }

    public static Texture get_indicator_yellow() {
        return _manager.get(_indicator_yellow);
    }

    public static Texture get_indicator_red() {
        return _manager.get(_indicator_red);
    }

    public static Texture get_ticker_background() {
        return _manager.get(_ticker_background);
    }

    public static Texture get_connected_status() {
        return _manager.get(_connected_status);
    }

    public static Texture get_disconnected_status() {
        return _manager.get(_disconnected_status);
    }

    public static Texture get_statusBar_background() {
        return _manager.get(_statusBar_background);
    }

    public static Texture get_work_status_background() {
        return _manager.get(_work_status_background);
    }

    public static Texture get_stop_button_not_pushed_disabled() {
        return _manager.get(_stop_button_not_pushed_disabled);
    }

    public static Texture get_stop_button_not_pushed() {
        return _manager.get(_stop_button_not_pushed);
    }

    public static Texture get_stop_button_pushed() {
        return _manager.get(_stop_button_pushed);
    }

    public static Texture get_stop_button_pushed_disabled() {
        return _manager.get(_stop_button_pushed_disabled);
    }

    public static Texture get_yellow_button_pushed() {
        return _manager.get(_yellow_button_pushed);
    }

    public static Texture get_yellow_button_not_pushed() {
        return _manager.get(_yellow_button_not_pushed);
    }

    public static Texture get_textBox_wide_background() {
        return _manager.get(_textBox_wide_background);
    }

    public static Texture get_textBox_wide_grey_background() {
        return _manager.get(_textBox_wide_grey_background);
    }

    public static Texture get_progress_bar_fill_element() {
        return _manager.get(_progress_bar_fill_element);
    }

    public static Texture get_transparent_background() {
        return _manager.get(_transparent_background);
    }

    public static Texture get_progress_bar_font() {
        return _manager.get(_progress_bar_font);
    }

    public static Texture get_numericEnter_textBox_background() {
        return _manager.get(_numericEnter_textBox_background);
    }

    public static Texture get_main_button_pushed() {
        return _manager.get(_main_button_pushed);
    }


    public static Texture get_main_button_pushed_disabled() {
        return _manager.get(_main_button_pushed_disabled);
    }

    public static Texture get_green_button_not_pushed() {
        return _manager.get(_green_button_not_pushed);
    }

    public static Texture get_table_background() {
        return _manager.get(_table_background);
    }

    public static Texture get_table_tint() {
        return _manager.get(_table_tint);
    }

    public static Texture get_error_background() {
        return _manager.get(_error_background);
    }

    public static Texture get_arrow() {
        return _manager.get(_arrow);
    }

    public static Texture get_arrow_up() {
        return _manager.get(_arrow_up);
    }

    public static Texture get_arrow_down() {
        return _manager.get(_arrow_down);
    }

    public static Texture get_check_mark() {
        return _manager.get(_check_mark);
    }

    public static Texture get_water_flow_active() {
        return _manager.get(_water_flow_active);
    }

    public static Texture get_water_flow_inactive() {
        return _manager.get(_water_flow_inactive);
    }

    public static Texture get_heat_active() {
        return _manager.get(_heat_active);
    }

    public static Texture get_heat_inactive() {
        return _manager.get(_heat_inactive);
    }

    public static Texture get_main_button_not_pushed_darker() {
        return _manager.get(_main_button_not_pushed_darker);
    }

    public static Texture get_register_change_not_pushed() {
        return _manager.get(_register_change_not_pushed);
    }

    public static Texture get_register_change_pushed() {
        return _manager.get(_register_change_pushed);
    }

    public static Texture get_erase_symbol_not_pushed() {
        return _manager.get(_erase_symbol_not_pushed);
    }

    public static Texture get_erase_symbol_pushed() {
        return _manager.get(_erase_symbol_pushed);
    }

    public static Texture get_indicator_white_2() {
        return _manager.get(_indicator_white_2);
    }

    public static Texture get_indicator_green_2() {
        return _manager.get(_indicator_green_2);
    }

    public static Texture get_indicator_yellow_2() {
        return _manager.get(_indicator_yellow_2);
    }

    public static Texture get_indicator_red_2() {
        return _manager.get(_indicator_red_2);
    }

    public static Texture get_checkBox_background() {
        return _manager.get(_checkBox_background);
    }

    public static Texture get_checkBox_disabled_background() {
        return _manager.get(_checkBox_disabled_background);
    }

    public static Texture get_switch_off_red() {
        return _manager.get(_switch_off_red);
    }

    public static Texture get_switch_on_green() {
        return _manager.get(_switch_on_green);
    }

    public static Texture get_error_message_background() {
        return _manager.get(_error_message_background);
    }

    public static Texture get_warning_message_background() {
        return _manager.get(_warning_message_background);
    }

    public static Texture get_green_button_pushed() {
        return _manager.get(_green_button_pushed);
    }
}
