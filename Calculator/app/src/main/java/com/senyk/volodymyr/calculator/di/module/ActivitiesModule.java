package com.senyk.volodymyr.calculator.di.module;

import com.senyk.volodymyr.calculator.di.module.activity.CalculatorActivityModule;
import com.senyk.volodymyr.calculator.di.scope.ActivityScope;
import com.senyk.volodymyr.calculator.presentation.view.activity.CalculatorActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {CalculatorActivityModule.class})
    CalculatorActivity contributeCalculatorActivity();
}
