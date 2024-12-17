package com.audioquiz.data.local.cache.user_data;

import com.audioquiz.api.datasources.user_stats.GeneralStatsDataSource;
import com.audioquiz.data.local.dao.user_data.GeneralStatsDao;
import com.audioquiz.data.local.entity.user_data.GeneralStatsEntity;
import com.audioquiz.data.local.mapper.DatabaseMapper;
import com.audioquiz.data.local.provider.AppDatabase;
import com.audioquiz.core.model.user.stats.GeneralStats;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GeneralStatsCache implements GeneralStatsDataSource.Local {
    private final GeneralStatsDao generalStatsDao;

    @Inject
    public GeneralStatsCache(AppDatabase appDatabase) {
        this.generalStatsDao = appDatabase.generalStatsDao();
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
        return DatabaseMapper.map(generalStatsEntity, GeneralStats.class);
    }

    private GeneralStatsEntity mapToEntity (GeneralStats domain) {
        return DatabaseMapper.map(domain, GeneralStatsEntity.class);
    }

}