package com.audioquiz.sync.initializer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.audioquiz.sync.worker.SyncWorker;

import java.util.Collections;
import java.util.List;

public class SyncWorkerInitializer implements Initializer<Void> {
    private static final String TAG = "SyncWorkerInitializer";

    @SuppressWarnings("NullableProblems")
    @Override
    public Void create(@NonNull Context context) {
        OneTimeWorkRequest syncWorkRequest = SyncWorker.createStartUpSyncWorkRequest();
        WorkManager.getInstance(context).enqueue(syncWorkRequest);
        Log.d(TAG, "SyncWorker enqueued");
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        // Ensure this runs after WorkManagerInitializer
        return Collections.singletonList(WorkManagerInitializer.class);
    }
}