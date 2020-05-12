package com.senyk.volodymyr.lab3.data.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Answer implements Serializable {
    private final String text;
    private final boolean correct;

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public Answer(@NonNull String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }
}
