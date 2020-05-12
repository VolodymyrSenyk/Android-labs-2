package com.senyk.volodymyr.quiz.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.senyk.volodymyr.quiz.data.database.QuizRoomDatabase;
import com.senyk.volodymyr.quiz.data.database.dao.AnswersDao;
import com.senyk.volodymyr.quiz.data.database.dao.QuestionsDao;
import com.senyk.volodymyr.quiz.data.database.databasecopyframework.AssetSQLiteOpenHelperFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    private static final String SHARED_PREFS_NAME = "QuizGameSharedPrefs";
    private static final String DATABASE_NAME = "QuizAppDatabase.db";

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    QuizRoomDatabase provideQuizAppDatabase(Context context) {
        RoomDatabase.Builder<QuizRoomDatabase> builder = Room.databaseBuilder(
                context.getApplicationContext(),
                QuizRoomDatabase.class,
                DATABASE_NAME
        );
        return (builder.openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .allowMainThreadQueries()
                .addMigrations(QuizRoomDatabase.MIGRATION_2_3)
                .build());
    }

    @Provides
    QuestionsDao provideQuestionsDao(QuizRoomDatabase database) {
        return database.getQuestionsDao();
    }

    @Provides
    AnswersDao provideAnswersDao(QuizRoomDatabase database) {
        return database.getAnswersDao();
    }
}
