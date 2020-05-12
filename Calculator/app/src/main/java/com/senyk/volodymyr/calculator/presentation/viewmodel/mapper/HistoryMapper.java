package com.senyk.volodymyr.calculator.presentation.viewmodel.mapper;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.domain.entity.HistoryEntry;
import com.senyk.volodymyr.calculator.domain.entity.LogicalSymbol;
import com.senyk.volodymyr.calculator.presentation.viewmodel.entity.HistoryEntryUi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HistoryMapper {

    private final SymbolMapper symbolMapper;

    public SymbolMapper getSymbolMapper() {
        return symbolMapper;
    }

    @Inject
    HistoryMapper(SymbolMapper symbolMapper) {
        this.symbolMapper = symbolMapper;
    }

    public HistoryEntry convertToDomain(@NonNull final List<LogicalSymbol> equation, @NonNull final List<LogicalSymbol> answer) {
        return new HistoryEntry(null, equation, answer);
    }

    public List<LogicalSymbol> convertToLogical(@NonNull final String uiEntry) {
        return symbolMapper.convertToLogical(uiEntry);
    }

    public List<HistoryEntryUi> convertToUi(@NonNull final List<HistoryEntry> historyEntries) {
        List<HistoryEntryUi> historyUi = new ArrayList<>(historyEntries.size());
        for (HistoryEntry entry : historyEntries) {
            historyUi.addAll(convertToUi(entry));
        }
        return historyUi;
    }

    public List<HistoryEntryUi> convertToUi(final HistoryEntry historyEntry) {
        List<HistoryEntryUi> historyEntryUi = new ArrayList<>(2);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < historyEntry.getEquation().size(); i++) {
            LogicalSymbol logicalSymbol = historyEntry.getEquation().get(i);
            builder.append(symbolMapper.convertToUi(logicalSymbol, R.attr.colorTextDark));
        }
        historyEntryUi.add(new HistoryEntryUi(new SpannableString(builder), false));
        builder = new SpannableStringBuilder(symbolMapper.convertToUi(LogicalSymbol.EQU, R.attr.colorText));
        for (int i = 0; i < historyEntry.getAnswer().size(); i++) {
            LogicalSymbol logicalSymbol = historyEntry.getAnswer().get(i);
            builder.append(symbolMapper.convertToUi(logicalSymbol, R.attr.colorText));
        }
        historyEntryUi.add(new HistoryEntryUi(new SpannableString(builder), true));
        return historyEntryUi;
    }
}
