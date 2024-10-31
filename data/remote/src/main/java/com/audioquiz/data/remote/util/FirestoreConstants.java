package com.audioquiz.data.remote.util;

public class FirestoreConstants {

    public static final String USER_DATA_COLLECTION = "user_data";
    public static final String RANK_COLLECTION = "rank_data";
    public static final String USER_PROFILE_DOCUMENT = "user_profile";
    public static final String GENERAL_STATS_COLLECTION = "general_stats";
    public static final String CATEGORY_STATS_DOCUMENT = "category_stats";
    public static final String FREQUENCY_STATS_COLLECTION = "frequency_stats";
    public static final String WEEKLY_SCORES_COLLECTION = "weekly_scores";


    // UserStatistics fields
    public static final String FIELD_LAST_UPDATED = "lastUpdated";
    public static final String FIELD_GENERAL_STATS = "generalStatsDto";
    public static final String FIELD_CATEGORY_STATS = "categoryStatsDto";
    public static final String FIELD_FREQUENCY_STATS = "frequencyStatsDto";
    public static final String FIELD_LAST_7_DAYS_SCORES = "last7DaysScoresDto";

    // GeneralStatsDto fields
    public static final String FIELD_USER_LEVEL = "userLevel";
    public static final String FIELD_NUMBER_OF_LIVES = "numberOfLives";
    public static final String FIELD_NUMBER_OF_QUIZZES = "numberOfQuizzes";
    public static final String FIELD_NUMBER_OF_QUESTIONS = "numberOfQuestions";
    public static final String FIELD_TOTAL_SCORE = "totalScore";
    public static final String FIELD_AVERAGE_SCORE = "averageScore";
    public static final String FIELD_CURRENT_STREAK = "currentStreak";
    public static final String FIELD_LONGEST_STREAK = "longestStreak";
    public static final String FIELD_LAST_QUIZ_DATE = "lastQuizDate";

    // CategoryStatsDto fields
    public static final String FIELD_SOUND_WAVES_STATS = "soundWavesStats";
    public static final String FIELD_SYNTHESIS_STATS = "synthesisStats";
    public static final String FIELD_PRODUCTION_STATS = "productionStats";
    public static final String FIELD_MIXING_STATS = "mixingStats";
    public static final String FIELD_PROCESSING_STATS = "processingStats";
    public static final String FIELD_MUSIC_THEORY_STATS = "musicTheoryStats";
    public static final String FIELD_PITCH_STATS = "pitchStats";
    public static final String FIELD_INTERVAL_STATS = "intervalStats";

    // FrequencyStatsDto fields
    public static final String FIELD_PITCH_SCORES_MAP = "pitchScoresMap";
    public static final String FIELD_INTERVAL_SCORES_MAP = "intervalScoresMap";

    // Last7DaysScoresDto fields
    public static final String FIELD_DAILY_SCORES = "dailyScores";
    public static final String FIELD_TOTAL_LAST_7_DAYS = "totalLast7Days";

    private FirestoreConstants() {
        // Private constructor to prevent instantiation
    }
}
