package com.audioquiz.sync.initializer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.startup.Initializer;
import androidx.work.Configuration;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;
import androidx.work.WorkerFactory;
import androidx.work.WorkerParameters;

import com.audioquiz.core.domain.usecase.quiz.question.QuestionUseCaseFacade;
import com.audioquiz.core.domain.usecase.rank.RankUseCaseFacade;
import com.audioquiz.core.domain.usecase.resources.SyncStaticResourcesUseCase;
import com.audioquiz.core.domain.usecase.user.SyncUserDataUseCase;
import com.audioquiz.sync.di.InitializerEntryPoint;
import com.audioquiz.sync.worker.SyncQuestionDataWorker;
import com.audioquiz.sync.worker.SyncRankDataWorker;
import com.audioquiz.sync.worker.SyncStaticResourcesWorker;
import com.audioquiz.sync.worker.SyncUserDataWorker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.EntryPoints;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

// Initializes WorkManager.
public class WorkManagerInitializer implements Initializer<WorkManager>, Configuration.Provider {
    @Inject
    HiltWorkerFactory workerFactory;

    @NonNull
    @Override
    public WorkManager create(@NonNull Context context) {
        Log.d("WorkManagerInitializer", "create");

        InitializerEntryPoint.resolve(context).inject(this);
        WorkManager.initialize(context, getWorkManagerConfiguration());

        return WorkManager.getInstance(context);
    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        Log.d("WorkManagerInitializer", "getWorkManagerConfiguration: " + workerFactory);
        return new Configuration.Builder()
                .setWorkerFactory(new CustomWorkerFactory(workerFactory))
                .build();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return List.of(DependencyGraphInitializer.class, DatabaseInitializer.class);
    }

    public static class CustomWorkerFactory extends WorkerFactory {
        private final HiltWorkerFactory hiltWorkerFactory;

        @Inject
        public CustomWorkerFactory(HiltWorkerFactory hiltWorkerFactory) {
            this.hiltWorkerFactory = hiltWorkerFactory;
        }

        @Override
        public ListenableWorker createWorker(@NonNull Context appContext,
                                             @NonNull String workerClassName,
                                             @NonNull WorkerParameters workerParameters) {

            WorkerEntryPoint entryPoint = EntryPoints.get(appContext, WorkerEntryPoint.class);
            Log.d("CustomWorkerFactory", "createWorker: " + workerClassName);

            return switch (workerClassName) {
                case "com.audioquiz.sync.worker.SyncStaticResourcesWorker" ->
                        new SyncStaticResourcesWorker(appContext, workerParameters, entryPoint.syncStaticResourceUseCase());
                case "com.audioquiz.sync.worker.SyncUserDataWorker" ->
                        new SyncUserDataWorker(appContext, workerParameters, entryPoint.syncUserDataUseCase());
                case "SyncQuestionDataWorker" ->
                        new SyncQuestionDataWorker(appContext, workerParameters, entryPoint.syncQuestionDataUseCase(), WorkerEntryPoint.ioScheduler, WorkerEntryPoint.executorService);
                case "SyncRankDataWorker" ->
                        new SyncRankDataWorker(appContext, workerParameters, entryPoint.syncRankDataUseCase());
                default ->
                        hiltWorkerFactory.createWorker(appContext, workerClassName, workerParameters);
            };
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent.class)
    public interface WorkerEntryPoint {
        SyncStaticResourcesUseCase syncStaticResourceUseCase();
        SyncUserDataUseCase syncUserDataUseCase();
        QuestionUseCaseFacade syncQuestionDataUseCase();
        RankUseCaseFacade syncRankDataUseCase();
        // After
        Scheduler ioScheduler = Schedulers.io();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
    }
}