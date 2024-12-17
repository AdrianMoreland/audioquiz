package com.audioquiz.data.remote.service.rank;

import com.audioquiz.api.datasources.rank.RankAllTimeApi;
import com.audioquiz.core.model.rank.RankEntry;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class RankServiceDemo implements RankAllTimeApi, RankWeeklyApi {

    @Inject
    public RankAllTimeService_Impl() {
        // Required empty public constructor
    }

    @Override
    public List<RankEntry> getRankAllTime() {
        return Collections.emptyList();
    }
}
