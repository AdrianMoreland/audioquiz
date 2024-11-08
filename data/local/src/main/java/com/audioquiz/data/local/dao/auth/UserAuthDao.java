package com.audioquiz.data.local.dao.auth;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.audioquiz.data.local.dao.BaseDao;
import com.audioquiz.data.local.entity.user_data.UserProfileEntity;

@Dao
public interface UserAuthDao extends BaseDao<UserProfileEntity> {

    @Query("SELECT * FROM user_profile" )
    UserProfileEntity getUserAuth();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUserProfile(UserProfileEntity userProfile);

    @Update
    int updatePitchScore(UserProfileEntity userProfile);

    @Delete
    int deletePitchScore(UserProfileEntity userProfile);

}
