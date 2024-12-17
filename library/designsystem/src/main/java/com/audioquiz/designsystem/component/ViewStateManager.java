package com.audioquiz.designsystem.component;

import com.audioquiz.designsystem.base.view.ViewState;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.ReplaySubject;

public class ViewStateManager<VS extends ViewState> {
    private final ReplaySubject<VS> viewState = ReplaySubject.createWithSize(1);

    public Observable<VS> getViewState() {
        return viewState.hide();
    }

    public void updateViewState(VS newState) {
        viewState.onNext(newState);
    }
}
