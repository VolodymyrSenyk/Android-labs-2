package com.senyk.volodymyr.lab3.domain.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.senyk.volodymyr.lab3.data.entity.Question;

public interface QuestionRepository {

    @Nullable
    Question getQuestion();

    void saveQuestion(@NonNull Question question);

    void deleteQuestion();
}
