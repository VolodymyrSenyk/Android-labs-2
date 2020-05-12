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
import com.senyk.volodymyr.calculator.databinding.FragmentHistoryBinding;
import com.senyk.volodymyr.calculator.presentation.view.recyclerview.adapter.HistoryAdapter;
import com.senyk.volodymyr.calculator.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.calculator.presentation.viewmodel.factory.ViewModelFactory;

import javax.inject.Inject;

public class HistoryFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private FragmentHistoryBinding binding;
    private CalculatorViewModel viewModel;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CalculatorViewModel.class);
        binding.setViewModel(viewModel);

        HistoryAdapter adapter = new HistoryAdapter(viewModel);
        binding.historyList.setAdapter(adapter);
        viewModel.history().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}
