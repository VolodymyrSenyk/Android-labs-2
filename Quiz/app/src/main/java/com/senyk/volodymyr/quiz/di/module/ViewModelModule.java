package com.senyk.volodymyr.quiz.di.module;

import androidx.lifecycle.ViewModel;

import com.senyk.volodymyr.quiz.presentation.viewmodel.QuizViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(includes = {RepositoryModule.class})
public interface ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel.class)
    ViewModel bindsQuizViewModel(QuizViewModel userViewModel);
}
