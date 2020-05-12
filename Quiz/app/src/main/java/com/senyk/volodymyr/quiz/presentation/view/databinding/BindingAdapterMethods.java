package com.senyk.volodymyr.quiz.presentation.view.databinding;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.google.android.material.button.MaterialButton;
import com.senyk.volodymyr.quiz.R;
import com.senyk.volodymyr.quiz.domain.entity.Answer;

import java.util.List;

public class BindingAdapterMethods {

    @BindingAdapter({"android:background"})
    public static void setColor(MotionLayout layout, List<Answer> answers) {
        final Context context = layout.getContext();
        @ColorRes int colorRes = R.color.colorBackground;
        if (answers != null) {
            for (Answer answer : answers) {
                if (answer.isSelected()) {
                    colorRes = answer.isCorrect() ? R.color.colorTrueLight : R.color.colorFalseLight;
                    break;
                }
            }
        }
        layout.setBackgroundColor(ContextCompat.getColor(context, colorRes));
    }

    @BindingAdapter({"backgroundTint"})
    public static void setColor(MaterialButton answerButton, Answer answer) {
        final Context context = answerButton.getContext();
        @ColorRes final int colorRes;
        if (answer != null && answer.isSelected()) {
            colorRes = answer.isCorrect() ? R.color.colorTrue : R.color.colorFalse;
        } else {
            colorRes = R.color.colorButton;
        }
        answerButton.setBackgroundColor(ContextCompat.getColor(context, colorRes));
    }
}
