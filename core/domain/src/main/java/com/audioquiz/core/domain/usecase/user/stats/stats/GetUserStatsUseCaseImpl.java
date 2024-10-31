package com.audioquiz.core.domain.usecase.user.stats.stats;

import com.audioquiz.core.domain.repository.user.StatsRepository;
import com.audioquiz.core.model.user.stats.UserStats;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


public class GetUserStatsUseCaseImpl {
    private final StatsRepository userStatisticsRepository;

    @Inject
    public GetUserStatsUseCaseImpl(StatsRepository userStatisticsRepository) {
        this.userStatisticsRepository = userStatisticsRepository;
    }

    public Single<UserStats> execute() {
        return userStatisticsRepository.getUserStatsSingle();
    }
}
