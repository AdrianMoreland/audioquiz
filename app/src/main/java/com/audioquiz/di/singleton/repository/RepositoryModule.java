package com.audioquiz.di.singleton.repository;

import com.audioquiz.core.domain.repository.auth.AuthRepository;
import com.audioquiz.core.domain.repository.quiz.CategoryRepository;
import com.audioquiz.core.domain.repository.quiz.FrequenciesDataRepository;
import com.audioquiz.core.domain.repository.quiz.QuestionRepository;
import com.audioquiz.core.domain.repository.quiz.QuizRepository;
import com.audioquiz.core.domain.repository.rank.RankRepository;
import com.audioquiz.core.domain.repository.user.CategoryStatsRepository;
import com.audioquiz.core.domain.repository.user.FrequencyStatsRepository;
import com.audioquiz.core.domain.repository.user.GeneralStatsRepository;
import com.audioquiz.core.domain.repository.user.StatsRepository;
import com.audioquiz.core.domain.repository.user.UserProfileRepository;
import com.audioquiz.core.domain.repository.user.WeeklyScoresRepository;
import com.audioquiz.core.extensions.di.ExecutorModule;
import com.audioquiz.data.repository.auth.AuthRepositoryImpl;
import com.audioquiz.data.repository.question.FrequenciesDataRepository_impl;
import com.audioquiz.data.repository.question.QuestionRepositoryImpl;
import com.audioquiz.data.repository.quiz.CategoryRepositoryImpl;
import com.audioquiz.data.repository.quiz.QuizRepository_impl;
import com.audioquiz.data.repository.rank.RankRepository_Impl;
import com.audioquiz.data.repository.stats.CategoryStatsRepositoryImpl;
import com.audioquiz.data.repository.stats.FrequencyStatsRepository_Impl;
import com.audioquiz.data.repository.stats.GeneralStatsRepositoryImpl;
import com.audioquiz.data.repository.stats.StatsRepository_Impl;
import com.audioquiz.data.repository.stats.UserProfileRepositoryImpl;
import com.audioquiz.data.repository.stats.WeeklyScoresRepositoryImpl;

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
    abstract CategoryRepository bindCategoryRepository(CategoryRepositoryImpl categoryRepository);

    @Binds
    @Singleton
    abstract AuthRepository bindAuthRepository(AuthRepositoryImpl authRepository);

    @Binds
    @Singleton
    abstract QuestionRepository bindQuestionRepository(QuestionRepositoryImpl questionRepository);

    @Binds
    @Singleton
    abstract StatsRepository bindUserStatisticsRepository(StatsRepository_Impl statsRepository);

    @Binds
    @Singleton
    public abstract UserProfileRepository bindUserProfileRepository(UserProfileRepositoryImpl userProfileRepository);

    @Binds
    @Singleton
    abstract GeneralStatsRepository bindGeneralStatsRepository(GeneralStatsRepositoryImpl generalStatsRepository);

    @Binds
    @Singleton
    abstract CategoryStatsRepository bindCategoryStatsRepository(CategoryStatsRepositoryImpl categoryStatsRepository);

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
