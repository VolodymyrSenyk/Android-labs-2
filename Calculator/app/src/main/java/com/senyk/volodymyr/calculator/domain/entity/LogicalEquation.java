package com.senyk.volodymyr.calculator.domain.entity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LogicalEquation implements Cloneable {

    private static final String TAG = LogicalEquation.class.getSimpleName();
    private static final int DEFAULT_CURSOR_POSITION = 0;

    private List<LogicalSymbol> text = new ArrayList<>();
    private int cursorPosition = 0;
    private int numberOfNotClosedBrackets = 0;
    private boolean currentUnitDegrees = true;

    public List<LogicalSymbol> getText() {
        return text;
    }

    public void setText(List<LogicalSymbol> text) {
        this.text = text;
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    public void setCursorPosition(int cursorPosition) {
        this.cursorPosition = cursorPosition;
    }

    public int getNumberOfNotClosedBrackets() {
        return numberOfNotClosedBrackets;
    }

    public void incrementNotClosedBracketsCount() {
        numberOfNotClosedBrackets++;
    }

    public void decrementNotClosedBracketsCount() {
        numberOfNotClosedBrackets--;
    }

    public boolean isCurrentUnitDegrees() {
        return currentUnitDegrees;
    }

    public void setCurrentUnit(boolean degrees) {
        currentUnitDegrees = degrees;
    }

    public int size() {
        return text.size();
    }

    public boolean isEmpty() {
        return text.isEmpty();
    }

    @Nullable
    public LogicalSymbol getPrev() {
        if (isPositionValid(cursorPosition - 1)) {
            return text.get(cursorPosition - 1);
        }
        return null;
    }

    @Nullable
    public LogicalSymbol getNext() {
        if (isPositionValid(cursorPosition)) {
            return text.get(cursorPosition);
        }
        return null;
    }

    public LogicalSymbol get(int index) {
        return text.get(index);
    }

    public void add(LogicalSymbol symbol) {
        add(cursorPosition, symbol);
    }

    public void add(int index, LogicalSymbol symbol) {
        if (index < 0 || index > text.size()) {
            return;
        }
        text.add(index, symbol);
        cursorPosition++;
    }

    public void remove() {
        remove(cursorPosition - 1);
    }

    public void removeNext() {
        remove(cursorPosition);
    }

    public void remove(int index) {
        if (index < 0 || index >= text.size()) {
            return;
        }
        text.remove(index);
        if (index < cursorPosition) {
            cursorPosition--;
        }
    }

    public void clear() {
        text.clear();
        cursorPosition = DEFAULT_CURSOR_POSITION;
        numberOfNotClosedBrackets = 0;
    }

    @Override
    @NonNull
    public LogicalEquation clone() {
        try {
            LogicalEquation clone = (LogicalEquation) super.clone();
            clone.text = new ArrayList<>(this.text);
            clone.cursorPosition = this.cursorPosition;
            clone.numberOfNotClosedBrackets = this.numberOfNotClosedBrackets;
            clone.currentUnitDegrees = this.currentUnitDegrees;
            return clone;
        } catch (CloneNotSupportedException e) {
            Log.e(TAG, this.toString() + " can not be cloned; " + e.getMessage());
        }
        return new LogicalEquation();
    }

    private boolean isPositionValid(int position) {
        return position >= 0 && position < text.size();
    }
}
