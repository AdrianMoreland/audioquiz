package com.audioquiz.core.domain.repository.rank;

import com.audioquiz.core.model.rank.RankEntry;

import java.util.List;

public interface RankRepository {

    void init();

    List<RankEntry> getRankAllTimeList();

    interface OnRankDataCallback {
        void onRankDataFetched(List<RankEntry> documents);
    }
}
