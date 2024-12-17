package com.audioquiz.api.datasources.firebase_auth;


import com.audioquiz.core.model.util.Result;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface AuthApi {
    String getFirebaseUserId();
    Single<Boolean> isAuthorized();

    Observable<Boolean> registerUser(String email, String password, String username);
    Single<Result<?>> signInWithEmailAndPassword(String email, String password);
    Single<Result<?>> signInWithGoogle(String idToken);
    Completable logout();

    Completable sendVerificationEmail();
    Task<Void> changePassword(String currentPassword, String newPassword);
    Completable sendPasswordResetEmail(String email);

    void deleteUserAccount(String password);
}
