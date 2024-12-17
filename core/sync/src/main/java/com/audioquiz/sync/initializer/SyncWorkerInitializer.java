package com.audioquiz.sync.initializer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.audioquiz.sync.di.InitializerEntryPoint;
import com.audioquiz.sync.worker.SyncUserDataWorker;
import com.audioquiz.sync.worker.WorkScheduler;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class SyncWorkerInitializer implements Initializer<Void> {
    private static final String TAG = "SyncWorkerInitializer";

    @Inject
    WorkScheduler workScheduler;

    @SuppressWarnings("NullableProblems")
    @Override
    public Void create(@NonNull Context context) {
        // Manually resolve the entry point and inject dependencies
        InitializerEntryPoint entryPoint = InitializerEntryPoint.resolve(context);
        entryPoint.inject(this); // Ensure this injects the WorkScheduler instance

        // Now you can safely use workScheduler
        if (workScheduler != null) {
            workScheduler.scheduleImmediateUserDataSync();
            Log.d(TAG, "SyncUserDataWorker enqueued");
        } else {
            Log.e(TAG, "WorkScheduler is null!");
        }
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        // Ensure this runs after WorkManagerInitializer
        return Collections.singletonList(WorkManagerInitializer.class);
    }
}