package com.senyk.volodymyr.calculator.presentation.view.recyclerview.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.senyk.volodymyr.calculator.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.calculator.presentation.viewmodel.entity.HistoryEntryUi;

public abstract class BaseHistoryEntryViewHolder extends RecyclerView.ViewHolder {

    protected BaseHistoryEntryViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(HistoryEntryUi entry, CalculatorViewModel viewModel);
}
