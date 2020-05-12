package com.senyk.volodymyr.lab3.presentation.viewmodel;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.senyk.volodymyr.lab3.data.entity.Answer;
import com.senyk.volodymyr.lab3.data.entity.Question;
import com.senyk.volodymyr.lab3.domain.entity.ScreenMode;
import com.senyk.volodymyr.lab3.domain.repository.QuestionRepository;
import com.senyk.volodymyr.lab3.presentation.viewmodel.util.ResourcesProvider;

import java.util.ArrayList;
import java.util.List;

public class QuizViewModel extends ViewModel {

    private final QuestionRepository questionRepository;
    private final ResourcesProvider resourcesProvider;

    private MutableLiveData<Question> question = new MutableLiveData<>();

    private MutableLiveData<List<Integer>> answerResults = new MutableLiveData<>();

    private MutableLiveData<ScreenMode> screenMode = new MutableLiveData<>();

    public QuizViewModel(@NonNull QuestionRepository questionRepository, @NonNull ResourcesProvider resourcesProvider) {
        this.questionRepository = questionRepository;
        this.resourcesProvider = resourcesProvider;
    }

    public LiveData<Question> question() {
        return question;
    }

    public LiveData<List<Integer>> answerResults() {
        return answerResults;
    }

    public LiveData<ScreenMode> screenMode() {
        return screenMode;
    }

    public void onViewStart() {
        askQuestion();
    }

    public void onQuestionSaveClick(String questionTextUi, List<Pair<Boolean, String>> answersUi) {
        List<Answer> answers = new ArrayList<>(answersUi.size());
        for (Pair<Boolean, String> answerUi : answersUi) {
            Answer answer = new Answer(answerUi.second, answerUi.first);
            answers.add(answer);
        }
        Question question = new Question(questionTextUi, answers);
        questionRepository.saveQuestion(question);
        askQuestion();
    }

    public void onAnswerQuestionClick(List<String> answersText) {
        if (question.getValue() == null) {
            return;
        }
        List<Answer> answers = question.getValue().getAnswers();
        List<Integer> results = new ArrayList<>(answers.size());
        for (Answer answer : answers) {
            boolean correct = answer.isCorrect();
            boolean selected = answersText.contains(answer.getText());
            results.add(resourcesProvider.getAnswerFieldColor(selected, correct));
        }
        answerResults.setValue(results);
        screenMode.setValue(ScreenMode.SHOW_ANSWER_RESULTS);
    }

    public void onDeleteQuestionClick() {
        questionRepository.deleteQuestion();
        screenMode.setValue(ScreenMode.CREATE_QUESTION);
    }

    public void onBackPressed(Runnable exitApp) {
        if (screenMode.getValue() != null && screenMode.getValue() == ScreenMode.SHOW_ANSWER_RESULTS) {
            screenMode.setValue(ScreenMode.ASK_QUESTION);
        } else {
            exitApp.run();
        }
    }

    private void askQuestion() {
        Question question = questionRepository.getQuestion();
        if (question == null) {
            screenMode.setValue(ScreenMode.CREATE_QUESTION);
        } else {
            this.question.setValue(question);
            screenMode.setValue(ScreenMode.ASK_QUESTION);
        }
    }
}
