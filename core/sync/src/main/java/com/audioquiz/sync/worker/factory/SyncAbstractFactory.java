package com.audioquiz.sync.worker.factory;

import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;

import io.reactivex.rxjava3.core.Single;

public interface SyncAbstractFactory {
    <T extends ListenableWorker> Single<OneTimeWorkRequest> createOneTimeWorker(Class<T> workerClass);
    <T extends ListenableWorker> Single<PeriodicWorkRequest> createPeriodicWorker(Class<T> workerClass, long interval);
}
