package com.senyk.volodymyr.lab3.presentation.view.activity;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.lab3.R;
import com.senyk.volodymyr.lab3.data.repository.QuestionFileRepository;
import com.senyk.volodymyr.lab3.data.util.FileReaderWriter;
import com.senyk.volodymyr.lab3.databinding.ActivityQuizAllInOneBinding;
import com.senyk.volodymyr.lab3.presentation.view.clicklistener.OnAnswerQuestionClickListener;
import com.senyk.volodymyr.lab3.presentation.view.clicklistener.OnDeleteQuestionClickListener;
import com.senyk.volodymyr.lab3.presentation.view.clicklistener.OnSaveQuestionClickListener;
import com.senyk.volodymyr.lab3.presentation.viewmodel.QuizViewModel;
import com.senyk.volodymyr.lab3.presentation.viewmodel.factory.ViewModelFactory;
import com.senyk.volodymyr.lab3.presentation.viewmodel.util.ResourcesProvider;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements OnSaveQuestionClickListener, OnAnswerQuestionClickListener, OnDeleteQuestionClickListener {

    private static final String EMPTY_FIELD = "";

    private ActivityQuizAllInOneBinding binding;
    private QuizViewModel viewModel;
    private List<Pair<CheckBox, EditText>> answerViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(
                new QuestionFileRepository(new FileReaderWriter(getApplicationContext())),
                new ResourcesProvider(getApplicationContext())
        )).get(QuizViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_all_in_one);
        binding.setLifecycleOwner(this);

        binding.setOnSaveQuestionClickListener(this);
        binding.setOnAnswerQuestionClickListener(this);
        binding.setOnDeleteQuestionClickListener(this);
        binding.setViewModel(viewModel);

        initAnswerViews();

        viewModel.onViewStart();
    }

    @Override
    public void onSaveQuestionClick(View view) {
        String questionText = binding.questionTextInputField.getText().toString();
        List<Pair<Boolean, String>> answers = new ArrayList<>();
        for (Pair<CheckBox, EditText> answerView : answerViews) {
            boolean correct = answerView.first.isChecked();
            String text = answerView.second.getText().toString();
            Pair<Boolean, String> answer = new Pair<>(correct, text);
            answers.add(answer);
        }
        clearInputFields();
        viewModel.onQuestionSaveClick(questionText, answers);
    }

    @Override
    public void onAnswerQuestionClick(View view) {
        List<String> answers = new ArrayList<>();
        for (Pair<CheckBox, EditText> answerView : answerViews) {
            if (answerView.first.isChecked()) {
                String text = answerView.first.getText().toString();
                answers.add(text);
            }
        }
        viewModel.onAnswerQuestionClick(answers);
    }

    @Override
    public void onDeleteQuestionClick(View view) {
        clearInputFields();
        viewModel.onDeleteQuestionClick();
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed(this::finish);
    }

    private void initAnswerViews() {
        answerViews = new ArrayList<>();
        CheckBox checkBox = binding.firstAnswerCheckBox;
        EditText editText = binding.firstAnswerInputField;
        answerViews.add(new Pair<>(checkBox, editText));
        checkBox = binding.secondAnswerCheckBox;
        editText = binding.secondAnswerInputField;
        answerViews.add(new Pair<>(checkBox, editText));
        checkBox = binding.thirdAnswerCheckBox;
        editText = binding.thirdAnswerInputField;
        answerViews.add(new Pair<>(checkBox, editText));
        checkBox = binding.fourthAnswerCheckBox;
        editText = binding.fourthAnswerInputField;
        answerViews.add(new Pair<>(checkBox, editText));
    }

    private void clearCheckBoxes() {
        binding.questionTextInputField.setText(EMPTY_FIELD);
        for (Pair<CheckBox, EditText> answerView : answerViews) {
            answerView.first.setChecked(false);
        }
    }

    private void clearInputFields() {
        binding.questionTextInputField.setText(EMPTY_FIELD);
        for (Pair<CheckBox, EditText> answerView : answerViews) {
            answerView.first.setChecked(false);
            answerView.second.setText(EMPTY_FIELD);
        }
    }
}
