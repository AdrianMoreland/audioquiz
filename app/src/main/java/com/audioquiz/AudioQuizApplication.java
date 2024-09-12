package com.audioquiz;

import android.app.Application;
import android.util.Log;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class AudioQuizApplication extends Application {

    @Inject
    public AudioQuizApplication() {}

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApplication", "onCreate");
    }

    private void cleanUpResources() {
        // Example: Close any open database connections, stop any running services, etc.
        Log.d("MyApplication", "Cleaning up resources...");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // Perform cleanup here
        cleanUpResources();
    }
}