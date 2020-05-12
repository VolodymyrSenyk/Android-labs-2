package com.senyk.volodymyr.quiz.presentation.util.livedataframework.livedata;

import androidx.lifecycle.MutableLiveData;

import com.senyk.volodymyr.quiz.presentation.util.livedataframework.event.NavigationEvent;
import com.senyk.volodymyr.quiz.presentation.util.livedataframework.event.SingleEvent;

public class NavigationEventLiveData<T extends NavigationEvent> extends MutableLiveData<SingleEvent<T>> {
}
