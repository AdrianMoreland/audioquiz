package com.audioquiz.localdatasource.cache.rank;

import com.adrian.api.data.datasources.rank.RankAllTimeLocal;
import com.adrian.database.mapper.IDatabaseMapper;
import com.adrian.database.dao.rank.RankAllTimeDao;
import com.adrian.database.entity.QuizResultEntity;
import com.adrian.database.entity.rank_stats.RankAllTimeEntity;
import com.adrian.database.provider.AppDatabase;
import com.adrian.model.quiz.QuizResult;
import com.adrian.model.rank.RankEntry;

import java.util.List;

import javax.inject.Inject;

public class RankAllTimeCache implements RankAllTimeLocal {
    private RankAllTimeDao rankAllTimeDao;
    private final IDatabaseMapper mapper;

    @Inject
    public RankAllTimeCache(AppDatabase appDatabase,
                            IDatabaseMapper mapper) {
        this.rankAllTimeDao = appDatabase.rankAllTimeDao();
        this.mapper = mapper;
    }

    @Override
    public List<RankEntry> getRankAllTime() {
        List<RankAllTimeEntity> rankAllTimeEntityList = rankAllTimeDao.getAllRankEntries();
        List<RankEntry> rankEntryList = null;
        for (RankAllTimeEntity rankAllTimeEntity : rankAllTimeEntityList) {
            RankEntry rankEntry = mapper.mapToDomain(rankAllTimeEntity, RankEntry.class);
            rankEntryList.add(rankEntry);
        }
        return rankEntryList;
    }

    @Override
    public void insert(RankEntry rankEntry) {
        RankAllTimeEntity rankAllTimeEntity = mapper.mapFromDomain(rankEntry, RankAllTimeEntity.class);
        rankAllTimeDao.insert(rankAllTimeEntity);
    }

    @Override
    public void insertAll(List<RankEntry> rankEntries) {
        for (RankEntry rankEntry : rankEntries) {
            RankAllTimeEntity rankAllTimeEntity = mapper.mapFromDomain(rankEntry, RankAllTimeEntity.class);
            rankAllTimeDao.insert(rankAllTimeEntity);
        }
    }

    private QuizResultEntity mapToEntity(QuizResult domain) {
        return mapper.mapToDomain(domain, QuizResultEntity.class);
    }

    private QuizResult mapToDomain(QuizResultEntity entity) {
        return mapper.mapToDomain(entity, QuizResult.class);
    }
}