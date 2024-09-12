package com.audioquiz.localdatasource.cache.user_data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adrian.api.data.datasources.user.UserProfileLocal;
import com.adrian.database.mapper.IDatabaseMapper;
import com.adrian.database.dao.user_data.UserProfileDao;
import com.adrian.database.entity.user_data.UserProfileEntity;
import com.adrian.database.provider.AppDatabase;
import com.adrian.model.login.UserProfile;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class UserProfileCache implements UserProfileLocal {
    private static final String TAG = "UserProfileCache";
    private final UserProfileDao userDao;
    private final IDatabaseMapper mapper;


    @Inject
    public UserProfileCache(AppDatabase appDatabase,
                            IDatabaseMapper mapper) {
        this.userDao = appDatabase.userProfileDao();
        this.mapper = mapper;
    }

    @Override
    public void storeUserProfileInfo(@NonNull UserProfile userProfile) {
        userDao.insert(mapFromDomain(userProfile));
    }

    @Override
    public Completable insert(UserProfile userProfile) {
        return userDao.insertCompletable(mapFromDomain(userProfile))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private UserProfileEntity mapFromDomain(UserProfile domain) {
        return mapper.mapFromDomain(domain, UserProfileEntity.class);
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
        return mapper.mapToDomain(entity, UserProfile.class);
    }

    @Override
    public void flushData() {

    }

/*    @Override
    public Flowable<UserProfile> getUserProfileFlowable() {
        return userProfileEntityMapper.mapFromSource(userDao.getUserProfileFlowable());
    }*/
}