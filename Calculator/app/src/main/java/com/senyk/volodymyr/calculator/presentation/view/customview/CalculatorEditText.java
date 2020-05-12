package com.senyk.volodymyr.calculator.presentation.view.customview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.LiveData;

import com.senyk.volodymyr.calculator.presentation.viewmodel.util.SingleEventLiveData;

public class CalculatorEditText extends AppCompatEditText {

    private final SingleEventLiveData<String> pastedText = new SingleEventLiveData<>();

    public LiveData<String> pastedText() {
        return pastedText;
    }

    public CalculatorEditText(Context context) {
        super(context);
    }

    public CalculatorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalculatorEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) return;
        super.onFocusChanged(false, direction, previouslyFocusedRect);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste) {
            ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null && clipboard.hasPrimaryClip()) {
                ClipData clipData = clipboard.getPrimaryClip();
                if (clipData != null) {
                    pastedText.setValue(clipData.getItemAt(clipData.getItemCount() - 1).coerceToText(getContext()).toString());
                }
            }
            return true;
        } else {
            return super.onTextContextMenuItem(id);
        }
    }
}
