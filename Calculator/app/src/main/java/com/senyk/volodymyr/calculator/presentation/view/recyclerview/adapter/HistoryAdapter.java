package com.senyk.volodymyr.calculator.presentation.view.recyclerview.adapter;

import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.databinding.HistoryEntryAnswerBinding;
import com.senyk.volodymyr.calculator.databinding.HistoryEntryEquationBinding;
import com.senyk.volodymyr.calculator.presentation.view.recyclerview.viewholder.BaseHistoryEntryViewHolder;
import com.senyk.volodymyr.calculator.presentation.view.recyclerview.viewholder.HistoryEntryAnswerViewHolder;
import com.senyk.volodymyr.calculator.presentation.view.recyclerview.viewholder.HistoryEntryEquationViewHolder;
import com.senyk.volodymyr.calculator.presentation.viewmodel.CalculatorViewModel;
import com.senyk.volodymyr.calculator.presentation.viewmodel.entity.HistoryEntryUi;

import java.util.Arrays;
import java.util.List;

public class HistoryAdapter extends ListAdapter<HistoryEntryUi, BaseHistoryEntryViewHolder> {

    private static final int EQUATION_VIEW_TYPE = 1;
    private static final int ANSWER_VIEW_TYPE = 2;

    private final CalculatorViewModel viewModel;

    private RecyclerView recyclerView;

    public HistoryAdapter(CalculatorViewModel viewModel) {
        super(new HistoryEntryDiffUtilItemCallback());
        this.viewModel = viewModel;
    }

    @Override
    public void submitList(@Nullable List<HistoryEntryUi> list) {
        if (list != null && getItemCount() == list.size()) {
            super.submitList(list);
        } else {
            Runnable commitCallback = this::scrollToBottom;
            super.submitList(list, commitCallback);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    private void scrollToBottom() {
        if (getItemCount() > 0) {
            this.recyclerView.scrollToPosition(getItemCount() - 1);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @Override
    public int getItemViewType(int position) {
        HistoryEntryUi item = getItem(position);
        if (item.isAnswer()) {
            return ANSWER_VIEW_TYPE;
        } else {
            return EQUATION_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public BaseHistoryEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ANSWER_VIEW_TYPE) {
            HistoryEntryAnswerBinding binding = DataBindingUtil.inflate(inflater, R.layout.history_entry_answer, parent, false);
            return new HistoryEntryAnswerViewHolder(binding);
        } else {
            HistoryEntryEquationBinding binding = DataBindingUtil.inflate(inflater, R.layout.history_entry_equation, parent, false);
            return new HistoryEntryEquationViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHistoryEntryViewHolder holder, int position) {
        holder.bind(getItem(position), viewModel);
    }

    static class HistoryEntryDiffUtilItemCallback extends DiffUtil.ItemCallback<HistoryEntryUi> {

        @Override
        public boolean areItemsTheSame(@NonNull HistoryEntryUi oldItem, @NonNull HistoryEntryUi newItem) {
            return oldItem.getClass().equals(newItem.getClass());
        }

        @Override
        public boolean areContentsTheSame(@NonNull HistoryEntryUi oldItem, @NonNull HistoryEntryUi newItem) {
            if (!oldItem.getText().toString().equals(newItem.getText().toString())) {
                return false;
            }
            ForegroundColorSpan[] oldItemSpans = oldItem.getText().getSpans(0, oldItem.getText().length(), ForegroundColorSpan.class);
            ForegroundColorSpan[] newItemSpans = newItem.getText().getSpans(0, newItem.getText().length(), ForegroundColorSpan.class);
            if (oldItemSpans.length != newItemSpans.length) {
                return false;
            }
            if (Arrays.equals(oldItemSpans, newItemSpans)) {
                return true;
            }
            return oldItemSpans[0].getForegroundColor() == newItemSpans[0].getForegroundColor();
        }
    }
}
