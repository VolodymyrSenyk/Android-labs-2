package com.senyk.volodymyr.calculator.domain.interactor;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.calculator.domain.entity.HistoryEntry;
import com.senyk.volodymyr.calculator.domain.repository.HistoryRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HistoryInteractor {

    private final HistoryRepository repository;

    @Inject
    HistoryInteractor(HistoryRepository historyRepository) {
        this.repository = historyRepository;
    }

    public Observable<List<HistoryEntry>> getHistory() {
        return repository.getHistory();
    }

    public void addHistoryEntry(@NonNull HistoryEntry dto) {
        repository.addHistoryEntry(dto);
    }

    public void clearHistory() {
        repository.clearHistory();
    }
}
