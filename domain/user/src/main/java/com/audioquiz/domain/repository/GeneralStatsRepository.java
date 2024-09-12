package com.adrian.domain.user;

import com.adrian.model.stats.GeneralStats;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface GeneralStatsRepository {
    void init();
    Single<GeneralStats> getGeneralStats();

    Completable saveGeneralStats(GeneralStats generalStats);

    Completable deleteGeneralStats();

 }
