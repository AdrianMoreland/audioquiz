package com.audioquiz.di.singleton.repository;

import com.adrian.audioquiz.di.singleton.ExecutorModule;
import com.adrian.data.repository.auth.AuthRepositoryImpl;
import com.adrian.data.repository.question.FrequenciesDataRepository_impl;
import com.adrian.data.repository.question.QuestionRepository_Impl;
import com.adrian.data.repository.quiz.QuizRepository_impl;
import com.adrian.data.repository.rank.RankRepository_Impl;
import com.adrian.data.repository.stats.CategoryStatsRepository_Impl;
import com.adrian.data.repository.stats.FrequencyStatsRepository_Impl;
import com.adrian.data.repository.stats.GeneralStatsRepositoryImpl;
import com.adrian.data.repository.stats.StatsRepository_Impl;
import com.adrian.data.repository.stats.UserProfileRepository_Impl;
import com.adrian.data.repository.stats.WeeklyScoresRepositoryImpl;
import com.adrian.domain.auth.AuthRepository;
import com.adrian.domain.question.FrequenciesDataRepository;
import com.adrian.domain.question.QuestionRepository;
import com.adrian.domain.quiz.QuizRepository;
import com.adrian.domain.rank.RankRepository;
import com.adrian.domain.user.FrequencyStatsRepository;
import com.adrian.domain.user.GeneralStatsRepository;
import com.adrian.domain.user.StatsRepository;
import com.adrian.domain.user.CategoryStatsRepository;
import com.adrian.domain.user.UserProfileRepository;
import com.adrian.domain.user.WeeklyScoresRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module(includes = ExecutorModule.class)
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract AuthRepository bindAuthRepository(AuthRepositoryImpl authRepository);

    @Binds
    @Singleton
    abstract QuestionRepository bindQuestionRepository(QuestionRepository_Impl questionRepository);

    @Binds
    @Singleton
    abstract StatsRepository bindUserStatisticsRepository(StatsRepository_Impl statsRepository);

    @Binds
    @Singleton
    abstract UserProfileRepository bindUserProfileRepository(UserProfileRepository_Impl userProfileRepository);

    @Binds
    @Singleton
    abstract GeneralStatsRepository bindGeneralStatsRepository(GeneralStatsRepositoryImpl generalStatsRepository);

    @Binds
    @Singleton
    abstract CategoryStatsRepository bindCategoryStatsRepository(CategoryStatsRepository_Impl categoryStatsRepository);

    @Binds
    @Singleton
    abstract FrequencyStatsRepository bindFrequencyStatsRepository(FrequencyStatsRepository_Impl frequencyStatsRepository);

    @Binds
    @Singleton
    abstract WeeklyScoresRepository bindWeeklyScoresRepository(WeeklyScoresRepositoryImpl last7DaysScores);

    @Binds
    @Singleton
    abstract QuizRepository bindQuizRepository(QuizRepository_impl quizRepository);

    @Binds
    @Singleton
    abstract RankRepository bindRankRepository(RankRepository_Impl rankRepository);

    @Binds
    @Singleton
    abstract FrequenciesDataRepository bindFrequenciesDataRepository(FrequenciesDataRepository_impl frequenciesDataRepository);
}
