package com.audioquiz.localdatasource.cache.rank;

import com.adrian.api.data.datasources.rank_weekly.RankWeeklyLocal;
import com.adrian.database.mapper.IDatabaseMapper;
import com.adrian.database.dao.rank.RankWeeklyDao;
import com.adrian.database.entity.rank_stats.RankWeeklyEntity;
import com.adrian.database.provider.AppDatabase;
import com.adrian.model.rank.RankEntry;

import java.util.List;

import javax.inject.Inject;

public class RankWeeklyCache implements RankWeeklyLocal {
    private final RankWeeklyDao rankWeeklyDao;
    private final IDatabaseMapper mapper;


    @Inject
    public RankWeeklyCache(AppDatabase appDatabase,
                           IDatabaseMapper mapper) {
        this.rankWeeklyDao = appDatabase.rankWeeklyDao();
        this.mapper = mapper;
    }

    @Override
    public List<RankEntry> getRankWeekly() {
        List<RankWeeklyEntity> rankWeeklyEntityList = rankWeeklyDao.getAllRankEntries();
        List<RankEntry> rankEntryList = null;
        for (RankWeeklyEntity rankWeeklyEntity : rankWeeklyEntityList) {
            RankEntry rankEntry = mapper.mapToDomain(rankWeeklyEntity, RankEntry.class);
            rankEntryList.add(rankEntry);
        }
        return rankEntryList;
    }

    @Override
    public void insert(RankEntry rankEntry) {
        RankWeeklyEntity rankWeeklyEntity = mapper.mapFromDomain(rankEntry, RankWeeklyEntity.class);
        rankWeeklyDao.insert(rankWeeklyEntity);
    }

    @Override
    public void insertAll(List<RankEntry> rankEntries) {
        for (RankEntry rankEntry : rankEntries) {
            RankWeeklyEntity rankWeeklyEntity = mapper.mapFromDomain(rankEntry, RankWeeklyEntity.class);
            rankWeeklyDao.insert(rankWeeklyEntity);
        }
    }

    @Override
    public void flushData() {
        rankWeeklyDao.deleteAll();
    }
}
