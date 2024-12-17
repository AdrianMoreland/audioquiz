package com.audioquiz.api.datasources.user_stats;


import com.audioquiz.core.model.user.stats.FrequencyStats;
import com.audioquiz.core.model.user.stats.PitchStats;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface FrequencyStatsDataSource {

    interface Local {
        Single<FrequencyStats> getFrequencyStatsSingle();
        Completable insert(FrequencyStats frequencyStats);
        void insertAll(List<FrequencyStats> frequencyStatsList);
        void flushData();
        Completable deleteFrequencyStatsLocal();
    }

    interface Remote {
        Single<FrequencyStats> getFrequencyStats();
        @NonNull
        Completable saveFrequencyStatsData(PitchStats statsData);
        Completable saveFrequencyStats(FrequencyStats frequencyStats);
        Completable deleteFrequencyStats();
    }


}