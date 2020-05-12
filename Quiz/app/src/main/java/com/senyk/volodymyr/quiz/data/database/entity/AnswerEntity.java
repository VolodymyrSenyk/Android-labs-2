package com.senyk.volodymyr.quiz.data.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AnswerEntity.TABLE_NAME)
public class AnswerEntity {
    public static final String TABLE_NAME = "answers";
    public static final String ID = "answer_id";
    public static final String TEXT = "answer_text";

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AnswerEntity.ID)
    private final Integer id;

    @NonNull
    @ColumnInfo(name = AnswerEntity.TEXT)
    private final String text;

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public AnswerEntity(@NonNull Integer id, @NonNull String text) {
        this.id = id;
        this.text = text;
    }
}
