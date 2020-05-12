package com.senyk.volodymyr.quiz.presentation.viewmodel;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.senyk.volodymyr.quiz.domain.entity.Question;
import com.senyk.volodymyr.quiz.domain.entity.Score;
import com.senyk.volodymyr.quiz.domain.exception.NoMoreQuestionsException;
import com.senyk.volodymyr.quiz.domain.exception.NoQuestionsException;
import com.senyk.volodymyr.quiz.domain.interactor.AnswerQuestionInteractor;
import com.senyk.volodymyr.quiz.domain.interactor.GetQuestionInteractor;
import com.senyk.volodymyr.quiz.domain.interactor.GetUserScoreInteractor;
import com.senyk.volodymyr.quiz.presentation.util.ResourcesProvider;
import com.senyk.volodymyr.quiz.presentation.util.livedataframework.event.SingleEvent;
import com.senyk.volodymyr.quiz.presentation.util.livedataframework.livedata.SingleEventLiveData;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuizViewModel extends ViewModel {
    private final static String TAG = QuizViewModel.class.toString();
    private final static String ERROR_LOG_PATTERN = "An error occurred: ";
    private final static int RESET_BACK_CLICKS_COUNTER_DELAY_IN_MILLIS = 3000;
    private final static int NUMBER_OF_CLICKS_FOR_EXIT = 2;

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final ResourcesProvider resourcesProvider;
    private final GetQuestionInteractor getQuestionInteractor;
    private final AnswerQuestionInteractor answerQuestionInteractor;
    private final GetUserScoreInteractor getUserScoreInteractor;

    private int numberOfOnBackClicks = 0;

    private MutableLiveData<Question> currentQuestion = new MutableLiveData<>();

    public LiveData<Question> question() {
        return currentQuestion;
    }

    private MutableLiveData<SingleEvent<Boolean>> exitFromApplication = new MutableLiveData<>();

    public LiveData<SingleEvent<Boolean>> observeExitFromApplication() {
        return exitFromApplication;
    }

    private SingleEventLiveData<String> message = new SingleEventLiveData<>();

    public LiveData<String> observeMessage() {
        return message;
    }

    private SingleEventLiveData<String> scoreMessage = new SingleEventLiveData<>();

    public LiveData<String> observeScore() {
        return scoreMessage;
    }

    @Inject
    public QuizViewModel(
            final ResourcesProvider resourcesProvider,
            final GetQuestionInteractor getQuestionInteractor,
            final AnswerQuestionInteractor answerQuestionInteractor,
            final GetUserScoreInteractor getUserScoreInteractor
    ) {
        this.resourcesProvider = resourcesProvider;
        this.getUserScoreInteractor = getUserScoreInteractor;
        this.getQuestionInteractor = getQuestionInteractor;
        this.answerQuestionInteractor = answerQuestionInteractor;
    }

    public void onBackButtonClick() {
        numberOfOnBackClicks++;
        if (numberOfOnBackClicks < NUMBER_OF_CLICKS_FOR_EXIT) {
            message.setValue(resourcesProvider.getExitMessage());
            Handler handler = new Handler();
            handler.postDelayed(() -> numberOfOnBackClicks = 0, RESET_BACK_CLICKS_COUNTER_DELAY_IN_MILLIS);
        } else {
            exitFromApplication.setValue(new SingleEvent<>(true));
        }
    }

    public void onStartNewQuizClick(int numberOfAnswerButtons) {
        receiveQuestion(getQuestionInteractor.execute(true, numberOfAnswerButtons));
    }

    public void onNextQuestionClick(int numberOfAnswerButtons) {
        receiveQuestion(getQuestionInteractor.execute(false, numberOfAnswerButtons));
    }

    public void onAnswerClick(@NonNull String answerText) {
        Question question = currentQuestion.getValue();
        if (question == null) {
            return;
        }
        receiveQuestion(answerQuestionInteractor.execute(question, answerText));
    }

    private void receiveQuestion(Single<Question> questionSingle) {
        questionSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Question>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Question question) {
                        currentQuestion.setValue(question);
                    }

                    @Override
                    public void onError(Throwable exception) {
                        if (exception instanceof NoMoreQuestionsException) {
                            receiveScore(getUserScoreInteractor.execute());
                        } else {
                            if (exception instanceof NoQuestionsException) {
                                message.setValue(resourcesProvider.getErrorMessage());
                            }
                            Log.e(TAG, ERROR_LOG_PATTERN + exception.getMessage());
                        }
                    }
                });
    }

    private void receiveScore(Single<Score> scoreSingle) {
        scoreSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Score>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Score score) {
                        scoreMessage.setValue(resourcesProvider.getEndGameMessage(score));
                    }

                    @Override
                    public void onError(Throwable exception) {
                        Log.e(TAG, ERROR_LOG_PATTERN + exception.getMessage());
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
