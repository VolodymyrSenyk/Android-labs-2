package com.senyk.volodymyr.calculator.data.repository;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.calculator.data.database.dao.HistoryEntriesDao;
import com.senyk.volodymyr.calculator.data.database.entity.HistoryEntryEntity;
import com.senyk.volodymyr.calculator.data.database.mapper.HistoryEntryMapper;
import com.senyk.volodymyr.calculator.domain.entity.HistoryEntry;
import com.senyk.volodymyr.calculator.domain.repository.HistoryRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HistoryRoomRepository implements HistoryRepository {

    private final HistoryEntriesDao historyEntriesDao;
    private final HistoryEntryMapper historyEntryMapper;

    @Inject
    HistoryRoomRepository(final HistoryEntriesDao historyEntriesDao, final HistoryEntryMapper historyEntryMapper) {
        this.historyEntriesDao = historyEntriesDao;
        this.historyEntryMapper = historyEntryMapper;
    }

    @Override
    public Observable<List<HistoryEntry>> getHistory() {
        return historyEntriesDao.getAll()
                .map(entities -> {
                    List<HistoryEntry> convertedList = new ArrayList<>(entities.size());
                    for (HistoryEntryEntity entity : entities) {
                        convertedList.add(historyEntryMapper.mapToDomain(entity));
                    }
                    return convertedList;
                });
    }

    @Override
    public void addHistoryEntry(@NonNull HistoryEntry dto) {
        new Thread() {
            @Override
            public void run() {
                historyEntriesDao.add(historyEntryMapper.mapToDatabase(dto));
            }
        }.start();
    }

    @Override
    public void clearHistory() {
        new Thread() {
            @Override
            public void run() {
                historyEntriesDao.delete();
            }
        }.start();
    }
}
