package com.audioquiz.core.domain.usecase.user.stats;

import com.audioquiz.core.model.user.stats.CategoryStats;
import com.audioquiz.core.model.user.stats.FrequencyStats;
import com.audioquiz.core.model.user.stats.GeneralStats;
import com.audioquiz.core.model.user.stats.Last7DaysScores;
import com.audioquiz.core.model.user.stats.UserStats;

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
