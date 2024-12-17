package com.audioquiz.sync.worker;

import androidx.work.Constraints;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class WorkInfoUtils {

    private WorkInfoUtils() {
        // Utility class
    }

    public static Constraints getConstraints() {
        return new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
    }

    public static Observable<WorkInfo> filterNonSucceededWorkers(List<WorkInfo> workInfoList) {
        return Observable.fromIterable(workInfoList)
                .filter(workInfo -> workInfo.getState() == WorkInfo.State.FAILED ||
                        workInfo.getState() == WorkInfo.State.BLOCKED ||
                        workInfo.getState() == WorkInfo.State.RUNNING);
    }

    public static Observable<WorkInfo> filterFailedWorkers(List<WorkInfo> workInfoList) {
        return Observable.fromIterable(workInfoList)
                .filter(workInfo -> workInfo.getState() == WorkInfo.State.FAILED);
    }

    public static Single<Boolean> areAllWorkersSucceeded(List<WorkInfo> workInfoList) {
        return Observable.fromIterable(workInfoList)
                .all(workInfo -> workInfo.getState() == WorkInfo.State.SUCCEEDED);
    }

    public static Single<Boolean> areAllWorkersFinished(List<WorkInfo> workInfoList) {
        return Observable.fromIterable(workInfoList)
                .all(workInfo -> workInfo.getState() == WorkInfo.State.SUCCEEDED ||
                        workInfo.getState() == WorkInfo.State.FAILED ||
                        workInfo.getState() == WorkInfo.State.CANCELLED);
    }

    public static String toReadableErrorMessage(List<String> errorMessages) {
        return String.join(" and ", errorMessages);
    }
}
