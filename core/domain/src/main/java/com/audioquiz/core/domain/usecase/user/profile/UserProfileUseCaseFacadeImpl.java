package com.audioquiz.core.domain.usecase.user.profile;

import com.audioquiz.core.model.user.UserProfile;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class UserProfileUseCaseFacadeImpl implements UserProfileUseCaseFacade {
    private final GetUserProfileUseCaseImpl getUserProfileUseCaseImpl;
    private final PreloadUserProfileUseCaseImpl preloadUserProfileUseCaseImpl;
    private final UploadProfileImageUseCase uploadProfileImageUseCase;

    @Inject
    public UserProfileUseCaseFacadeImpl(GetUserProfileUseCaseImpl getUserProfileUseCaseImpl,
                                        PreloadUserProfileUseCaseImpl preloadUserProfileUseCaseImpl,
                                        UploadProfileImageUseCase uploadProfileImageUseCase) {
        this.getUserProfileUseCaseImpl = getUserProfileUseCaseImpl;
        this.preloadUserProfileUseCaseImpl = preloadUserProfileUseCaseImpl;
        this.uploadProfileImageUseCase = uploadProfileImageUseCase;
     }

    @Override
    public Single<UserProfile> getUserProfileSingle() {
        return getUserProfileUseCaseImpl.getUserProfileSingle();
    }

    @Override
    public void preloadUserProfile() {
        preloadUserProfileUseCaseImpl.execute();
    }



    @Override
    public String getUsernameLetter() {
        return getUserProfileUseCaseImpl.getUsernameLetter();
    }

    @Override
    public String getProfileImageUrl() {
        return getUserProfileUseCaseImpl.getProfileImageUrl();
    }
}
