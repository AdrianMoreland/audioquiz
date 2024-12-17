package com.audioquiz.data.remote.service.rank;


import com.audioquiz.api.datasources.rank.RankWeeklyDataSource;
import com.audioquiz.core.model.rank.RankEntry;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class RankWeeklyServiceImpl implements RankWeeklyDataSource.Remote {

    @Inject
    public RankWeeklyServiceImpl() {
        //  Required empty public constructor
    }

    @Override
    public List<RankEntry> getRankWeekly() {
            return Collections.emptyList();
    }
}