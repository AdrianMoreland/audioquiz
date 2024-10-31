package com.audioquiz.data.local.converter;

import androidx.room.TypeConverter;

import com.audioquiz.data.local.entity.user_data.CategoryStatsEntity;
import com.google.gson.Gson;

public class CategoryStatsTypeConverter {
    @TypeConverter
    public static String fromUserProfileEntity(CategoryStatsEntity categoryStats) {// Convert UserProfileEntity to String (e.g., using Gson)
        return new Gson().toJson(categoryStats);
    }

    @TypeConverter
    public static CategoryStatsEntity toUserProfileEntity(String categoryStatsString) {
        return new Gson().fromJson(categoryStatsString, CategoryStatsEntity.class);
    }
}
