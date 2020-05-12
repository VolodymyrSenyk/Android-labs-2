package com.senyk.volodymyr.quiz.domain.exception;

public class SelectedAnswerNotFoundException extends RuntimeException {

    public SelectedAnswerNotFoundException() {
        super("Selected answer not found");
    }
}
