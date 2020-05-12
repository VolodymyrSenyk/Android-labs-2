package com.senyk.volodymyr.quiz.domain.repository;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.quiz.domain.entity.Answer;

import java.util.List;

import io.reactivex.Single;

public interface AnswersRepository {

    Single<List<Integer>> getAllAnswersIds();

    Single<Answer> getAnswerById(@NonNull final Integer id, @NonNull final Boolean correct);
}
