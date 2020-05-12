package com.senyk.volodymyr.quiz.data.database.entity.dataentity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.senyk.volodymyr.quiz.data.database.entity.AnswerEntity;

@Entity(
        tableName = QuestionDataEntity.TABLE_NAME,
        foreignKeys = @ForeignKey(
                entity = AnswerEntity.class,
                parentColumns = AnswerEntity.ID,
                childColumns = QuestionDataEntity.CORRECT_ANSWER
        ),
        indices = @Index(value = QuestionDataEntity.CORRECT_ANSWER)
)
public class QuestionDataEntity {
    public static final String TABLE_NAME = "questions";
    public static final String ID = "question_id";
    public static final String TEXT = "question_text";
    public static final String CORRECT_ANSWER = "correct_answer_id";

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = QuestionDataEntity.ID)
    private final Integer id;

    @NonNull
    @ColumnInfo(name = QuestionDataEntity.TEXT)
    private final String question;

    @NonNull
    @ColumnInfo(name = QuestionDataEntity.CORRECT_ANSWER)
    private final Integer correctAnswerId;

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    @NonNull
    public Integer getCorrectAnswerId() {
        return correctAnswerId;
    }

    public QuestionDataEntity(@NonNull Integer id, @NonNull String question, @NonNull Integer correctAnswerId) {
        this.id = id;
        this.question = question;
        this.correctAnswerId = correctAnswerId;
    }
}
