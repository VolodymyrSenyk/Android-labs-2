package com.senyk.volodymyr.calculator.presentation.viewmodel.util;

import android.content.Context;

import androidx.annotation.NonNull;

public class MetricsMapperUtil {

    public static float convertPxToSp(float px, @NonNull final Context context) {
        return convertPxToDp(px, context);
    }

    public static float convertPxToDp(float px, @NonNull final Context context) {
        return px / context.getResources().getDisplayMetrics().scaledDensity;
    }
}
