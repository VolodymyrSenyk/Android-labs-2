package com.senyk.volodymyr.calculator.presentation.viewmodel.entity;

import android.text.Spannable;
import android.text.SpannableString;

public class HistoryEntryUi {
    private final Spannable text;
    private final boolean answer;

    public Spannable getText() {
        return text;
    }

    public boolean isAnswer() {
        return answer;
    }

    public HistoryEntryUi(SpannableString text, boolean answer) {
        this.text = text;
        this.answer = answer;
    }
}
