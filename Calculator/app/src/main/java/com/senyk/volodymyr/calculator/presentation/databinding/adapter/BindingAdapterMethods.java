package com.senyk.volodymyr.calculator.presentation.databinding.adapter;

import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class BindingAdapterMethods {

    @BindingAdapter({"setCursorPosition"})
    public static void setEditTextCursorPosition(EditText view, int cursorPosition) {
        view.setSelection(cursorPosition);
    }
}
