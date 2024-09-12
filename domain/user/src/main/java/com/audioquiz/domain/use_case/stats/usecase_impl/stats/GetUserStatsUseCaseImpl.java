package com.adrian.usecase.stats.usecase_impl.stats;

import javax.inject.Inject;
import com.adrian.domain.user.StatsRepository;
import com.adrian.model.stats.UserStats;

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
