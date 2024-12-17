package com.audioquiz.data.remote.service.user_data;

import static com.audioquiz.data.remote.util.FirestoreConstants.CATEGORY_STATS_COLLECTION;

import android.util.Log;

import com.audioquiz.api.datasources.user_stats.CategoryStatsDataSource;
import com.audioquiz.api.exceptions.DataNotFoundTypeException;
import com.audioquiz.core.model.user.stats.CategoryStats;
import com.audioquiz.data.remote.datasource.FirestoreDataSource;
import com.audioquiz.data.remote.dto.CategoryStatsDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryStatsService implements CategoryStatsDataSource.Remote {
    private static final String TAG = "CategoryStatsService";
    private final FirestoreDataSource<CategoryStats> firestoreDataSource;

    @Inject
    public CategoryStatsService(FirestoreDataSource<CategoryStats> firestoreDataSource) {
        this.firestoreDataSource = firestoreDataSource;
    }

    @Override
    public Single<CategoryStats> getCategoryStats() {
        Log.d(TAG, "getCategoryStats: " + CATEGORY_STATS_COLLECTION);
        return Single.<CategoryStats>create(emitter ->
                        firestoreDataSource.getDocument(
                                        CATEGORY_STATS_COLLECTION)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful() && task.getResult() != null) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        Log.d(TAG, "getCategoryStats: documentSnapshot: " + documentSnapshot);
                                        handleDocumentSnapshot(documentSnapshot, emitter);
                                    } else {
                                        // Check and propagate the exact exception
                                        Exception exception = task.getException();
                                        if (exception instanceof DataNotFoundTypeException) {
                                            Log.e(TAG, "getCategoryStats: DataNotFoundTypeException: " + exception.getMessage());
                                            emitter.onError(exception);  // Custom exception is propagated here
                                        } else {
                                            emitter.onError(new RuntimeException("An unexpected error occurred", exception));
                                        }
                                    }
                                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void handleDocumentSnapshot(DocumentSnapshot documentSnapshot, SingleEmitter<CategoryStats> emitter) {
        if (documentSnapshot.exists()) {
            CategoryStatsDto dto = documentSnapshot.toObject(CategoryStatsDto.class);
            if (dto != null) {
                Log.d(TAG, "handleDocumentSnapshot: dto: " + dto);
                emitter.onSuccess(mapToDomain(dto));
            } else {
                emitter.onError(new NullPointerException("CategoryStats object is null"));
            }
        } else {
            emitter.onError(new RuntimeException("No CategoryStats document found"));
        }
    }

    @Override
    public Completable saveCategoryStats(CategoryStats categoryStats) {
        Log.d(TAG, "saveCategoryStats: saving stats for categories: " + categoryStats.getAllCategoryStatsData().keySet());
        return Completable.create(emitter ->
                        firestoreDataSource.setDocument(
                                        CATEGORY_STATS_COLLECTION,
                                        categoryStats)
                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable deleteCategoryStats() {
        Log.d(TAG, "deleteCategoryStatsLocal() called");
        return Completable.create(emitter ->
                        firestoreDataSource
                                .deleteDocument(CATEGORY_STATS_COLLECTION)
                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // MAPPER
    private CategoryStats mapToDomain(CategoryStatsDto categoryStatsDto) {
        return NetworkMapper.map(categoryStatsDto, CategoryStats.class);
    }

}
