package com.senyk.volodymyr.lab2.presentation.viewmodel.util;

import android.content.res.Resources;

import com.senyk.volodymyr.lab2.R;

public class ResourcesProvider {
    private final Resources resources;

    public ResourcesProvider(Resources resources) {
        this.resources = resources;
    }

    public String getNotValidEquationErrorMessage() {
        return resources.getString(R.string.can_not_calculate);
    }
}
