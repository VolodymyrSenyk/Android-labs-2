package com.senyk.volodymyr.quiz.domain.exception;

public class QuestionWithoutAnswersException extends RuntimeException {

    public QuestionWithoutAnswersException() {
        super("Question can not contain no answers");
    }
}
