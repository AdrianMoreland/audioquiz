package com.audioquiz.core.domain.usecase.auth;

import com.audioquiz.core.domain.repository.auth.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

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
