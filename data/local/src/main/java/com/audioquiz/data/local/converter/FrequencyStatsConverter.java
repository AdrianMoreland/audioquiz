package com.audioquiz.data.local.converter;
import androidx.room.TypeConverter;

import com.audioquiz.data.local.entity.user_data.frequency_stats.PitchStatsEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

public class FrequencyStatsConverter {
    @TypeConverter
    public static String fromFrequencyStatsMap(Map<String, PitchStatsEntity> map) {
        if (map == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, PitchStatsEntity>>() {}.getType();
        return gson.toJson(map, type);
    }

    @TypeConverter
    public static Map<String, PitchStatsEntity> toFrequencyStatsMap(String value) {
        if (value == null) {
            return null;
        }Gson gson = new Gson();
        Type type = new TypeToken<Map<String, PitchStatsEntity>>() {}.getType();
        return gson.fromJson(value, type);
    }
}