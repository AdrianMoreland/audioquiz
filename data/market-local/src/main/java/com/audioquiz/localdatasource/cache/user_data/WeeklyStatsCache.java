package com.audioquiz.localdatasource.cache.user_data;

import com.adrian.api.data.datasources.user_stats.stats_weekly_scores.WeeklyStatsLocal;
import com.adrian.model.stats.Last7DaysScores;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class WeeklyStatsCache implements WeeklyStatsLocal {

    @Inject
    public WeeklyStatsCache() {
    }


    @Override
    public Single<Last7DaysScores> getWeeklyStats() {
        return null;
    }

    @Override
    public Completable saveWeeklyStats(Last7DaysScores last7DaysScores) {
        return null;
    }

    @Override
    public Completable deleteWeeklyStats() {
        return null;
    }
}
