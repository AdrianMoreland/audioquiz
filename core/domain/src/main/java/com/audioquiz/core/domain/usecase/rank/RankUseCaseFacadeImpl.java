package com.audioquiz.core.domain.usecase.rank;


import com.audioquiz.core.domain.repository.rank.RankRepository;
import com.audioquiz.core.model.rank.RankEntry;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;


public class RankUseCaseFacadeImpl implements RankUseCaseFacade {
    private final RankRepository rankRepository;

    @Inject
    public RankUseCaseFacadeImpl(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @Override
    public void preloadRankEntries() {
        rankRepository.init();
    }

    @Override
    public List<RankEntry> updateRank() {
            return null;
    }

    @Override
    public Completable sync() {
        return null;
    }
}
