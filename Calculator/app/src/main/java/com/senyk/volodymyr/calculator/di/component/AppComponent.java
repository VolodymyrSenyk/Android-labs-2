package com.senyk.volodymyr.calculator.di.component;

import android.content.Context;

import com.senyk.volodymyr.calculator.Application;
import com.senyk.volodymyr.calculator.di.module.ActivitiesModule;
import com.senyk.volodymyr.calculator.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidSupportInjectionModule.class, ActivitiesModule.class, ViewModelModule.class})
@Singleton
public interface AppComponent extends AndroidInjector<Application> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }
}
