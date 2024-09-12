package com.audioquiz.di.singleton.database;

import com.adrian.api.data.datasources.question.QuestionLocal;
import com.adrian.api.data.datasources.quiz_result.QuizLocal;
import com.adrian.api.data.datasources.rank.RankAllTimeLocal;
import com.adrian.api.data.datasources.rank_weekly.RankWeeklyLocal;
import com.adrian.api.data.datasources.user.UserProfileLocal;
import com.adrian.api.data.datasources.user_stats.stats.UserDataLocal;
import com.adrian.api.data.datasources.user_stats.stats_category.CategoryStatsLocal;
import com.adrian.api.data.datasources.user_stats.stats_frequency.StatsFrequencyLocal;
import com.adrian.api.data.datasources.user_stats.stats_general.GeneralStatsLocal;
import com.adrian.api.data.datasources.user_stats.stats_weekly_scores.WeeklyStatsLocal;
import com.adrian.database.cache.user_data.CategoryStatsCache;
import com.adrian.database.cache.user_data.FrequencyStatsCache;
import com.adrian.database.cache.user_data.GeneralStatsCache;
import com.adrian.database.cache.quiz.QuestionsCache;
import com.adrian.database.cache.quiz.QuizResultCache;
import com.adrian.database.cache.rank.RankAllTimeCache;
import com.adrian.database.cache.rank.RankWeeklyCache;
import com.adrian.database.cache.user_data.UserProfileCache;
import com.adrian.database.cache.user_data.UserDataCache;
import com.adrian.database.cache.user_data.WeeklyStatsCache;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class CacheModule {

    /**********************************
     ****** USER DATA ******
     **********************************/
    @Binds
    abstract UserDataLocal bindUserStatsDb(UserDataCache userStatsCache);

    @Binds
    abstract UserProfileLocal bindUserProfileDb(UserProfileCache userProfileCache);

    @Binds
    abstract GeneralStatsLocal bindGeneralStatsDb(GeneralStatsCache generalStatsCache);

    @Binds
    abstract CategoryStatsLocal bindCategoryStatsDb(CategoryStatsCache categoryStatsCache);

    @Binds
    abstract StatsFrequencyLocal bindStatsFrequencyDb(FrequencyStatsCache frequencyStatsCache);

    @Binds
    abstract WeeklyStatsLocal bindWeeklyStatsDb(WeeklyStatsCache weeklyStatsCache);

    /**********************************
     ****** QUESTIONS ******
     **********************************/
    @Binds
    abstract QuestionLocal bindQuestionDb(QuestionsCache questionsCache);

    /**********************************
     ****** QUIZ RESULT ******
     **********************************/
    @Binds
    abstract QuizLocal bindQuizDb(QuizResultCache quizResultCache);

    /**********************************
     ****** RANKS ******
     **********************************/
    @Binds
    abstract RankAllTimeLocal bindRankAllTimeDb(RankAllTimeCache rankAllTimeCache);

    @Binds
    abstract RankWeeklyLocal bindRankWeeklyDb(RankWeeklyCache rankWeeklyCache);

}