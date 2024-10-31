package com.audioquiz.navigation;
/*

import com.audioquiz.data.remote.dto.RankEntryDto;
import com.audioquiz.data.remote.dto.UserProfileDto;
import com.audioquiz.data.remote.dto.UserStatsDto;
import com.audioquiz.data.remote.mapper.NetworkMapper;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AllTimeRankDataSource {
    private static final String TAG = "UserStatsDataSource";
    private final FirestoreProvider firestoreProvider;

    @Inject
    public AllTimeRankDataSource(FirestoreProvider firestoreProvider,
                                 AuthProvider authProvider) {
        this.firestoreProvider = firestoreProvider;
        firestoreProvider.getFirestore().setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .build());
    }

    protected CollectionReference getCollection() {
        return firestoreProvider.getFirestore().collection("user_statistics");
    }

    public void fetchTopRankedUsers(int limit, RankRepository.OnRankDataCallback onRankDataFetched) {
        firestoreProvider.getFirestore().collection("user_statistics")
                .orderBy("totalScore", Query.Direction.DESCENDING)
                .limit(limit)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                    List<RankEntryDto> rankEntriesDtoList = new ArrayList<>();
                    List<UserStatsDto> userStatsDtoList= new ArrayList<>();
                    List<RankEntry> rankEntries = new ArrayList<>();
                    for (DocumentSnapshot document : documents) {
                        userStatsDtoList.add(NetworkMapper.map(document, UserStatsDto.class));
                    }
                    for (RankEntryDto rankEntryDto : rankEntriesDtoList) {
                        rankEntries.add(NetworkMapper.map(rankEntryDto, RankEntry.class));
                    }
                    onRankDataFetched.onRankDataFetched(rankEntries);
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }

    public RankEntryDto convertUserStatsDtoToRankEntryDto(UserStatsDto userStatsDto, UserProfileDto userProfileDto) {
        String userId = userProfileDto.getUserId();
        String username = userProfileDto.getUsername();
        String profileImage = userProfileDto.getProfileImage();
        double averageScore = userStatsDto.getGeneralStatsDto().getAverageScore();
        int totalScore = userStatsDto.getGeneralStatsDto().getTotalScore();
        return new RankEntryDto(userId, username, profileImage, totalScore, averageScore);
    }


*/
/*


    public void fetchTopRankedUsers(int limit, RankRepository.
            OnRankDataCallback onRankDataFetched) {
        Query query = getCollection()
                .orderBy("totalScore", Query.Direction.DESCENDING)
                .limit(limit);

        readQuery(query)
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    onRankDataFetched.onRankDataFetched(firebaseMapper.mapFromSnapshot(documents));
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }
*//*


    public Task<QuerySnapshot> readQuery(Query query) {
        return query.get();
    }
}
*/
