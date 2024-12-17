/*
package com.audioquiz.sync.worker.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import dagger.hilt.EntryPoints;
import io.reactivex.rxjava3.core.Single;

public class DelegatingWorker extends RxWorker {

    private static final String WORKER_CLASS_NAME = "RouterWorkerDelegateClassName";
    private final String workerClassName;
    private final RxWorker delegateWorker;

    public DelegatingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        // Retrieve the class name of the worker to delegate to
        this.workerClassName = workerParams.getInputData().getString(WORKER_CLASS_NAME);

        if (workerClassName == null || workerClassName.isEmpty()) {
            throw new IllegalArgumentException("Worker class name is missing or empty");
        }

        // Get the HiltWorkerFactory and use it to create the delegate worker
        HiltWorkerFactoryEntryPoint entryPoint = EntryPoints.get(context, HiltWorkerFactoryEntryPoint.class);
        this.delegateWorker = (RxWorker) entryPoint.hiltWorkerFactory()
                .createWorker(context, workerClassName, workerParams);

        if (this.delegateWorker == null) {
            throw new IllegalArgumentException("Unable to find appropriate worker: " + workerClassName);
        }
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        // Delegate the work to the actual worker
        return delegateWorker.createWork();
    }
}

*/
