/*
package com.audioquiz.sync.worker.util.trash;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Configuration;
import androidx.work.WorkManager;

import com.audioquiz.sync.worker.util.HiltWorkerFactoryEntryPoint;

import dagger.hilt.android.EntryPointAccessors;

public class WorkManagerInitProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            Configuration configuration = new Configuration.Builder()
                    .setWorkerFactory(EntryPointAccessors
                            .fromApplication(context, HiltWorkerFactoryEntryPoint.class)
                            .hiltWorkerFactory()).build();
            WorkManager.initialize(context, configuration);
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    // ... other required ContentProvider methods ...
}*/
