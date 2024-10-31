package com.audioquiz.navigation;

/*

import com.audioquiz.data.remote.datasource.BaseFirestoreDataSource;
import com.audioquiz.data.remote.dto.RankEntryDto;
import com.audioquiz.data.remote.mapper.NetworkMapper;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;

import com.audioquiz.domain.rank.repository.RankWeeklyRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WeeklyRankDataSource extends BaseFirestoreDataSource {
    private static final String TAG = "UserStatsDataSource";
    private final FirebaseFirestore mFirestore;

    @Inject
    public WeeklyRankDataSource(FirestoreProvider firestoreProvider,
                                AuthProvider authProvider) {
        super(firestoreProvider, authProvider);
        mFirestore = firestoreProvider.getFirestore();
        mFirestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .build());
    }

    @Override
    protected CollectionReference getCollection() {
        return mFirestore.collection("user_statistics");
    }

    public void fetchTopWeeklyRankedUsers(int limit, RankWeeklyRepository.OnRankDataCallback onRankDataFetched) {
        Query query = getCollection()
                .orderBy("last7DaysScoresDto.totalLast7Days", Query.Direction.DESCENDING)
                        .limit(limit);

        readQuery(query)
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    List<RankEntryDto> rankEntryDtoList = new ArrayList<>();
                    for (DocumentSnapshot document : documents) {
                        RankEntryDto rankEntryDto = document.toObject(RankEntryDto.class);
                        rankEntryDtoList.add(rankEntryDto);
                    }
                    List<RankEntry> rankEntryList = new ArrayList<>();
                    for (RankEntryDto rankEntryDto : rankEntryDtoList) {
                        rankEntryList.add(NetworkMapper.map(rankEntryDto, RankEntry.class));
                    }
                    onRankDataFetched.onRankDataFetched(rankEntryList);
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }
    public Task<QuerySnapshot> readQuery(Query query) {
        return query.get();
    }
}
*/
