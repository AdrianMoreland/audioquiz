package com.audioquiz.data.local.cache.user_data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audioquiz.api.datasources.user.UserProfileDataSource;
import com.audioquiz.core.model.user.UserProfile;
import com.audioquiz.data.local.dao.user_data.UserProfileDao;
import com.audioquiz.data.local.entity.user_data.UserProfileEntity;
import com.audioquiz.data.local.mapper.DatabaseMapper;
import com.audioquiz.data.local.provider.AppDatabase;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class UserProfileCache implements UserProfileDataSource.Local {
    private static final String TAG = "UserProfileCache";
    private final UserProfileDao userDao;

    @Inject
    public UserProfileCache(AppDatabase appDatabase) {
        this.userDao = appDatabase.userProfileDao();
    }

    @Override
    public void storeUserProfileInfo(@NonNull UserProfile userProfile) {
        userDao.insert(mapFromDomain(userProfile));
    }

    @Override
    public Completable insert(UserProfile userProfile) {
        if (userProfile == null) {
            userProfile = UserProfile.createDefault("67889", "12345");
        }
        return userDao.insertCompletable(mapFromDomain(userProfile))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private UserProfileEntity mapFromDomain(UserProfile domain) {
        return DatabaseMapper.map(domain, UserProfileEntity.class);
    }

    @Nullable
    @Override
    public UserProfile getUserProfile() {
        UserProfileEntity userProfileEntity = userDao.getUserProfile();
        return mapToDomain(userProfileEntity);
    }

    @Override
    public Single<UserProfile> getUserProfileSingle() {
        Log.d(TAG, "getUserProfileSingle called");
        return userDao.getUserProfileSingle()
                .subscribeOn(Schedulers.io()) // Perform database access on IO thread
                .map(this::mapToDomain) // Use map with separate transformation function
                .observeOn(AndroidSchedulers.mainThread()); // Observe on main thread (if transformation is light)
    }


    private UserProfile mapToDomain (UserProfileEntity entity) {
        Log.d(TAG, "mapToDomain called");
        return DatabaseMapper.map(entity, UserProfile.class);
    }

    @Override
    public void flushData() {

    }

/*    @Override
    public Flowable<UserProfile> getUserProfileFlowable() {
        return userProfileEntityMapper.mapFromSource(userDao.getUserProfileFlowable());
    }*/
}