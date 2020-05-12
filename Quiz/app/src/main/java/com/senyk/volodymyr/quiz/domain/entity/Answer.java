package com.senyk.volodymyr.quiz.domain.entity;

public class Answer {
    private final int id;
    private final String text;
    private final boolean correct;
    private boolean selected;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setSelected() {
        this.selected = true;
    }

    public boolean isSelected() {
        return selected;
    }

    public Answer(final int id, final String text, boolean correct) {
        this.id = id;
        this.text = text;
        this.correct = correct;
        this.selected = false;
    }
}
