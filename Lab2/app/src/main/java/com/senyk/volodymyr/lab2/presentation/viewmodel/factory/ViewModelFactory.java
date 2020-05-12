package com.senyk.volodymyr.lab2.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.senyk.volodymyr.lab2.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.lab2.presentation.viewmodel.mapper.SymbolMapper;
import com.senyk.volodymyr.lab2.presentation.viewmodel.util.ResourcesProvider;

import javax.script.ScriptEngine;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final ScriptEngine scriptEngine;
    private final ResourcesProvider resourcesProvider;
    private final SymbolMapper symbolMapper;

    public ViewModelFactory(
            ScriptEngine scriptEngine,
            ResourcesProvider resourcesProvider,
            SymbolMapper symbolMapper
    ) {
        super();
        this.scriptEngine = scriptEngine;
        this.resourcesProvider = resourcesProvider;
        this.symbolMapper = symbolMapper;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == CalculatorViewModel.class) {
            return (T) new CalculatorViewModel(scriptEngine, resourcesProvider, symbolMapper);
        }
        return null;
    }
}
