package com.senyk.volodymyr.quiz.presentation.view.customview;

import android.content.Context;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textview.MaterialTextView;

public class FixedLinesAdaptiveTextView extends MaterialTextView {

    public FixedLinesAdaptiveTextView(@NonNull Context context) {
        super(context);
    }

    public FixedLinesAdaptiveTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedLinesAdaptiveTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (text != null && text.length() > 0 && getHeight() > 0 && getWidth() > 0) {
            resizeText();
        }
    }

    private void resizeText() {
        CharSequence text = getText();
        if (getTransformationMethod() != null) {
            text = getTransformationMethod().getTransformation(text, this);
        }

        float textPlaceWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float textPlaceHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        float newTextSize = getCharSizeForMaxWidth(text.toString(), textPlaceWidth, textPlaceHeight, getPaint(), getTextSize());
        setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
    }

    private String getLongestLine(final String text, final TextPaint textPaint, final float charSize) {
        String[] lines = text.split("\n");
        String longestLine = "";
        for (String line : lines) {
            if (getTextWidth(line, textPaint, charSize) > getTextWidth(longestLine, textPaint, charSize)) {
                longestLine = line;
            }
        }
        return longestLine;
    }

    private float getCharSizeForMaxWidth(final String text, final float maxWidth, final float maxHeight, final TextPaint textPaint, final float oldCharSize) {
        float currentCharSize = oldCharSize;
        String longestLine = getLongestLine(text, textPaint, currentCharSize);
        float longestLineWidth = getTextWidth(longestLine, textPaint, currentCharSize);
        while (longestLineWidth < maxWidth) {
            currentCharSize += 2;
            longestLineWidth = getTextWidth(longestLine, textPaint, currentCharSize);
        }
        currentCharSize = getCharSizeToFitViewWidth(longestLine, maxWidth, textPaint, currentCharSize);
        currentCharSize = getCharSizeToFitViewHeight(text, maxWidth, maxHeight, textPaint, currentCharSize);
        return currentCharSize;
    }

    private float getCharSizeForMaxHeight(final String text, final float maxWidth, final float maxHeight, final TextPaint textPaint, final float oldCharSize) {
        float currentCharSize = oldCharSize;
        float longestLineWidth = getTextHeight(text, textPaint, maxWidth, currentCharSize);
        while (longestLineWidth < maxHeight) {
            currentCharSize += 2;
            longestLineWidth = getTextHeight(text, textPaint, maxWidth, currentCharSize);
        }
        currentCharSize = getCharSizeToFitViewHeight(text, maxWidth, maxHeight, textPaint, currentCharSize);
        return currentCharSize;
    }

    private float getCharSizeToFitViewWidth(final String longestLine, final float viewWidth, final TextPaint textPaint, float currentCharSize) {
        float longestLineWidth = getTextWidth(longestLine, textPaint, currentCharSize);
        while (longestLineWidth > viewWidth) {
            currentCharSize -= 2;
            longestLineWidth = getTextWidth(longestLine, textPaint, currentCharSize);
        }
        return currentCharSize;
    }

    private float getCharSizeToFitViewHeight(final String text, final float maxWidth, final float maxHeight, final TextPaint textPaint, float currentCharSize) {
        float textHeight = getTextHeight(text, textPaint, maxWidth, currentCharSize);
        while (textHeight > maxHeight) {
            currentCharSize -= 2;
            textHeight = getTextHeight(text, textPaint, maxWidth, currentCharSize);
        }
        return currentCharSize;
    }

    private float getTextWidth(final String text, final TextPaint paint, final float textSize) {
        TextPaint paintCopy = new TextPaint(paint);
        paintCopy.setTextSize(textSize);
        return paintCopy.measureText(text);
    }

    private float getTextHeight(final String text, final TextPaint paint, final float viewWidth, final float textSize) {
        TextPaint paintCopy = new TextPaint(paint);
        paintCopy.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(text, paintCopy, (int) viewWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true);
        return layout.getHeight();
    }
}
