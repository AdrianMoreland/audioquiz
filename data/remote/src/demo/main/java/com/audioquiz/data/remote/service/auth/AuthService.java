package com.audioquiz.data.remote.service.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.audioquiz.api.datasources.firebase_auth.AuthApi;
import com.audioquiz.core.model.auth.LoggedInUser;
import com.audioquiz.core.model.auth.util.Result;
import com.audioquiz.data.remote.datasource.AuthDataSource;
import com.audioquiz.data.remote.datasource.auth.GoogleSignInDataSource;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
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
    private final GoogleSignInDataSource googleSignInDataSource;
    private final ExecutorService executorService;


    @Inject
    public AuthService(AuthDataSource authDataSource,
                       GoogleSignInDataSource googleSignInDataSource) {
        this.authDataSource = authDataSource;
        this.googleSignInDataSource = googleSignInDataSource;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public Observable<Boolean> isAuthorized() {
        return Observable.fromCallable(() -> authDataSource.getCurrentUser() != null)
                .subscribeOn(Schedulers.from(executorService));
    }


    @Override
    public String getFirebaseUserId() {
        return authDataSource.getFirebaseUserId();
    }

    @Override
    public Observable<Result<?>> login (String email, String password) {
        return Observable.fromCallable(() -> {
                    Task<AuthResult> task = authDataSource.loginWithEmailAndPassword(email, password);
                    if (task.isSuccessful()) {
                        Log.d(TAG, "login Task is successful");
                        LoggedInUser loggedInUser = authDataSource.getLoggedInUser();
                        return (Result<?>) new Result.Success<>(loggedInUser);
                    } else {
                        Log.d(TAG, "login Task is not successful");
                        return  (Result<?>) new Result.Error(task.getException()); // Ensure Result is parameterized with LoggedInUser
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
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


    @Override
    public Completable logout() {
        return Completable.fromAction(() -> authDataSource.logOut())
                .subscribeOn(Schedulers.from(executorService));
    }


    @Override
    public Completable sendPasswordResetEmail(String email) {
        return Completable.create(emitter -> {
            try {
                authDataSource.sendPasswordResetEmail(email);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Completable sendVerificationEmail() {
        return Completable.create(emitter -> {
            try {
                authDataSource.sendVerificationEmail();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public LiveData<Boolean> registerUser(String email, String password, String username) {
        return null;
    }

    public Task<AuthResult> registerUser(String email, String password) {
        return authDataSource.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (authDataSource.getCurrentUser() != null) {
                            sendVerificationEmail();
                        }
                    } else {
                        authDataSource.handleRegistrationError(task.getException());
                    }
                });
    }

    public Task<Void> changePassword(String currentPassword, String newPassword) {
        return authDataSource.changePassword(currentPassword, newPassword);
    }


    @Override
    public void deleteUserAccount(String password) {
            authDataSource.deleteUserAccount();
    }




    /*
    public Future<Result<LoggedInUser>> loginWithGoogleEx(String idToken) {
        Log.d(TAG, "loginWithGoogle: ");
        return executorService.submit(() -> {
            try {
                // Perform Firebase authentication
                Task<AuthResult> task = googleSignInDataSource.signInWithGoogle(idToken);
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = authDataSource.getCurrentUser();
                    if (firebaseUser != null) {
                        Intent signInIntent = googleSignInDataSource.getLoginWithGoogleIntent(application.getApplicationContext(), webClientId); // Get intent from AuthDataSource
                        googleSignInCallback.onStartGoogleSignIn(signInIntent); // Start activity and pass emitter
                        googleSignInResultListener = data -> googleSignInDataSource.handleGoogleSignInResult(data);
                        LoggedInUser loggedInUser = new LoggedInUser(firebaseUser.getUid(), firebaseUser.getDisplayName());
                        return new Result.Success<>(loggedInUser);
                    } else {
                        return new Result.Error(new Exception("User not found"));
                    }
                } else {
                    return new Result.Error(task.getException());
                }
            } catch (Exception e) {
                return new Result.Error(e);
            }
        });
    }
*/

}
