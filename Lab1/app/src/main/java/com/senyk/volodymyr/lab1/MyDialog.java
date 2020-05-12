package com.senyk.volodymyr.lab1;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            throw new IllegalStateException("Context can not be null");
        }
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_title)
                .setNeutralButton(R.string.dialog_action_neutral, null)
                .setPositiveButton(R.string.dialog_action_positive, null)
                .setMessage(R.string.dialog_text)
                .create();
    }
}
