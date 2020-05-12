package com.senyk.volodymyr.quiz.domain.exception;

public class NoQuestionsException extends Exception {

    public NoQuestionsException() {
        super("Questions not found");
    }
}
