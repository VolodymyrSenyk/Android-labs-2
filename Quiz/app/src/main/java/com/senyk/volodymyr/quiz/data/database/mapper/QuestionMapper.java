package com.senyk.volodymyr.quiz.data.database.mapper;

import com.senyk.volodymyr.quiz.data.database.entity.QuestionEntity;
import com.senyk.volodymyr.quiz.domain.entity.Answer;
import com.senyk.volodymyr.quiz.domain.entity.Question;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QuestionMapper {

    private final AnswerMapper answerMapper;

    @Inject
    public QuestionMapper(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    public Question map(QuestionEntity entity) {
        List<Answer> answers = new ArrayList<>(1);
        answers.add(answerMapper.map(entity.correctAnswer, true));
        return new Question(entity.question.getId(), entity.question.getQuestion(), answers);
    }
}
