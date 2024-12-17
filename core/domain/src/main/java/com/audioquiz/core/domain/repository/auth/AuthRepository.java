package com.audioquiz.core.domain.repository.auth;

import com.audioquiz.core.model.auth.LoginType;
import com.audioquiz.core.model.util.Result;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface AuthRepository {
    Single<Result<?>> login(String username, String password, LoginType loginType);

    Completable sendVerificationEmail();

    Observable<Boolean> registerUser(String email, String password, String username);

    Completable sendPasswordResetEmail(String email);

    void init();

    Single<Boolean> sync();

    Completable logoutUser();

//    Observable<Result> loginWithEmail(String email, String password);
//    Observable<Result> loginWithGoogle(String idToken);
//    String getEmail();
//    boolean isSignedOut();
//    boolean isUserVerified();
//    boolean hasEmail();
//    boolean isEmailVerified();
//    Completable reloadUser();
//    Completable signOut();
//    Completable deleteGoogleUser();
//    Completable deleteEmailUser(String password);
//    Flowable<Boolean> userSignedOutObservable();
}
