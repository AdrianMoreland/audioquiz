package com.audioquiz.data.repository.stats;

import android.util.Log;

import com.audioquiz.api.datasources.firebase_auth.AuthApi;
import com.audioquiz.api.datasources.user_stats.GeneralStatsDataSource;
import com.audioquiz.core.domain.repository.user.GeneralStatsRepository;
import com.audioquiz.core.model.user.stats.GeneralStats;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class GeneralStatsRepositoryImpl implements GeneralStatsRepository {
    private static final String TAG = "GeneralStatsRepository";
    private final AuthApi auth;
    private final GeneralStatsDataSource.Remote remote;
    private final GeneralStatsDataSource.Local local;

    @Inject
    public GeneralStatsRepositoryImpl(AuthApi auth, GeneralStatsDataSource.Remote remote, GeneralStatsDataSource.Local local) {
        this.auth = auth;
        this.remote = remote;
        this.local = local;
    }

    public void init() {

    }

    private String getUserId() {
        return auth.getFirebaseUserId();
    }

    public Single<GeneralStats> observeGeneralStats() {
        return local.getGeneralStatsLocal()
                .onErrorResumeNext(throwable ->
                        remote.observeGeneralStats()
                                .firstOrError()
                                .flatMap(generalStats ->
                                        saveGeneralStatsLocally(generalStats)
                                                .toSingleDefault(generalStats))
                );
    }

    @Override
    public Single<GeneralStats> getGeneralStats() {
        Log.d(TAG, "getGeneralStats");
        return local.getGeneralStatsLocal()
                .onErrorResumeNext(error ->
                        remote.getGeneralStats()
                                .flatMap(generalStats ->
                                        saveGeneralStatsLocally(generalStats)
                                                .toSingleDefault(generalStats))
                );
    }

    @Override
    public Completable deleteGeneralStats() {
        return local.deleteGeneralStatsLocal(getUserId())
                .andThen(remote.deleteGeneralStats());
    }

    @Override
    public Completable saveGeneralStats(GeneralStats generalStats) {
        return local.insertGeneralStatsLocal(generalStats)
                .andThen(remote.saveGeneralStats(generalStats));
    }

    private Completable saveGeneralStatsLocally(GeneralStats generalStats) {
        return local.insertGeneralStatsLocal(generalStats);
    }


}



