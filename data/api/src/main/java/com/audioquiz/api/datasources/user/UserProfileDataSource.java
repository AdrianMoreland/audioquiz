package com.audioquiz.api.datasources.user;

import com.audioquiz.core.model.user.UserProfile;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


public interface UserProfileDataSource {

    interface Remote {
        Observable<UserProfile> observeUserProfile();
        Single<UserProfile> getUserProfile();
        Completable saveUserProfile(UserProfile userProfile);
        Completable deleteUserProfile();
        void createUserProfileDocument(UserProfile userProfile);
        Task<Void> updateUserProfileDocument(String userId, UserProfile userProfile);
        void updateUsernameInFirestore(String newUsername);
    }

    interface Local {
        void storeUserProfileInfo(UserProfile userAccount);
        Completable insert (UserProfile userProfile);
        Single<UserProfile> getUserProfileSingle();
        UserProfile getUserProfile();
        void flushData();
    }
}


