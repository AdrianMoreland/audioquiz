package com.audioquiz.data.local.converter;

import androidx.room.TypeConverter;

import com.audioquiz.data.local.entity.user_data.GeneralStatsEntity;
import com.google.gson.Gson;

public class GeneralStatsTypeConverter {
    @TypeConverter
    public static String fromUserProfileEntity(GeneralStatsEntity generalStats) {// Convert UserProfileEntity to String (e.g., using Gson)
        return new Gson().toJson(generalStats);
    }
    @TypeConverter
    public static GeneralStatsEntity toUserProfileEntity(String generalStatsString) {
         return new Gson().fromJson(generalStatsString, GeneralStatsEntity.class);
    }
}
