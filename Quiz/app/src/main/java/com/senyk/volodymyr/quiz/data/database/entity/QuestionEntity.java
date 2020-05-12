package com.senyk.volodymyr.quiz.data.database.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.senyk.volodymyr.quiz.data.database.entity.dataentity.QuestionDataEntity;

public class QuestionEntity {
    @Embedded
    public QuestionDataEntity question;

    @Relation(parentColumn = QuestionDataEntity.CORRECT_ANSWER, entity = AnswerEntity.class, entityColumn = AnswerEntity.ID)
    public AnswerEntity correctAnswer;
}
