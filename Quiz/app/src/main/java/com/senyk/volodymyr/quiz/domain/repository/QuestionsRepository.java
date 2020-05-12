package com.senyk.volodymyr.quiz.domain.repository;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.quiz.domain.entity.Question;

import java.util.List;

import io.reactivex.Single;

public interface QuestionsRepository {

    Single<List<Integer>> getAllQuestionsIds();

    Single<Question> getQuestionById(@NonNull final Integer id);
}
