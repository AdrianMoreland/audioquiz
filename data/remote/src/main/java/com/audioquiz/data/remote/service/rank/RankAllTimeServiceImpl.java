package com.audioquiz.data.remote.service.rank;

import com.audioquiz.api.datasources.rank.RankAllTimeDataSource;
import com.audioquiz.core.model.rank.RankEntry;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class RankAllTimeServiceImpl implements RankAllTimeDataSource.Remote {

    @Inject
    public RankAllTimeServiceImpl() {
        // Required empty public constructor
    }

    @Override
    public List<RankEntry> getRankAllTime() {
        return Collections.emptyList();
    }
}
