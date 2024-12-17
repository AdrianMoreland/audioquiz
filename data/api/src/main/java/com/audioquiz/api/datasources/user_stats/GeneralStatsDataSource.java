package com.audioquiz.api.datasources.user_stats;

import com.audioquiz.core.model.user.stats.GeneralStats;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface GeneralStatsDataSource {

    interface Local {
        Single<GeneralStats> getGeneralStatsLocal();
        Completable insertGeneralStatsLocal(GeneralStats generalStats);
        Completable deleteGeneralStatsLocal(String userId);
    }

    interface Remote {
        Single<GeneralStats> getGeneralStats();
        Observable<GeneralStats> observeGeneralStats();
        Completable saveGeneralStats(GeneralStats generalStats);
        Completable deleteGeneralStats();
    }

 }
