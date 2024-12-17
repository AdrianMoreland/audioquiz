package com.audioquiz.data.remote.service.user_data;

import static com.audioquiz.data.remote.util.FirestoreConstants.GENERAL_STATS_COLLECTION;
import static com.audioquiz.data.remote.util.FirestoreConstants.GENERAL_STATS_DOCUMENT;

import android.util.Log;

import com.audioquiz.api.datasources.user_stats.GeneralStatsDataSource;
import com.audioquiz.core.model.user.stats.GeneralStats;
import com.audioquiz.data.remote.datasource.FirestoreDataSource;
import com.audioquiz.data.remote.dto.GeneralStatsDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GeneralStatsService implements GeneralStatsDataSource.Remote {
    private static final String TAG = "GeneralStatsService";
    private static final String DOCUMENT = GENERAL_STATS_DOCUMENT;
    private final FirestoreDataSource<GeneralStats> firestoreDataSource;

    @Inject
    public GeneralStatsService(FirestoreDataSource<GeneralStats> firestoreDataSource) {
        this.firestoreDataSource = firestoreDataSource;
    }

    @Override
    public Observable<GeneralStats> observeGeneralStats() {
        return Observable.<GeneralStats>create(emitter -> {
                    firestoreDataSource.getDocument(GENERAL_STATS_COLLECTION)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    handleDocumentSnapshot(documentSnapshot, (SingleEmitter<GeneralStats>) emitter);
                                } else {
                                    emitter.onError(task.getException());
                                }
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private void handleDocumentSnapshot(DocumentSnapshot documentSnapshot, SingleEmitter<GeneralStats> emitter) {
        if (documentSnapshot.exists()) {
            GeneralStatsDto dto = documentSnapshot.toObject(GeneralStatsDto.class);
            if (dto != null) {
                emitter.onSuccess(mapToDomain(dto));
            } else {
                emitter.onError(new NullPointerException("CategoryStats object is null"));
            }
        } else {
            emitter.onError(new RuntimeException("No CategoryStats document found"));
        }
    }

    // MAPPER
    private GeneralStats mapToDomain(GeneralStatsDto generalStatsDto) {
        return NetworkMapper.map(generalStatsDto, GeneralStats.class);
    }


    public Single<GeneralStats> getGeneralStats() {
        Log.d(TAG, "getGeneralStats() called");
        return Single.<GeneralStats>create(emitter -> {
                    firestoreDataSource.getDocument(GENERAL_STATS_COLLECTION)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    handleDocumentSnapshot(documentSnapshot, emitter);
                                } else {
                                    emitter.onError(task.getException());
                                }
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable saveGeneralStats(GeneralStats generalStats) {
        Log.d(TAG, "saveGeneralStats() called with: generalStats = [" + generalStats + "]");
        return Completable.create(emitter -> {
                    firestoreDataSource.setDocument(
                                    GENERAL_STATS_COLLECTION,
                                    generalStats)
                            .addOnSuccessListener(aVoid -> emitter.onComplete())
                            .addOnFailureListener(emitter::onError);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteGeneralStats() {
        Log.d(TAG, "deleteGeneralStatsLocal() called");
        return Completable.create(emitter ->
                        firestoreDataSource.deleteDocument(GENERAL_STATS_COLLECTION)
                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}