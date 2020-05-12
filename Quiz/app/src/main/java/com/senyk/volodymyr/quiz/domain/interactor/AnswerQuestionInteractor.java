package com.senyk.volodymyr.quiz.domain.interactor;

import com.senyk.volodymyr.quiz.domain.entity.Answer;
import com.senyk.volodymyr.quiz.domain.entity.Question;
import com.senyk.volodymyr.quiz.domain.exception.SelectedAnswerNotFoundException;
import com.senyk.volodymyr.quiz.domain.repository.ScoreRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AnswerQuestionInteractor {

    private final ScoreRepository scoreRepository;

    @Inject
    public AnswerQuestionInteractor(final ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Single<Question> execute(final Question question, final String answerText) {
        question.setAnswered();
        return setSelection(question, answerText)
                .andThen(checkScore(question.getAnswers()))
                .andThen(Single.fromCallable(() -> question));
    }

    private Completable setSelection(final Question question, final String answerText) {
        for (Answer answer : question.getAnswers()) {
            if (answerText.equals(answer.getText())) {
                answer.setSelected();
                return Completable.complete();
            }
        }
        return Completable.error(new SelectedAnswerNotFoundException());
    }

    private Completable checkScore(final List<Answer> answers) {
        for (Answer answer : answers) {
            if (answer.isSelected() && answer.isCorrect()) {
                return scoreRepository.raiseScore();
            }
        }
        return Completable.complete();
    }
}
