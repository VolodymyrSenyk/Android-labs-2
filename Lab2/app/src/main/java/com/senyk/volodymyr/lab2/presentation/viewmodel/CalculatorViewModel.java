package com.senyk.volodymyr.lab2.presentation.viewmodel;

import android.text.Spannable;
import android.text.SpannableString;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.senyk.volodymyr.lab2.domain.entity.LogicalSymbol;
import com.senyk.volodymyr.lab2.presentation.viewmodel.mapper.SymbolMapper;
import com.senyk.volodymyr.lab2.presentation.viewmodel.util.ResourcesProvider;
import com.senyk.volodymyr.lab2.presentation.viewmodel.util.SingleEventLiveData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class CalculatorViewModel extends ViewModel {
    private static final String EMPTY_STRING = "";

    private static final int MAX_SYMBOLS_COUNT = 60;

    private static final int RESULT_NUMBER_SCALE = 10;

    private MutableLiveData<Spannable> currentEquation = new MutableLiveData<>();

    public LiveData<Spannable> currentEquation() {
        return currentEquation;
    }

    private MutableLiveData<String> currentResult = new MutableLiveData<>();

    public LiveData<String> currentResult() {
        return currentResult;
    }

    private SingleEventLiveData<String> message = new SingleEventLiveData<>();

    public LiveData<String> message() {
        return message;
    }

    private final ScriptEngine scriptEngine;
    private final ResourcesProvider resourcesProvider;
    private final SymbolMapper symbolMapper;

    private List<LogicalSymbol> logicalEquation;
    private int numOfNotClosedBrackets;

    public CalculatorViewModel(ScriptEngine scriptEngine, ResourcesProvider resourcesProvider, SymbolMapper symbolMapper) {
        this.scriptEngine = scriptEngine;
        this.resourcesProvider = resourcesProvider;
        this.symbolMapper = symbolMapper;
        this.logicalEquation = new ArrayList<>();
        this.numOfNotClosedBrackets = 0;

        currentEquation.setValue(new SpannableString(EMPTY_STRING));
        currentResult.setValue(EMPTY_STRING);
    }

    public void onSymbolClick(LogicalSymbol symbol) {
        if (logicalEquation.size() == MAX_SYMBOLS_COUNT) {
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
        } else if (symbol.isUnaryOperator()) {
            addUnaryOperator(symbol);
        } else if (symbol.isBinaryOperator()) {
            addBinaryOperator(symbol);
        }
        updateUi();
    }

    public void onInverseClick() {
        if (logicalEquation.isEmpty()) {
            return;
        }
        LogicalSymbol symbol = getLastSymbol();
        if (isWithUnaryMinus()) {
            removeUnaryMinus();
        } else if (symbol.isDigit()) {
            addUnaryMinus();
        }
        updateUi();
    }

    public void onBracketsClick() {
        if (logicalEquation.isEmpty()) {
            addSymbol(LogicalSymbol.BRACKET_OPEN);
            numOfNotClosedBrackets++;
        } else {
            LogicalSymbol prevSymbol = getLastSymbol();
            if (numOfNotClosedBrackets <= 0) {
                if (prevSymbol == LogicalSymbol.BRACKET_CLOSE || prevSymbol.isUnaryOperator() || prevSymbol.isDigit()) {
                    addSymbol(LogicalSymbol.MULTIPLICATION);
                }
                addSymbol(LogicalSymbol.BRACKET_OPEN);
                numOfNotClosedBrackets++;
            } else if (prevSymbol == LogicalSymbol.BRACKET_OPEN) {
                addSymbol(LogicalSymbol.BRACKET_OPEN);
                numOfNotClosedBrackets++;
            } else {
                addSymbol(LogicalSymbol.BRACKET_CLOSE);
                numOfNotClosedBrackets--;
            }
        }
        updateUi();
    }

    public void onDeleteClick() {
        if (logicalEquation.isEmpty()) {
            return;
        }
        LogicalSymbol symbolToRemove = getLastSymbol();
        if (symbolToRemove == LogicalSymbol.BRACKET_OPEN) {
            numOfNotClosedBrackets--;
            removeSymbol();
        } else if (symbolToRemove == LogicalSymbol.BRACKET_CLOSE) {
            numOfNotClosedBrackets++;
            removeSymbol();
        } else if (symbolToRemove.isComa()) {
            removeComa();
        } else if (symbolToRemove.isFunction()) {
            removeSymbol();
            numOfNotClosedBrackets--;
        } else if (symbolToRemove.isDigit()) {
            removeSymbol();
        } else {
            removeSymbol();
        }
        updateUi();
    }

    public void onEquClick() {
        performCalculations();
    }

    public void onClearClick() {
        logicalEquation.clear();
        numOfNotClosedBrackets = 0;
        updateUi();
    }

    private void addZero() {
        if (logicalEquation.isEmpty()) {
            addSymbol(LogicalSymbol.ZERO);
        } else if (isFloatingPartOfDouble()) {
            addDigit(LogicalSymbol.ZERO);
        } else if (!isAlreadyZero()) {
            addDigit(LogicalSymbol.ZERO);
        }
    }

    private void addDigit(LogicalSymbol digit) {
        if (!logicalEquation.isEmpty()) {
            LogicalSymbol prevSymbol = getLastSymbol();
            if (prevSymbol == LogicalSymbol.BRACKET_CLOSE || prevSymbol.isUnaryOperator()) {
                addSymbol(LogicalSymbol.MULTIPLICATION);
            } else if (prevSymbol == LogicalSymbol.ZERO) {
                if (isZeroFirstInNumber()) {
                    removeSymbol();
                }
            }
        }
        addSymbol(digit);
    }

    private void addComa() {
        if (!logicalEquation.isEmpty()) {
            LogicalSymbol prevSymbol = getLastSymbol();
            if (!isFloatingPartOfDouble() && prevSymbol.isDigit()) {
                addSymbol(LogicalSymbol.COMA);
            } else if (!prevSymbol.isDigit() && !isAlreadyDouble()) {
                makeNumberDouble();
            }
        } else if (!isAlreadyDouble()) {
            makeNumberDouble();
        }
    }

    private void makeNumberDouble() {
        addSymbol(LogicalSymbol.ZERO);
        addSymbol(LogicalSymbol.COMA);
    }

    private void addFunction(LogicalSymbol function) {
        if (!logicalEquation.isEmpty()) {
            LogicalSymbol prevSymbol = getLastSymbol();
            if (prevSymbol.isComa()) {
                removeSymbol();
            }
            if (prevSymbol.isDigit() || prevSymbol == LogicalSymbol.BRACKET_CLOSE) {
                addSymbol(LogicalSymbol.MULTIPLICATION);
            }
        }
        numOfNotClosedBrackets++;
        addDigit(function);
    }

    private void addBinaryOperator(LogicalSymbol operator) {
        if (!logicalEquation.isEmpty()) {
            LogicalSymbol prevSymbol = getLastSymbol();
            if (prevSymbol != LogicalSymbol.BRACKET_OPEN && !prevSymbol.isFunction()) {
                if (prevSymbol.isBinaryOperator() || prevSymbol.isComa()) {
                    removeSymbol();
                }
                if (!logicalEquation.isEmpty()) {
                    prevSymbol = getLastSymbol();
                    if (prevSymbol != LogicalSymbol.BRACKET_OPEN) {
                        addSymbol(operator);
                    }
                }
            } else if (operator == LogicalSymbol.MINUS) {
                addSymbol(operator);
            }
        } else if (operator == LogicalSymbol.MINUS) {
            addSymbol(operator);
        }
    }

    private void addUnaryOperator(LogicalSymbol operator) {
        if (!logicalEquation.isEmpty()) {
            LogicalSymbol prevSymbol = getLastSymbol();
            if (!prevSymbol.isBinaryOperator() && prevSymbol != LogicalSymbol.BRACKET_OPEN) {
                addSymbol(operator);
            }
        }
    }

    private void addUnaryMinus() {
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (i == 0 || (!symbol.isPartOfNumber())) {
                if (i == 0 && (symbol.isPartOfNumber())) {
                    i--;
                }
                addSymbol(i + 1, LogicalSymbol.BRACKET_OPEN);
                addSymbol(i + 2, LogicalSymbol.MINUS);
                addSymbol(LogicalSymbol.BRACKET_CLOSE);
                break;
            }
        }
    }

    private void removeUnaryMinus() {
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol == LogicalSymbol.BRACKET_CLOSE) {
                removeSymbol(i);
                break;
            }
        }
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol == LogicalSymbol.BRACKET_OPEN) {
                removeSymbol(i);
                break;
            }
        }
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol == LogicalSymbol.MINUS) {
                removeSymbol(i);
                break;
            }
        }
    }

    private void removeComa() {
        if (logicalEquation.size() < 2) {
            removeSymbol();
            return;
        }
        removeSymbol();
        if (getLastSymbol() == LogicalSymbol.ZERO) {
            removeSymbol();
        }
    }

    private boolean isFloatingPartOfDouble() {
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol.isComa()) {
                return true;
            } else if (!symbol.isDigit()) {
                return false;
            }
        }
        return false;
    }

    private boolean isZeroFirstInNumber() {
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if ((symbol.isDigit() && symbol != LogicalSymbol.ZERO) || symbol.isComa()) {
                return false;
            } else if (!symbol.isDigit()) {
                return true;
            }
        }
        return true;
    }

    private boolean isAlreadyZero() {
        if (logicalEquation.isEmpty()) {
            return false;
        } else {
            return getLastSymbol() == LogicalSymbol.ZERO;
        }
    }

    private boolean isAlreadyDouble() {
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol.isComa()) {
                return true;
            } else if (!symbol.isDigit()) {
                return false;
            }
        }
        return false;
    }

    private boolean isWithUnaryMinus() {
        List<LogicalSymbol> symbolsAroundNumber = new ArrayList<>();
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
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

    private void addSymbol(LogicalSymbol symbol) {
        logicalEquation.add(symbol);
    }

    private void addSymbol(int symbolIndex, LogicalSymbol symbol) {
        logicalEquation.add(symbolIndex, symbol);
    }

    private void removeSymbol() {
        if (!logicalEquation.isEmpty()) {
            logicalEquation.remove(getLastSymbol());
        }
    }

    private void removeSymbol(int symbolIndex) {
        if (symbolIndex < logicalEquation.size()) {
            logicalEquation.remove(symbolIndex);
        }
    }

    private void updateUi() {
        currentEquation.setValue(symbolMapper.convertToUi(logicalEquation));
        performRuntimeCalculations();
    }

    private void performRuntimeCalculations() {
        String result = getCalculationsResult();
        if (result == null) {
            currentResult.setValue(EMPTY_STRING);
        } else {
            String formattedResult = formatResult(result);
            List<LogicalSymbol> logicalResult = symbolMapper.convertToLogical(formattedResult);
            String uiResult = symbolMapper.convertToUi(logicalResult).toString();
            currentResult.setValue(uiResult);
        }
    }

    private void performCalculations() {
        if (logicalEquation.isEmpty()) {
            return;
        }
        currentResult.setValue(EMPTY_STRING);
        String result = getCalculationsResult();
        if (result == null) {
            message.setValue(resourcesProvider.getNotValidEquationErrorMessage());
        } else {
            String formattedResult = formatResult(result);
            logicalEquation = symbolMapper.convertToLogical(formattedResult);
            updateUi();
        }
    }

    private String getCalculationsResult() {
        String calculationsResult;
        try {
            Object scriptResult = scriptEngine.eval(
                    symbolMapper.convertToEquation(new ArrayList<>(logicalEquation), numOfNotClosedBrackets)
            );
            if (scriptResult == null) {
                calculationsResult = null;
            } else {
                calculationsResult = scriptResult.toString();
            }
        } catch (ScriptException e) {
            calculationsResult = null;
        }
        return calculationsResult;
    }

    private String formatResult(String result) {
        try {
            BigDecimal decimal = new BigDecimal(result);
            decimal = decimal.setScale(RESULT_NUMBER_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
            if (decimal.compareTo(new BigDecimal(0)) == 0) {
                result = "0";
            } else {
                result = decimal.toPlainString();
            }
        } catch (NumberFormatException e) {
            result = EMPTY_STRING;
        }
        return result;
    }

    private LogicalSymbol getLastSymbol() {
        return logicalEquation.get(logicalEquation.size() - 1);
    }
}
