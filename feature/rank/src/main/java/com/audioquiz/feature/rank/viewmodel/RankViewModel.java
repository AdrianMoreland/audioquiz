package com.audioquiz.feature.rank.viewmodel;

import androidx.lifecycle.ViewModel;

import com.audioquiz.core.domain.usecase.rank.RankUseCaseFacade;
import com.audioquiz.core.model.rank.RankEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RankViewModel extends ViewModel {
    private final RankUseCaseFacade rankUseCaseFacade;
    private final List<RankEntry> rankEntries = new ArrayList<>();
    private List<RankEntry> rankAllEntries;
    private List<RankEntry> rankWeeklyEntries;



    @Inject
    public RankViewModel(RankUseCaseFacade rankUseCaseFacade) {
        this.rankUseCaseFacade = rankUseCaseFacade;
        this.rankAllEntries = new ArrayList<>();
        this.rankWeeklyEntries = new ArrayList<>();
        init();
    }

    private void init (){
     //   userStatisticsUpdaterRepository.generateUserMockValues();
        updateRanks();
        this.rankAllEntries = new ArrayList<>();
        rankWeeklyEntries = new ArrayList<>();
    }

    public void updateRanks() {
        List<RankEntry> result = rankUseCaseFacade.updateRank();
        //   rankAllEntries = result.get(0);
        // rankWeeklyEntries = result.get(1);
    }

    public List<RankEntry> getRankEntries() {
        return rankAllEntries;
    }

    public List<RankEntry> getRankWeeklyEntries() {
        return rankWeeklyEntries;
    }
}