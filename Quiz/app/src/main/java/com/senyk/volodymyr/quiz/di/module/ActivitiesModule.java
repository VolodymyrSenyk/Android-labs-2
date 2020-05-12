package com.senyk.volodymyr.quiz.di.module;

import com.senyk.volodymyr.quiz.di.module.activity.QuizActivityModule;
import com.senyk.volodymyr.quiz.presentation.view.activity.QuizActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivitiesModule {

    @ContributesAndroidInjector(modules = {QuizActivityModule.class})
    QuizActivity contributeQuizActivityInjector();
}
