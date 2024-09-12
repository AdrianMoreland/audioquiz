package com.adrian.domain.user;

import com.adrian.model.login.UserProfile;

import io.reactivex.rxjava3.core.Single;


public interface UserProfileRepository {
    void init();

    Single<UserProfile> getUserProfileSingle();

    void uploadProfileImage(String imageUri, ImageUploadCallback listener);


    void updateUsername(String username, UsernameCompletionCallback completionCallback);

    String getUsernameLetter();

    interface CheckDocCompletionCallback {
        void onSuccess(boolean exists);
        void onError(Exception e);
    }
    interface UserProfileCallback {
        void onUserProfileChanged (UserProfile documentSnapshot);
        void onUserProfileEmpty();
        void onError(Exception e);
    }
    interface ImageUploadCallback {
        void onSuccess(String imageUrl);
        void onError(Exception e);
    }
    interface UsernameCompletionCallback {
        void onSuccess();
        void onError(Exception e);
    }
}
