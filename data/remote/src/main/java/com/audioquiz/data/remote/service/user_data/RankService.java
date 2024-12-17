package com.audioquiz.data.remote.service.user_data;

import static com.audioquiz.data.remote.util.FirestoreConstants.RANK_COLLECTION;

import com.audioquiz.api.datasources.rank.RankAllTimeDataSource;
import com.audioquiz.api.datasources.rank.RankWeeklyDataSource;
import com.audioquiz.core.model.rank.RankEntry;
import com.audioquiz.data.remote.datasource.FirestoreDataSource;
import com.audioquiz.data.remote.dto.RankEntryDto;
import com.audioquiz.data.remote.dto.UserProfileDto;
import com.audioquiz.data.remote.dto.UserStatsDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RankService implements RankAllTimeDataSource.Remote, RankWeeklyDataSource.Remote {
    private static final String TAG = "RankService";
    private final FirestoreDataSource<RankEntryDto> firestoreDataSource;

    @Inject
    public RankService(FirestoreDataSource<RankEntryDto> firestoreDataSource) {
        this.firestoreDataSource = firestoreDataSource;
    }

    @Override
    public List<RankEntry> getRankAllTime() {
        return fetchTopRankedUsers(30);
    }

    @Override
    public List<RankEntry> getRankWeekly() {
        return fetchTopWeeklyRankedUsers(30);
    }

    public List<RankEntry> fetchTopRankedUsers(int limit) {
        List<RankEntry> rankEntries = new ArrayList<>();

        firestoreDataSource.getQuery(RANK_COLLECTION,
                        firestoreDataSource.getFirebaseUserId(),
                        limit,
                        "totalScore",
                        Query.Direction.DESCENDING).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                    List<RankEntryDto> rankEntriesDtoList = new ArrayList<>();
                    List<UserStatsDto> userStatsDtoList = new ArrayList<>();
                    for (DocumentSnapshot document : documents) {
                        UserStatsDto userStatsDto = document.toObject(UserStatsDto.class);
                        UserProfileDto userProfileDto = document.toObject(UserProfileDto.class);
                        RankEntryDto rankEntryDto = convertUserStatsDtoToRankEntryDto(userStatsDto, userProfileDto);
                        rankEntries.add(NetworkMapper.map(rankEntryDto, RankEntry.class));
                    }
                })
                .addOnFailureListener(e -> {

                });
        return rankEntries;
    }


    public List<RankEntry> fetchTopWeeklyRankedUsers(int limit) {
        List<RankEntry> rankEntryList = new ArrayList<>();
        readQuery(firestoreDataSource.getQuery(RANK_COLLECTION,
                        firestoreDataSource.getFirebaseUserId(),
                        limit,
                        "last7DaysScoresDto.totalLast7Days",
                        Query.Direction.DESCENDING))
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    List<RankEntryDto> rankEntryDtoList = new ArrayList<>();
                    for (DocumentSnapshot document : documents) {
                        RankEntryDto rankEntryDto = document.toObject(RankEntryDto.class);
                        rankEntryDtoList.add(rankEntryDto);
                    }
                    for (RankEntryDto rankEntryDto : rankEntryDtoList) {
                        rankEntryList.add(NetworkMapper.map(rankEntryDto, RankEntry.class));
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
        return rankEntryList;
    }

    public Task<QuerySnapshot> readQuery(Query query) {
        return query.get();
    }

    public RankEntryDto convertUserStatsDtoToRankEntryDto(UserStatsDto userStatsDto, UserProfileDto userProfileDto) {
        String userId = userProfileDto.getUserId();
        String username = userProfileDto.getUsername();
        String profileImage = userProfileDto.getProfileImage();
        double averageScore = userStatsDto.getGeneralStatsDto().getAverageScore();
        int totalScore = userStatsDto.getGeneralStatsDto().getTotalScore();
        return new RankEntryDto(userId, username, profileImage, totalScore, averageScore);
    }


}
