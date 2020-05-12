package com.senyk.volodymyr.labthread.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.labthread.presentation.viewmodel.ThreadViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == ThreadViewModel.class) {
            return (T) new ThreadViewModel();
        }
        return null;
    }
}
