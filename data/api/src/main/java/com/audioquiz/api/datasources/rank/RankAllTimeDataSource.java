package com.audioquiz.api.datasources.rank;


import com.audioquiz.core.model.rank.RankEntry;

import java.util.List;

public interface RankAllTimeDataSource {

    interface Local {
        List<RankEntry> getRankAllTime ();
        void insert(RankEntry rankAllTimeEntity);
        void insertAll(List<RankEntry> rankEntries);
    }

    interface Remote {
        List<RankEntry> getRankAllTime();
    }


}