package com.audioquiz.data.repository.rank;


import com.audioquiz.api.datasources.rank.RankWeeklyDataSource;
import com.audioquiz.core.domain.repository.rank.RankWeeklyRepository;
import com.audioquiz.core.model.rank.RankEntry;

import java.util.List;

public class RankWeeklyRepositoryImpl implements RankWeeklyRepository {
    private final RankWeeklyDataSource.Local local;
    private final RankWeeklyDataSource.Remote remote;

    public RankWeeklyRepositoryImpl(RankWeeklyDataSource.Local local,
                                    RankWeeklyDataSource.Remote remote) {
        this.local = local;
        this.remote = remote;
    }

    @Override
    public void init() {
        // Do nothing
    }

    @Override
    public List<RankEntry> getRankWeeklyList() {
        List<RankEntry> rankWeeklyList = local.getRankWeekly();
        if (rankWeeklyList == null) {
            rankWeeklyList = remote.getRankWeekly();
            if (rankWeeklyList != null) {
                local.insertAll(rankWeeklyList);
            }
        }
        return rankWeeklyList;
    }

    @Override
    public void flushUserProfileData() {
        local.flushData();
    }
}
