package com.audioquiz.data.remote.service.user_data;

import static com.audioquiz.data.remote.util.FirestoreConstants.RANK_COLLECTION;

import com.audioquiz.api.datasources.rank.RankAllTimeApi;
import com.audioquiz.api.datasources.rank_weekly.RankWeeklyApi;
import com.audioquiz.core.model.rank.RankEntry;
import com.audioquiz.data.remote.dto.RankEntryDto;
import com.audioquiz.data.remote.dto.UserProfileDto;
import com.audioquiz.data.remote.dto.UserStatsDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RankServiceDemo implements RankAllTimeApi, RankWeeklyApi {
    private static final String TAG = "RankService";
    private final List<RankEntry> rankEntries = new ArrayList<>();

    @Inject
    public RankServiceDemo() {
        // Required empty public constructor
    }

    @Override
    public List<RankEntry> getEntriesList() {
        if (rankEntries.isEmpty()) {
            for (int i = 0; i < 30; i++) {
                rankEntries.add(NetworkMapper.map(getRandomRankEntry(), RankEntry.class));
            }
        }
        return rankEntries;
    }


    @Override
    public List<RankEntry> getRankAllTime() {
        List<RankEntry> rankEntries = getEntriesList();
        rankEntries.sort((entry1, entry2) ->
                Integer.compare(
                        entry2.getTotalScore(),
                        entry1.getTotalScore()));
        return rankEntries;
    }

    @Override
    public List<RankEntry> getRankWeekly() {
        List<RankEntry> rankEntries = getEntriesList();
        rankEntries.sort((entry1, entry2) ->
                Integer.compare(
                        entry2.getWeeklyScore(),
                        entry1.getWeeklyScore()
                ));
        return rankEntries;
    }

    public RankEntryDto getRandomRankEntry() {
        int totalScore = (int) (Math.random() * 5000);
        int numberOfEntries = (int) Math.random() * 450;
        return new RankEntryDto(
                StringUtils.getRandomText(10),
                StringUtils.getRandomUsernameString(),
                StringUtils.getRandomUsernameString(),
                "https://example.com/profile/",
                totalScore,
                totalScore / ((((int) Math.random()) * 450) * 100),
                (((int) Math.random()) * 500);
        );
    }
}
