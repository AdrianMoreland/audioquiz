package com.adrian.usecase.stats.usecase_impl.stats;


import com.adrian.domain.user.WeeklyScoresRepository;
import com.adrian.model.stats.Last7DaysScores;

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
