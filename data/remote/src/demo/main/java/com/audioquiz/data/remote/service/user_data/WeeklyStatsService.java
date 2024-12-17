package com.audioquiz.data.remote.service.user_data;

import com.audioquiz.api.datasources.user_stats.stats_weekly_scores.WeeklyStatsApi;
import com.audioquiz.core.model.user.stats.Last7DaysScores;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class WeeklyStatsService implements WeeklyStatsApi {

    @Inject
    public WeeklyStatsService() {
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
