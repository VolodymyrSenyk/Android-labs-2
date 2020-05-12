package com.senyk.volodymyr.lab2.domain.entity;

public enum LogicalSymbol {
    ZERO(LogicalSymbolType.DIGIT),
    ONE(LogicalSymbolType.DIGIT),
    TWO(LogicalSymbolType.DIGIT),
    THREE(LogicalSymbolType.DIGIT),
    FOUR(LogicalSymbolType.DIGIT),
    FIVE(LogicalSymbolType.DIGIT),
    SIX(LogicalSymbolType.DIGIT),
    SEVEN(LogicalSymbolType.DIGIT),
    EIGHT(LogicalSymbolType.DIGIT),
    NINE(LogicalSymbolType.DIGIT),

    COMA(LogicalSymbolType.COMA),

    BRACKET_OPEN(LogicalSymbolType.BRACKET),
    BRACKET_CLOSE(LogicalSymbolType.BRACKET),

    PLUS(LogicalSymbolType.BINARY_OPERATOR),
    MINUS(LogicalSymbolType.BINARY_OPERATOR),
    MULTIPLICATION(LogicalSymbolType.BINARY_OPERATOR),
    DIVISION(LogicalSymbolType.BINARY_OPERATOR),

    ROOT(LogicalSymbolType.FUNCTION);

    private final LogicalSymbolType type;

    LogicalSymbol(LogicalSymbolType type) {
        this.type = type;
    }

    public boolean isDigit() {
        return type == LogicalSymbolType.DIGIT;
    }

    public boolean isComa() {
        return type == LogicalSymbolType.COMA;
    }

    public boolean isPartOfNumber() {
        return type == LogicalSymbolType.DIGIT || type == LogicalSymbolType.COMA;
    }

    public boolean isUnaryOperator() {
        return type == LogicalSymbolType.UNARY_OPERATOR;
    }

    public boolean isBinaryOperator() {
        return type == LogicalSymbolType.BINARY_OPERATOR;
    }

    public boolean isFunction() {
        return type == LogicalSymbolType.FUNCTION;
    }
}
