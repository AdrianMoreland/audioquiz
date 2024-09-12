package com.adrian.usecase.user.usecase_impl.profile;


import com.adrian.domain.user.UserProfileRepository;

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
