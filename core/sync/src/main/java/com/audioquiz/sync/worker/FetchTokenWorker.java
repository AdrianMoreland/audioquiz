package com.audioquiz.sync.worker;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.audioquiz.api.datasources.google.GoogleApi;

import javax.inject.Inject;

public class FetchTokenWorker extends Worker {

    private final GoogleApi googleApi;
    private final SharedPreferences sharedPreferences;


    @Inject
    public FetchTokenWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams,
            GoogleApi googleApi,
            SharedPreferences sharedPreferences) {
        super(context, workerParams);
        this.googleApi = googleApi;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            // Use getApplicationContext() to get the application context
//            String token = googleApi.fetchTokenString();
//            saveTokenToSharedPreferences(token);

            return Result.success();
        } catch (Exception e) {
            // Handle errors
            return Result.failure();
        }
    }

    private String generateNonce() {
        // Implement your nonce generation logic here
        // For example, you can use a random string generator or a secure random number generator
        return "your_nonce_string";
    }

    private void saveTokenToSharedPreferences(String idToken) {
        sharedPreferences.edit().putString("token", idToken).apply();
    }
}
