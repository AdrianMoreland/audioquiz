package com.audioquiz.data.remote.service.user_data;

import static com.audioquiz.data.remote.util.FirestoreConstants.FREQUENCY_STATS_COLLECTION;

import android.util.Log;

import com.audioquiz.api.datasources.user_stats.FrequencyStatsDataSource;
import com.audioquiz.core.model.user.stats.FrequencyStats;
import com.audioquiz.core.model.user.stats.PitchStats;
import com.audioquiz.data.remote.datasource.FirestoreDataSource;
import com.audioquiz.data.remote.dto.FrequencyStatsDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FrequencyStatsService implements FrequencyStatsDataSource.Remote {
    private static final String TAG = "FrequencyStatsService";
    private static final String USER_DATA_COLLECTION = "user_data";
    private static final String FREQUENCY_STATS_DOCUMENT = "frequency_stats";
    private final FirestoreDataSource<FrequencyStats> firestoreDataSource;
    private final FirestoreDataSource<PitchStats> firestoreDataSourcePitch;

    @Inject
    public FrequencyStatsService(FirestoreDataSource<FrequencyStats> firestoreDataSource,
                                 FirestoreDataSource<PitchStats> firestoreDataSourcePitch) {
        this.firestoreDataSource = firestoreDataSource;
        this.firestoreDataSourcePitch = firestoreDataSourcePitch;
    }

    public Single<FrequencyStats> getFrequencyStats() {
        return Single.<FrequencyStats>create(emitter ->
                        firestoreDataSource.getDocument(FREQUENCY_STATS_COLLECTION)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful() && task.getResult() != null) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        handleDocumentSnapshot(documentSnapshot, emitter);
                                    } else {
                                        emitter.onError(task.getException());
                                    }
                                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void handleDocumentSnapshot(DocumentSnapshot documentSnapshot, SingleEmitter<FrequencyStats> emitter) {
        if (documentSnapshot.exists()) {
            FrequencyStatsDto dto = documentSnapshot.toObject(FrequencyStatsDto.class);
            if (dto != null) {
                emitter.onSuccess(mapToDomain(dto));
            } else {
                emitter.onError(new NullPointerException("FrequencyStats object is null"));
            }
        } else {
            emitter.onError(new RuntimeException("No FrequencyStats document found"));
        }
    }

    // MAPPER
    private FrequencyStats mapToDomain(FrequencyStatsDto frequencyStatsDto) {
        return NetworkMapper.map(frequencyStatsDto, FrequencyStats.class);
    }


    public Completable saveFrequencyStats(FrequencyStats frequencyStats) {
        Log.d(TAG, "saveFrequencyStats() called with: frequencyStats = [" + frequencyStats + "]");
        return Completable.create(emitter -> firestoreDataSource.setDocument(
                                FREQUENCY_STATS_COLLECTION,
                                frequencyStats)
                        .addOnSuccessListener(aVoid -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public @NonNull Completable saveFrequencyStatsData(PitchStats statsData) {
        Log.d(TAG, "saveFrequencyStats() called with: frequencyStats = [" + statsData + "]");
        return Completable.create(emitter -> {
                    Object frequencyStats = getFrequencyStats().blockingGet();
                    Map<String, Object> pitchScoresMap = new HashMap<>();
                    String frequency = statsData.getFrequency();
                    pitchScoresMap.put(frequency, frequencyStats);
                    firestoreDataSourcePitch.updateSubDocumentMap(
                                    USER_DATA_COLLECTION,
                                    FREQUENCY_STATS_DOCUMENT,
                                    frequency,
                                    statsData)
                            .addOnSuccessListener(aVoid -> emitter.onComplete())
                            .addOnFailureListener(emitter::onError);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteFrequencyStats() {
        Log.d(TAG, "deleteFrequencyStats() called");
        return Completable.create(emitter ->
                        firestoreDataSource.deleteDocument(FREQUENCY_STATS_COLLECTION)
                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
