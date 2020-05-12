package com.senyk.volodymyr.quiz.domain.repository;

import com.senyk.volodymyr.quiz.domain.entity.Score;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ScoreRepository {

    Completable resetScore(final int numberOfQuestions);

    Completable raiseScore();

    Single<Score> getScore();
}
