package com.audioquiz.core.domain.usecase.rank;


import com.audioquiz.core.model.rank.RankEntry;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public interface RankUseCaseFacade {
    void preloadRankEntries();

    List<RankEntry> updateRank();

    Completable sync();
}
