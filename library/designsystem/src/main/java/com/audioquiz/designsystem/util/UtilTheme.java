package com.audioquiz.designsystem.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

public class UtilTheme implements UtilThemeApi {

    private final SharedPreferences sharedPrefs;
    private final Application application;
    private final String key;

    public UtilTheme(SharedPreferences sharedPrefs, Application application, String key) {
        this.sharedPrefs = sharedPrefs;
        this.application = application;
        this.key = key;
    }

    @Override
    public boolean isNightModeEnabled() {
        return getMode() == Configuration.UI_MODE_NIGHT_YES;
    }

    private int getMode() {
        return application.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    }

    @Override
    public void setDay() {
        setMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void setDark() {
        setMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    @Override
    public void setFollowSystem() {
        setMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    @Override
    public void restore() {
        AppCompatDelegate.setDefaultNightMode(
                sharedPrefs.getInt(key, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        );
    }

    private void setMode(int mode) {
        AppCompatDelegate.setDefaultNightMode(mode);
        saveChange(mode);
    }

    private void saveChange(int mode) {
        sharedPrefs.edit().putInt(key, mode).apply();
    }
}