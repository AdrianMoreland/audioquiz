package com.audioquiz.localdatasource.converter;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

public class QuizResultTypeConverters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static Map<String, Integer> fromStringToIntMap(String value) {
        Type mapType = new TypeToken<Map<String, Integer>>() {}.getType();
        return gson.fromJson(value, mapType);
    }

    @TypeConverter
    public static String fromIntMapToString(Map<String, Integer> map) {
        return gson.toJson(map);
    }

    @TypeConverter
    public static Map<String, Object> fromStringToObjectMap(String value) {
        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(value, mapType);
    }

    @TypeConverter
    public static String fromObjectMapToString(Map<String, Object> map) {
        return gson.toJson(map);
    }
}