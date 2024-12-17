package com.audioquiz.sync.initializer;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.audioquiz.api.datasources.firebase.FirebaseApi;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class FirebaseInitializer implements Initializer<Void> {
    private static final String TAG = "FirebaseInitializer";

    @Inject
    FirebaseApi firebaseApi;

    @NonNull
    @Override
    public Void create(@NonNull Context context) {
        Log.d(TAG, "create FirebaseInitializer");
        firebaseApi.initializeFirebase((Application) context);
        Log.d(TAG, "FirebaseInitializer completed");
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}