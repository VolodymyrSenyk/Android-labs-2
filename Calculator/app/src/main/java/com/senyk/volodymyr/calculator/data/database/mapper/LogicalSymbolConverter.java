package com.senyk.volodymyr.calculator.data.database.mapper;

import androidx.room.TypeConverter;

import com.senyk.volodymyr.calculator.domain.entity.LogicalSymbol;

import java.util.ArrayList;
import java.util.List;

public class LogicalSymbolConverter {

    private static final String ENUM_ENTRY_SEPARATOR = ", ";

    @TypeConverter
    public List<LogicalSymbol> enumListToString(String value) {
        String[] enumEntities = value.split("\\s*,\\s*");
        List<LogicalSymbol> enums = new ArrayList<>();
        for (String enumEntity : enumEntities) {
            enums.add(LogicalSymbol.valueOf(enumEntity));
        }
        return enums;
    }

    @TypeConverter
    public String stringToEnumList(List<LogicalSymbol> list) {
        StringBuilder builder = new StringBuilder();
        for (LogicalSymbol lang : list) {
            builder.append(lang.name());
            builder.append(ENUM_ENTRY_SEPARATOR);
        }
        if (builder.length() > ENUM_ENTRY_SEPARATOR.length()) {
            builder.setLength(builder.length() - ENUM_ENTRY_SEPARATOR.length());
        }
        return new String(builder);
    }
}
