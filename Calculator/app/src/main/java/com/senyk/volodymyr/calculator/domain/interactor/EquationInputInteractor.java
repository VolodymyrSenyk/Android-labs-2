package com.senyk.volodymyr.calculator.domain.interactor;

import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;
import com.senyk.volodymyr.calculator.domain.entity.LogicalSymbol;
import com.senyk.volodymyr.calculator.domain.exception.NotValidPowUsageException;
import com.senyk.volodymyr.calculator.domain.util.LogicalEquationChecker;
import com.senyk.volodymyr.calculator.presentation.viewmodel.mapper.CursorMapper;

import java.util.List;

import javax.inject.Inject;

public class EquationInputInteractor {

    private final static int MAX_SYMBOLS_COUNT = 60;

    private final GetEquationInteractor getEquationInteractor;
    private final LogicalEquationChecker logicalEquationChecker;
    private final CursorMapper cursorMapper;

    @Inject
    EquationInputInteractor(GetEquationInteractor getEquationInteractor, LogicalEquationChecker logicalEquationChecker, CursorMapper cursorMapper) {
        this.getEquationInteractor = getEquationInteractor;
        this.logicalEquationChecker = logicalEquationChecker;
        this.cursorMapper = cursorMapper;
    }

    public void resetEquation() {
        getEquationInteractor.execute().clear();
    }

    public void changeCursorPosition(int newCursorPosition) {
        LogicalEquation logicalEquationClone = getEquationInteractor.execute();
        int newLogicalCursorPosition = cursorMapper.convertToLogical(logicalEquationClone, newCursorPosition);
        getEquationInteractor.execute().setCursorPosition(newLogicalCursorPosition);
    }

    public void changeUnit(boolean degrees) {
        getEquationInteractor.execute().setCurrentUnit(degrees);
    }

    public void addOtherEquation(List<LogicalSymbol> logicalSymbols) throws NotValidPowUsageException {
        for (LogicalSymbol symbol : logicalSymbols) {
            if (symbol == LogicalSymbol.BRACKET_OPEN || symbol == LogicalSymbol.BRACKET_CLOSE) {
                addBracket(symbol);
            }
            addSymbol(symbol);
        }
    }

    public void addSymbol(LogicalSymbol symbol) throws NotValidPowUsageException {
        if (getEquationInteractor.execute().size() == MAX_SYMBOLS_COUNT) {
            return;
        }
        if (symbol == LogicalSymbol.ZERO) {
            addZero();
        } else if (symbol.isDigit()) {
            addDigit(symbol);
        } else if (symbol.isComa()) {
            addComa();
        } else if (symbol.isFunction()) {
            addFunction(symbol);
        } else if (symbol.isConstant()) {
            addConstant(symbol);
        } else if (symbol.isUnaryOperator()) {
            addUnaryOperator(symbol);
        } else if (symbol.isBinaryOperator()) {
            addBinaryOperator(symbol);
        }
    }

    public void addBracket() {
        LogicalEquation equation = getEquationInteractor.execute();
        LogicalSymbol prevSymbol = equation.getPrev();
        if (prevSymbol == null) {
            equation.add(LogicalSymbol.BRACKET_OPEN);
            equation.incrementNotClosedBracketsCount();
        } else {
            if (equation.getNumberOfNotClosedBrackets() <= 0) {
                if (prevSymbol == LogicalSymbol.BRACKET_CLOSE || prevSymbol.isUnaryOperator() ||
                        prevSymbol.isDigit() || prevSymbol.isConstant()) {
                    equation.add(LogicalSymbol.MULTIPLICATION);
                }
                equation.add(LogicalSymbol.BRACKET_OPEN);
                equation.incrementNotClosedBracketsCount();
            } else if (prevSymbol == LogicalSymbol.BRACKET_OPEN) {
                equation.add(LogicalSymbol.BRACKET_OPEN);
                equation.incrementNotClosedBracketsCount();
            } else {
                equation.add(LogicalSymbol.BRACKET_CLOSE);
                equation.decrementNotClosedBracketsCount();
            }
        }
    }

    public void inverse() {
        LogicalSymbol symbol = getEquationInteractor.execute().getPrev();
        if (symbol == null) {
            return;
        }
        if (logicalEquationChecker.isWithUnaryMinus(getEquationInteractor.execute().getCursorPosition() - 1)) {
            removeUnaryMinus();
        } else if (symbol.isDigit() || symbol.isConstant()) {
            addUnaryMinus();
        }
    }

    public void delete() {
        LogicalSymbol symbolToRemove = getEquationInteractor.execute().getPrev();
        if (symbolToRemove != null) {
            if (symbolToRemove == LogicalSymbol.BRACKET_OPEN) {
                removeBracketOpen();
            } else if (symbolToRemove == LogicalSymbol.BRACKET_CLOSE) {
                removeBracketClose();
            } else if (symbolToRemove == LogicalSymbol.ZERO) {
                removeZero();
            } else if (symbolToRemove.isComa()) {
                removeComa();
            } else if (symbolToRemove.isFunction()) {
                removeFunction();
            } else if (symbolToRemove.isDigit() || symbolToRemove.isConstant()) {
                removeDigit();
            } else {
                getEquationInteractor.execute().remove();
            }
        }
    }

    private void addBracket(LogicalSymbol bracket) {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        if (bracket == LogicalSymbol.BRACKET_CLOSE) {
            logicalEquation.add(bracket);
            logicalEquation.decrementNotClosedBracketsCount();
        } else {
            LogicalSymbol prevSymbol = logicalEquation.getPrev();
            if (prevSymbol == null) {
                return;
            }
            if (prevSymbol == LogicalSymbol.BRACKET_CLOSE || prevSymbol.isUnaryOperator() || prevSymbol.isConstant()) {
                logicalEquation.add(LogicalSymbol.MULTIPLICATION);
            }
            logicalEquation.add(LogicalSymbol.BRACKET_OPEN);
            logicalEquation.incrementNotClosedBracketsCount();
        }
    }

    private void addZero() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        if (logicalEquation.isEmpty()) {
            logicalEquation.add(LogicalSymbol.ZERO);
        } else if (logicalEquationChecker.isFloatingPartOfDouble(logicalEquation.getCursorPosition() - 1)) {
            addDigit(LogicalSymbol.ZERO);
        } else if (!logicalEquationChecker.isAlreadyZero(logicalEquation.getCursorPosition() - 1)) {
            if (!logicalEquationChecker.willBeZeroAtTheStartOfNumber(logicalEquation.getCursorPosition() - 1)) {
                addDigit(LogicalSymbol.ZERO);
            }
        }
    }

    private void addDigit(LogicalSymbol digit) {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        LogicalSymbol prevSymbol = logicalEquation.getPrev();
        if (prevSymbol != null) {
            if (prevSymbol == LogicalSymbol.BRACKET_CLOSE || prevSymbol.isUnaryOperator() || prevSymbol.isConstant()) {
                logicalEquation.add(LogicalSymbol.MULTIPLICATION);
            } else if (prevSymbol == LogicalSymbol.ZERO) {
                if (logicalEquationChecker.isZeroFirstInNumber()) {
                    logicalEquation.remove();
                }
            }
        }
        logicalEquation.add(digit);
    }

    private void addComa() {
        LogicalSymbol prevSymbol = getEquationInteractor.execute().getPrev();
        if (prevSymbol == null) {
            if (logicalEquationChecker.isNotAlreadyDouble()) {
                makeNumberDouble();
            }
        } else if (!logicalEquationChecker.isFloatingPartOfDouble(getEquationInteractor.execute().getCursorPosition() - 1) && prevSymbol.isDigit()) {
            getEquationInteractor.execute().add(LogicalSymbol.COMA);
        } else if (!prevSymbol.isDigit() && !prevSymbol.isConstant() && logicalEquationChecker.isNotAlreadyDouble()) {
            makeNumberDouble();
        }
    }

    private void makeNumberDouble() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        LogicalSymbol nextSymbol = logicalEquation.getNext();
        if (nextSymbol != null) {
            if (nextSymbol.isDigit()) {
                logicalEquation.add(LogicalSymbol.ZERO);
                logicalEquation.add(LogicalSymbol.COMA);
            }
        } else {
            logicalEquation.add(LogicalSymbol.ZERO);
            logicalEquation.add(LogicalSymbol.COMA);
        }
    }

    private void addFunction(LogicalSymbol function) throws NotValidPowUsageException {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        if (function == LogicalSymbol.POW_MINUS_ONE) {
            LogicalSymbol prevSymbol = getEquationInteractor.execute().getPrev();
            if (prevSymbol != null) {
                if (prevSymbol == LogicalSymbol.BRACKET_CLOSE || prevSymbol.isUnaryOperator() ||
                        prevSymbol.isDigit() || prevSymbol.isConstant()) {
                    getEquationInteractor.execute().add(LogicalSymbol.MULTIPLICATION);
                }
            }
            logicalEquation.add(LogicalSymbol.BRACKET_OPEN);
            logicalEquation.incrementNotClosedBracketsCount();
            logicalEquation.add(LogicalSymbol.ONE);
            logicalEquation.add(LogicalSymbol.DIVISION);
        } else if (function == LogicalSymbol.X_POW_Y) {
            LogicalSymbol prevSymbol = getEquationInteractor.execute().getPrev();
            if (prevSymbol != null) {
                if (prevSymbol.isDigit() || prevSymbol.isConstant()) {
                    logicalEquation.add(LogicalSymbol.POW);
                    logicalEquation.incrementNotClosedBracketsCount();
                } else {
                    throw new NotValidPowUsageException();
                }
            } else {
                throw new NotValidPowUsageException();
            }
        } else if (function == LogicalSymbol.E_POW_X) {
            addConstant(LogicalSymbol.E);
            logicalEquation.add(LogicalSymbol.POW);
            logicalEquation.incrementNotClosedBracketsCount();
        } else if (function == LogicalSymbol.X_POW_2) {
            LogicalSymbol prevSymbol = logicalEquation.getPrev();
            if (prevSymbol == null) {
                throw new NotValidPowUsageException();
            }
            if (prevSymbol.isDigit() || prevSymbol.isConstant()) {
                logicalEquation.add(LogicalSymbol.POW);
                logicalEquation.add(LogicalSymbol.TWO);
                logicalEquation.add(LogicalSymbol.BRACKET_CLOSE);
            } else {
                throw new NotValidPowUsageException();
            }
        } else {
            LogicalSymbol prevSymbol = logicalEquation.getPrev();
            if (prevSymbol != null) {
                if (prevSymbol.isComa()) {
                    logicalEquation.remove();
                }
                if (prevSymbol.isDigit() || prevSymbol == LogicalSymbol.BRACKET_CLOSE) {
                    logicalEquation.add(LogicalSymbol.MULTIPLICATION);
                }
            }
            logicalEquation.incrementNotClosedBracketsCount();
            addDigit(function);
        }
    }

    private void addConstant(LogicalSymbol constant) {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        LogicalSymbol prevSymbol = logicalEquation.getPrev();
        if (prevSymbol != null) {
            if (prevSymbol.isComa()) {
                logicalEquation.remove();
            } else if (prevSymbol.isDigit()) {
                logicalEquation.add(LogicalSymbol.MULTIPLICATION);
            }
        }
        addDigit(constant);
    }

    private void addBinaryOperator(LogicalSymbol operator) {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        LogicalSymbol prevSymbol = logicalEquation.getPrev();
        if (prevSymbol == null) {
            if (operator == LogicalSymbol.MINUS) {
                logicalEquation.add(operator);
            }
        } else {
            if (prevSymbol != LogicalSymbol.BRACKET_OPEN && !prevSymbol.isFunction()) {
                if (prevSymbol.isBinaryOperator() || prevSymbol.isComa()) {
                    logicalEquation.remove();
                }
                prevSymbol = logicalEquation.getPrev();
                if (prevSymbol != null) {
                    if (prevSymbol != LogicalSymbol.BRACKET_OPEN) {
                        logicalEquation.add(operator);
                    }
                }
            } else if (operator == LogicalSymbol.MINUS) {
                logicalEquation.add(operator);
            }
        }
    }

    private void addUnaryOperator(LogicalSymbol operator) {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        LogicalSymbol prevSymbol = logicalEquation.getPrev();
        if (prevSymbol == null) {
            return;
        }
        if (!prevSymbol.isBinaryOperator() && prevSymbol != LogicalSymbol.BRACKET_OPEN) {
            logicalEquation.add(operator);
        }
    }

    private void addUnaryMinus() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        for (int i = logicalEquation.getCursorPosition() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (i == 0 || (!symbol.isPartOfNumber() && !symbol.isConstant())) {
                if (i == 0 && (symbol.isPartOfNumber() || symbol.isConstant())) {
                    i--;
                }
                logicalEquation.add(i + 1, LogicalSymbol.BRACKET_OPEN);
                logicalEquation.add(i + 2, LogicalSymbol.MINUS);
                logicalEquation.add(LogicalSymbol.BRACKET_CLOSE);
                break;
            }
        }
    }

    private void removeUnaryMinus() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        for (int i = logicalEquation.getCursorPosition() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol == LogicalSymbol.BRACKET_CLOSE) {
                logicalEquation.remove(i);
                break;
            }
        }
        for (int i = logicalEquation.getCursorPosition() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol == LogicalSymbol.BRACKET_OPEN) {
                logicalEquation.remove(i);
                break;
            }
        }
        for (int i = logicalEquation.getCursorPosition() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol == LogicalSymbol.MINUS) {
                logicalEquation.remove(i);
                break;
            }
        }
    }

    private void removeBracketOpen() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        logicalEquation.decrementNotClosedBracketsCount();
        if (logicalEquation.getCursorPosition() < logicalEquation.size()) {
            checkToRight();
        }
        logicalEquation.remove();
    }

    private void removeBracketClose() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        logicalEquation.incrementNotClosedBracketsCount();
        checkToRight();
        logicalEquation.remove();
    }

    private void removeZero() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        if (logicalEquation.size() == 1) {
            logicalEquation.remove();
        } else {
            removeDigit();
        }
    }

    private void removeDigit() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        if (logicalEquation.getCursorPosition() == 1 && logicalEquation.size() > 1) {
            checkToRight();
        } else if (logicalEquation.size() > logicalEquation.getCursorPosition()) {
            checkToRight();
            checkToLeft();
        }
        logicalEquation.remove();
    }

    private void removeFunction() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        for (int i = logicalEquation.getCursorPosition(); i < logicalEquation.size(); i++) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (!symbol.isPartOfNumber()) {
                if (symbol == LogicalSymbol.BRACKET_CLOSE) {
                    logicalEquation.remove(i);
                    logicalEquation.incrementNotClosedBracketsCount();
                    break;
                } else if (symbol.isUnaryOperator() || symbol.isBinaryOperator()) {
                    if (symbol == LogicalSymbol.MINUS && i != 0) {
                        LogicalSymbol prevSymbol = logicalEquation.get(i - 1);
                        if (prevSymbol != LogicalSymbol.BRACKET_OPEN) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        removeDigit();
        logicalEquation.decrementNotClosedBracketsCount();
    }

    private void removeComa() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        if (logicalEquation.getCursorPosition() < 2) {
            logicalEquation.remove();
            return;
        }
        LogicalSymbol prevSymbol = logicalEquation.get(logicalEquation.getCursorPosition() - 2);
        if (prevSymbol == LogicalSymbol.ZERO) {
            logicalEquation.remove();
            logicalEquation.remove();
            while (logicalEquation.getCursorPosition() < logicalEquation.size() && logicalEquation.getNext() == LogicalSymbol.ZERO) {
                logicalEquation.removeNext();
            }
        } else {
            logicalEquation.remove();
        }
    }

    private void checkToRight() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        if (logicalEquation.getCursorPosition() == logicalEquation.size()) {
            return;
        }
        if (logicalEquation.getCursorPosition() == 1 || (logicalEquation.getCursorPosition() > 1 &&
                !logicalEquation.get(logicalEquation.getCursorPosition() - 2).isDigit())) {
            LogicalSymbol nextSymbol = logicalEquation.getNext();
            if (nextSymbol != null) {
                if (nextSymbol.isBinaryOperator() || nextSymbol.isUnaryOperator()) {
                    logicalEquation.removeNext();
                } else if (nextSymbol == LogicalSymbol.COMA) {
                    logicalEquation.removeNext();
                    while (logicalEquation.getCursorPosition() < logicalEquation.size() && logicalEquation.getNext() == LogicalSymbol.ZERO) {
                        logicalEquation.removeNext();
                    }
                }
            }
        }
    }

    private void checkToLeft() {
        LogicalEquation logicalEquation = getEquationInteractor.execute();
        if (logicalEquation.getCursorPosition() == 0) {
            return;
        }
        LogicalSymbol prevSymbol = logicalEquation.get(logicalEquation.getCursorPosition() - 2);
        LogicalSymbol nextSymbol = logicalEquation.getNext();
        if (nextSymbol != null) {
            if (prevSymbol.isBinaryOperator() && (nextSymbol.isBinaryOperator() || nextSymbol.isUnaryOperator())) {
                logicalEquation.remove(logicalEquation.getCursorPosition() - 2);
            } else if (prevSymbol.isComa() && !nextSymbol.isDigit()) {
                logicalEquation.remove(logicalEquation.getCursorPosition() - 2);
            }
        }
    }
}
