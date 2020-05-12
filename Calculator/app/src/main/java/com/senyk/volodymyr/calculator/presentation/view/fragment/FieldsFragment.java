package com.senyk.volodymyr.calculator.presentation.view.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.databinding.FragmentFieldsBinding;
import com.senyk.volodymyr.calculator.presentation.view.customview.CalculatorEditText;
import com.senyk.volodymyr.calculator.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.calculator.presentation.viewmodel.factory.ViewModelFactory;

import javax.inject.Inject;

public class FieldsFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private FragmentFieldsBinding binding;
    private CalculatorViewModel viewModel;

    public static FieldsFragment newInstance() {
        return new FieldsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fields, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CalculatorViewModel.class);
        binding.setViewModel(viewModel);
        setUpEquationInputField();
    }

    private void setUpEquationInputField() {
        CalculatorEditText equationInputField = binding.inputField;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            equationInputField.setShowSoftInputOnFocus(false);
        } else {
            equationInputField.setTextIsSelectable(true);
        }
        equationInputField.setOnClickListener(view ->
                viewModel.onCursorPositionChanged(((EditText) view).getSelectionStart()));
        equationInputField.pastedText().observe(getViewLifecycleOwner(), pastedText -> viewModel.onTextPasted(pastedText));
        equationInputField.requestFocus();
        hideKeyboard(requireActivity());
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
