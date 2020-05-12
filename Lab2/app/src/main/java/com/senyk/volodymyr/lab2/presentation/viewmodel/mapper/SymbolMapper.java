package com.senyk.volodymyr.lab2.presentation.viewmodel.mapper;

import android.content.Context;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.senyk.volodymyr.lab2.R;
import com.senyk.volodymyr.lab2.domain.entity.LogicalSymbol;

import java.util.ArrayList;
import java.util.List;

public class SymbolMapper {

    private final Context context;
    private final Resources resources;

    public SymbolMapper(Context context) {
        this.context = context;
        this.resources = context.getResources();
    }

    public String convertToEquation(List<LogicalSymbol> logicalEquation, int numberOfNotClosedBrackets) {
        fixLogicalEquation(logicalEquation, numberOfNotClosedBrackets);
        StringBuilder builder = new StringBuilder();
        for (LogicalSymbol symbol : logicalEquation) {
            builder.append(convertLogicalSymbols(symbol));
        }
        Log.e("TAG", new String(builder));
        return new String(builder);
    }

    public List<LogicalSymbol> convertToLogical(String equation) {
        List<LogicalSymbol> logicalEquation = new ArrayList<>();
        char[] equationChars = equation.toCharArray();
        for (char equationChar : equationChars) {
            switch (equationChar) {
                case '0':
                    logicalEquation.add(LogicalSymbol.ZERO);
                    break;
                case '1':
                    logicalEquation.add(LogicalSymbol.ONE);
                    break;
                case '2':
                    logicalEquation.add(LogicalSymbol.TWO);
                    break;
                case '3':
                    logicalEquation.add(LogicalSymbol.THREE);
                    break;
                case '4':
                    logicalEquation.add(LogicalSymbol.FOUR);
                    break;
                case '5':
                    logicalEquation.add(LogicalSymbol.FIVE);
                    break;
                case '6':
                    logicalEquation.add(LogicalSymbol.SIX);
                    break;
                case '7':
                    logicalEquation.add(LogicalSymbol.SEVEN);
                    break;
                case '8':
                    logicalEquation.add(LogicalSymbol.EIGHT);
                    break;
                case '9':
                    logicalEquation.add(LogicalSymbol.NINE);
                    break;
                case '-':
                    logicalEquation.add(LogicalSymbol.MINUS);
                    break;
                case '.':
                    logicalEquation.add(LogicalSymbol.COMA);
                    break;
            }
        }
        return logicalEquation;
    }

    public Spannable convertToUi(List<LogicalSymbol> logicalEquation) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < logicalEquation.size(); i++) {
            switch (logicalEquation.get(i)) {
                case ZERO:
                    addSpannableSymbol(builder, R.string.calc_0, R.color.colorTextDark);
                    break;
                case ONE:
                    addSpannableSymbol(builder, R.string.calc_1, R.color.colorTextDark);
                    break;
                case TWO:
                    addSpannableSymbol(builder, R.string.calc_2, R.color.colorTextDark);
                    break;
                case THREE:
                    addSpannableSymbol(builder, R.string.calc_3, R.color.colorTextDark);
                    break;
                case FOUR:
                    addSpannableSymbol(builder, R.string.calc_4, R.color.colorTextDark);
                    break;
                case FIVE:
                    addSpannableSymbol(builder, R.string.calc_5, R.color.colorTextDark);
                    break;
                case SIX:
                    addSpannableSymbol(builder, R.string.calc_6, R.color.colorTextDark);
                    break;
                case SEVEN:
                    addSpannableSymbol(builder, R.string.calc_7, R.color.colorTextDark);
                    break;
                case EIGHT:
                    addSpannableSymbol(builder, R.string.calc_8, R.color.colorTextDark);
                    break;
                case NINE:
                    addSpannableSymbol(builder, R.string.calc_9, R.color.colorTextDark);
                    break;
                case PLUS:
                    addSpannableSymbol(builder, R.string.calc_plus, R.color.colorText);
                    break;
                case MINUS:
                    addSpannableSymbol(builder, R.string.calc_minus, R.color.colorText);
                    break;
                case MULTIPLICATION:
                    addSpannableSymbol(builder, R.string.calc_mult, R.color.colorText);
                    break;
                case DIVISION:
                    addSpannableSymbol(builder, R.string.calc_div, R.color.colorText);
                    break;
                case COMA:
                    addSpannableSymbol(builder, R.string.calc_coma, R.color.colorTextDark);
                    break;
                case BRACKET_OPEN:
                    addSpannableSymbol(builder, R.string.calc_bracket_open, R.color.colorTextDark);
                    break;
                case BRACKET_CLOSE:
                    addSpannableSymbol(builder, R.string.calc_bracket_close, R.color.colorTextDark);
                    break;
                case ROOT:
                    addSpannableSymbol(builder, R.string.calc_root, R.color.colorTextDark);
                    break;
            }
        }
        return builder;
    }

    private void fixLogicalEquation(List<LogicalSymbol> logicalEquation, int numberOfNotClosedBrackets) {
        removeExcessFunctionsAndOperators(logicalEquation);
        addCloseBrackets(logicalEquation, numberOfNotClosedBrackets);
        removeExcessBrackets(logicalEquation, numberOfNotClosedBrackets);
    }

    private void removeExcessFunctionsAndOperators(List<LogicalSymbol> logicalEquation) {
        for (int i = logicalEquation.size() - 1; i > -1; i--) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol.isFunction() || symbol.isBinaryOperator()) {
                logicalEquation.remove(i);
            } else {
                break;
            }
        }
    }

    private void removeExcessBrackets(List<LogicalSymbol> logicalEquation, int numberOfNotClosedBrackets) {
        for (int i = 0; i < logicalEquation.size(); i++) {
            LogicalSymbol symbol = logicalEquation.get(i);
            if (symbol == LogicalSymbol.BRACKET_CLOSE) {
                logicalEquation.remove(i);
                i--;
                numberOfNotClosedBrackets++;
            } else if (symbol == LogicalSymbol.BRACKET_OPEN || symbol.isFunction()) {
                break;
            }
        }
    }

    private void addCloseBrackets(List<LogicalSymbol> logicalEquation, int numberOfNotClosedBrackets) {
        if (numberOfNotClosedBrackets > 0) {
            for (int i = 0; i < numberOfNotClosedBrackets; i++) {
                logicalEquation.add(LogicalSymbol.BRACKET_CLOSE);
            }
        }
    }

    private String convertLogicalSymbols(LogicalSymbol logicalSymbol) {
        List<LogicalSymbol> list = new ArrayList<>(1);
        list.add(logicalSymbol);
        return convertLogicalSymbols(list);
    }

    private String convertLogicalSymbols(List<LogicalSymbol> logicalSymbolsList) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < logicalSymbolsList.size(); i++) {
            LogicalSymbol symbol = logicalSymbolsList.get(i);
            switch (symbol) {
                case ZERO:
                    builder.append("0");
                    break;
                case ONE:
                    builder.append("1");
                    break;
                case TWO:
                    builder.append("2");
                    break;
                case THREE:
                    builder.append("3");
                    break;
                case FOUR:
                    builder.append("4");
                    break;
                case FIVE:
                    builder.append("5");
                    break;
                case SIX:
                    builder.append("6");
                    break;
                case SEVEN:
                    builder.append("7");
                    break;
                case EIGHT:
                    builder.append("8");
                    break;
                case NINE:
                    builder.append("9");
                    break;
                case COMA:
                    builder.append(".");
                    break;
                case PLUS:
                    builder.append("+");
                    break;
                case MINUS:
                    builder.append("-");
                    break;
                case MULTIPLICATION:
                    builder.append("*");
                    break;
                case DIVISION:
                    builder.append("/");
                    break;
                case BRACKET_OPEN:
                    builder.append("(");
                    break;
                case BRACKET_CLOSE:
                    builder.append(")");
                    break;
                case ROOT:
                    builder.append("Math.sqrt(");
                    break;
            }
        }
        return new String(builder);
    }

    private void addSpannableSymbol(SpannableStringBuilder builder, int stringRes, int colorRes) {
        SpannableString spannableString = new SpannableString(resources.getString(stringRes));
        ForegroundColorSpan color = new ForegroundColorSpan(ContextCompat.getColor(context, colorRes));
        spannableString.setSpan(color, 0, spannableString.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
    }
}
