package com.senyk.volodymyr.lab3.presentation.viewmodel.util;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.senyk.volodymyr.lab3.R;

public class ResourcesProvider {

    private final Context context;

    public ResourcesProvider(@NonNull final Context context) {
        this.context = context;
    }

    @ColorInt
    public int getAnswerFieldColor(boolean selected, boolean correct) {
        @ColorRes final int colorRes;
        if (correct) {
            if (selected) {
                colorRes = R.color.colorCorrectAnswer;
            } else {
                colorRes = R.color.colorNotSelectedCorrectAnswer;
            }
        } else {
            if (selected) {
                colorRes = R.color.colorWrongAnswer;
            } else {
                colorRes = R.color.colorBackground;
            }
        }
        return getColor(colorRes);
    }

    @ColorInt
    private int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }
}
