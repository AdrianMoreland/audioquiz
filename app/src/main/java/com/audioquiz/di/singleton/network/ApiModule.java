package com.audioquiz.di.singleton.network;

import com.adrian.api.data.datasources.firebase_auth.AuthApi;
import com.adrian.api.data.datasources.question.QuestionApi;
import com.adrian.api.data.datasources.rank.RankAllTimeApi;
import com.adrian.api.data.datasources.rank_weekly.RankWeeklyApi;
import com.adrian.api.data.datasources.user.UserProfileApi;
import com.adrian.api.data.datasources.user_stats.stats.UserStatsApi;
import com.adrian.api.data.datasources.user_stats.stats_category.CategoryStatsApi;
import com.adrian.api.data.datasources.user_stats.stats_frequency.FrequencyStatsApi;
import com.adrian.api.data.datasources.user_stats.stats_general.GeneralStatsApi;
import com.adrian.api.data.datasources.user_stats.stats_weekly_scores.WeeklyStatsApi;
import com.adrian.api.data.util.AuthorizationGateway;
import com.adrian.api.data.util.GoogleSignInHelper;
import com.adrian.data.datasource.auth.GoogleSignInDataSource;
import com.adrian.data.service.auth.AuthService;
import com.adrian.data.service.auth.AuthorizationGatewayImpl;
import com.adrian.data.service.quiz.QuestionServiceImpl;
import com.adrian.data.service.rank.RankAllTimeService_Impl;
import com.adrian.data.service.rank.RankWeeklyService_Impl;
import com.adrian.data.service.user_data.CategoryStatsService;
import com.adrian.data.service.user_data.FrequencyStatsService;
import com.adrian.data.service.user_data.GeneralStatsService;
import com.adrian.data.service.user_data.UserProfileService;
import com.adrian.data.service.user_data.UserStatsService;
import com.adrian.data.service.user_data.WeeklyStatsService;

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
    abstract AuthApi bindAuthApi(AuthService authService_impl);

    @Binds
    @Singleton
    abstract GoogleSignInHelper bindGoogleSignInHelper(GoogleSignInDataSource googleSignInDataSource);

    @Binds
    @Singleton
    public abstract AuthorizationGateway bindAuthorizationGateway(AuthorizationGatewayImpl authorizationGatewayImpl);

    /**********************************
     ****** USER_DATA ******
     **********************************/
    @Binds
    abstract UserStatsApi bindStatsApi(UserStatsService userStatsService);

    @Binds
    abstract UserProfileApi bindUserProfileApi(UserProfileService userProfileService);

    @Binds
    abstract GeneralStatsApi bindGeneralStatsApi(GeneralStatsService generalStatsService);

    @Binds
    abstract CategoryStatsApi bindCategoryStatsApi(CategoryStatsService categoryStatsServiceImpl);

    @Binds
    abstract FrequencyStatsApi bindFrequencyStatsApi(FrequencyStatsService frequencyStatsServiceImpl);

    @Binds
    abstract WeeklyStatsApi bindWeeklyStatsApi(WeeklyStatsService weeklyStatsService);


    /**********************************
     ****** QUESTIONS ******
     **********************************/
    @Binds
    abstract QuestionApi bindQuestionApi(QuestionServiceImpl questionService_impl);

    /**********************************
     ****** RANKS ******
     **********************************/
    @Binds
    abstract RankAllTimeApi bindRankAllTimeApi(RankAllTimeService_Impl rankAllTimeService_impl);

    @Binds
    abstract RankWeeklyApi bindRankWeeklyApi(RankWeeklyService_Impl rankWeeklyService_impl);

}