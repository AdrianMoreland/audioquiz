package com.audioquiz.core.domain.usecase.user.stats.stats;


import com.audioquiz.core.domain.repository.user.GeneralStatsRepository;
import com.audioquiz.core.model.user.stats.GeneralStats;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetGeneralStatsUseCaseImpl {
    private final GeneralStatsRepository repository;

    @Inject
    public GetGeneralStatsUseCaseImpl(GeneralStatsRepository repository){
        this.repository = repository;
    }

    public Single<GeneralStats> execute() {
        return repository.getGeneralStats();
    }
}
