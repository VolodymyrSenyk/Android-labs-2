package com.senyk.volodymyr.quiz.data.repository;

import android.content.SharedPreferences;

import com.senyk.volodymyr.quiz.domain.entity.Score;
import com.senyk.volodymyr.quiz.domain.repository.ScoreRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ScoreSharedPrefsRepository implements ScoreRepository {

    private static final String NUM_OF_CORRECT_ANSWERS_KEY = "NUM_OF_CORRECT_ANSWERS_KEY";
    private static final String NUM_OF_QUESTIONS_KEY = "NUM_OF_QUESTIONS_KEY";

    private final SharedPreferences preferences;

    private Score userScore;

    @Inject
    public ScoreSharedPrefsRepository(final SharedPreferences sharedPreferences) {
        preferences = sharedPreferences;
    }

    @Override
    public Completable resetScore(final int numberOfQuestions) {
        userScore = new Score(numberOfQuestions);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(NUM_OF_CORRECT_ANSWERS_KEY, userScore.getCorrectAnswers());
        editor.putInt(NUM_OF_QUESTIONS_KEY, userScore.getNumberOfQuestions());
        editor.apply();
        return Completable.complete();
    }

    @Override
    public Completable raiseScore() {
        userScore.addPoint();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(NUM_OF_CORRECT_ANSWERS_KEY, userScore.getCorrectAnswers());
        editor.apply();
        return Completable.complete();
    }

    @Override
    public Single<Score> getScore() {
        return Single.fromCallable(() -> userScore);
    }
}
