package com.mygdx.game.android;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;


public class AndroidLauncher extends AndroidApplication {


    private Activity ACTIVITY;
    private PendingIntent RESTART_INTENT;

    private Thread.UncaughtExceptionHandler onRuntimeError = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable globalException) {
            //System.out.println("[][][][][][]UNCAUGHT EXCEPTION[][][][]");

            globalException.printStackTrace();
            /**
             * WRITING EXCEPTION DETAILS TO EXTERNAL STORAGE
             */
            /**
             * getting link to external storage and create required categories if needed
             */
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/laundry_log");
            myDir.mkdirs();
            /**
             * define the file name and create it if file doesn't exists
             */
            String fileName = "exceptions" + ".log";
            File file = new File(myDir, fileName);
            if (!file.exists()) try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                //System.out.println("==========cannot create file==========");
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
                 * write separator into file for user
                 */
                String _temp_string = "|||||||||||||||||||||||||||||||" + "\n";
                out.append(_temp_string);
                /**
                 * get current date and time and write it into file
                 */
                _temp_string = DateFormat.getDateTimeInstance().format(new Date()) + "\n";
                if (_temp_string.getBytes() != null) out.append(_temp_string);
                /**
                 * get full message of current exception and write it into file
                 */
                StringWriter sw = new StringWriter();
                globalException.printStackTrace(new PrintWriter(sw));
                _temp_string = sw.toString();
                out.append(_temp_string);
                /**
                 * close file
                 */
                out.close();
                //System.out.println("==========WRITTEN==========");
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("==========NOT WRITTEN==========");
            }

            /**
             * starting new example of program and stop current example of program
             */
            //System.out.println("[][][][][][]RESTARTING[][][][][][][][]");
            AlarmManager mgr = (AlarmManager) ACTIVITY.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, RESTART_INTENT);
            System.exit(2);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * this will allow our program to kill itself on restart
         */
        ACTIVITY = this;
        RESTART_INTENT = PendingIntent.getActivity(this.getBaseContext(), 0, new Intent(getIntent()), getIntent().getFlags());
        Thread.setDefaultUncaughtExceptionHandler(onRuntimeError);

        /**
         * hiding isShowed bar
         */
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(Build.VERSION_CODES.ICE_CREAM_SANDWICH);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        initialize(new Main());

    }
}
