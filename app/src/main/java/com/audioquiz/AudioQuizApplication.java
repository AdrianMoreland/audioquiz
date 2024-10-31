package com.audioquiz;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;


@HiltAndroidApp
public class AudioQuizApplication extends Application {

    @Inject
    public AudioQuizApplication() {
        // Required empty public constructor
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // EntryPointAccessors.fromApplication(this, HiltInitializationEntryPoint.class);
        initTimber();
        Timber.tag("MyApplication").d("onCreate");
    }

    private void initTimber() {
        boolean isDebuggable = (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        if (isDebuggable) {
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, @NonNull String message, Throwable t) {
                    if (priority >= Log.DEBUG) {
                        if (tag == null) { tag = ""; }
                        Log.println(priority, "Timber_"+tag, message);
                    }
                }
            });
        }
    }

    private void cleanUpResources() {
        // Example: Close any open database connections, stop any running services, etc.
        Timber.d("Cleaning up resources...");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // Perform cleanup here
        cleanUpResources();
    }
}