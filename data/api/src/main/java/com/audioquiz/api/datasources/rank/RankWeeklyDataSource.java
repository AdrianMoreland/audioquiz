package com.audioquiz.api.datasources.rank;


import com.audioquiz.core.model.rank.RankEntry;

import java.util.List;

public interface RankWeeklyDataSource {

    interface Local {
        List<RankEntry> getRankWeekly ();
        void insert(RankEntry rankWeeklyEntity);
        void insertAll(List<RankEntry> rankEntries);
        void flushData();
    }

    interface Remote {
        List<RankEntry> getRankWeekly();

    }



}