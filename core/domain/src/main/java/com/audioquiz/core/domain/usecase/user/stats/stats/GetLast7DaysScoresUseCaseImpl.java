package com.audioquiz.core.domain.usecase.user.stats.stats;


import com.audioquiz.core.domain.repository.user.WeeklyScoresRepository;
import com.audioquiz.core.model.user.stats.Last7DaysScores;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


public class GetLast7DaysScoresUseCaseImpl {
    private final WeeklyScoresRepository repository;

    @Inject
    public GetLast7DaysScoresUseCaseImpl(WeeklyScoresRepository repository) {
        this.repository = repository;
    }

    public Single<Last7DaysScores> execute() {
        return repository.getWeeklyScores();
    }
}
