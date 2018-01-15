package com.jaykallen.appweather.utils;
// Created by jkallen on 4/4/2017

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefsManager {
    static SharedPreferences preferences;
    static SharedPreferences.Editor sharedprefs;

    public static void setValue (Context context, String key, String value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedprefs = preferences.edit();
        sharedprefs.putString(key, value).apply();
    }

    public static String getValue (Context context, String key, String defaultVal) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, defaultVal);  // 2nd value is default value.
    }
}
