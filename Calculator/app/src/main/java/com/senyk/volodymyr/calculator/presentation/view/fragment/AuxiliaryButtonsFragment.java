package com.senyk.volodymyr.calculator.presentation.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.databinding.FragmentAuxiliaryButtonsBinding;
import com.senyk.volodymyr.calculator.presentation.databinding.clicklistener.animated.OnChangeModeClickListener;
import com.senyk.volodymyr.calculator.presentation.databinding.clicklistener.animated.OnChangeThemeClickListener;
import com.senyk.volodymyr.calculator.presentation.databinding.clicklistener.animated.OnDeleteClickListener;
import com.senyk.volodymyr.calculator.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.calculator.presentation.viewmodel.factory.ViewModelFactory;

import javax.inject.Inject;

public class AuxiliaryButtonsFragment extends Fragment implements OnChangeModeClickListener, OnChangeThemeClickListener, OnDeleteClickListener {

    @Inject
    ViewModelFactory viewModelFactory;
    private FragmentAuxiliaryButtonsBinding binding;
    private CalculatorViewModel viewModel;

    public static AuxiliaryButtonsFragment newInstance() {
        return new AuxiliaryButtonsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auxiliary_buttons, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CalculatorViewModel.class);
        binding.setViewModel(viewModel);
        binding.setOnChangeModeClickListener(this);
        binding.setOnChangeThemeClickListener(this);
        binding.setOnDeleteClickListener(this);
    }

    @Override
    public void onChangeModeClick(View view) {
        final int animationDuration = 160;
        view.animate().rotationBy(-160f).setDuration(animationDuration);
        view.setClickable(false);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            viewModel.onModeChangeClick();
            view.setClickable(true);
        }, animationDuration);
    }

    @Override
    public void onChangeThemeClick(View view) {
        final int animationDuration = 300;
        final int scale = 3;
        view.animate().scaleX(scale).setDuration(animationDuration);
        view.animate().scaleY(scale).setDuration(animationDuration);
        view.animate().alpha(0).setDuration(animationDuration);
        view.setClickable(false);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            viewModel.onAppThemeChangeClick();
            view.setClickable(true);
        }, animationDuration);
    }

    @Override
    public void onDeleteClick(View view) {
        viewModel.onDeleteClick();
        final int animationDuration = 100;
        final int restoreAnimationDuration = 50;
        final float scale = 0.9f;
        view.animate().scaleX(scale).setDuration(animationDuration);
        view.animate().scaleY(scale).setDuration(animationDuration);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            view.animate().scaleX(1).setDuration(restoreAnimationDuration);
            view.animate().scaleY(1).setDuration(restoreAnimationDuration);
        }, animationDuration);
    }
}
