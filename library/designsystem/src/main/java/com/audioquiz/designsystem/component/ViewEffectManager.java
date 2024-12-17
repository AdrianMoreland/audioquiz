package com.audioquiz.designsystem.component;

import com.audioquiz.designsystem.base.view.ViewEffect;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class ViewEffectManager<VE extends ViewEffect> {
    private final PublishSubject<VE> viewEffect = PublishSubject.create();

    public Observable<VE> getViewEffect() {
        return viewEffect.hide();
    }

    public void sendViewEffect(VE effect) {
        viewEffect.onNext(effect);
    }
}
