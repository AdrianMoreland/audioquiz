package com.audioquiz.core.domain.usecase.auth;

import com.audioquiz.core.domain.repository.auth.AuthRepository;
import com.audioquiz.core.model.auth.LoginType;
import com.audioquiz.core.model.auth.util.Result;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class LogoutUserUseCaseImpl {
    private final AuthRepository authRepository;

    @Inject
    public LogoutUserUseCaseImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Completable execute () {
        return authRepository.logoutUser();
    }
}
