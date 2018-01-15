package com.jaykallen.appweather.utils;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Jay Kallen on 9/11/2017.
 */

public class StartApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected void log(int priority, String tag, String message, Throwable t) {
                super.log(priority, tag + "Tag", message, t);
            }
        });
    }
}
