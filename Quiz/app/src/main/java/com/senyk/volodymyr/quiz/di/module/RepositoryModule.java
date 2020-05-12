package com.senyk.volodymyr.quiz.di.module;

import com.senyk.volodymyr.quiz.data.repository.AnswersRoomRepository;
import com.senyk.volodymyr.quiz.data.repository.QuestionsRoomRepository;
import com.senyk.volodymyr.quiz.data.repository.ScoreSharedPrefsRepository;
import com.senyk.volodymyr.quiz.domain.repository.AnswersRepository;
import com.senyk.volodymyr.quiz.domain.repository.QuestionsRepository;
import com.senyk.volodymyr.quiz.domain.repository.ScoreRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module(includes = {PersistenceModule.class})
public interface RepositoryModule {

    @Singleton
    @Binds
    QuestionsRepository bindQuestionsRepository(QuestionsRoomRepository repository);

    @Singleton
    @Binds
    AnswersRepository bindAnswersRepository(AnswersRoomRepository repository);

    @Singleton
    @Binds
    ScoreRepository bindScoreRepository(ScoreSharedPrefsRepository repository);
}
