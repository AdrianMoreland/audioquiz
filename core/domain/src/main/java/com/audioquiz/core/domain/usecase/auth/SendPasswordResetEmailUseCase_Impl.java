package com.audioquiz.core.domain.usecase.auth;

import com.audioquiz.core.domain.repository.auth.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class SendPasswordResetEmailUseCase_Impl {
    private final AuthRepository authRepository;

    @Inject
    public SendPasswordResetEmailUseCase_Impl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Completable execute(String email) {
       return authRepository.sendPasswordResetEmail(email);
    }

    public void sendVerificationEmail() {
        authRepository.sendVerificationEmail();
    }
}
