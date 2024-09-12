package com.adrian.usecase.stats.usecase_impl.stats;

import com.adrian.domain.user.GeneralStatsRepository;
import com.adrian.model.stats.GeneralStats;

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
