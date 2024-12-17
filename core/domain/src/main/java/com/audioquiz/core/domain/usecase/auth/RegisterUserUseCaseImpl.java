package com.audioquiz.core.domain.usecase.auth;

import com.audioquiz.core.domain.repository.auth.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class RegisterUserUseCaseImpl {
    private final AuthRepository authRepository;

    @Inject
    public RegisterUserUseCaseImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Observable<Boolean> execute (String email, String password, String username) {
        return authRepository.registerUser(email, password, username);
    }

}
