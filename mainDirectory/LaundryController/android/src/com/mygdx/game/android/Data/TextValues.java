package com.mygdx.game.android.Data;


public class TextValues {
    /**
     * string values for each massive class, which uses it's own strings
     * they named as their classes for comfort
     */

    public static final class helpScreenData {
        //////ATTENTION!!! watch comments below
        /**
         * each description sentence must start with double spaces - "  " and finishes with space and endOfLine symbol - " \n"
         */
        public static String desc_for_laundrySetupScreen =
                "  Для начала стирки требуется ввести вес белья(от 1 до 20 КГ), ввести номер клиента и выбрать программу стирки из доступных. \n";
        public static String desc_for_programUpdate =
                "  Программы на экране настройки стирки загрузятся сразу при подключении к блоку сбора данных. \n";
        public static String desc_for_ServiceScreen =
                "  Вход в меню сервиса и настроек соединения доступен только после ввода пароля. \n" +
                        "                       ВНИМАНИЕ! \n" + "  ПОЛЬЗОВАНИЕ СЕРВИСНЫМ МЕНЮ НЕ РЕКОМЕНДУЕТСЯ! \n";
        public static String desc_for_connectionSetupScreen =
                "  На экране настроек соединения требуется ввести ip-аддрес и порт в соответствующем для них виде.";
    }

    public static final class keyboardEnterButtons {
        /**
         * this data used to append into string, user writes.
         * DO NOT write here uppercase letters, user could be confused
         */
        public static String button_q = "й";
        public static String button_w = "ц";
        public static String button_e = "у";
        public static String button_r = "к";
        public static String button_t = "е";
        public static String button_y = "н";
        public static String button_u = "г";
        public static String button_i = "ш";
        public static String button_o = "щ";
        public static String button_p = "з";
        public static String button_LeftBracket = "х";

        public static String button_a = "ф";
        public static String button_s = "ы";
        public static String button_d = "в";
        public static String button_f = "а";
        public static String button_g = "п";
        public static String button_h = "р";
        public static String button_j = "о";
        public static String button_k = "л";
        public static String button_l = "д";
        public static String button_colon = "ж";
        public static String button_quotes = "э";

        public static String button_z = "я";
        public static String button_x = "ч";
        public static String button_c = "с";
        public static String button_v = "м";
        public static String button_b = "и";
        public static String button_n = "т";
        public static String button_m = "ь";
        public static String button_LeftAngleBracket = "б";
        public static String button_RightAngleBracket = "ю";
        public static String space = "Пробел";
    }

    public static final class laundryProcess {
        public static String weight = "Вес: ";
        public static String clientID = "Клиент: ";
        public static String programName = "Программа: ";
        public static String temperature = "Температура: ";
        public static String currentStageGroup = "Этап: ";
        public static String waterConsumption = "Потребление воды (л): ";
        public static String electricalEnergyTotal = "Расход эл. (кВт/ч): ";
        public static String electricalActivePower = "Ток (А): ";
        public static String chemicals = "Лоток 1";
        public static String continue_program = "Продолжить";
        public static String time_left = "Осталось: ";
        public static String minutes = " мин.";
        public static String progress = "Прогресс: ";
        public static String finishButton = "Завершить";
    }

    public static final class laundrySetup {
        public static String weight = "Вес белья (кг):";
        public static String clientID = "Номер клиента:";
        public static String choose = "Выбрать";
        public static String laundryProgram = "Программа стирки:";
        public static String preWashTemperature = "Температура I (  С):";
        public static String mainWashTemperature = "Температура II (  С):";
        public static String launchButton = "ЗАПУСК";
        public static String dropData = "Сбросить";
        public static String userMessage_FillTheFields = "ЗАПОЛНИТЕ ПОЛЯ!";
        public static String dropFieldValues = "Сбросить значения полей?";
        public static String noData = "НЕТ ДАННЫХ";
        public static String startTheWash = "Начать стирку?";
    }

    public static final class primaryScreen {
        public static String spinner_serviceButton = "Сервис";
        public static String spinner_helpButton = "Помощь";
        public static String spinner_connectionButton = "Соединение";
        public static String spinner_washButton = "Стирка";
    }

    public static final class serviceScreen {
        public static String confirmAction = "Подтвердите действие:";
        public static String enablePower = "Включить питание?";
        public static String power = "Питание";
        public static String deceleration = "Остановка";
        public static String washLeft = "Стирка <";
        public static String washRight = "Стирка >";
        public static String spin1 = "Отжим 1";
        public static String spin2 = "Отжим 2";
        public static String drumPositionSensor = "Полож. барабана";
        public static String sink = "Слив";
        public static String tray1 = "Лоток 1";
        public static String tray2 = "Лоток 2";
        public static String tray3 = "Лоток 3";
        public static String waterValve = "Налив";
        public static String clearDoor = "Чистая дверь";
        public static String electricalHeat = "Тены";
        public static String steamHeat = "Пар";
        public static String waterLevel0 = "Уровень 0";
        public static String waterLevel1 = "Уровень 1";
        public static String waterLevel2 = "Уровень 2";
        public static String waterLevel3 = "Уровень 3";
        public static String waterLevel4 = "Уровень 4";
        public static String temperatureField1 = "Температура ( ";
        public static String dropSettings = "  Сброс\nнастроек";
        public static String data_waterConsumption = "Расход воды: ";
        public static String data_temperature = "Температура (  C): ";
        public static String data_drumRPM = "ОВМ барабана: ";
        public static String drop_all_settings = "Сбросить настройки?";


    }

    public static final class navigation {
        public static String connectionSetupScreen = "НАСТРОЙКА СОЕДИНЕНИЯ";
        public static String enterIPScreen = "ВВОД IP-АДРЕСА";
        public static String enterPortScreen = "ВВОД ПОРТА";
        public static String helpScreen = "МЕНЮ ПОМОЩИ";
        public static String keyboardEnterScreen = "ВВОД ТЕКСТА";
        public static String laundrySetupScreen = "НАСТРОЙКА СТИРКИ";
        public static String laundryProcessScreen = "СТИРКА";
        public static String numericEnterScreen = "ВВОД ДАННЫХ";
        public static String passwordEnterScreen = "ВВОД ПАРОЛЯ";
        public static String primaryScreen = "ГЛАВНОЕ МЕНЮ";
        public static String programViewScreen = "ВЫБОР ПРОГРАММЫ";
        public static String serviceScreen = "СЕРВИС";
        public static String loginScreen = "ВХОД";

    }

    public static final class statusBar {
        public static String connectionStatusDescription = "СОЕДИНЕНИЕ:";
        public static String status_waiting = "ОЖИДАНИЕ";
        public static String status_working = "В РАБОТЕ";
        public static String status_finished = "ЗАВЕРШЕНО";
        public static String status_notConnected = "НЕ ПОДКЛЮЧЕНО";
        public static String status_description = "СТАТУС: ";
    }

    public static final class elements {
        public static String yes_uppercase = "ДА";
        public static String no_uppercase = "НЕТ";
        public static String ok_uppercase = "ОК";

    }

    /**
     * global string values
     */
    public static final class globalValues {
        public static String pause_button_text = "ПАУЗА";
        public static String name = "name";
        public static String ip = "IP:";
        public static String port = "Порт: ";
        public static String connect = "Подключится";
        public static String disconnect = "Отключится";
        public static String confirmEnteredData = "ПОДТВЕРДИТЕ ВВЕДЕННЫЕ ДАННЫЕ: ";
        public static String drop = "Сброс";
        public static String confirm = "ПРИНЯТЬ";
        public static String inputError = "ОШИБКА ВВОДА";
        public static String description_Enter_Weight = "Вес белья (кг) (1-20):";
        public static String description_Enter_password = "Пароль: ";
        public static String description_Enter_ClientID = "Номер клиента:";
        public static String description_NO_CONNECTION = "НЕТ ПОДКЛЮЧЕНИЯ!";
        public static String description_Username = "Имя Пользователя:";
        public static String description_Enter_User_Name = "Введите имя пользователя (только буквы):";
        public static String description_loading = "ЗАГРУЗКА...";
        public static String error = "АВАРИЯ!";
        public static String warning = "Внимание!";
        public static String checkInputData = "Проверьте введенные данные:";
        public static String beforeStartMessage =
                "- После подтверждения начала стирки\n" +
                "   дверь будет заблокирована и начнется стирка.\n" +
                "- Вы не сможете изменить параметры стирки в процессе работы.";
    }
}
