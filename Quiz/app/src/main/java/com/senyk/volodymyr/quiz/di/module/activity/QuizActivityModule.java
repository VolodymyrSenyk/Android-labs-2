package com.senyk.volodymyr.quiz.di.module.activity;

import com.senyk.volodymyr.quiz.presentation.view.activity.QuizActivity;

import dagger.Binds;
import dagger.Module;

@Module
public interface QuizActivityModule {

    @Binds
    QuizActivity bindActivity(QuizActivity activity);
}
