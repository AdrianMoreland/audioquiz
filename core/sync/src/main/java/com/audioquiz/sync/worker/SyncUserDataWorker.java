package com.audioquiz.sync.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.audioquiz.core.domain.usecase.user.SyncUserDataUseCase;

import java.util.concurrent.TimeUnit;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltWorker
public class SyncUserDataWorker extends RxWorker {
    private static final String TAG = "SyncUserDataWorker";
    private static final long DEFAULT_MIN_INTERVAL = 15L;
    public static final String WORKER_NAME = SyncUserDataWorker.class.getName();
    private final SyncUserDataUseCase syncUserDataUseCase;

    @AssistedInject
    public SyncUserDataWorker(@Assisted @NonNull Context appContext,
                              @Assisted @NonNull WorkerParameters workerParams,
                              SyncUserDataUseCase syncUserDataUseCase) {
        super(appContext, workerParams);
        this.syncUserDataUseCase = syncUserDataUseCase;
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        Log.d(TAG, "createWork");
        if (syncUserDataUseCase == null) {
            Log.e(TAG, "UseCase is null");
            return Single.just(Result.failure());
        }
        // Sync user data through the use case
        return syncUserDataUseCase.execute()
                .toSingleDefault(Result.success())
                .onErrorReturn(throwable -> {
                    Log.e(TAG, "User data sync failed", throwable);
                    return Result.failure();
                })
                .subscribeOn(Schedulers.io());
    }
}
