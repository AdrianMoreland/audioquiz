package com.audioquiz.core.domain.repository.rank;

import com.audioquiz.core.model.rank.RankEntry;

import java.util.List;

public interface RankWeeklyRepository {

    void init();

    List<RankEntry> getRankWeeklyList();

    void flushUserProfileData();

    interface OnRankDataCallback {
        void onRankDataFetched(List<RankEntry> documents);
    }
}
