package com.senyk.volodymyr.calculator.data.database.mapper;

import com.senyk.volodymyr.calculator.data.database.entity.HistoryEntryEntity;
import com.senyk.volodymyr.calculator.domain.entity.HistoryEntry;

import java.util.Calendar;

import javax.inject.Inject;

public class HistoryEntryMapper {

    @Inject
    HistoryEntryMapper() {
    }

    public HistoryEntryEntity mapToDatabase(final HistoryEntry dto) {
        return new HistoryEntryEntity(dto.getId(), Calendar.getInstance().getTimeInMillis(), dto.getEquation(), dto.getAnswer());
    }

    public HistoryEntry mapToDomain(final HistoryEntryEntity entity) {
        return new HistoryEntry(entity.getId(), entity.getEquation(), entity.getAnswer());
    }
}
