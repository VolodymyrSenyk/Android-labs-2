package com.senyk.volodymyr.quiz.presentation.util.livedataframework.event;

import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleEvent<T> {

    private final T value;
    private AtomicBoolean alreadyHandled;

    public SingleEvent(@Nullable final T value) {
        this.value = value;
        this.alreadyHandled = new AtomicBoolean(false);
    }

    @Nullable
    public T handleEvent() {
        if (this.alreadyHandled.compareAndSet(false, true)) {
            return this.value;
        }
        return null;
    }

    @Nullable
    public T getEventData() {
        return this.value;
    }
}
