package com.senyk.volodymyr.calculator.domain.entity;

import java.util.List;

public class HistoryEntry {
    private final Integer id;
    private final List<LogicalSymbol> equation;
    private final List<LogicalSymbol> answer;

    public Integer getId() {
        return id;
    }

    public List<LogicalSymbol> getEquation() {
        return equation;
    }

    public List<LogicalSymbol> getAnswer() {
        return answer;
    }

    public HistoryEntry(Integer id, List<LogicalSymbol> equation, List<LogicalSymbol> answer) {
        this.id = id;
        this.equation = equation;
        this.answer = answer;
    }
}
