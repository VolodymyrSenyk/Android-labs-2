package com.senyk.volodymyr.quiz.data.repository;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.quiz.data.database.dao.QuestionsDao;
import com.senyk.volodymyr.quiz.data.database.mapper.QuestionMapper;
import com.senyk.volodymyr.quiz.domain.entity.Question;
import com.senyk.volodymyr.quiz.domain.repository.QuestionsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class QuestionsRoomRepository implements QuestionsRepository {
    private final QuestionsDao questionsDao;
    private final QuestionMapper questionMapper;

    @Inject
    public QuestionsRoomRepository(QuestionsDao questionsDao, QuestionMapper questionMapper) {
        this.questionsDao = questionsDao;
        this.questionMapper = questionMapper;
    }

    @Override
    public Single<List<Integer>> getAllQuestionsIds() {
        return questionsDao.getAllIds();
    }

    @Override
    public Single<Question> getQuestionById(@NonNull final Integer id) {
        return questionsDao.getById(id).map(questionMapper::map);
    }
}
