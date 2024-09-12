package com.adrian.domain.user;

import com.adrian.model.stats.CategoryStats;
import com.adrian.model.stats.CategoryStatsData;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CategoryStatsRepository {
    void init();

    Single<CategoryStats> getCategoryStats();

    Completable deleteCategoryStats();

    Completable saveCategoryStats(CategoryStats frequencyStats);

    Single<CategoryStatsData> getCategoryStatsData(String category);

    void sync();
}
