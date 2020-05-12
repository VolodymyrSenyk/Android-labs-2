package com.senyk.volodymyr.quiz.data.database.mapper;

import com.senyk.volodymyr.quiz.data.database.entity.AnswerEntity;
import com.senyk.volodymyr.quiz.domain.entity.Answer;

import javax.inject.Inject;

public class AnswerMapper {

    @Inject
    public AnswerMapper() {}

    public Answer map(final AnswerEntity entity, final boolean correct) {
        return new Answer(entity.getId(), entity.getText(), correct);
    }
}
