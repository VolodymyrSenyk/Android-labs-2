package com.senyk.volodymyr.calculator.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.databinding.FragmentDigitsBinding;
import com.senyk.volodymyr.calculator.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.calculator.presentation.viewmodel.factory.ViewModelFactory;

import javax.inject.Inject;

public class DigitsFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private FragmentDigitsBinding binding;
    private CalculatorViewModel viewModel;

    public static DigitsFragment newInstance() {
        return new DigitsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_digits, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CalculatorViewModel.class);
        binding.setViewModel(viewModel);
    }
}
