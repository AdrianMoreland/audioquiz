package com.audioquiz.sync.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.audioquiz.core.domain.usecase.rank.RankUseCaseFacade;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SyncRankDataWorker extends RxWorker {
    private static final String TAG = "SyncRankDataWorker";
    private static final long DEFAULT_MIN_INTERVAL = 15L;
    public static final String WORKER_NAME = SyncRankDataWorker.class.getName();
    private final RankUseCaseFacade rankUseCaseFacade;

    @AssistedInject
    public SyncRankDataWorker(@Assisted @NonNull Context appContext,
                              @Assisted @NonNull WorkerParameters workerParams,
                              RankUseCaseFacade rankUseCaseFacade) {
        super(appContext, workerParams);
        this.rankUseCaseFacade = rankUseCaseFacade;
    }

    @NonNull
    @Override
    public Single<ListenableWorker.Result> createWork() {
        Log.d(TAG, "createWork");
        return syncData()
                .toSingleDefault(ListenableWorker.Result.success())
                .onErrorReturn(throwable -> {
                    Log.e(TAG, "Sync failed", throwable);
                    return ListenableWorker.Result.failure();
                })
                .subscribeOn(Schedulers.io());
    }

    private Completable syncData() {
        Log.d(TAG, "Syncing rank data");
        if (rankUseCaseFacade == null) {
            return Completable.error(new NullPointerException("UseCase is null"));
        }
        return rankUseCaseFacade.sync();
    }
}
