package com.adrian.usecase.user.usecase_impl.profile;


import com.adrian.domain.user.UserProfileRepository;
import com.adrian.model.login.UserProfile;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetUserProfileUseCaseImpl {
    private final UserProfileRepository userProfileRepository;


    @Inject
    public GetUserProfileUseCaseImpl(UserProfileRepository userProfileRepository){
        this.userProfileRepository = userProfileRepository;
    }

    public Single<UserProfile> getUserProfileSingle() {
        return userProfileRepository.getUserProfileSingle();
    }

    public String getProfileImageUrl() {
        return getUserProfileSingle()
                .blockingGet()
                .getProfileImage();
    }

    public void uploadProfileImage(String imageUri, UserProfileRepository.ImageUploadCallback imageUploadCallback) {
        userProfileRepository.uploadProfileImage(imageUri, imageUploadCallback);
    }

    public String getUsernameLetter() {
        return userProfileRepository.getUsernameLetter();
    }
}
