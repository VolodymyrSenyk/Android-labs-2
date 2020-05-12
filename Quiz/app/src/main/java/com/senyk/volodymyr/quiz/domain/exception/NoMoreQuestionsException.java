package com.senyk.volodymyr.quiz.domain.exception;

public class NoMoreQuestionsException extends Exception {

    public NoMoreQuestionsException() {
        super("No more questions left");
    }
}
