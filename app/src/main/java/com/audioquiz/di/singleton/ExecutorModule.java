package com.audioquiz.di.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ExecutorModule {

    @Provides
    @Singleton
    public ExecutorService provideExecutorService() {
        return Executors.newFixedThreadPool(4); // or any other configuration
    }

    @Provides
    @Singleton
    @Named("SingleThreadExecutor")
    public static ExecutorService provideSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    @Named("AuthExecutor")
    public static ExecutorService provideAuthExecutor() {
        return Executors.newSingleThreadExecutor(); // Or a custom thread pool if needed
    }

    @Provides
    @Singleton
    @Named("GeneralPurposeExecutor")
    public static ExecutorService provideGeneralPurposeExecutor() {
        return Executors.newCachedThreadPool();
    }

    @Provides
    @Singleton
    @Named("DatabaseExecutor")
    public static ExecutorService provideDatabaseExecutor() {
        return Executors.newFixedThreadPool(4); // Example thread count
    }

    @Provides
    @Singleton
    @Named("NetworkExecutor")
    public static ExecutorService provideNetworkExecutor() {
        return Executors.newCachedThreadPool();
    }
}