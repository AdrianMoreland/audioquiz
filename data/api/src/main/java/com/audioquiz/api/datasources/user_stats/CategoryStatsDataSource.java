package com.audioquiz.api.datasources.user_stats;


import com.audioquiz.core.model.user.stats.CategoryStats;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CategoryStatsDataSource {

    interface Local {
        Completable insert(CategoryStats categoryStats);
        Completable insertAll(List<CategoryStats> categoryStatsList);
        Single<CategoryStats> getCategoryStatsSingle();
        Completable deleteCategoryStatsLocal();
        void flushData();
    }

    interface Remote {
        Single<CategoryStats> getCategoryStats();
        Completable saveCategoryStats(CategoryStats categoryStats);
        Completable deleteCategoryStats();
    }


}