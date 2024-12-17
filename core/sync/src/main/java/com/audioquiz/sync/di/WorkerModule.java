package com.audioquiz.sync.di;

import android.app.Application;

import com.audioquiz.sync.worker.WorkScheduler;
import com.audioquiz.sync.worker.factory.SyncAbstractFactory;
import com.audioquiz.sync.worker.factory.SyncAbstractFactoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class WorkerModule {

    @Binds
    @Singleton
    public abstract SyncAbstractFactory bindSyncAbstractFactory(SyncAbstractFactoryImpl impl);

    @Provides
    @Singleton
    public static WorkScheduler provideWorkScheduler(Application application, SyncAbstractFactory syncAbstractFactory) {
        return new WorkScheduler(application, syncAbstractFactory);
    }
}
