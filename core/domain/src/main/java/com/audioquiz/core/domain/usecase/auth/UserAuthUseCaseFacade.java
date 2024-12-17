package com.audioquiz.core.domain.usecase.auth;


import com.audioquiz.core.model.auth.LoginType;
import com.audioquiz.core.model.util.Result;
import com.audioquiz.core.model.user.UserProfile;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


public interface UserAuthUseCaseFacade {
    Single<Result<?>> login(String username, String password, LoginType loginType);
    Observable<Boolean> registerUser(String email, String password, String username);
    void sendVerificationEmail();
    Completable sendPasswordResetEmail(String email);
    Observable<UserProfile> getUserProfile();
    Completable logout();

//    Observable<Result> loginWithEmail(String username, String password);
//    Observable<Result> loginWithGoogle(String idToken);
}
