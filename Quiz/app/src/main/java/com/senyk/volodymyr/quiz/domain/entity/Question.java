package com.senyk.volodymyr.quiz.domain.entity;

import java.util.List;

public class Question {
    private final int id;
    private final String question;
    private final List<Answer> answers;
    private boolean answered;

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered() {
        this.answered = true;
    }

    public Question(int id, String question, List<Answer> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.answered = false;
    }
}
