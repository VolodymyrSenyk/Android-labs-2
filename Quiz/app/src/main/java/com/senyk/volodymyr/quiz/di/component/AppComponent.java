package com.senyk.volodymyr.quiz.di.component;

import android.content.Context;

import com.senyk.volodymyr.quiz.di.module.ActivitiesModule;
import com.senyk.volodymyr.quiz.di.module.ViewModelModule;
import com.senyk.volodymyr.quiz.Application;

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
