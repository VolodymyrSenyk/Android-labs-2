package com.senyk.volodymyr.calculator.presentation.viewmodel.util;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.senyk.volodymyr.calculator.R;

import javax.inject.Inject;

public class ResourcesProvider {

    private final Resources resources;

    @Inject
    public ResourcesProvider(@NonNull final Context context) {
        this.resources = context.getResources();
    }

    public String getNotImplementedMessage() {
        return resources.getString(R.string.not_implemented);
    }

    public String getNotValidPowUsageMessage() {
        return resources.getString(R.string.can_not_raise);
    }

    public String getNotValidTextMessage() {
        return resources.getString(R.string.not_valid_text);
    }

    public String getNotValidEquationErrorMessage() {
        return resources.getString(R.string.can_not_calculate);
    }
}
