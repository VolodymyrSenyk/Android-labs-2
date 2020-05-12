package com.senyk.volodymyr.calculator.domain.repository;

public interface SettingsRepository {

    void saveAppTheme(int appTheme);

    int getAppTheme(int defaultAppTheme);
}
