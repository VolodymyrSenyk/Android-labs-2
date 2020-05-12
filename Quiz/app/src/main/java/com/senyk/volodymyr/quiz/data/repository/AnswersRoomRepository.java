package com.senyk.volodymyr.quiz.data.repository;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.quiz.data.database.dao.AnswersDao;
import com.senyk.volodymyr.quiz.data.database.mapper.AnswerMapper;
import com.senyk.volodymyr.quiz.domain.entity.Answer;
import com.senyk.volodymyr.quiz.domain.repository.AnswersRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AnswersRoomRepository implements AnswersRepository {
    private final AnswersDao answersDao;
    private final AnswerMapper answerMapper;

    @Inject
    public AnswersRoomRepository(final AnswersDao answersDao, final AnswerMapper answerMapper) {
        this.answersDao = answersDao;
        this.answerMapper = answerMapper;
    }

    @Override
    public Single<List<Integer>> getAllAnswersIds() {
        return answersDao.getAllIds();
    }

    @Override
    public Single<Answer> getAnswerById(@NonNull final Integer id, @NonNull final Boolean correct) {
        return answersDao.getById(id).map(entity -> answerMapper.map(entity, correct));
    }
}
