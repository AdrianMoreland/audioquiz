package com.adrian.usecase.stats.usecase;

import com.adrian.model.stats.CategoryStats;
import com.adrian.model.stats.FrequencyStats;
import com.adrian.model.stats.GeneralStats;
import com.adrian.model.stats.Last7DaysScores;
import com.adrian.model.stats.UserStats;

import io.reactivex.rxjava3.core.Single;

public interface StatisticsUseCaseFacade {
    void preloadUserData();

    Single<UserStats> getUserStatsSingle();

    Single<GeneralStats> getGeneralStats();

    Single<CategoryStats> getCategoryStats();

    Single<FrequencyStats> getFrequencyStats();

    Single<Last7DaysScores> getWeeklyScores();

    Single<Boolean> sync();
}
