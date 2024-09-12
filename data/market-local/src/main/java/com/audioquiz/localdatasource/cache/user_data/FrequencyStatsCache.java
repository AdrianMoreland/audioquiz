package com.audioquiz.localdatasource.cache.user_data;

import com.adrian.api.data.datasources.user_stats.stats_frequency.StatsFrequencyLocal;
import com.adrian.database.dao.user_data.IntervalStatsDao;
import com.adrian.database.entity.user_data.frequency_stats.IntervalStatsEntity;
import com.adrian.database.entity.user_data.frequency_stats.PitchStatsEntity;
import com.adrian.database.mapper.IDatabaseMapper;
import com.adrian.database.dao.user_data.FrequencyStatsDao;
import com.adrian.database.provider.AppDatabase;
import com.adrian.model.stats.FrequencyStats;
import com.adrian.model.stats.IntervalStats;
import com.adrian.model.stats.PitchStats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FrequencyStatsCache implements StatsFrequencyLocal {
    private final FrequencyStatsDao frequencyStatsDao;
    private final IntervalStatsDao intervalStatsDao;
    private final IDatabaseMapper mapper;

    @Inject
    public FrequencyStatsCache(AppDatabase appDatabase,
                               IDatabaseMapper mapper) {
        this.frequencyStatsDao = appDatabase.frequencyStatsDao();
        this.intervalStatsDao = appDatabase.intervalStatsDao();
        this.mapper = mapper;
    }

    @Override
    public Completable insert(FrequencyStats frequencyStats) {
        return frequencyStatsDao.insertCompletable(mapToEntity(frequencyStats))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void insertAll(List<FrequencyStats> frequencyStatsList) {

    }

    @Override
    public Single<FrequencyStats> getFrequencyStatsSingle() {
        return Single.fromCallable(() -> {
                    List<PitchStatsEntity> pitchScoresEntities = frequencyStatsDao.getAllPitchScores();
                    Map<String, PitchStats> pitchScoresMap = new HashMap<>();
                    for (PitchStatsEntity entity : pitchScoresEntities) {
                        pitchScoresMap.put(String.valueOf(entity.getFrequency()), mapPitchToDomain(entity));
                    }
                    List<IntervalStatsEntity> intervalScoresEntities = intervalStatsDao.getAllIntervalScores();
                    Map<String, IntervalStats> intervalScoresMap = new HashMap<>();
                    for (IntervalStatsEntity entity : intervalScoresEntities) {
                        intervalScoresMap.put(
                                String.valueOf(entity.getFrequency()),
                                mapIntervalToDomain(entity));
                    }
                    return new FrequencyStats.Builder()
                            .setId(getUserId())
                            .setPitchScoresMap(pitchScoresMap)
                            .setIntervalScoresMap(intervalScoresMap)
                            .build();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private String getUserId() {
        return null;
    }

    @Override
    public void flushData() {

    }

    @Override
    public Completable deleteFrequencyStatsLocal() {
        return null;
    }

    private PitchStats mapPitchToDomain(PitchStatsEntity frequencyStatsEntity) {
        return mapper.mapToDomain(frequencyStatsEntity, PitchStats.class);
    }

    private IntervalStats mapIntervalToDomain (IntervalStatsEntity intervalStats) {
        return mapper.mapToDomain(intervalStats, IntervalStats.class);
    }

    private PitchStatsEntity mapToEntity (FrequencyStats domain) {
        return mapper.mapFromDomain(domain, PitchStatsEntity.class);
    }
}