package com.senyk.volodymyr.quiz.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.senyk.volodymyr.quiz.data.database.entity.dataentity.QuestionDataEntity;
import com.senyk.volodymyr.quiz.data.database.entity.QuestionEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface QuestionsDao {

    @Query("SELECT " + QuestionDataEntity.ID + " FROM " + QuestionDataEntity.TABLE_NAME)
    Single<List<Integer>> getAllIds();

    @Transaction
    @Query("SELECT * FROM " + QuestionDataEntity.TABLE_NAME + " WHERE " + QuestionDataEntity.ID + " = :id")
    Single<QuestionEntity> getById(int id);
}
