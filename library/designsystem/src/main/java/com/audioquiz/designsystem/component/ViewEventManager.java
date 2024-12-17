package com.audioquiz.designsystem.component;

import com.audioquiz.designsystem.base.view.ViewEvent;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class ViewEventManager<VA extends ViewEvent> {
    private final PublishSubject<VA> viewEvent = PublishSubject.create();

    public Observable<VA> getViewEvent() {
        return viewEvent.hide();
    }

    public void sendViewEvent(VA action) {
        viewEvent.onNext(action);
    }
}
