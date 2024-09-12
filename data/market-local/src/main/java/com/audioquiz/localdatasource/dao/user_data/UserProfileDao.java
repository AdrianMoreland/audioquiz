package com.audioquiz.localdatasource.dao.user_data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.adrian.database.dao.BaseDao;
import com.adrian.database.entity.user_data.UserProfileEntity;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserProfileDao extends BaseDao<UserProfileEntity> {
    @Query("SELECT * FROM user_profile")
    UserProfileEntity getUserProfile();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserProfileEntity userAccount);

    @Query("SELECT * FROM user_profile LIMIT 1")
    Single<UserProfileEntity> getUserProfileSingle();

    @Query("SELECT * FROM user_profile LIMIT 1")
    Flowable<UserProfileEntity> getUserProfileFlowable();
}
