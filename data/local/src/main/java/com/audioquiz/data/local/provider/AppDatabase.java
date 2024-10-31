package com.audioquiz.data.local.provider;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.audioquiz.data.local.converter.DateTypeConverter;
import com.audioquiz.data.local.converter.FrequencyStatsConverter;
import com.audioquiz.data.local.converter.QuizResultTypeConverters;
import com.audioquiz.data.local.dao.user_data.CategoryStatsDao;
import com.audioquiz.data.local.dao.user_data.FrequencyStatsDao;
import com.audioquiz.data.local.dao.user_data.GeneralStatsDao;
import com.audioquiz.data.local.dao.quiz.QuestionDao;
import com.audioquiz.data.local.dao.quiz.QuizResultDao;
import com.audioquiz.data.local.dao.rank.RankAllTimeDao;
import com.audioquiz.data.local.dao.rank.RankWeeklyDao;
import com.audioquiz.data.local.dao.auth.UserAuthDao;
import com.audioquiz.data.local.dao.user_data.IntervalStatsDao;
import com.audioquiz.data.local.dao.user_data.UserProfileDao;
import com.audioquiz.data.local.entity.QuestionEntity;
import com.audioquiz.data.local.entity.QuizResultEntity;
import com.audioquiz.data.local.entity.rank_stats.RankAllTimeEntity;
import com.audioquiz.data.local.entity.rank_stats.RankWeeklyEntity;
import com.audioquiz.data.local.entity.user_data.CategoryStatsEntity;
import com.audioquiz.data.local.entity.user_data.DailyScoresEntity;
import com.audioquiz.data.local.entity.user_data.GeneralStatsEntity;
import com.audioquiz.data.local.entity.user_data.UserProfileEntity;
import com.audioquiz.data.local.entity.user_data.frequency_stats.PitchStatsEntity;
import com.audioquiz.data.local.entity.user_data.frequency_stats.FrequencyStatsEntity;
import com.audioquiz.data.local.entity.user_data.frequency_stats.IntervalStatsEntity;

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
