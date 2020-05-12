package com.senyk.volodymyr.quiz.data.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.senyk.volodymyr.quiz.data.database.entity.AnswerEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface AnswersDao {

    @Query("SELECT " + AnswerEntity.ID + " FROM " + AnswerEntity.TABLE_NAME)
    Single<List<Integer>> getAllIds();

    @Query("SELECT * FROM " + AnswerEntity.TABLE_NAME + " WHERE " + AnswerEntity.ID + " = :id")
    Single<AnswerEntity> getById(int id);
}
