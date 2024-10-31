package com.audioquiz.core.domain.usecase.user.stats.stats;


import com.audioquiz.core.domain.repository.user.CategoryStatsRepository;
import com.audioquiz.core.model.user.stats.CategoryStats;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCategoryStatsUseCaseImpl {
    private final CategoryStatsRepository repository;

    @Inject
    public GetCategoryStatsUseCaseImpl(CategoryStatsRepository repository){
        this.repository = repository;
    }

    public Single<CategoryStats> execute() {
        return repository.getCategoryStats();
    }

}
