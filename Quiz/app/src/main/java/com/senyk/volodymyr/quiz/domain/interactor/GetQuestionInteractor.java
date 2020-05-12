package com.senyk.volodymyr.quiz.domain.interactor;

import com.senyk.volodymyr.quiz.domain.entity.Answer;
import com.senyk.volodymyr.quiz.domain.entity.Question;
import com.senyk.volodymyr.quiz.domain.exception.NoAnswersException;
import com.senyk.volodymyr.quiz.domain.exception.NoMoreQuestionsException;
import com.senyk.volodymyr.quiz.domain.exception.NoQuestionsException;
import com.senyk.volodymyr.quiz.domain.exception.QuestionWithoutAnswersException;
import com.senyk.volodymyr.quiz.domain.repository.AnswersRepository;
import com.senyk.volodymyr.quiz.domain.repository.QuestionsRepository;
import com.senyk.volodymyr.quiz.domain.repository.ScoreRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class GetQuestionInteractor {

    private final QuestionsRepository questionsRepository;
    private final AnswersRepository answersRepository;
    private final ScoreRepository scoreRepository;
    private final Random random = new Random(Calendar.getInstance().getTimeInMillis());

    private List<Integer> questionsIds = new ArrayList<>();
    private List<Integer> answersIds = new ArrayList<>();
    private int currentQuestionIndex = 0;

    @Inject
    public GetQuestionInteractor(final QuestionsRepository questionsRepository, final AnswersRepository answersRepository, final ScoreRepository scoreRepository) {
        this.questionsRepository = questionsRepository;
        this.answersRepository = answersRepository;
        this.scoreRepository = scoreRepository;
    }

    public Single<Question> execute(final boolean startNewQuiz, final int numberOfAnswers) {
        if (numberOfAnswers < 1) {
            throw new QuestionWithoutAnswersException();
        }
        Completable completable = Completable.complete();
        if (startNewQuiz) {
            currentQuestionIndex = 0;
            if (!questionsIds.isEmpty()) {
                Collections.shuffle(questionsIds);
                completable = completable.andThen(Completable.defer(() -> scoreRepository.resetScore(questionsIds.size())));
            }
        }
        if (questionsIds.isEmpty()) {
            completable = completable
                    .andThen(Completable.defer(this::getIds))
                    .andThen(Completable.defer(() -> scoreRepository.resetScore(questionsIds.size())));
        }
        return completable.andThen(Single.defer(() -> checkDataAndGetQuestion(numberOfAnswers)))
                .doAfterSuccess(question -> currentQuestionIndex++);
    }

    private Completable getIds() {
        return getQuestionsIds().andThen(getAnswersIds());
    }

    private Single<Question> checkDataAndGetQuestion(final int numberOfAnswers) {
        if (questionsIds.isEmpty()) {
            return Single.error(new NoQuestionsException());
        } else if (answersIds.isEmpty()) {
            return Single.error(new NoAnswersException());
        } else if (currentQuestionIndex > questionsIds.size() - 1) {
            return Single.error(new NoMoreQuestionsException());
        } else {
            return getQuestion(numberOfAnswers);
        }
    }

    private Single<Question> getQuestion(final int numberOfAnswers) {
        int currentQuestionId = questionsIds.get(currentQuestionIndex);
        Single<Question> question = questionsRepository.getQuestionById(currentQuestionId);
        return addWrongAnswersToQuestion(question, numberOfAnswers);
    }

    private Completable getQuestionsIds() {
        return questionsRepository.getAllQuestionsIds()
                .flatMapCompletable(questionsIds -> {
                    Collections.shuffle(questionsIds);
                    this.questionsIds = questionsIds;
                    return Completable.complete();
                });
    }

    private Completable getAnswersIds() {
        return answersRepository.getAllAnswersIds()
                .flatMapCompletable(answersIds -> {
                    this.answersIds = answersIds;
                    return Completable.complete();
                });
    }

    private Single<Question> addWrongAnswersToQuestion(final Single<Question> questionSingle, final int numberOfAnswers) {
        return questionSingle.flatMap(question -> {
                    int correctAnswerId = question.getAnswers().get(0).getId();
                    int numberOfWrongAnswers = numberOfAnswers - 1;
                    List<Integer> wrongAnswersIds = getWrongAnswersIds(correctAnswerId, numberOfWrongAnswers);
                    return getQuestionWithWrongAnswers(wrongAnswersIds, question);
                }
        );
    }

    private List<Integer> getWrongAnswersIds(final int correctAnswerId, final int numberOfWrongAnswers) {
        List<Integer> wrongAnswersIds = new ArrayList<>(numberOfWrongAnswers);
        for (int i = 0; i < numberOfWrongAnswers; i++) {
            wrongAnswersIds.add(getWrongAnswerId(wrongAnswersIds, correctAnswerId));
        }
        return wrongAnswersIds;
    }

    private int getWrongAnswerId(List<Integer> wrongAnswers, int correctAnswerId) {
        int randomIndex = random.nextInt(answersIds.size() - 1);
        int wrongAnswerId = answersIds.get(randomIndex);
        if (wrongAnswerId == correctAnswerId || wrongAnswers.contains(wrongAnswerId)) {
            return getWrongAnswerId(wrongAnswers, correctAnswerId);
        } else {
            return wrongAnswerId;
        }
    }

    private Single<Question> getQuestionWithWrongAnswers(final List<Integer> wrongAnswersIds, final Question question) {
        return Observable.fromIterable(wrongAnswersIds)
                .flatMap((Function<Integer, ObservableSource<Answer>>) wrongAnswerId ->
                        answersRepository.getAnswerById(wrongAnswerId, false).toObservable())
                .toList(wrongAnswersIds.size())
                .flatMap((Function<List<Answer>, SingleSource<Question>>) answers -> {
                    for (Answer answer : answers) {
                        question.getAnswers().add(answer);
                    }
                    Collections.shuffle(question.getAnswers());
                    return Single.fromCallable(() -> question);
                });
    }
}
