package com.audioquiz.library.util;


import java.util.Map;
import java.util.Optional;

public class DataUtils {
    private static final String TAG = "DataUtils";

    private DataUtils() {
        // Private constructor to prevent instantiation
    }

    public static String getStringValue(Number value) {
        return String.valueOf(value != null ? value : "N/A");
    }


    /**
     * Safely get a value from a Map, handling null cases and providing a default value.
     */
    public static <T> T getOrDefault(Map<String, Object> map, String key, Class<T> clazz, T defaultValue) {
        return Optional.ofNullable(clazz.cast(map.get(key)))
                .orElse(defaultValue);
    }

    public static String getString(Map<?, ?> map, String fieldName) {
        Object value = map.get(fieldName);
        if (value instanceof String) {
            return (String) value;
        } else {
            throw new IllegalArgumentException("Value is not a String for key: " + fieldName);
        }
    }


    public static <T> Optional<T> getValueFromMapOrElse(Map<String, Object> map, String key, Class<T> clazz) {
        return Optional.ofNullable(clazz.cast(map.get(key)));
    }


/*    public static double extractDoubleFromObject(Object parent, Object value) {
        if (parent instanceof GeneralStats) {
            if ("averageScore".equals(value)) {
                return ((GeneralStats) parent).getAverageScore();
            } else {
                return 0.0;
            }
        }
        return 0.0; // Handle if parent or fieldName is incorrect
    }

    public static Integer extractIntegerFromObject(Object parent, String fieldName) {
        if (parent instanceof GeneralStats) {
            if ("userLevel".equals(fieldName)) {
                return ((GeneralStats) parent).getUserLevel();
            } else if ("numberOfLives".equals(fieldName)) {
                return ((GeneralStats) parent).getNumberOfLives();
            } else if ("totalScore".equals(fieldName)) {
                return ((GeneralStats) parent).getTotalScore();
            } else if ("numberOfQuizzes".equals(fieldName)) {
                return ((GeneralStats) parent).getNumberOfQuizzes();
            } else if ("numberOfQuestions".equals(fieldName)) {
                return ((GeneralStats) parent).getNumberOfQuestions();
            } else if ("longestStreak".equals(fieldName)) {
                return ((GeneralStats) parent).getLongestStreak();
            } else if ("currentStreak".equals(fieldName)) {
                return ((GeneralStats) parent).getCurrentStreak();
            }
        }
        return null; // Handle if parent or fieldName is incorrect
    }

    public static Date extractTimestampFromObject(Object parent, String fieldName) {
        if (parent instanceof GeneralStats) {
            Date value = ((GeneralStats) parent).getLastQuizDate();
            if (value != null) {
                return value;
            }
        }
        // Default to current timestamp if field extraction fails or types don't match
        return new Date();
    }



       public static CategoryStats setupCategoryStats(UserStatistics userStatistics) {
        userStatistics.getCategoryStats().getCategoryStatsData("SoundWaves").setCategoryIndex(0);
        userStatistics.getCategoryStats().getCategoryStatsData("SoundWaves").setCategoryName("SoundWaves");
        userStatistics.getCategoryStats().getCategoryStatsData("Synthesis").setCategoryIndex(1);
        userStatistics.getCategoryStats().getCategoryStatsData("Synthesis").setCategoryName("Synthesis");
        userStatistics.getCategoryStats().getCategoryStatsData("Production").setCategoryIndex(2);
        userStatistics.getCategoryStats().getCategoryStatsData("Production").setCategoryName("Production");
        userStatistics.getCategoryStats().getCategoryStatsData("Mixing").setCategoryIndex(3);
        userStatistics.getCategoryStats().getCategoryStatsData("Mixing").setCategoryName("Mixing");
        userStatistics.getCategoryStats().getCategoryStatsData("Processing").setCategoryIndex(4);
        userStatistics.getCategoryStats().getCategoryStatsData("Processing").setCategoryName("Processing");
        userStatistics.getCategoryStats().getCategoryStatsData("MusicTheory").setCategoryIndex(5);
        userStatistics.getCategoryStats().getCategoryStatsData("MusicTheory").setCategoryName("MusicTheory");
        userStatistics.getCategoryStats().getCategoryStatsData("Pitch").setCategoryIndex(6);
        userStatistics.getCategoryStats().getCategoryStatsData("Pitch").setCategoryName("Pitch");
        userStatistics.getCategoryStats().getCategoryStatsData("Interval").setCategoryIndex(7);
        userStatistics.getCategoryStats().getCategoryStatsData("Interval").setCategoryName("Interval");
        return userStatistics.getCategoryStats();
    }


   public static <T> Optional<T> getValueFromMapOrElse(Map<String, Object> map, String key, Class<T> clazz) {
        Object value = map.get(key);
        if (value != null && clazz.isInstance(value)) {
            return Optional.ofNullable(clazz.cast(value));
        } else {
            return Optional.empty();
        }
    }*/

}
