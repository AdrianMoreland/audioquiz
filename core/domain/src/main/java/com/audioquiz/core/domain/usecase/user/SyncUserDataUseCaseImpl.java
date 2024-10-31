package com.audioquiz.core.domain.usecase.user;

import com.audioquiz.core.domain.repository.user.CategoryStatsRepository;
import com.audioquiz.core.domain.repository.user.FrequencyStatsRepository;
import com.audioquiz.core.domain.repository.user.GeneralStatsRepository;
import com.audioquiz.core.domain.repository.user.UserProfileRepository;
import com.audioquiz.core.domain.repository.user.WeeklyScoresRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class SyncUserDataUseCaseImpl implements SyncUserDataUseCase {
    private final UserProfileRepository userProfileRepository;
    private final GeneralStatsRepository generalStatsRepository;
    private final CategoryStatsRepository categoryStatsRepository;
    private final WeeklyScoresRepository weeklyScoresRepository;
    private final FrequencyStatsRepository frequencyStatsRepository;

    @Inject
    public SyncUserDataUseCaseImpl(UserProfileRepository userProfileRepository,
                                   GeneralStatsRepository generalStatsRepository,
                                   CategoryStatsRepository categoryStatsRepository,
                                   WeeklyScoresRepository weeklyScoresRepository,
                                   FrequencyStatsRepository frequencyStatsRepository) {
        this.userProfileRepository = userProfileRepository;
        this.generalStatsRepository = generalStatsRepository;
        this.categoryStatsRepository = categoryStatsRepository;
        this.weeklyScoresRepository = weeklyScoresRepository;
        this.frequencyStatsRepository = frequencyStatsRepository;
    }

    public Completable execute() {
        return Completable.fromAction(() -> {
            userProfileRepository.init();
            generalStatsRepository.init();
            categoryStatsRepository.init();
            weeklyScoresRepository.init();
            frequencyStatsRepository.init();
        });
    }
}
