package com.audioquiz.core.domain.usecase.user.profile;

import com.audioquiz.core.domain.repository.user.UserProfileRepository;

import javax.inject.Inject;


public class PreloadUserProfileUseCaseImpl {
    private final UserProfileRepository userProfileRepository;

    @Inject
    public PreloadUserProfileUseCaseImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void execute() {
        userProfileRepository.init();
    }
}
