/*
package com.audioquiz.sync.di;

import androidx.hilt.work.HiltWorkerFactory;

import com.audioquiz.sync.worker.SyncWorker;
import com.audioquiz.sync.worker.SyncWorkerFactory;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
@InstallIn(SingletonComponent.class)
public abstract class WorkerModule {

    @Binds
    @IntoMap
    @StringKey("SyncWorker")
    public abstract HiltWorkerFactory bindSyncWorkerFactory(SyncWorkerFactory factory);

*/
/*    @Binds
    abstract WorkerFactory bindWorkerFactory(MyWorkerFactory factory);

    // Worker factory implementation
    public static class MyWorkerFactory extends WorkerFactory {
        @Override
        public ListenableWorker createWorker(@NonNull Context appContext,
                                             @NonNull String workerClassName,
                                             @NonNull WorkerParameters workerParameters) {
            if (workerClassName.equals(SyncWorker.class.getName())) {
                // Use Hilt to retrieve the SyncUserDataUseCase
                SyncUserDataUseCase syncUserDataUseCase = EntryPointAccessors.fromApplication(
                        appContext,
                        WorkerEntryPoint.class
                ).syncUserDataUseCase();
                return new SyncWorker(appContext, workerParameters, syncUserDataUseCase);
            }
            return null; // Return null for unrecognized worker classes
        }
    }


    // Define the entry point interface
    @EntryPoint
    @InstallIn(SingletonComponent.class)
    public interface WorkerEntryPoint {
        SyncUserDataUseCase syncUserDataUseCase();
    }*//*

}
*/
