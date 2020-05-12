package com.senyk.volodymyr.calculator.di.module;

import javax.inject.Singleton;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilModule {

    private static final String SCRIPT_ENGINE_SHORT_NAME = "rhino";

    @Singleton
    @Provides
    ScriptEngine provideScriptEngine() {
        return new ScriptEngineManager().getEngineByName(SCRIPT_ENGINE_SHORT_NAME);
    }
}
