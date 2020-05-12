package com.senyk.volodymyr.calculator.domain.entity;

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

    PERCENT(LogicalSymbolType.UNARY_OPERATOR),

    ROOT(LogicalSymbolType.SIMPLE_FUNCTION),
    POW(LogicalSymbolType.POW_FUNCTION),
    POW_MINUS_ONE(LogicalSymbolType.POW_FUNCTION),
    X_POW_Y(LogicalSymbolType.POW_FUNCTION),
    E_POW_X(LogicalSymbolType.POW_FUNCTION),
    X_POW_2(LogicalSymbolType.POW_FUNCTION),
    ABS(LogicalSymbolType.SIMPLE_FUNCTION),

    SIN(LogicalSymbolType.TRIGONOMETRIC_FUNCTION),
    COS(LogicalSymbolType.TRIGONOMETRIC_FUNCTION),
    TAN(LogicalSymbolType.TRIGONOMETRIC_FUNCTION),

    LN(LogicalSymbolType.SIMPLE_FUNCTION),
    LOG(LogicalSymbolType.SIMPLE_FUNCTION),

    PI(LogicalSymbolType.CONSTANT),
    E(LogicalSymbolType.CONSTANT),

    EQU(LogicalSymbolType.BINARY_OPERATOR);

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

    public boolean isConstant() {
        return type == LogicalSymbolType.CONSTANT;
    }

    public boolean isFunction() {
        return type == LogicalSymbolType.SIMPLE_FUNCTION ||
                type == LogicalSymbolType.POW_FUNCTION ||
                type == LogicalSymbolType.TRIGONOMETRIC_FUNCTION;
    }

    public boolean isSimpleFunction() {
        return type == LogicalSymbolType.SIMPLE_FUNCTION;
    }

    public boolean isPowFunction() {
        return type == LogicalSymbolType.POW_FUNCTION;
    }

    public boolean isTrigonometricFunction() {
        return type == LogicalSymbolType.TRIGONOMETRIC_FUNCTION;
    }
}
