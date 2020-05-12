package com.senyk.volodymyr.calculator.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.senyk.volodymyr.calculator.data.database.dao.HistoryEntriesDao;
import com.senyk.volodymyr.calculator.data.database.entity.HistoryEntryEntity;

@Database(entities = {HistoryEntryEntity.class}, version = 1, exportSchema = false)
public abstract class CalculatorRoomDatabase extends RoomDatabase {

    public abstract HistoryEntriesDao getHistoryEntriesDao();
}
