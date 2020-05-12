package com.senyk.volodymyr.calculator.presentation.viewmodel.mapper;

import android.content.Context;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.domain.entity.LogicalSymbol;
import com.senyk.volodymyr.calculator.presentation.viewmodel.util.AttrValuesProvisionUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SymbolMapper {

    private final Resources resources;
    private Resources.Theme theme;

    public void setTheme(Resources.Theme theme) {
        this.theme = theme;
    }

    @Inject
    SymbolMapper(Context context) {
        this.theme = context.getTheme();
        this.resources = context.getResources();
    }

    public SpannableString convertToUi(List<LogicalSymbol> logicalSymbols, int colorAttr) {
        final String string = convertToUi(logicalSymbols);
        final SpannableString spannableString = new SpannableString(string);
        final ForegroundColorSpan color = new ForegroundColorSpan(AttrValuesProvisionUtil.getThemeColor(theme, colorAttr));
        spannableString.setSpan(color, 0, spannableString.length(), SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public SpannableString convertToUi(LogicalSymbol logicalSymbol, int colorAttr) {
        List<LogicalSymbol> logicalSymbols = new ArrayList<>(1);
        logicalSymbols.add(logicalSymbol);
        return convertToUi(logicalSymbols, colorAttr);
    }

    public String convertToUi(List<LogicalSymbol> logicalSymbols) {
        StringBuilder builder = new StringBuilder();
        for (LogicalSymbol symbol : logicalSymbols) {
            builder.append(convertToUi(symbol));
        }
        return new String(builder);
    }

    public String convertToUi(LogicalSymbol logicalSymbol) {
        switch (logicalSymbol) {
            case ZERO:
                return resources.getString(R.string.calc_0);
            case ONE:
                return resources.getString(R.string.calc_1);
            case TWO:
                return resources.getString(R.string.calc_2);
            case THREE:
                return resources.getString(R.string.calc_3);
            case FOUR:
                return resources.getString(R.string.calc_4);
            case FIVE:
                return resources.getString(R.string.calc_5);
            case SIX:
                return resources.getString(R.string.calc_6);
            case SEVEN:
                return resources.getString(R.string.calc_7);
            case EIGHT:
                return resources.getString(R.string.calc_8);
            case NINE:
                return resources.getString(R.string.calc_9);
            case PLUS:
                return resources.getString(R.string.calc_plus);
            case MINUS:
                return resources.getString(R.string.calc_minus);
            case MULTIPLICATION:
                return resources.getString(R.string.calc_mult);
            case DIVISION:
                return resources.getString(R.string.calc_div);
            case COMA:
                return resources.getString(R.string.calc_coma);
            case BRACKET_OPEN:
                return resources.getString(R.string.calc_bracket_open);
            case BRACKET_CLOSE:
                return resources.getString(R.string.calc_bracket_close);
            case ROOT:
                return resources.getString(R.string.calc_root);
            case POW:
                return resources.getString(R.string.calc_pow);
            case ABS:
                return resources.getString(R.string.calc_abs);
            case SIN:
                return resources.getString(R.string.calc_sin);
            case COS:
                return resources.getString(R.string.calc_cos);
            case TAN:
                return resources.getString(R.string.calc_tan);
            case LN:
                return resources.getString(R.string.calc_ln);
            case LOG:
                return resources.getString(R.string.calc_log);
            case PI:
                return resources.getString(R.string.calc_pi);
            case E:
                return resources.getString(R.string.calc_e);
            case PERCENT:
                return resources.getString(R.string.calc_percent);
            case EQU:
                return resources.getString(R.string.calc_equ);
        }
        throw new IllegalArgumentException("No such logical symbol");
    }

    public String convertToEquation(List<LogicalSymbol> logicalSymbols) {
        StringBuilder builder = new StringBuilder();
        for (LogicalSymbol symbol : logicalSymbols) {
            builder.append(convertToEquation(symbol));
        }
        return new String(builder);
    }

    public String convertToEquation(LogicalSymbol logicalSymbol) {
        switch (logicalSymbol) {
            case ZERO:
                return "0";
            case ONE:
                return "1";
            case TWO:
                return "2";
            case THREE:
                return "3";
            case FOUR:
                return "4";
            case FIVE:
                return "5";
            case SIX:
                return "6";
            case SEVEN:
                return "7";
            case EIGHT:
                return "8";
            case NINE:
                return "9";
            case COMA:
                return ".";
            case PLUS:
                return "+";
            case MINUS:
                return "-";
            case MULTIPLICATION:
                return "*";
            case DIVISION:
                return "/";
            case BRACKET_OPEN:
                return "(";
            case BRACKET_CLOSE:
                return ")";
            case ROOT:
                return "Math.sqrt(";
            case POW:
                return "Math.pow(";
            case ABS:
                return "Math.abs(";
            case SIN:
                return "Math.sin(";
            case COS:
                return "Math.cos(";
            case TAN:
                return "Math.tan(";
            case LN:
                return "Math.log(";
            case LOG:
                return "Math.log10(";
            case PERCENT:
                return "/100 ";
            case PI:
                return "Math.PI";
            case E:
                return "Math.E";
        }
        throw new IllegalArgumentException("No such logical symbol");
    }

    public List<LogicalSymbol> convertToLogical(Spannable equation) {
        return convertToLogical(equation.toString());
    }

    public List<LogicalSymbol> convertToLogical(String equation) {
        final char sin = 'ₛ';
        final char cos = 'ᶜ';
        final char tan = 'ₜ';
        final char root = 'ᵣ';
        final char pow = 'ₚ';
        final char log = 'ₗ';
        final char ln = 'ₙ';
        equation = equation.replace("sin(", sin + "");
        equation = equation.replace("cos(", cos + "");
        equation = equation.replace("tan(", tan + "");
        equation = equation.replace("√(", root + "");
        equation = equation.replace("^(", pow + "");
        equation = equation.replace("log(", log + "");
        equation = equation.replace("ln(", ln + "");
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
                case '−':
                    logicalEquation.add(LogicalSymbol.MINUS);
                    break;
                case '.':
                case ',':
                    logicalEquation.add(LogicalSymbol.COMA);
                    break;
                case '+':
                    logicalEquation.add(LogicalSymbol.PLUS);
                    break;
                case '*':
                case '×':
                    logicalEquation.add(LogicalSymbol.MULTIPLICATION);
                    break;
                case '/':
                case '÷':
                    logicalEquation.add(LogicalSymbol.DIVISION);
                    break;
                case '%':
                    logicalEquation.add(LogicalSymbol.PERCENT);
                    break;
                case 'π':
                    logicalEquation.add(LogicalSymbol.PI);
                    break;
                case 'e':
                    logicalEquation.add(LogicalSymbol.E);
                    break;
                case '(':
                    logicalEquation.add(LogicalSymbol.BRACKET_OPEN);
                    break;
                case ')':
                    logicalEquation.add(LogicalSymbol.BRACKET_CLOSE);
                    break;
                case sin:
                    logicalEquation.add(LogicalSymbol.SIN);
                    break;
                case cos:
                    logicalEquation.add(LogicalSymbol.COS);
                    break;
                case tan:
                    logicalEquation.add(LogicalSymbol.TAN);
                    break;
                case root:
                    logicalEquation.add(LogicalSymbol.ROOT);
                    break;
                case pow:
                    logicalEquation.add(LogicalSymbol.POW);
                    break;
                case log:
                    logicalEquation.add(LogicalSymbol.LOG);
                    break;
                case ln:
                    logicalEquation.add(LogicalSymbol.LN);
                    break;
            }
        }
        return logicalEquation;
    }
}
