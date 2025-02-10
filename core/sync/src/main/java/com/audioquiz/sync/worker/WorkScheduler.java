package com.audioquiz.sync.worker;

import android.content.Context;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.WorkManager;
import androidx.work.ExistingWorkPolicy;

import com.audioquiz.sync.worker.factory.SyncAbstractFactory;

import javax.inject.Inject;

public class WorkScheduler {

    private static final String SYNC_STATIC_RESOURCES_WORK = "SyncStaticResourcesWork";
    private static final String SYNC_USER_DATA_WORK = "SyncUserDataWork";
    private static final String SYNC_RANK_DATA_WORK = "SyncRankDataWork";

    private final WorkManager workManager;
    private final SyncAbstractFactory syncFactory;

    @Inject
    public WorkScheduler(Context context, SyncAbstractFactory syncFactory) {
        this.workManager = WorkManager.getInstance(context);
        this.syncFactory = syncFactory;
    }

    // Immediate one-time user data sync, e.g., at startup
    public void scheduleImmediateUserDataSync() {
        syncFactory.createOneTimeWorker(SyncUserDataWorker.class)
                .doOnSuccess(syncRequest ->
                        workManager.enqueueUniqueWork(
                                SYNC_USER_DATA_WORK,
                                ExistingWorkPolicy.REPLACE,
                                syncRequest
                        )
                )
                .ignoreElement()
                .subscribe();
    }

    // Schedule a periodic sync for user data with a specified interval
    public void schedulePeriodicUserDataSync(long interval) {
        syncFactory.createPeriodicWorker(SyncUserDataWorker.class, interval)
                .doOnSuccess(syncRequest ->
                        workManager.enqueueUniquePeriodicWork(
                                SYNC_USER_DATA_WORK,
                                ExistingPeriodicWorkPolicy.KEEP,
                                syncRequest
                        )
                )
                .ignoreElement()
                .subscribe();
    }

    // Schedule a one-time sync for question data
    public void scheduleOneTimeQuestionSync() {
        syncFactory.createOneTimeWorker(SyncQuestionDataWorker.class)
                .doOnSuccess(syncRequest ->
                        workManager.enqueueUniqueWork(
                                SYNC_USER_DATA_WORK,
                                ExistingWorkPolicy.REPLACE,
                                syncRequest
                        )
                )
                .ignoreElement()
                .subscribe();
    }

    public void scheduleOneTimeStaticResourcesSync() {
        syncFactory.createOneTimeWorker(SyncStaticResourcesWorker.class)
                .doOnSuccess(syncRequest ->
                        workManager.enqueueUniqueWork(
                                SYNC_STATIC_RESOURCES_WORK,
                                ExistingWorkPolicy.REPLACE,
                                syncRequest
                        ))
                .ignoreElement()
                .subscribe();
    }

    // Schedule a one-time sync for leaderboard/rank data
    public void scheduleOneTimeLeaderboardSync() {
        syncFactory.createOneTimeWorker(SyncRankDataWorker.class)
                .doOnSuccess(syncRequest ->
                        workManager.enqueueUniqueWork(
                                SYNC_RANK_DATA_WORK,
                                ExistingWorkPolicy.REPLACE,
                                syncRequest
                        )
                )
                .ignoreElement()
                .subscribe();
    }

}
