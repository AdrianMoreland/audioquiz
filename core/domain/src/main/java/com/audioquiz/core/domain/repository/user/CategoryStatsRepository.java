package com.audioquiz.core.domain.repository.user;


import com.audioquiz.core.model.user.stats.CategoryStats;
import com.audioquiz.core.model.user.stats.CategoryStatsData;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CategoryStatsRepository {
    Completable init();
    void sync();
    Single<CategoryStats> getCategoryStats();
    Completable deleteCategoryStats();
    Completable saveCategoryStats(CategoryStats frequencyStats);
    Single<CategoryStatsData> getCategoryStatsData(String category);
}
