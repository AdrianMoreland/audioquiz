/*
package com.audioquiz.sync.worker.util;

import android.content.Context;

import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.audioquiz.sync.worker.SyncUserDataWorker;

public class Sync {

    public static final String SYNC_WORK_NAME = "SyncWorkName";

    private Sync() {
        // Prevent instantiation
    }

    // This method initializes sync, the process that keeps the app's data current.
    // It is called from the app module's Application.onCreate() and should be only done once.
    public static void initialize(Context context) {
        WorkManager workManager = WorkManager.getInstance(context);

        // Run sync on app startup and ensure only one sync worker runs at any time
        OneTimeWorkRequest syncWorkRequest = SyncUserDataWorker.createStartUpSyncWorkRequest();
        workManager.enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                syncWorkRequest
        );
    }
}

*/
