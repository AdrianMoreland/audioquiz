/*
package com.audioquiz.sync.worker.status;


import static com.audioquiz.sync.worker.util.Sync.SYNC_WORK_NAME;

import android.content.Context;
import android.util.Log;

import androidx.work.ExistingWorkPolicy;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.audioquiz.sync.worker.SyncUserDataWorker;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

*/
/**
 * {@link SyncManager} backed by {@link WorkInfo} from {@link WorkManager}
 *//*

class WorkManagerSyncManager implements SyncManager {

    private final Context context;

    @Inject
    public WorkManagerSyncManager(@ApplicationContext Context context) {
        this.context = context;
    }


*/
/*    @Override
    public Observable<Boolean> isSyncing() {
        Log.d("WorkManagerSyncManager", "isSyncing");
        return Observable.fromCallable(() ->
                        WorkManager.getInstance(context).getWorkInfosForUniqueWork(SYNC_WORK_NAME).get())
                .flatMap(Observable::fromIterable).map(workInfo -> workInfo.getState() == WorkInfo.State.RUNNING)
                .debounce(100, TimeUnit.MILLISECONDS) // Debounce for 100ms
                ;
    }*//*


    @Override
    public void requestSync() {
        Log.d("WorkManagerSyncManager", "requestSync");
        WorkManager workManager = WorkManager.getInstance(context);
        // Run sync on app startup and ensure only one sync worker runs at any time
        workManager.enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                SyncUserDataWorker.createStartUpSyncWorkRequest()
        );
    }
}*/
