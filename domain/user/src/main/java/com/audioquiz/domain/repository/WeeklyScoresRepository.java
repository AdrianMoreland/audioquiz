package com.adrian.domain.user;

import com.adrian.model.stats.Last7DaysScores;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface WeeklyScoresRepository {

    void init();

    Single<Last7DaysScores> getWeeklyScores();

    Completable deleteWeeklyScores();

    Completable saveWeeklyScores(Last7DaysScores weeklyScores);
}
