package com.senyk.volodymyr.calculator.domain.repository;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.calculator.domain.entity.HistoryEntry;

import java.util.List;

import io.reactivex.Observable;

public interface HistoryRepository {

    Observable<List<HistoryEntry>> getHistory();

    void addHistoryEntry(@NonNull HistoryEntry dto);

    void clearHistory();
}
