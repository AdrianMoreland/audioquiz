package com.audioquiz.sync.di;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.room.RoomDatabase;

import com.audioquiz.data.local.provider.AppDatabase;
import com.audioquiz.sync.initializer.SyncWorkerInitializer;
import com.audioquiz.sync.initializer.WorkManagerInitializer;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface InitializerEntryPoint {
    String TAG = "InitializerEntryPoint";

    void inject(WorkManagerInitializer initializer);
    void inject(SyncWorkerInitializer initializer);

    // Add a method for AppDatabase
    AppDatabase getAppDatabase();


    // Helper method to resolve the InitializerEntryPoint from the context
    static InitializerEntryPoint resolve(@NonNull Context context) {
        Log.d(TAG, "resolve");
        Context appContext = context.getApplicationContext();
        if (appContext == null) {
            throw new IllegalStateException();
        }
        return EntryPointAccessors.fromApplication(
                appContext,
                InitializerEntryPoint.class
        );
    }

}
