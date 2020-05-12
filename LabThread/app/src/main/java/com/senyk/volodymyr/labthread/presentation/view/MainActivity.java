package com.senyk.volodymyr.labthread.presentation.view;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.labthread.R;
import com.senyk.volodymyr.labthread.databinding.ActivityMainBinding;
import com.senyk.volodymyr.labthread.presentation.viewmodel.ThreadViewModel;
import com.senyk.volodymyr.labthread.presentation.viewmodel.factory.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThreadViewModel viewModel = new ViewModelProvider(this, new ViewModelFactory()).get(ThreadViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.resultsField.setMovementMethod(new ScrollingMovementMethod());
    }
}
