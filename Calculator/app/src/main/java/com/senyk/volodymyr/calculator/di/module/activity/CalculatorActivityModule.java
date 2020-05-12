package com.senyk.volodymyr.calculator.di.module.activity;

import com.senyk.volodymyr.calculator.di.scope.ActivityScope;
import com.senyk.volodymyr.calculator.presentation.view.activity.CalculatorActivity;

import dagger.Binds;
import dagger.Module;

@Module
public interface CalculatorActivityModule {

    @ActivityScope
    @Binds
    CalculatorActivity bindActivity(CalculatorActivity activity);
}
