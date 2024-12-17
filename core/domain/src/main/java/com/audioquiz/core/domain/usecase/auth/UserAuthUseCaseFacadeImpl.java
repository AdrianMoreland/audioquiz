package com.audioquiz.core.domain.usecase.auth;


import com.audioquiz.core.model.auth.LoginType;
import com.audioquiz.core.model.util.Result;
import com.audioquiz.core.model.user.UserProfile;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class UserAuthUseCaseFacadeImpl implements UserAuthUseCaseFacade {
    private static final String TAG = "UserAuthUseCaseFacadeImpl";
    private final LoginUserUseCaseImpl loginUserUseCaseImpl;
    private final RegisterUserUseCaseImpl registerUserUseCaseImpl;
    private final SendPasswordResetEmailUseCase_Impl sendPasswordResetEmailUseCase;
    private final LogoutUserUseCaseImpl logoutUserUseCaseImpl;

    @Inject
    public UserAuthUseCaseFacadeImpl(LoginUserUseCaseImpl loginUserUseCaseImpl,
                                     RegisterUserUseCaseImpl registerUserUseCaseImpl,
                                     SendPasswordResetEmailUseCase_Impl sendPasswordResetEmailUseCase,
                                     LogoutUserUseCaseImpl logoutUserUseCaseImpl) {
        this.loginUserUseCaseImpl = loginUserUseCaseImpl;
        this.registerUserUseCaseImpl = registerUserUseCaseImpl;
        this.sendPasswordResetEmailUseCase = sendPasswordResetEmailUseCase;
        this.logoutUserUseCaseImpl = logoutUserUseCaseImpl;
    }


    @Override
    public Observable<Boolean> registerUser(String email, String password, String username) {
        return registerUserUseCaseImpl.execute(email, password, username);
    }

    public Single<Result<?>> login(String username, String password, LoginType loginType) {
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
    public Completable logout() {
       return logoutUserUseCaseImpl.execute();
    }

    @Override
    public void sendVerificationEmail() {
        sendPasswordResetEmailUseCase.sendVerificationEmail();
    }


}
