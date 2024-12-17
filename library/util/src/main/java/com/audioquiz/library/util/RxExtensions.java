package com.audioquiz.library.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

public class RxExtensions {

    public static <T> LiveData<T> toLiveData(Observable<T> observable) {
        Flowable<T> flowable = observable.toFlowable(BackpressureStrategy.MISSING);
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }

    /** Apply the mapping function if object is not null. */
    public static <T, U> Flowable<U> view(Flowable<T> source, Function<T, U> fn) {
        return source.flatMap(item -> {
            U mapped = fn.apply(item);
            return mapped == null ? Flowable.empty() : Flowable.just(mapped);
        }).distinctUntilChanged();
    }

    /** Apply the mapping function if object is not null. */
    public static <T, U> Observable<U> view(Observable<T> source, Function<T, U> fn) {
        return source.flatMap(item -> {
            U mapped = fn.apply(item);
            return mapped == null ? Observable.empty() : Observable.just(mapped);
        }).distinctUntilChanged();
    }

    /** Take the first element that matches the filter function. */
    public static <T> Maybe<T> filterOne(Observable<T> source, Function<T, Boolean> fn) {
        return source.filter(fn::apply).take(1).singleElement();
    }

    /** Take the first element that matches the filter function. */
    public static <T> Maybe<T> filterOne(Flowable<T> source, Function<T, Boolean> fn) {
        return source.filter(fn::apply).take(1).singleElement();
    }

    public static <S, D, T extends TypedTask<D>> void onNextTerminalState(
            Flowable<S> source,
            Function<S, T> mapFn,
            Function<S, Void> success,
            Function<Throwable, Void> failure) {

        CompositeDisposable disposables = new CompositeDisposable();

        disposables.add(
                filterOne(source, item -> mapFn.apply(item).isTerminal())
                        .subscribe(item -> {
                            T task = mapFn.apply(item);
                            if (task.isSuccessful()) {
                                success.apply(item);
                            } else {
                                failure.apply(task.getError());
                            }
                        })
        );
    }

    public static Completable defaultTimeout(Completable source) {
        return source.timeout(20, TimeUnit.SECONDS);
    }

    public static <T> Maybe<T> defaultTimeout(Maybe<T> source) {
        return source.timeout(20, TimeUnit.SECONDS);
    }

    public static <T> Single<T> defaultTimeout(Single<T> source) {
        return source.timeout(20, TimeUnit.SECONDS);
    }

    public static <T> Observable<T> defaultTimeout(Observable<T> source) {
        return source.timeout(20, TimeUnit.SECONDS);
    }

    public interface SubscriptionTracker {
        /** Clear Subscriptions. */
        void cancelSubscriptions();

        /** Start tracking a disposable. */
        <T extends Disposable> T track(T disposable);
    }

    // Define the TypedTask interface
    public interface TypedTask<D> {
        boolean isSuccessful();
        boolean isTerminal();
        Throwable getError();
    }

    public static class DefaultSubscriptionTracker implements SubscriptionTracker {
        private final CompositeDisposable disposables = new CompositeDisposable();

        @Override
        public void cancelSubscriptions() {
            disposables.clear();
        }

        @Override
        public <T extends Disposable> T track(T disposable) {
            disposables.add(disposable);
            return disposable;
        }
    }
}