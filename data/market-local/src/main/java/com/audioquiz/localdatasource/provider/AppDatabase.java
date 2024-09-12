package com.audioquiz.localdatasource.provider;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.adrian.database.converter.DateTypeConverter;
import com.adrian.database.converter.FrequencyStatsConverter;
import com.adrian.database.converter.QuizResultTypeConverters;
import com.adrian.database.dao.user_data.CategoryStatsDao;
import com.adrian.database.dao.user_data.FrequencyStatsDao;
import com.adrian.database.dao.user_data.GeneralStatsDao;
import com.adrian.database.dao.quiz.QuestionDao;
import com.adrian.database.dao.quiz.QuizResultDao;
import com.adrian.database.dao.rank.RankAllTimeDao;
import com.adrian.database.dao.rank.RankWeeklyDao;
import com.adrian.database.dao.auth.UserAuthDao;
import com.adrian.database.dao.user_data.IntervalStatsDao;
import com.adrian.database.dao.user_data.UserProfileDao;
import com.adrian.database.entity.QuestionEntity;
import com.adrian.database.entity.QuizResultEntity;
import com.adrian.database.entity.rank_stats.RankAllTimeEntity;
import com.adrian.database.entity.rank_stats.RankWeeklyEntity;
import com.adrian.database.entity.user_data.CategoryStatsEntity;
import com.adrian.database.entity.user_data.DailyScoresEntity;
import com.adrian.database.entity.user_data.GeneralStatsEntity;
import com.adrian.database.entity.user_data.UserProfileEntity;
import com.adrian.database.entity.user_data.frequency_stats.PitchStatsEntity;
import com.adrian.database.entity.user_data.frequency_stats.FrequencyStatsEntity;
import com.adrian.database.entity.user_data.frequency_stats.IntervalStatsEntity;

import javax.inject.Singleton;

@Database(entities = {
        UserProfileEntity.class,
        GeneralStatsEntity.class,
        QuizResultEntity.class,
        QuestionEntity.class,
        IntervalStatsEntity.class,
        FrequencyStatsEntity.class,
        CategoryStatsEntity.class,
        DailyScoresEntity.class,
        PitchStatsEntity.class,
        RankAllTimeEntity.class,
        RankWeeklyEntity.class},
        version = 2, exportSchema = false)
@TypeConverters({QuizResultTypeConverters.class,
        FrequencyStatsConverter.class,
        DateTypeConverter.class})
@Singleton
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserAuthDao userAuthDao();

    public abstract QuestionDao questionDao();

    public abstract QuizResultDao quizResultDao();

    public abstract UserProfileDao userProfileDao();

    public abstract GeneralStatsDao generalStatsDao();

    public abstract CategoryStatsDao categoryStatsDao();

    public abstract FrequencyStatsDao frequencyStatsDao();

    public abstract IntervalStatsDao intervalStatsDao();

    public abstract RankAllTimeDao rankAllTimeDao();

    public abstract RankWeeklyDao rankWeeklyDao();

}
