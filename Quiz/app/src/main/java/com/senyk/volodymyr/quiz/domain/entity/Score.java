package com.senyk.volodymyr.quiz.domain.entity;

public class Score {
    private int correctAnswers;
    private final int numberOfQuestions;

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void addPoint() {
        correctAnswers++;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public Score(final int numberOfQuestions) {
        this.correctAnswers = 0;
        this.numberOfQuestions = numberOfQuestions;
    }
}
