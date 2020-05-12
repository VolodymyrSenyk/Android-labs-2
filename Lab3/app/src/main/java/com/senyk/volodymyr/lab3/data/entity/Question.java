package com.senyk.volodymyr.lab3.data.entity;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private final String text;
    private final List<Answer> answers;

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }
}
