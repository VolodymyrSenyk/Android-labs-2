package com.senyk.volodymyr.labthread.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.senyk.volodymyr.labthread.domain.entity.ThreadState;
import com.senyk.volodymyr.labthread.domain.interactor.PrimeNumbersFinder;

public class ThreadViewModel extends ViewModel {

    private static final String NO_RESULTS = "";

    private final PrimeNumbersFinder primeNumbersFinder = new PrimeNumbersFinder(this::addResult);

    private MutableLiveData<String> results = new MutableLiveData<>();

    public LiveData<ThreadState> threadState() {
        return primeNumbersFinder.getThreadState();
    }

    public LiveData<String> results() {
        return results;
    }

    public ThreadViewModel() {
        results.setValue(NO_RESULTS);
    }

    public void onStartClick() {
        if (results.getValue() != null && !results.getValue().equals(NO_RESULTS)) {
            results.setValue(NO_RESULTS);
        }
        primeNumbersFinder.start();
    }

    public void onPauseClick() {
        primeNumbersFinder.suspend();
    }

    public void onContinueClick() {
        primeNumbersFinder.resume();
    }

    public void onStopClick() {
        primeNumbersFinder.stop();
    }

    public void onRestartClick() {
        results.setValue(NO_RESULTS);
        primeNumbersFinder.stop();
        primeNumbersFinder.start();
    }

    private void addResult() {
        results.postValue(results.getValue() + "\n" + primeNumbersFinder.getLastPrimeNumber());
    }
}
