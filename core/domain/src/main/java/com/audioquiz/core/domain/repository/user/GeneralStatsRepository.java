package com.audioquiz.core.domain.repository.user;

import com.audioquiz.core.model.user.stats.GeneralStats;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface GeneralStatsRepository {
    void init();
    Single<GeneralStats> getGeneralStats();
    Completable saveGeneralStats(GeneralStats generalStats);
    Completable deleteGeneralStats();
 }
