package com.senyk.volodymyr.calculator.presentation.view.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.senyk.volodymyr.calculator.R;
import com.senyk.volodymyr.calculator.presentation.viewmodel.util.AttrValuesProvisionUtil;
import com.senyk.volodymyr.calculator.presentation.viewmodel.util.MetricsMapperUtil;

public class CalculatorButton extends MaterialButton {

    private static final int ON_CLICK_ANIMATION_DURATION = 100;

    public CalculatorButton(@NonNull Context context) {
        super(context);
    }

    public CalculatorButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CalculatorButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        if (super.hasOnClickListeners()) {
            runOnClickAnimation();
        }
        return super.performClick();
    }

    public void runOnClickAnimation() {
        runOnClickTextAnimation();
        runOnClickElevationAnimation();
    }

    public void runOnClickTextAnimation() {
        final float normalTextSize = MetricsMapperUtil.convertPxToSp(
                AttrValuesProvisionUtil.getThemeDimenInPixels(getContext().getTheme(), R.attr.text_size_calculator_button),
                getContext()
        );
        final float onClickTextSize = normalTextSize * 0.8f;

        ValueAnimator animator = ObjectAnimator.ofFloat(this, "textSize", normalTextSize, onClickTextSize);
        animator.setDuration(ON_CLICK_ANIMATION_DURATION);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.start();
    }

    public void runOnClickElevationAnimation() {
        final float normalElevation = AttrValuesProvisionUtil.getThemeDimenInPixels(getContext().getTheme(), R.attr.elevation_button);
        final float onClickElevation = normalElevation / 2;

        ValueAnimator animator = ObjectAnimator.ofFloat(this, "elevation", normalElevation, onClickElevation);
        animator.setDuration(ON_CLICK_ANIMATION_DURATION);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.start();
    }
}
