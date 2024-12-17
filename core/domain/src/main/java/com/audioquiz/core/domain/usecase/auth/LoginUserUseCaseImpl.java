package com.audioquiz.core.domain.usecase.auth;


import com.audioquiz.core.domain.repository.auth.AuthRepository;
import com.audioquiz.core.model.auth.LoginType;
import com.audioquiz.core.model.util.Result;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class LoginUserUseCaseImpl {
    private final AuthRepository authRepository;

    @Inject
    public LoginUserUseCaseImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Single<Result<?>> execute (String username, String password, LoginType loginType) {
        return authRepository.login(username, password, loginType);
    }


/*
    public Observable<Result> execute2(String username, String password) {
        return authRepository.loginWithEmail(username, password);
    }

    public Observable<Result> loginWithGoogle(String idToken) {
        return authRepository.loginWithGoogle(idToken);
    }



    public Disposable getGoogleIdToken(AuthRepository.GoogleSignInResultCallback callback) {
        return authRepository.getGoogleIdToken(callback);
    }

 */
}
