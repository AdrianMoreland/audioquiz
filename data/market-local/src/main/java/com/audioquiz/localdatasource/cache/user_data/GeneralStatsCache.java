package com.audioquiz.localdatasource.cache.user_data;

import com.adrian.api.data.datasources.user_stats.stats_general.GeneralStatsLocal;
import com.adrian.database.mapper.IDatabaseMapper;
import com.adrian.database.provider.AppDatabase;
import com.adrian.database.dao.user_data.GeneralStatsDao;
import com.adrian.database.entity.user_data.GeneralStatsEntity;
import com.adrian.model.stats.GeneralStats;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GeneralStatsCache implements GeneralStatsLocal {
    private final GeneralStatsDao generalStatsDao;
    private final IDatabaseMapper mapper;

    @Inject
    public GeneralStatsCache(AppDatabase appDatabase,
                             IDatabaseMapper mapper) {
        this.generalStatsDao = appDatabase.generalStatsDao();
        this.mapper = mapper;
    }

    @Override
    public Single<GeneralStats> getGeneralStatsLocal() {
        return generalStatsDao.getGeneralStatsSingle()
                .subscribeOn(Schedulers.io()) // Perform database access on IO thread
                .map(this::mapToDomain) // Use map with separate transformation function
                .observeOn(AndroidSchedulers.mainThread()); // Observe on main thread (if transformation is light)
    }

    @Override
    public Completable insertGeneralStatsLocal(GeneralStats generalStats) {
        return generalStatsDao.insertCompletable(mapToEntity(generalStats))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable deleteGeneralStatsLocal(String userId) {
        return Completable.fromAction(() -> {
            GeneralStatsEntity entity = generalStatsDao.findByUserId(userId);
            if (entity != null) {
                generalStatsDao.delete(entity);
            }
        });
    }


    private GeneralStats mapToDomain (GeneralStatsEntity generalStatsEntity) {
        return mapper.mapToDomain(generalStatsEntity, GeneralStats.class);
    }

    private GeneralStatsEntity mapToEntity (GeneralStats domain) {
        return mapper.mapFromDomain(domain, GeneralStatsEntity.class);
    }

}