package com.senyk.volodymyr.calculator.domain.util;

import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;
import com.senyk.volodymyr.calculator.domain.entity.LogicalSymbol;
import com.senyk.volodymyr.calculator.domain.repository.EquationRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LogicalEquationChecker {

    private final LogicalEquation equation;

    @Inject
    LogicalEquationChecker(EquationRepository equationRepository) {
        this.equation = equationRepository.getEquation();
    }

    public boolean isWithUnaryMinus(int numbersLastSymbolIndex) {
        List<LogicalSymbol> symbolsAroundNumber = new ArrayList<>();
        for (int i = numbersLastSymbolIndex; i > -1; i--) {
            LogicalSymbol symbol = equation.get(i);
            if (symbol.isUnaryOperator() || (symbol.isBinaryOperator() && symbol != LogicalSymbol.MINUS)) {
                break;
            }
            if (!symbol.isPartOfNumber()) {
                symbolsAroundNumber.add(symbol);
            }
        }
        return symbolsAroundNumber.contains(LogicalSymbol.BRACKET_OPEN) &&
                symbolsAroundNumber.contains(LogicalSymbol.BRACKET_CLOSE) &&
                symbolsAroundNumber.contains(LogicalSymbol.MINUS);
    }

    public boolean isFloatingPartOfDouble(int previousDigitIndex) {
        for (int i = previousDigitIndex; i > -1; i--) {
            LogicalSymbol symbol = equation.get(i);
            if (symbol.isComa()) {
                return true;
            } else if (!symbol.isDigit()) {
                return false;
            }
        }
        return false;
    }

    public boolean isZeroFirstInNumber() {
        for (int i = equation.getCursorPosition() - 1; i > -1; i--) {
            LogicalSymbol symbol = equation.get(i);
            if ((symbol.isDigit() && symbol != LogicalSymbol.ZERO) || symbol.isComa()) {
                return false;
            } else if (!symbol.isDigit()) {
                return true;
            }
        }
        return true;
    }

    public boolean isAlreadyZero(int previousDigitIndex) {
        for (int i = previousDigitIndex; i > -1; i--) {
            LogicalSymbol symbol = equation.get(i);
            if (i == previousDigitIndex && !symbol.isDigit()) {
                return false;
            } else if (symbol.isDigit() && symbol != LogicalSymbol.ZERO) {
                return false;
            } else if (!symbol.isDigit()) {
                return true;
            }
        }
        return true;
    }

    public boolean willBeZeroAtTheStartOfNumber(int prevSymbolIndex) {
        if (prevSymbolIndex == equation.size() - 1) {
            return false;
        } else {
            LogicalSymbol nextSymbol = equation.get(prevSymbolIndex + 1);
            LogicalSymbol prevSymbol = equation.get(prevSymbolIndex);
            if (prevSymbol.isDigit() && nextSymbol.isDigit()) {
                return false;
            } else {
                return !prevSymbol.isDigit() && nextSymbol.isDigit();
            }
        }
    }

    public boolean isNotAlreadyDouble() {
        return !isAlreadyDoubleLeft() && !isAlreadyDoubleRight();
    }

    private boolean isAlreadyDoubleLeft() {
        for (int i = equation.getCursorPosition() - 1; i > -1; i--) {
            LogicalSymbol symbol = equation.get(i);
            if (symbol.isComa()) {
                return true;
            } else if (!symbol.isDigit()) {
                return false;
            }
        }
        return false;
    }

    private boolean isAlreadyDoubleRight() {
        for (int i = equation.getCursorPosition(); i < equation.size(); i++) {
            LogicalSymbol symbol = equation.get(i);
            if (symbol.isComa()) {
                return true;
            } else if (!symbol.isDigit()) {
                return false;
            }
        }
        return false;
    }
}
