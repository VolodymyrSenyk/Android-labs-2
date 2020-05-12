package com.senyk.volodymyr.quiz.data.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.senyk.volodymyr.quiz.data.database.dao.AnswersDao;
import com.senyk.volodymyr.quiz.data.database.dao.QuestionsDao;
import com.senyk.volodymyr.quiz.data.database.entity.AnswerEntity;
import com.senyk.volodymyr.quiz.data.database.entity.dataentity.QuestionDataEntity;

@Database(entities = {QuestionDataEntity.class, AnswerEntity.class}, version = 1, exportSchema = false)
public abstract class QuizRoomDatabase extends RoomDatabase {

    public abstract QuestionsDao getQuestionsDao();

    public abstract AnswersDao getAnswersDao();

    public static final Migration MIGRATION_2_3 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE IF NOT EXISTS '" + QuestionDataEntity.TABLE_NAME + "' " +
                    "(" +
                    "  '" + QuestionDataEntity.ID + "' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "  '" + QuestionDataEntity.TEXT + "' TEXT NOT NULL ," +
                    "  '" + QuestionDataEntity.CORRECT_ANSWER + "' INTEGER NOT NULL," +
                    "  FOREIGN KEY('" + QuestionDataEntity.CORRECT_ANSWER + "') REFERENCES '" + AnswerEntity.TABLE_NAME + "'('" + AnswerEntity.ID + "') " +
                    ")";
            String SQL_CREATE_ANSWERS_TABLE = "CREATE TABLE IF NOT EXISTS '" + AnswerEntity.TABLE_NAME + "' " +
                    "(" +
                    "  '" + AnswerEntity.ID + "' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "  '" + AnswerEntity.TEXT + "' TEXT NOT NULL " +
                    ")";
            database.execSQL(SQL_CREATE_QUESTIONS_TABLE);
            database.execSQL(SQL_CREATE_ANSWERS_TABLE);
        }
    };
}
