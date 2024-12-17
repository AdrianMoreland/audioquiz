package com.audioquiz.sync.initializer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.audioquiz.sync.di.HiltInitializationEntryPoint;
import com.audioquiz.sync.di.InitializerEntryPoint;

import java.util.Collections;
import java.util.List;

import dagger.hilt.android.EntryPointAccessors;

public class DependencyGraphInitializer implements Initializer<Void> {
    private static final String TAG = "DependencyGraphInitializer";

    @NonNull
    @Override
    public Void create(@NonNull Context context) {
        Log.d(TAG, "create: Lazily initialize ApplicationComponent before Application's `onCreate`");
        EntryPointAccessors.fromApplication(context, HiltInitializationEntryPoint.class);
        InitializerEntryPoint.resolve(context); // Initializes dependencies
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}