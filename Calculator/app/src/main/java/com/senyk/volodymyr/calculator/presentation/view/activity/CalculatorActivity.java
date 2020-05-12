package com.senyk.volodymyr.calculator.presentation.view.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.databinding.ActivityCalculatorBinding;
import com.senyk.volodymyr.calculator.presentation.view.fragment.AdditionalOperatorsFragment;
import com.senyk.volodymyr.calculator.presentation.view.fragment.DigitsFragment;
import com.senyk.volodymyr.calculator.presentation.view.fragment.HistoryFragment;
import com.senyk.volodymyr.calculator.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.calculator.presentation.viewmodel.factory.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class CalculatorActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;
    private CalculatorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(CalculatorViewModel.class);
        ActivityCalculatorBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator);
        binding.setViewModel(viewModel);
        if (savedInstanceState == null) {
            viewModel.onAppThemeApply();
        }
        setObservers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onAppThemeChanged(getTheme());
    }

    private void setObservers() {
        viewModel.appTheme().observe(this, AppCompatDelegate::setDefaultNightMode);
        viewModel.isCurrentModeFull().observe(this, this::changeMode);
        viewModel.isShowingHistory().observe(this, this::showOrHideHistory);
        viewModel.message().observe(this, this::showToast);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void changeMode(boolean modeFull) {
        if (modeFull) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    private void showOrHideHistory(boolean showingHistory) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Boolean fullMode = viewModel.isCurrentModeFull().getValue();
        if (fullMode == null) {
            fullMode = false;
        }
        int fragmentContainerId;
        if (fullMode) {
            fragmentContainerId = R.id.additionalOperatorsContainer;
        } else {
            fragmentContainerId = R.id.digitsContainer;
        }
        Fragment fragment;
        if (showingHistory) {
            fragment = HistoryFragment.newInstance();
        } else {
            if (fullMode) {
                fragment = AdditionalOperatorsFragment.newInstance();
            } else {
                fragment = DigitsFragment.newInstance();
            }
        }
        fragmentTransaction.replace(fragmentContainerId, fragment, null);
        fragmentTransaction.commit();
    }

    private void showToast(String message) {
        Toast.makeText(CalculatorActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
