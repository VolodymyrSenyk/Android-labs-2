package com.senyk.volodymyr.calculator.presentation.viewmodel.mapper;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;
import com.senyk.volodymyr.calculator.domain.entity.LogicalSymbol;

import java.util.List;

import javax.inject.Inject;

public class EquationMapper {

    private final static String CONVERT_TO_RADIANS = "/180.0*Math.PI";

    private final SymbolMapper symbolMapper;

    public SymbolMapper getSymbolMapper() {
        return symbolMapper;
    }

    @Inject
    EquationMapper(SymbolMapper symbolMapper) {
        this.symbolMapper = symbolMapper;
    }

    public String convertToEquation(@NonNull final LogicalEquation logicalEquation) {

        fixLogicalEquation(logicalEquation);

        boolean isTrigonometricFunctionArgument = false;
        boolean isSimpleFunctionArgument = false;
        boolean isPowFunctionFirstArgument = false;
        String powFunctionFirstArgument = "";
        boolean isPowFunctionSecondArgument = false;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < logicalEquation.size(); i++) {
            String number = "";
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol.isConstant()) {
                number = symbolMapper.convertToEquation(symbol);
                if (i < logicalEquation.size() - 1) {
                    LogicalSymbol nextSymbol = logicalEquation.get(i + 1);
                    if (nextSymbol == LogicalSymbol.POW) {
                        powFunctionFirstArgument = number;
                        number = "";
                    }
                }
            } else if (symbol.isPartOfNumber()) {
                int j = i;
                for (; j < logicalEquation.size(); j++) {
                    LogicalSymbol nextSymbol = logicalEquation.get(j);
                    if (nextSymbol == LogicalSymbol.POW) {
                        isPowFunctionFirstArgument = true;
                        break;
                    } else if (!nextSymbol.isPartOfNumber()) {
                        break;
                    }
                }
                number = symbolMapper.convertToEquation(logicalEquation.getText().subList(i, j));
                if (isPowFunctionFirstArgument) {
                    powFunctionFirstArgument = number;
                    isPowFunctionFirstArgument = false;
                    number = "";
                }
                i = j;
                i--;
            }

            if (symbol.isConstant() || symbol.isPartOfNumber()) {
                if (isTrigonometricFunctionArgument) {
                    builder.append(number);
                    isTrigonometricFunctionArgument = false;
                    if (logicalEquation.isCurrentUnitDegrees()) {
                        builder.append(CONVERT_TO_RADIANS);
                    }
                } else if (isSimpleFunctionArgument) {
                    builder.append(number);
                    isSimpleFunctionArgument = false;
                } else if (isPowFunctionSecondArgument) {
                    builder.append(powFunctionFirstArgument);
                    builder.append(",");
                    builder.append(number);
                    isPowFunctionSecondArgument = false;
                } else {
                    builder.append(number);
                }
            } else if (symbol.isSimpleFunction()) {
                builder.append(symbolMapper.convertToEquation(symbol));
                isSimpleFunctionArgument = true;
            } else if (symbol.isPowFunction()) {
                builder.append(symbolMapper.convertToEquation(symbol));
                isPowFunctionSecondArgument = true;
            } else if (symbol.isTrigonometricFunction()) {
                builder.append(symbolMapper.convertToEquation(symbol));
                isTrigonometricFunctionArgument = true;
            } else {
                builder.append(symbolMapper.convertToEquation(symbol));
            }
        }
        String result = new String(builder);
        Log.d("EQUATION_TO_CALCULATE", result);
        return result;
    }

    public List<LogicalSymbol> convertToLogical(String equation) {
        return symbolMapper.convertToLogical(equation);
    }

    public Spannable convertToUi(@NonNull final LogicalEquation logicalEquation) {
        return convertToUi(logicalEquation.getText());
    }

    public Spannable convertToUi(@NonNull final List<LogicalSymbol> logicalEquation) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < logicalEquation.size(); i++) {
            LogicalSymbol logicalSymbol = logicalEquation.get(i);
            if (logicalSymbol.isBinaryOperator() || logicalSymbol.isUnaryOperator()) {
                builder.append(symbolMapper.convertToUi(logicalSymbol, R.attr.colorText));
            } else {
                builder.append(symbolMapper.convertToUi(logicalSymbol, R.attr.colorTextDark));
            }
        }
        return builder;
    }

    public void fixLogicalEquation(@NonNull final LogicalEquation logicalEquation) {
        removeExcessFunctionsAndOperators(logicalEquation);
        addCloseBrackets(logicalEquation);
        removeExcessBrackets(logicalEquation);
        addMultiplications(logicalEquation);
    }

    private void removeExcessFunctionsAndOperators(@NonNull final LogicalEquation logicalEquation) {
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol.isFunction() || symbol.isBinaryOperator()) {
                logicalEquation.remove(i);
                if (symbol.isFunction()) {
                    logicalEquation.decrementNotClosedBracketsCount();
                }
            } else {
                break;
            }
        }
    }

    private void removeExcessBrackets(@NonNull final LogicalEquation logicalEquation) {
        for (int i = 0; i < logicalEquation.size(); i++) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol == LogicalSymbol.BRACKET_CLOSE) {
                logicalEquation.remove(i);
                i--;
                logicalEquation.incrementNotClosedBracketsCount();
            } else if (symbol == LogicalSymbol.BRACKET_OPEN || symbol.isFunction()) {
                break;
            }
        }
    }

    private void addCloseBrackets(@NonNull final LogicalEquation logicalEquation) {
        if (logicalEquation.getNumberOfNotClosedBrackets() > 0) {
            for (int i = 0; i < logicalEquation.getNumberOfNotClosedBrackets(); i++) {
                logicalEquation.add(LogicalSymbol.BRACKET_CLOSE);
            }
        }
    }

    private void addMultiplications(@NonNull final LogicalEquation logicalEquation) {
        for (int i = 0; i < logicalEquation.size() - 1; i++) {
            LogicalSymbol symbol = logicalEquation.get(i);
            LogicalSymbol nextSymbol = logicalEquation.get(i + 1);
            if ((symbol == LogicalSymbol.BRACKET_CLOSE || symbol.isDigit() || symbol.isConstant() || symbol.isUnaryOperator()) &&
                    (nextSymbol == LogicalSymbol.BRACKET_OPEN || nextSymbol.isDigit() || nextSymbol.isConstant() || nextSymbol.isFunction())) {
                if (!nextSymbol.isPowFunction()) {
                    if (!symbol.isDigit() && !nextSymbol.isDigit()) {
                        logicalEquation.add(i + 1, LogicalSymbol.MULTIPLICATION);
                    }
                }
            }
        }
    }
}
