package com.senyk.volodymyr.quiz.presentation.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.quiz.R;
import com.senyk.volodymyr.quiz.databinding.ActivityQuizBinding;
import com.senyk.volodymyr.quiz.presentation.view.dialog.QuizEndDialogFragment;
import com.senyk.volodymyr.quiz.presentation.viewmodel.QuizViewModel;
import com.senyk.volodymyr.quiz.presentation.viewmodel.factory.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class QuizActivity extends DaggerAppCompatActivity implements QuizEndDialogFragment.OnButtonClickListener {

    private static final int NUM_OF_ANSWER_BUTTONS = 4;

    @Inject
    ViewModelFactory viewModelFactory;
    private QuizViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(QuizViewModel.class);

        ActivityQuizBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);

        setObservers();

        if (savedInstanceState == null) {
            viewModel.onStartNewQuizClick(NUM_OF_ANSWER_BUTTONS);
        }
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackButtonClick();
    }

    private void setObservers() {

        viewModel.observeExitFromApplication().observe(this, exit -> {
            Boolean needExit = exit.handleEvent();
            if (needExit != null && needExit) {
                finish();
            }
        });

        viewModel.observeMessage().observe(this, message ->
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show());

        viewModel.observeScore().observe(this, score ->
                QuizEndDialogFragment.createAndShow(getSupportFragmentManager(), score));
    }

    @Override
    public void OnNoQuestionsLeftDialogFragmentPositiveButtonClickListener() {
        viewModel.onStartNewQuizClick(NUM_OF_ANSWER_BUTTONS);
    }

    @Override
    public void OnNoQuestionsLeftDialogFragmentNegativeButtonClickListener() {
        finish();
    }
}
