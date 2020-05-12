package com.senyk.volodymyr.quiz.domain.exception;

public class NoAnswersException extends Exception {

    public NoAnswersException() {
        super("Answers not found");
    }
}
