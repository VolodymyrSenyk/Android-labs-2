package com.senyk.volodymyr.labthread.domain.interactor;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senyk.volodymyr.labthread.domain.entity.ThreadState;

public class PrimeNumbersFinder implements Runnable {

    private static final int INTERVAL = 1000;
    private static final int CHECK_PAUSE_INTERVAL = 1000;

    private final Runnable displayPrimeNumber;

    private Thread workerThread;
    private int lastPrimeNumber;

    private MutableLiveData<ThreadState> threadState = new MutableLiveData<>();

    public PrimeNumbersFinder(@NonNull final Runnable addResult) {
        threadState.setValue(ThreadState.INACTIVE);
        displayPrimeNumber = addResult;
    }

    public LiveData<ThreadState> getThreadState() {
        return threadState;
    }

    public int getLastPrimeNumber() {
        return lastPrimeNumber;
    }

    @Override
    public void run() {
        for (int number = 0; number < Integer.MAX_VALUE; number++) {
            try {
                if (isPrime(number)) {
                    lastPrimeNumber = number;
                    displayPrimeNumber.run();
                    Thread.sleep(INTERVAL);
                }
            } catch (InterruptedException intervalInterruptedException) {
                while (threadState.getValue() == ThreadState.PAUSED) {
                    try {
                        Thread.sleep(CHECK_PAUSE_INTERVAL);
                    } catch (InterruptedException pauseInterruptedException) {
                        if (threadState.getValue() == ThreadState.INACTIVE) {
                            return;
                        }
                    }
                }
                if (threadState.getValue() == ThreadState.INACTIVE) {
                    return;
                }
            }
        }
        threadState.postValue(ThreadState.INACTIVE);
    }

    private boolean isPrime(int numberToCheck) {
        if (numberToCheck <= 1) return false;
        if (numberToCheck <= 3) return true;
        if (numberToCheck % 2 == 0 || numberToCheck % 3 == 0) return false;
        for (long i = 5; i * i <= numberToCheck; i += 6) {
            if (numberToCheck % i == 0 || numberToCheck % (i + 2) == 0) return false;
        }
        return true;
    }

    public void start() {
        workerThread = new Thread(this);
        workerThread.start();
        threadState.setValue(ThreadState.RUNNING);
    }

    public void suspend() {
        workerThread.interrupt();
        threadState.setValue(ThreadState.PAUSED);
    }

    public void resume() {
        workerThread.interrupt();
        threadState.setValue(ThreadState.RUNNING);
    }

    public void stop() {
        workerThread.interrupt();
        threadState.setValue(ThreadState.INACTIVE);
    }
}
