package com.adrian.usecase.stats.usecase_impl.stats;


import com.adrian.domain.user.StatsRepository;

import javax.inject.Inject;

public class PreloadUserStatisticsUseCaseImpl {
    private final StatsRepository userStatisticsRepository;

    @Inject
    public PreloadUserStatisticsUseCaseImpl(StatsRepository userStatisticsRepository) {
        this.userStatisticsRepository = userStatisticsRepository;
    }

    public void execute() {
        userStatisticsRepository.init();
    }
}
