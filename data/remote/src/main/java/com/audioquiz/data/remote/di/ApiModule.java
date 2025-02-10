package com.audioquiz.data.remote.di;

import com.audioquiz.api.datasources.firebase_auth.AuthApi;
import com.audioquiz.api.datasources.question.QuestionDataSource;
import com.audioquiz.api.datasources.rank.RankAllTimeDataSource;
import com.audioquiz.api.datasources.rank.RankWeeklyDataSource;
import com.audioquiz.api.datasources.resources.ResourceDataSource;
import com.audioquiz.api.datasources.user.UserProfileDataSource;
import com.audioquiz.api.datasources.user_stats.CategoryStatsDataSource;
import com.audioquiz.api.datasources.user_stats.DailyScoresDataSource;
import com.audioquiz.api.datasources.user_stats.FrequencyStatsDataSource;
import com.audioquiz.api.datasources.user_stats.GeneralStatsDataSource;
import com.audioquiz.api.datasources.user_stats.stats.UserStatsApi;
import com.audioquiz.api.util.AuthorizationGateway;
import com.audioquiz.data.remote.service.auth.AuthService;
import com.audioquiz.data.remote.service.auth.AuthorizationGatewayImpl;
import com.audioquiz.data.remote.service.quiz.QuestionServiceImpl;
import com.audioquiz.data.remote.service.rank.RankAllTimeServiceImpl;
import com.audioquiz.data.remote.service.rank.RankWeeklyServiceImpl;
import com.audioquiz.data.remote.service.user_data.CategoryStatsService;
import com.audioquiz.data.remote.service.user_data.FrequencyStatsService;
import com.audioquiz.data.remote.service.user_data.GeneralStatsService;
import com.audioquiz.data.remote.service.user_data.UserProfileService;
import com.audioquiz.data.remote.service.user_data.UserStatsService;
import com.audioquiz.data.remote.service.user_data.WeeklyStatsService;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ApiModule {

    /**********************************
     ****** AUTH ******
     **********************************/

    @Binds
    abstract AuthApi bindAuthApi(AuthService authServiceImpl);

    @Binds
    @Singleton
    public abstract AuthorizationGateway bindAuthorizationGateway(AuthorizationGatewayImpl authorizationGatewayImpl);

    /**********************************
     ****** USER_DATA ******
     **********************************/
    @Binds
    abstract UserStatsApi bindStatsApi(UserStatsService userStatsService);

    @Binds
    abstract UserProfileDataSource.Remote bindUserProfileApi(UserProfileService userProfileService);

    @Binds
    abstract GeneralStatsDataSource.Remote bindGeneralStatsApi(GeneralStatsService generalStatsService);

    @Binds
    abstract CategoryStatsDataSource.Remote bindCategoryStatsApi(CategoryStatsService categoryStatsServiceImpl);

    @Binds
    abstract FrequencyStatsDataSource.Remote bindFrequencyStatsApi(FrequencyStatsService frequencyStatsServiceImpl);

    @Binds
    abstract DailyScoresDataSource.Remote bindWeeklyStatsApi(WeeklyStatsService weeklyStatsService);


    /**********************************
     ****** QUESTIONS ******
     **********************************/
    @Binds
    abstract QuestionDataSource.Remote bindQuestionApi(QuestionServiceImpl questionServiceImpl);

    /**********************************
     ****** RANKS ******
     **********************************/
    @Binds
    abstract RankAllTimeDataSource.Remote bindRankAllTimeApi(RankAllTimeServiceImpl rankAllTimeServiceImpl);

    @Binds
    abstract RankWeeklyDataSource.Remote bindRankWeeklyApi(RankWeeklyServiceImpl rankWeeklyServiceImpl);

}