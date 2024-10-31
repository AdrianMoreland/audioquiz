package com.audioquiz.data.local.converter;

import androidx.room.TypeConverter;

import com.audioquiz.data.local.entity.user_data.UserProfileEntity;
import com.google.gson.Gson;

public class UserProfileTypeConverter {
    @TypeConverter
    public static String fromUserProfileEntity(UserProfileEntity userProfileEntity) {// Convert UserProfileEntity to String (e.g., using Gson)
        return new Gson().toJson(userProfileEntity);
    }
    @TypeConverter
    public static UserProfileEntity toUserProfileEntity(String userProfileString) {
        // Convert String back to UserProfileEntity
        return new Gson().fromJson(userProfileString, UserProfileEntity.class);
    }
}
