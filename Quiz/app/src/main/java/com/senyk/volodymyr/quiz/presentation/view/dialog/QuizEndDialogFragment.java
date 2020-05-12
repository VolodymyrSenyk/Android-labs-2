package com.senyk.volodymyr.quiz.presentation.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.senyk.volodymyr.quiz.R;

public class QuizEndDialogFragment extends DialogFragment {

    public static final String TAG = QuizEndDialogFragment.class.toString();

    private static final String SCORE_MESSAGE_KEY = "SCORE_MESSAGE_KEY";

    private QuizEndDialogFragment.OnButtonClickListener onButtonClickListener;

    public static QuizEndDialogFragment newInstance(final String scoreMessage) {
        QuizEndDialogFragment dialog = new QuizEndDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SCORE_MESSAGE_KEY, scoreMessage);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static QuizEndDialogFragment createAndShow(@NonNull final FragmentManager supportFragmentManager, final String scoreMessage) {
        QuizEndDialogFragment dialog = newInstance(scoreMessage);
        dialog.setCancelable(false);
        dialog.show(supportFragmentManager, TAG);
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getTargetFragment() != null) {
            onButtonClickListener = (QuizEndDialogFragment.OnButtonClickListener) getTargetFragment();
        } else {
            onButtonClickListener = (QuizEndDialogFragment.OnButtonClickListener) getActivity();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            throw new IllegalArgumentException("Context can not be null");
        }
        String message;
        if (getArguments() == null) {
            message = getString(R.string.no_questions_left_dialog_message, "");
        } else {
            message = getString(R.string.no_questions_left_dialog_message, getArguments().getString(SCORE_MESSAGE_KEY));
        }
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.no_questions_left_dialog_title)
                .setMessage(message)
                .setNegativeButton(
                        R.string.no_questions_left_dialog_exit,
                        (dialogInterface, i) -> onButtonClickListener.OnNoQuestionsLeftDialogFragmentNegativeButtonClickListener()
                )
                .setPositiveButton(
                        R.string.no_questions_left_dialog_again,
                        (dialogInterface, i) -> onButtonClickListener.OnNoQuestionsLeftDialogFragmentPositiveButtonClickListener()
                )
                .create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onButtonClickListener = null;
    }

    public interface OnButtonClickListener {

        void OnNoQuestionsLeftDialogFragmentPositiveButtonClickListener();

        void OnNoQuestionsLeftDialogFragmentNegativeButtonClickListener();
    }
}
