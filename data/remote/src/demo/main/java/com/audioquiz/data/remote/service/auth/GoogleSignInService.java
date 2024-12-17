package com.audioquiz.data.remote.service.auth;

import android.util.Log;

import com.audioquiz.core.model.auth.LoggedInUser;
import com.audioquiz.core.model.auth.util.Result;
import com.audioquiz.data.remote.datasource.AuthDataSource;
import com.audioquiz.data.remote.datasource.auth.GoogleSignInDataSource;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GoogleSignInService {
    private static final String TAG = "GoogleSignInService";
    private final AuthDataSource authDataSource;
    private final GoogleSignInDataSource googleSignInDataSource;

    @Inject
    @Named("AuthExecutor")
    ExecutorService executorService;

    @Inject
    public GoogleSignInService(AuthDataSource authDataSource,
                               GoogleSignInDataSource googleSignInDataSource) {
        this.authDataSource = authDataSource;
        this.googleSignInDataSource = googleSignInDataSource;
    }

     public Observable<Result<?>> loginWithGoogle(String idToken) {
        Log.d(TAG, "loginWithGoogle called: ");
        return Observable.fromCallable(() -> {
                    Task<AuthResult> task = googleSignInDataSource.signInWithGoogle(idToken);
                    try {
                        Tasks.await(task); // Block until the task is complete
                        if (task.isSuccessful()) {
                            LoggedInUser loggedInUser = authDataSource.getLoggedInUser();
                            Log.d(TAG, "loginWithGoogle successful: " + loggedInUser.getDisplayName());
                            return (Result<?>)  new Result.Success<>(loggedInUser);
                        } else {
                            Log.d(TAG, "loginWithGoogle failed: ");
                            Exception exception = task.getException();
                            return (Result<?>)  new Result.Error(exception != null ? exception : new Exception("Google sign-in failed"));
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "loginWithGoogle failed: " + e.getMessage());
                        return  (Result<?>) new Result.Error(e);
                    }
                })
                .subscribeOn(Schedulers.from(executorService))
                .observeOn(AndroidSchedulers.mainThread());
    }

     public void logout() {

    }
}
