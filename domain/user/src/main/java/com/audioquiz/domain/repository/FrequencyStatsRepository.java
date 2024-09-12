package com.adrian.domain.user;

import com.adrian.model.stats.FrequencyStats;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface FrequencyStatsRepository {

    void init();

    Single<FrequencyStats> getFrequencyStats();

    Completable deleteFrequencyStats();

    Completable saveFrequencyStats(FrequencyStats frequencyStats);
}
