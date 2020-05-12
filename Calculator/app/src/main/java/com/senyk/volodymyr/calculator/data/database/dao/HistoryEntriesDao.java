package com.senyk.volodymyr.calculator.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.senyk.volodymyr.calculator.data.database.entity.HistoryEntryEntity;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface HistoryEntriesDao {

    @Query("SELECT * FROM " + HistoryEntryEntity.TABLE_NAME + " ORDER BY (" + HistoryEntryEntity.DATE + ") ASC")
    Observable<List<HistoryEntryEntity>> getAll();

    @Insert
    void add(HistoryEntryEntity entity);

    @Query("DELETE FROM " + HistoryEntryEntity.TABLE_NAME)
    void delete();
}
