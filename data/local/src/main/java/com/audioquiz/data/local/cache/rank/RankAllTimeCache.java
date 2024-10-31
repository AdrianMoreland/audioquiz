package com.audioquiz.data.local.cache.rank;

import com.audioquiz.api.datasources.rank.RankAllTimeLocal;
import com.audioquiz.core.model.rank.RankEntry;
import com.audioquiz.data.local.dao.rank.RankAllTimeDao;
import com.audioquiz.data.local.entity.rank_stats.RankAllTimeEntity;
import com.audioquiz.data.local.mapper.DatabaseMapper;
import com.audioquiz.data.local.provider.AppDatabase;

import java.util.List;

import javax.inject.Inject;

public class RankAllTimeCache implements RankAllTimeLocal {
    private final RankAllTimeDao rankAllTimeDao;

    @Inject
    public RankAllTimeCache(AppDatabase appDatabase) {
        this.rankAllTimeDao = appDatabase.rankAllTimeDao();
    }

    @Override
    public List<RankEntry> getRankAllTime() {
        List<RankAllTimeEntity> rankAllTimeEntityList = rankAllTimeDao.getAllRankEntries();
        List<RankEntry> rankEntryList = null;
        for (RankAllTimeEntity rankAllTimeEntity : rankAllTimeEntityList) {
            RankEntry rankEntry = mapToDomain(rankAllTimeEntity);
            rankEntryList.add(rankEntry);
        }
        return rankEntryList;
    }

    @Override
    public void insert(RankEntry rankEntry) {
        RankAllTimeEntity rankAllTimeEntity = DatabaseMapper.map(rankEntry, RankAllTimeEntity.class);
        rankAllTimeDao.insert(rankAllTimeEntity);
    }

    @Override
    public void insertAll(List<RankEntry> rankEntries) {
        for (RankEntry rankEntry : rankEntries) {
            RankAllTimeEntity rankAllTimeEntity = mapToEntity(rankEntry);
            rankAllTimeDao.insert(rankAllTimeEntity);
        }
    }

    private RankAllTimeEntity mapToEntity(RankEntry domain) {
        return DatabaseMapper.map(domain, RankAllTimeEntity.class);
    }

    private RankEntry mapToDomain(RankAllTimeEntity entity) {
        return DatabaseMapper.map(entity, RankEntry.class);
    }
}