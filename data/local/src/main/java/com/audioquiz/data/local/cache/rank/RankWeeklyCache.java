package com.audioquiz.data.local.cache.rank;


import com.audioquiz.api.datasources.rank.RankWeeklyDataSource;
import com.audioquiz.core.model.rank.RankEntry;
import com.audioquiz.data.local.dao.rank.RankWeeklyDao;
import com.audioquiz.data.local.entity.rank_stats.RankWeeklyEntity;
import com.audioquiz.data.local.mapper.DatabaseMapper;
import com.audioquiz.data.local.provider.AppDatabase;

import java.util.List;

import javax.inject.Inject;

public class RankWeeklyCache implements RankWeeklyDataSource.Local {
    private final RankWeeklyDao dao;


    @Inject
    public RankWeeklyCache(AppDatabase appDatabase) {
        this.dao = appDatabase.rankWeeklyDao();
    }

    @Override
    public List<RankEntry> getRankWeekly() {
        List<RankWeeklyEntity> rankWeeklyEntityList = dao.getAllRankEntries();
        List<RankEntry> rankEntryList = null;
        for (RankWeeklyEntity rankWeeklyEntity : rankWeeklyEntityList) {
            RankEntry rankEntry = DatabaseMapper.map(rankWeeklyEntity, RankEntry.class);
            rankEntryList.add(rankEntry);
        }
        return rankEntryList;
    }

    @Override
    public void insert(RankEntry rankEntry) {
        RankWeeklyEntity rankWeeklyEntity = DatabaseMapper.map(rankEntry, RankWeeklyEntity.class);
        dao.insert(rankWeeklyEntity);
    }

    @Override
    public void insertAll(List<RankEntry> rankEntries) {
        for (RankEntry rankEntry : rankEntries) {
            RankWeeklyEntity rankWeeklyEntity = DatabaseMapper.map(rankEntry, RankWeeklyEntity.class);
            dao.insert(rankWeeklyEntity);
        }
    }

    @Override
    public void flushData() {
        dao.deleteAll();
    }
}
