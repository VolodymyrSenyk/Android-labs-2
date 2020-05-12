package com.senyk.volodymyr.calculator.data.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.senyk.volodymyr.calculator.data.database.mapper.LogicalSymbolConverter;
import com.senyk.volodymyr.calculator.domain.entity.LogicalSymbol;

import java.util.List;

@Entity(tableName = HistoryEntryEntity.TABLE_NAME)
public class HistoryEntryEntity {
    public static final String TABLE_NAME = "history_entities";
    public static final String ID = "history_entity_id";
    public static final String DATE = "history_entity_date";
    public static final String EQUATION = "history_entity_equation";
    public static final String ANSWER = "history_entity_answer";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = HistoryEntryEntity.ID)
    private final Integer id;

    @ColumnInfo(name = HistoryEntryEntity.DATE)
    private final long date;

    @NonNull
    @TypeConverters({LogicalSymbolConverter.class})
    @ColumnInfo(name = HistoryEntryEntity.EQUATION)
    private final List<LogicalSymbol> equation;

    @NonNull
    @TypeConverters({LogicalSymbolConverter.class})
    @ColumnInfo(name = HistoryEntryEntity.ANSWER)
    private final List<LogicalSymbol> answer;

    public Integer getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    @NonNull
    public List<LogicalSymbol> getEquation() {
        return equation;
    }

    @NonNull
    public List<LogicalSymbol> getAnswer() {
        return answer;
    }

    public HistoryEntryEntity(Integer id, long date, @NonNull List<LogicalSymbol> equation, @NonNull List<LogicalSymbol> answer) {
        this.id = id;
        this.date = date;
        this.equation = equation;
        this.answer = answer;
    }
}
