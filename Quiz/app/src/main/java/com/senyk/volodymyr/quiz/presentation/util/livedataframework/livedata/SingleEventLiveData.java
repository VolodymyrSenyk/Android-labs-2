package com.senyk.volodymyr.quiz.presentation.util.livedataframework.livedata;

import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleEventLiveData<T> extends MutableLiveData<T> {

    private final AtomicBoolean alreadySent = new AtomicBoolean(true);

    @Override
    @MainThread
    public void observe(@NonNull final LifecycleOwner owner, @NonNull final Observer<? super T> observer) {
        super.observe(owner, value -> {
            if (this.alreadySent.compareAndSet(false, true)) {
                observer.onChanged(value);
            }
        });
    }

    @Override
    @MainThread
    public void setValue(@Nullable final T value) {
        this.alreadySent.set(false);
        super.setValue(value);
    }

    @Override
    @AnyThread
    public void postValue(@Nullable T value) {
        this.alreadySent.set(false);
        super.postValue(value);
    }
}
