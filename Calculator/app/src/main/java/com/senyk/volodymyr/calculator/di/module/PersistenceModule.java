package com.senyk.volodymyr.calculator.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.senyk.volodymyr.calculator.data.database.CalculatorRoomDatabase;
import com.senyk.volodymyr.calculator.data.database.dao.HistoryEntriesDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    private static final String SHARED_PREFS_NAME = "CalculatorSettingsSharedPrefs";
    private static final String DATABASE_NAME = "CalculatorAppDatabase.db";

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    CalculatorRoomDatabase provideCalculatorRoomDatabase(Context context) {
        RoomDatabase.Builder<CalculatorRoomDatabase> builder = Room.databaseBuilder(
                context.getApplicationContext(),
                CalculatorRoomDatabase.class,
                DATABASE_NAME
        );
        return builder.build();
    }

    @Provides
    HistoryEntriesDao provideHistoryEntriesDao(CalculatorRoomDatabase database) {
        return database.getHistoryEntriesDao();
    }
}
