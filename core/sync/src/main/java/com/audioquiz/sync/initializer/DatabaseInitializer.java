package com.audioquiz.sync.initializer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.startup.Initializer;

import com.audioquiz.data.local.provider.AppDatabase;
import com.audioquiz.sync.di.InitializerEntryPoint;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class DatabaseInitializer implements Initializer<AppDatabase> {
    private static final String TAG = "DatabaseInitializer";

    @NonNull
    @Override
    public AppDatabase create(@NonNull Context context) {
        Log.d(TAG, "create");
        // Resolve InitializerEntryPoint to get AppDatabase
        InitializerEntryPoint entryPoint = InitializerEntryPoint.resolve(context);
        AppDatabase appDatabase = entryPoint.getAppDatabase();

        // Log to confirm initialization (optional)
        Log.d(TAG, "Database initialized: " + appDatabase);

        // Return the initialized AppDatabase instance
        return appDatabase;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        // Ensure Hilt's dependency graph is initialized first
        return List.of(DependencyGraphInitializer.class);
    }
}