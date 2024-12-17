package com.audioquiz.data.remote.service.auth;

import android.util.Log;

import com.audioquiz.api.datasources.firebase_auth.AuthApi;
import com.audioquiz.core.model.util.Result;
import com.audioquiz.data.remote.datasource.AuthDataSource;
import com.audioquiz.data.remote.datasource.google.GoogleAuthService;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Service implementation for Firebase Auth.
 * Implements the service interfaces.
 * Uses the API interfaces to perform low-level operations.
 * Creates Completable instances to handle asynchronous operations.
 */
public class AuthService implements AuthApi {
     private static final String TAG = "AuthService";
    private final AuthDataSource authDataSource;
    private final ExecutorService executorService;


    @Inject
    public AuthService(AuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
        this.executorService = Executors.newSingleThreadExecutor();
    }


    @Override
    public Single<Boolean> isAuthorized() {
        return Single.fromCallable(() -> authDataSource.getCurrentUser() != null);
    }


    @Override
    public String getFirebaseUserId() {
        return authDataSource.getFirebaseUserId();
    }

public Single<Result<?>> signInWithEmailAndPassword(String email, String password) {
    return Single.fromCallable(() -> authDataSource.signInWithEmailAndPassword(email, password))
            .flatMap(task -> Single.<Result<?>>create(emitter -> task
                    .addOnSuccessListener(authResult -> emitter.onSuccess(new Result.Success<>(authDataSource.getLoggedInUser())))
                    .addOnFailureListener(emitter::onError)).onErrorReturn(e -> new Result.Error((Exception) e)));
}

public Single<Result<?>> signInWithGoogle(String idToken) {
    return Single.fromCallable(() -> authDataSource.signInWithCredential(idToken))
            .flatMap(task -> Single.<Result<?>>create(emitter -> task
                    .addOnSuccessListener(authResult -> emitter.onSuccess(new Result.Success<>(authDataSource.getLoggedInUser())))
                    .addOnFailureListener(emitter::onError)).onErrorReturn(e -> new Result.Error((Exception) e)));
}

  /*  @Override
    public Observable<Result<?>> login (String email, String password) {
        return Observable.fromCallable(() -> {
                    Task<AuthResult> task = authDataSource.login(email, password);
                    if (task.isSuccessful()) {
                        LoggedInUser loggedInUser = authDataSource.getLoggedInUser();
                        Log.d(TAG, "login with email successful: " + loggedInUser.getDisplayName());
                        return new Result.Success<>(loggedInUser);
                    } else {
                        Log.d(TAG, "login Task is not successful");
                        return new Result.Error(task.getException()); // Ensure Result is parameterized with LoggedInUser
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Result<?>> loginWithGoogle(String idToken) {
        Log.d(TAG, "loginWithGoogle called: ");
        return Observable.fromCallable(() -> {
                    Task<AuthResult> task = googleAuthService.signInWithGoogle();
                    try {
                        Tasks.await(task); // Block until the task is complete
                        if (task.isSuccessful()) {
                            LoggedInUser loggedInUser = authDataSource.getLoggedInUser();
                            Log.d(TAG, "loginWithGoogle successful: " + loggedInUser.getDisplayName());
                            return new Result.Success<>(loggedInUser);
                        } else {
                            Log.d(TAG, "loginWithGoogle failed: ");
                            Exception exception = task.getException();
                            return new Result.Error(exception != null ? exception : new Exception("Google sign-in failed"));
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "loginWithGoogle failed: " + e.getMessage());
                        return new Result.Error(e);
                    }
                })
                .subscribeOn(Schedulers.from(executorService))
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    @Override
    public Observable<Boolean> registerUser(String email, String password, String username) {
        return Observable.create(emitter -> authDataSource.signUp(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Trigger email verification within the Observable
                        try {
                            authDataSource.sendVerificationEmail();
                            emitter.onNext(true); // Assume success for now
                            emitter.onComplete();
                        } catch (Exception e) {
                            emitter.onError(e); // Handle potential errors from sendVerificationEmail()
                        }
                    } else {
                        authDataSource.handleRegistrationError(task.getException());
                        emitter.onError(task.getException()); // Registration failed
                    }
                }));
    }

    @Override
    public Completable logout() {
        return Completable.fromAction(authDataSource::logOut)
                .subscribeOn(Schedulers.from(executorService));
    }

    @Override
    public Completable sendVerificationEmail() {
        return Completable.create(emitter -> authDataSource.sendVerificationEmail()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onComplete();
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    @Override
    public Completable sendPasswordResetEmail(String email) {
        return Completable.create(emitter -> authDataSource.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onComplete();
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    public Task<Void> changePassword(String currentPassword, String newPassword) {
        return authDataSource.changePassword(currentPassword, newPassword);
    }


    @Override
    public void deleteUserAccount(String password) {
            authDataSource.deleteUserAccount();
    }

}
