package com.audioquiz.data.repository.stats;

import com.audioquiz.api.datasources.firebase_auth.AuthApi;
import com.audioquiz.api.datasources.user_stats.DailyScoresDataSource;
import com.audioquiz.core.domain.repository.user.WeeklyScoresRepository;
import com.audioquiz.core.model.user.stats.Last7DaysScores;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class WeeklyScoresRepositoryImpl implements WeeklyScoresRepository {
    private static final String TAG = "WeeklyScoresRepository";
    private final AuthApi auth;
    private final DailyScoresDataSource.Remote remote;
    private final DailyScoresDataSource.Local local;

    @Inject
    public WeeklyScoresRepositoryImpl(AuthApi auth, DailyScoresDataSource.Remote remote, DailyScoresDataSource.Local local) {
        this.auth = auth;
        this.remote = remote;
        this.local = local;
    }


    @Override
    public void init() {

    }

    @Override
    public Single<Last7DaysScores> getWeeklyScores() {
        return local.getWeeklyStats()
                .onErrorResumeNext(error ->
                        remote.getWeeklyStats()
                                .flatMap(generalStats ->
                                        saveWeeklyScoresLocally(generalStats)
                                                .toSingleDefault(generalStats))
                );
    }

    @Override
    public Completable deleteWeeklyScores() {
        return local.deleteWeeklyStats()
                .andThen(remote.deleteWeeklyStats());
    }

    @Override
    public Completable saveWeeklyScores(Last7DaysScores weeklyScores) {
        return saveWeeklyScoresLocally(weeklyScores)
                .andThen(remote.saveWeeklyStats(weeklyScores));
    }

    private Completable saveWeeklyScoresLocally(Last7DaysScores weeklyScores) {
        return local.saveWeeklyStats(weeklyScores);
    }


    private void updateWeeklyScores(Last7DaysScores last7DaysScores) {
        if (last7DaysScores != null) {
            remote.saveWeeklyStats(last7DaysScores);
//            for (Map.Entry<String, Last7DaysScores> entry : last7DaysScores.getDailyScores().entrySet()) {
//                Last7DaysScores data = entry.getValue();
//                api.saveWeeklyStats(data);
//            }
        }
    }
}
