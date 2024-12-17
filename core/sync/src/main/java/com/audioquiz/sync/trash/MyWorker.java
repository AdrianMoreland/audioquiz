/*
package com.audioquiz.sync.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.audioquiz.core.domain.usecase.user.stats.StatisticsUseCaseFacade;

import java.util.concurrent.TimeUnit;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltWorker
public class MyWorker extends Worker {

    public static final String TAG = "MyWork";
    private static final long DEFAULT_MIN_INTERVAL = 15L;

    private final StatisticsUseCaseFacade useCase;

    @AssistedInject
    public MyWorker(@Assisted @NonNull Context appContext,
                    @Assisted @NonNull WorkerParameters workerParams,
                    StatisticsUseCaseFacade useCase) {
        super(appContext, workerParams);
        this.useCase = useCase;
    }

    @NonNull
    public Single<Result> createWork() {
        return Completable.fromAction(() -> {
                    Log.d(getClass().getSimpleName(), "createWork: starting my work");
                    useCase.sync();
                    Thread.sleep(2000L);
                })
                .andThen(Single.just(Result.success()))
//                .andThen(Single.just(ListenableWorker.Result.success()))
//                .onErrorReturnItem(ListenableWorker.Result.failure())
                .subscribeOn(Schedulers.io());
    }


    public static OneTimeWorkRequest oneTimeWorkRequest() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        return new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints)
                .addTag("my_work_tag")
                .build();
    }

    public static PeriodicWorkRequest periodicWorkRequest() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        return new PeriodicWorkRequest.Builder(MyWorker.class, DEFAULT_MIN_INTERVAL, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: starting my work");
        return null;
    }
}*/
