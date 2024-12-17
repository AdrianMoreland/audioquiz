package com.audioquiz.data.remote.service.rank;


import com.audioquiz.api.datasources.rank_weekly.RankWeeklyApi;
import com.audioquiz.core.model.rank.RankEntry;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class RankWeeklyService_Impl implements RankWeeklyApi {

    @Inject
    public RankWeeklyService_Impl() {
        //  Required empty public constructor
    }

    @Override
    public List<RankEntry> getRankWeekly() {
            return Collections.emptyList();
    }
}
