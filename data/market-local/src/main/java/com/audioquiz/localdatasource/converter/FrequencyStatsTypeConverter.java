package com.audioquiz.localdatasource.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class FrequencyStatsTypeConverter {
    @TypeConverter
    public static Map<String, Integer> fromString(String value) {
        Type listType = new TypeToken<Map<String, Integer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromMap(Map<String, Integer> map) {
        return new Gson().toJson(map);
    }
}
