package com.audioquiz.data.local.di;


import com.audioquiz.api.datasources.question.QuestionDataSource;
import com.audioquiz.api.datasources.quiz_result.QuizLocal;
import com.audioquiz.api.datasources.rank.RankAllTimeDataSource;
import com.audioquiz.api.datasources.rank.RankWeeklyDataSource;
import com.audioquiz.api.datasources.user.UserProfileDataSource;
import com.audioquiz.api.datasources.user_stats.CategoryStatsDataSource;
import com.audioquiz.api.datasources.user_stats.DailyScoresDataSource;
import com.audioquiz.api.datasources.user_stats.FrequencyStatsDataSource;
import com.audioquiz.api.datasources.user_stats.GeneralStatsDataSource;
import com.audioquiz.api.datasources.user_stats.stats.UserDataLocal;
import com.audioquiz.data.local.cache.quiz.QuestionsCache;
import com.audioquiz.data.local.cache.quiz.QuizResultCache;
import com.audioquiz.data.local.cache.rank.RankAllTimeCache;
import com.audioquiz.data.local.cache.rank.RankWeeklyCache;
import com.audioquiz.data.local.cache.user_data.CategoryStatsCache;
import com.audioquiz.data.local.cache.user_data.FrequencyStatsCache;
import com.audioquiz.data.local.cache.user_data.GeneralStatsCache;
import com.audioquiz.data.local.cache.user_data.UserDataCache;
import com.audioquiz.data.local.cache.user_data.UserProfileCache;
import com.audioquiz.data.local.cache.user_data.WeeklyStatsCache;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class CacheModule {


    /*********************************
     ****** USER DATA ******
     ********************************/


    @Binds
    abstract DailyScoresDataSource.Local bindDailyScoresDb(WeeklyStatsCache weeklyStatsCache);

    @Binds
    abstract GeneralStatsDataSource.Local bindGeneralStatsDb(GeneralStatsCache generalStatsCache);

    @Binds
    abstract UserProfileDataSource.Local bindUserProfileDb(UserProfileCache userProfileCache);

    @Binds
    abstract UserDataLocal bindUserStatsDb(UserDataCache userStatsCache);

    @Binds
    abstract CategoryStatsDataSource.Local bindCategoryStatsDb(CategoryStatsCache categoryStatsCache);

    @Binds
    abstract FrequencyStatsDataSource.Local bindStatsFrequencyDb(FrequencyStatsCache frequencyStatsCache);

    /*********************************
     ****** QUESTIONS ******
     *********************************/

    @Binds
    abstract QuestionDataSource.Local bindQuestionDb(QuestionsCache questionsCache);

    /*********************************
     ****** QUIZ RESULT ******
     *********************************/

    @Binds
    abstract QuizLocal bindQuizDb(QuizResultCache quizResultCache);

    /*********************************
     ****** RANKS ******
     *********************************/

    @Binds
    abstract RankAllTimeDataSource.Local bindRankAllTimeDb(RankAllTimeCache rankAllTimeCache);

    @Binds
    abstract RankWeeklyDataSource.Local bindRankWeeklyDb(RankWeeklyCache rankWeeklyCache);

}
