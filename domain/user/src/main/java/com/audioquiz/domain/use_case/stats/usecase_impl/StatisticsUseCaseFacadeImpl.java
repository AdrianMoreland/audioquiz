package com.adrian.usecase.stats.usecase_impl;


import com.adrian.model.login.UserProfile;
import com.adrian.model.stats.CategoryStats;
import com.adrian.model.stats.FrequencyStats;
import com.adrian.model.stats.GeneralStats;
import com.adrian.model.stats.Last7DaysScores;
import com.adrian.model.stats.UserStats;
import com.adrian.usecase.stats.usecase.StatisticsUseCaseFacade;
import com.adrian.usecase.stats.usecase_impl.stats.GetCategoryStatsUseCaseImpl;
import com.adrian.usecase.stats.usecase_impl.stats.GetFrequencyStatsUseCaseImpl;
import com.adrian.usecase.stats.usecase_impl.stats.GetGeneralStatsUseCaseImpl;
import com.adrian.usecase.stats.usecase_impl.stats.GetLast7DaysScoresUseCaseImpl;
import com.adrian.usecase.stats.usecase_impl.stats.GetUserStatsUseCaseImpl;
import com.adrian.usecase.stats.usecase_impl.stats.PreloadUserStatisticsUseCaseImpl;
import com.adrian.usecase.user.usecase.UserProfileUseCaseFacade;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


public class StatisticsUseCaseFacadeImpl implements StatisticsUseCaseFacade {
    private final PreloadUserStatisticsUseCaseImpl preloadUserStatisticsUseCase;
    private final UserProfileUseCaseFacade userProfileUseCaseFacade;
    private final GetUserStatsUseCaseImpl getUserStatsUseCase;
    private final GetGeneralStatsUseCaseImpl getGeneralStatsUseCase;
    private final GetCategoryStatsUseCaseImpl getCategoryStatsUseCase;
    private final GetLast7DaysScoresUseCaseImpl getLast7DaysScoresUseCase;
    private final GetFrequencyStatsUseCaseImpl getFrequencyStatsUseCase;

    @Inject
    public StatisticsUseCaseFacadeImpl(UserProfileUseCaseFacade userProfileUseCaseFacade,
                                       GetGeneralStatsUseCaseImpl getGeneralStatsUseCase,
                                        GetUserStatsUseCaseImpl getUserStatsUseCase,
                                       GetCategoryStatsUseCaseImpl getCategoryStatsUseCase,
                                       PreloadUserStatisticsUseCaseImpl preloadUserStatisticsUseCase,
                                       GetLast7DaysScoresUseCaseImpl getLast7DaysScoresUseCase,
                                       GetFrequencyStatsUseCaseImpl getFrequencyStatsUseCase) {
        this.preloadUserStatisticsUseCase = preloadUserStatisticsUseCase;
        this.userProfileUseCaseFacade = userProfileUseCaseFacade;
        this.getUserStatsUseCase = getUserStatsUseCase;
        this.getGeneralStatsUseCase = getGeneralStatsUseCase;
        this.getCategoryStatsUseCase = getCategoryStatsUseCase;
        this.getLast7DaysScoresUseCase = getLast7DaysScoresUseCase;
        this.getFrequencyStatsUseCase = getFrequencyStatsUseCase;
    }

    @Override
    public void preloadUserData() {
        preloadUserStatisticsUseCase.execute();
    }


    public Single<UserProfile> getUserProfile() {
        return userProfileUseCaseFacade.getUserProfileSingle();
    }


    @Override
    public Single<GeneralStats> getGeneralStats() {
        return getGeneralStatsUseCase.execute();
    }

    @Override
    public Single<CategoryStats> getCategoryStats() {
        return getCategoryStatsUseCase.execute();
    }

    @Override
    public Single<FrequencyStats> getFrequencyStats() {
        return getFrequencyStatsUseCase.execute();
    }

    @Override
    public Single<Last7DaysScores> getWeeklyScores() {
        return getLast7DaysScoresUseCase.execute();
    }

    @Override
    public Single<Boolean> sync() {
        return Single.zip(
                getUserProfile(),
                getGeneralStats(),
                getCategoryStats(),
                getFrequencyStats(),
                getWeeklyScores(),
                (userProfile, generalStats, categoryStats, frequencyStats, weeklyScores) -> {
                    // Combine the results here
                    return true;
                }
        );
    }
    @Override
    public Single<UserStats> getUserStatsSingle() {
        return getUserStatsUseCase.execute();
    }
}
