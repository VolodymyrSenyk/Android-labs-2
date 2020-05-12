package com.senyk.volodymyr.calculator.data.repository;

import android.content.SharedPreferences;

import com.senyk.volodymyr.calculator.domain.repository.SettingsRepository;

import javax.inject.Inject;

public class SettingsSharedPrefsRepository implements SettingsRepository {

    private static final String APP_THEME_KEY = "APP_THEME_KEY";

    private final SharedPreferences preferences;

    @Inject
    SettingsSharedPrefsRepository(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void saveAppTheme(int appTheme) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(APP_THEME_KEY, appTheme);
        editor.apply();
    }

    @Override
    public int getAppTheme(int defaultAppTheme) {
        return preferences.getInt(APP_THEME_KEY, defaultAppTheme);
    }
}
