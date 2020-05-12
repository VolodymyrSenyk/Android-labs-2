package com.senyk.volodymyr.calculator.presentation.view.recyclerview.viewholder;

import com.senyk.volodymyr.calculator.databinding.HistoryEntryEquationBinding;
import com.senyk.volodymyr.calculator.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.calculator.presentation.viewmodel.entity.HistoryEntryUi;

public class HistoryEntryEquationViewHolder extends BaseHistoryEntryViewHolder {

    private final HistoryEntryEquationBinding binding;

    public HistoryEntryEquationViewHolder(HistoryEntryEquationBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public void bind(HistoryEntryUi entry, CalculatorViewModel viewModel) {
        binding.setEntry(entry);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }
}
