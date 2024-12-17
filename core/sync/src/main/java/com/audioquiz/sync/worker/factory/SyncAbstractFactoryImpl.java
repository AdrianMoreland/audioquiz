package com.audioquiz.sync.worker.factory;

import androidx.work.BackoffPolicy;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;

import com.audioquiz.sync.worker.WorkInfoUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class SyncAbstractFactoryImpl implements SyncAbstractFactory {

    @Inject
    public SyncAbstractFactoryImpl() {
        // Empty constructor
    }

    @Override
    public <T extends ListenableWorker> Single<OneTimeWorkRequest> createOneTimeWorker(Class<T> workerClass) {
        return Single.fromCallable(() ->
                new OneTimeWorkRequest.Builder(workerClass)
                        .addTag(workerClass.getName()+"_OneTime")
                        .setConstraints(WorkInfoUtils.getConstraints())
                        .build()
        );
    }

    @Override
    public <T extends ListenableWorker> Single<PeriodicWorkRequest> createPeriodicWorker(Class<T> workerClass, long interval) {
        return Single.fromCallable(() ->
                new PeriodicWorkRequest.Builder(workerClass, interval, TimeUnit.MINUTES)
                        .addTag(workerClass.getName()+"_Periodic")
                        .setConstraints(WorkInfoUtils.getConstraints())
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MAX_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build()
        );
    }
}
