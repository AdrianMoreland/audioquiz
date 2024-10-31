package com.audioquiz.core.domain.usecase.user.stats.stats;

import com.audioquiz.core.domain.repository.user.StatsRepository;

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
