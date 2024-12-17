package com.audioquiz.api.datasources.user_stats;

import com.audioquiz.core.model.user.stats.Last7DaysScores;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface DailyScoresDataSource {

    interface Local {
        Single<Last7DaysScores> getWeeklyStats();
        Completable saveWeeklyStats(Last7DaysScores last7DaysScores);
        Completable deleteWeeklyStats();
    }

    interface Remote {
        Single<Last7DaysScores> getWeeklyStats();
        Completable saveWeeklyStats(Last7DaysScores last7DaysScores);
        Completable deleteWeeklyStats();
    }

}
