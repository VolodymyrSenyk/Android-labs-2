package com.senyk.volodymyr.quiz.domain.interactor;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.quiz.domain.entity.Score;
import com.senyk.volodymyr.quiz.domain.repository.ScoreRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetUserScoreInteractor {

    private final ScoreRepository scoreRepository;

    @Inject
    public GetUserScoreInteractor(@NonNull final ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Single<Score> execute() {
        return scoreRepository.getScore();
    }
}
