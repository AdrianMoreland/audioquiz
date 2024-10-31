package com.audioquiz.data.remote.di;

import com.audioquiz.api.datasources.firebase_auth.AuthApi;
import com.audioquiz.api.datasources.question.QuestionApi;
import com.audioquiz.api.datasources.rank.RankAllTimeApi;
import com.audioquiz.api.datasources.rank_weekly.RankWeeklyApi;
import com.audioquiz.api.datasources.user.UserProfileApi;
import com.audioquiz.api.datasources.user_stats.stats.UserStatsApi;
import com.audioquiz.api.datasources.user_stats.stats_category.CategoryStatsApi;
import com.audioquiz.api.datasources.user_stats.stats_frequency.FrequencyStatsApi;
import com.audioquiz.api.datasources.user_stats.stats_general.GeneralStatsApi;
import com.audioquiz.api.datasources.user_stats.stats_weekly_scores.WeeklyStatsApi;
import com.audioquiz.api.util.AuthorizationGateway;
import com.audioquiz.api.util.GoogleSignInHelper;
import com.audioquiz.data.remote.datasource.auth.GoogleSignInDataSource;
import com.audioquiz.data.remote.service.auth.AuthService;
import com.audioquiz.data.remote.service.auth.AuthorizationGatewayImpl;
import com.audioquiz.data.remote.service.quiz.QuestionServiceImpl;
import com.audioquiz.data.remote.service.rank.RankAllTimeService_Impl;
import com.audioquiz.data.remote.service.rank.RankWeeklyService_Impl;
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