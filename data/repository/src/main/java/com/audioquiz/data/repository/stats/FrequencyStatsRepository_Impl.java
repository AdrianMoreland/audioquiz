package com.audioquiz.data.repository.stats;


import android.util.Log;

import com.audioquiz.api.datasources.user_stats.FrequencyStatsDataSource;
import com.audioquiz.core.domain.repository.user.FrequencyStatsRepository;
import com.audioquiz.core.model.user.stats.FrequencyStats;
import com.audioquiz.core.model.user.stats.PitchStats;
import com.audioquiz.core.model.user.stats.UserStats;

import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FrequencyStatsRepository_Impl implements FrequencyStatsRepository {
    private static final String TAG = "FrequencyStatsRepository";

    private final Random random = new Random();
    private final FrequencyStatsDataSource.Remote remote;
    private final FrequencyStatsDataSource.Local local;

    private @NonNull Single<Object> frequencyStats;

    @Inject
    public FrequencyStatsRepository_Impl(FrequencyStatsDataSource.Remote remote, FrequencyStatsDataSource.Local local) {
        this.remote = remote;
        this.local = local;
        init();
    }

    public void init() {
        Log.d(TAG, "init: ");
    }

    @Override
    public Single<FrequencyStats> getFrequencyStats() {
        return local.getFrequencyStatsSingle()
                .onErrorResumeNext(error ->
                        remote.getFrequencyStats()
                                .flatMap(generalStats ->
                                        saveFrequencyStatsLocally(generalStats)
                                                .toSingleDefault(generalStats))
                );
    }

    @Override
    public Completable deleteFrequencyStats() {
        return local.deleteFrequencyStatsLocal()
                .andThen(remote.deleteFrequencyStats());
    }

    @Override
    public Completable saveFrequencyStats(FrequencyStats frequencyStats) {
        return local.insert(frequencyStats)
                .andThen(remote.saveFrequencyStats(frequencyStats));
    }

    private Completable saveFrequencyStatsLocally(FrequencyStats frequencyStats) {
        return local.insert(frequencyStats);
    }


    private void updateFrequencyStats(UserStats userStatistics) {
        FrequencyStats frequencyStats = userStatistics.getFrequencyStats();
        if (frequencyStats != null) {
            for (Map.Entry<String, PitchStats> entry : frequencyStats.getPitchScoresMap().entrySet()) {
                PitchStats statsData = entry.getValue();
                remote.saveFrequencyStatsData(statsData);
            }
        }
    }

}
