package com.adrian.usecase.user.usecase_impl;

import com.adrian.model.login.LoginType;
import com.adrian.model.login.UserProfile;
import com.adrian.model.login.util.Result;
import com.adrian.usecase.auth.UserAuthUseCaseFacade;
import com.adrian.usecase.auth.usecase_impl.LoginUserUseCaseImpl;
import com.adrian.usecase.auth.usecase_impl.SendPasswordResetEmailUseCase_Impl;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class UserAuthUseCaseFacadeImpl implements UserAuthUseCaseFacade {
    private static final String TAG = "UserAuthUseCaseFacadeImpl";
    private final LoginUserUseCaseImpl loginUserUseCaseImpl;
    private final SendPasswordResetEmailUseCase_Impl sendPasswordResetEmailUseCase;

    @Inject
    public UserAuthUseCaseFacadeImpl(LoginUserUseCaseImpl loginUserUseCaseImpl,
                                     SendPasswordResetEmailUseCase_Impl sendPasswordResetEmailUseCase) {
        this.loginUserUseCaseImpl = loginUserUseCaseImpl;
        this.sendPasswordResetEmailUseCase = sendPasswordResetEmailUseCase;
    }


    @Override
    public Boolean registerUser(String email, String password, String username) {
        return null;
    }

    public Observable<Result<?>> login(String username, String password, LoginType loginType) {
        return loginUserUseCaseImpl.execute(username, password, loginType);
    }

    @Override
    public Completable sendPasswordResetEmail(String email) {
        return sendPasswordResetEmailUseCase.execute(email);
    }

    @Override
    public Observable<UserProfile> getUserProfile() {
            return null;
    }

    @Override
    public void sendVerificationEmail() {
        sendPasswordResetEmailUseCase.sendVerificationEmail();
    }


    /*    @Override
    public Observable<Result> loginWithEmail(String username, String password) {
        return loginUserUseCaseImpl.execute(username, password);
    }

     @Override
    public Observable<Result> loginWithGoogle(String idToken) {
        return loginUserUseCaseImpl.loginWithGoogle(idToken);
    }

    @Override
    public Disposable getGoogleIdToken(AuthRepository.GoogleSignInResultCallback callback) {
        return loginUserUseCaseImpl.getGoogleIdToken(callback);
    }

    */

}
