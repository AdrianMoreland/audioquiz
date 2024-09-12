package com.adrian.usecase.stats.usecase_impl.stats;


import com.adrian.domain.user.CategoryStatsRepository;
import com.adrian.model.stats.CategoryStats;

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
