package com.audioquiz.core.domain.usecase.user.stats;


import com.audioquiz.core.domain.usecase.user.stats.stats.GetCategoryStatsUseCaseImpl;
import com.audioquiz.core.domain.usecase.user.stats.stats.GetFrequencyStatsUseCaseImpl;
import com.audioquiz.core.domain.usecase.user.stats.stats.GetGeneralStatsUseCaseImpl;
import com.audioquiz.core.domain.usecase.user.stats.stats.GetLast7DaysScoresUseCaseImpl;
import com.audioquiz.core.domain.usecase.user.stats.stats.GetUserStatsUseCaseImpl;
import com.audioquiz.core.domain.usecase.user.stats.stats.PreloadUserStatisticsUseCaseImpl;
import com.audioquiz.core.model.user.UserProfile;
import com.audioquiz.core.model.user.stats.CategoryStats;
import com.audioquiz.core.model.user.stats.FrequencyStats;
import com.audioquiz.core.model.user.stats.GeneralStats;
import com.audioquiz.core.model.user.stats.Last7DaysScores;
import com.audioquiz.core.model.user.stats.UserStats;
import com.audioquiz.core.domain.usecase.user.profile.UserProfileUseCaseFacade;

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
