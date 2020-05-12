package com.senyk.volodymyr.calculator.presentation.viewmodel.mapper;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.domain.entity.LogicalEquation;

import javax.inject.Inject;

public class CursorMapper {

    private final Resources resources;

    @Inject
    CursorMapper(@NonNull final Context context) {
        this.resources = context.getResources();
    }

    public int convertToLogical(@NonNull final LogicalEquation logicalEquation, int newCursorPosition) {
        if (newCursorPosition == 0) {
            return 0;
        }
        int cursorPosition = 0;
        int tempCursor = 0;
        for (int i = 0; i < logicalEquation.size(); i++) {
            cursorPosition++;
            switch (logicalEquation.get(i)) {
                case ZERO:
                    tempCursor += resources.getString(R.string.calc_0).length();
                    break;
                case ONE:
                    tempCursor += resources.getString(R.string.calc_1).length();
                    break;
                case TWO:
                    tempCursor += resources.getString(R.string.calc_2).length();
                    break;
                case THREE:
                    tempCursor += resources.getString(R.string.calc_3).length();
                    break;
                case FOUR:
                    tempCursor += resources.getString(R.string.calc_4).length();
                    break;
                case FIVE:
                    tempCursor += resources.getString(R.string.calc_5).length();
                    break;
                case SIX:
                    tempCursor += resources.getString(R.string.calc_6).length();
                    break;
                case SEVEN:
                    tempCursor += resources.getString(R.string.calc_7).length();
                    break;
                case EIGHT:
                    tempCursor += resources.getString(R.string.calc_8).length();
                    break;
                case NINE:
                    tempCursor += resources.getString(R.string.calc_9).length();
                    break;
                case PLUS:
                    tempCursor += resources.getString(R.string.calc_plus).length();
                    break;
                case MINUS:
                    tempCursor += resources.getString(R.string.calc_minus).length();
                    break;
                case MULTIPLICATION:
                    tempCursor += resources.getString(R.string.calc_mult).length();
                    break;
                case DIVISION:
                    tempCursor += resources.getString(R.string.calc_div).length();
                    break;
                case COMA:
                    tempCursor += resources.getString(R.string.calc_coma).length();
                    break;
                case BRACKET_OPEN:
                    tempCursor += resources.getString(R.string.calc_bracket_open).length();
                    break;
                case BRACKET_CLOSE:
                    tempCursor += resources.getString(R.string.calc_bracket_close).length();
                    break;
                case ROOT:
                    tempCursor += resources.getString(R.string.calc_root).length();
                    break;
                case POW:
                    tempCursor += resources.getString(R.string.calc_pow).length();
                    break;
                case ABS:
                    tempCursor += resources.getString(R.string.calc_abs).length();
                    break;
                case SIN:
                    tempCursor += resources.getString(R.string.calc_sin).length();
                    break;
                case COS:
                    tempCursor += resources.getString(R.string.calc_cos).length();
                    break;
                case TAN:
                    tempCursor += resources.getString(R.string.calc_tan).length();
                    break;
                case LN:
                    tempCursor += resources.getString(R.string.calc_ln).length();
                    break;
                case LOG:
                    tempCursor += resources.getString(R.string.calc_log).length();
                    break;
                case PI:
                    tempCursor += resources.getString(R.string.calc_pi).length();
                    break;
                case E:
                    tempCursor += resources.getString(R.string.calc_e).length();
                    break;
                case PERCENT:
                    tempCursor += resources.getString(R.string.calc_percent).length();
                    break;
            }
            if (tempCursor >= newCursorPosition) {
                break;
            }
        }
        return cursorPosition;
    }

    public int convertToUi(@NonNull final LogicalEquation logicalEquation) {
        int uiCursorPosition = 0;
        for (int i = 0; i < logicalEquation.getCursorPosition(); i++) {
            switch (logicalEquation.get(i)) {
                case ZERO:
                    uiCursorPosition += resources.getString(R.string.calc_0).length();
                    break;
                case ONE:
                    uiCursorPosition += resources.getString(R.string.calc_1).length();
                    break;
                case TWO:
                    uiCursorPosition += resources.getString(R.string.calc_2).length();
                    break;
                case THREE:
                    uiCursorPosition += resources.getString(R.string.calc_3).length();
                    break;
                case FOUR:
                    uiCursorPosition += resources.getString(R.string.calc_4).length();
                    break;
                case FIVE:
                    uiCursorPosition += resources.getString(R.string.calc_5).length();
                    break;
                case SIX:
                    uiCursorPosition += resources.getString(R.string.calc_6).length();
                    break;
                case SEVEN:
                    uiCursorPosition += resources.getString(R.string.calc_7).length();
                    break;
                case EIGHT:
                    uiCursorPosition += resources.getString(R.string.calc_8).length();
                    break;
                case NINE:
                    uiCursorPosition += resources.getString(R.string.calc_9).length();
                    break;
                case PLUS:
                    uiCursorPosition += resources.getString(R.string.calc_plus).length();
                    break;
                case MINUS:
                    uiCursorPosition += resources.getString(R.string.calc_minus).length();
                    break;
                case MULTIPLICATION:
                    uiCursorPosition += resources.getString(R.string.calc_mult).length();
                    break;
                case DIVISION:
                    uiCursorPosition += resources.getString(R.string.calc_div).length();
                    break;
                case COMA:
                    uiCursorPosition += resources.getString(R.string.calc_coma).length();
                    break;
                case BRACKET_OPEN:
                    uiCursorPosition += resources.getString(R.string.calc_bracket_open).length();
                    break;
                case BRACKET_CLOSE:
                    uiCursorPosition += resources.getString(R.string.calc_bracket_close).length();
                    break;
                case ROOT:
                    uiCursorPosition += resources.getString(R.string.calc_root).length();
                    break;
                case POW:
                    uiCursorPosition += resources.getString(R.string.calc_pow).length();
                    break;
                case ABS:
                    uiCursorPosition += resources.getString(R.string.calc_abs).length();
                    break;
                case SIN:
                    uiCursorPosition += resources.getString(R.string.calc_sin).length();
                    break;
                case COS:
                    uiCursorPosition += resources.getString(R.string.calc_cos).length();
                    break;
                case TAN:
                    uiCursorPosition += resources.getString(R.string.calc_tan).length();
                    break;
                case LN:
                    uiCursorPosition += resources.getString(R.string.calc_ln).length();
                    break;
                case LOG:
                    uiCursorPosition += resources.getString(R.string.calc_log).length();
                    break;
                case PI:
                    uiCursorPosition += resources.getString(R.string.calc_pi).length();
                    break;
                case E:
                    uiCursorPosition += resources.getString(R.string.calc_e).length();
                    break;
                case PERCENT:
                    uiCursorPosition += resources.getString(R.string.calc_percent).length();
                    break;
            }
        }
        return uiCursorPosition;
    }
}
