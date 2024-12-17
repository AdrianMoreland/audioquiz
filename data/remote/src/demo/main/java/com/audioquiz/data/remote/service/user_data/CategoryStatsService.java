package com.audioquiz.data.remote.service.user_data;

import static com.audioquiz.data.remote.util.FirestoreConstants.CATEGORY_STATS_DOCUMENT;
import static com.audioquiz.data.remote.util.FirestoreConstants.USER_DATA_COLLECTION;

import android.util.Log;

import com.audioquiz.api.datasources.user_stats.stats_category.CategoryStatsApi;
import com.audioquiz.data.remote.datasource.FirestoreDataSource;
import com.audioquiz.data.remote.dto.CategoryStatsDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.audioquiz.core.model.user.stats.CategoryStats;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryStatsService implements CategoryStatsApi {
    private static final String TAG = "CategoryStatsService";

    @Inject
    public CategoryStatsService(FirestoreDataSource<CategoryStats> firestoreDataSource) {
        this.firestoreDataSource = firestoreDataSource;
    }

    @Override
    public Single<CategoryStats> getCategoryStats() {
        Log.d(TAG, "getCategoryStats: ");
        return Single.<CategoryStats>create(emitter ->
                        firestoreDataSource.getDocumentSnapshot(
                                        USER_DATA_COLLECTION,
                                        CATEGORY_STATS_DOCUMENT)
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

    private void handleDocumentSnapshot(DocumentSnapshot documentSnapshot, SingleEmitter<CategoryStats> emitter) {
        if (documentSnapshot.exists()) {
            CategoryStatsDto dto = documentSnapshot.toObject(CategoryStatsDto.class);
            if (dto != null) {
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
                                        USER_DATA_COLLECTION,
                                        CATEGORY_STATS_DOCUMENT, categoryStats)
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
                                .deleteDocument(USER_DATA_COLLECTION, CATEGORY_STATS_DOCUMENT)
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
