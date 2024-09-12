package com.audioquiz.localdatasource.cache.user_data;

import android.util.Log;

import com.adrian.api.data.datasources.firebase_auth.AuthApi;
import com.adrian.api.data.datasources.user_stats.stats_category.CategoryStatsLocal;
import com.adrian.database.mapper.IDatabaseMapper;
import com.adrian.database.dao.user_data.CategoryStatsDao;
import com.adrian.database.entity.user_data.CategoryStatsEntity;
import com.adrian.database.provider.AppDatabase;
import com.adrian.model.stats.CategoryStats;
import com.adrian.model.stats.CategoryStatsData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryStatsCache implements CategoryStatsLocal {
    private static final String TAG = "CategoryStatsCache";
    private final CategoryStatsDao categoryStatsDao;
    private final AuthApi authApi;
    private final IDatabaseMapper mapper;

    @Inject
    public CategoryStatsCache(AppDatabase appDatabase,
                              AuthApi authApi,
                              IDatabaseMapper mapper) {
        this.categoryStatsDao = appDatabase.categoryStatsDao();
        this.authApi = authApi;
        this.mapper = mapper;
    }

    private String getUserId() {
        return authApi.getFirebaseUserId();
    }

    @Override
    public Completable insert(CategoryStats categoryStats) {
        return categoryStatsDao.insertCompletable(mapToEntity(categoryStats))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable insertAll(List<CategoryStats> categoryStatsList) {
        return null;
    }

    @Override
    public Single<CategoryStats> getCategoryStatsSingle() {
        Log.d(TAG, "getCategoryStatsSingle: ");
        return categoryStatsDao.getCategoryStatsDataList()
                .map(this::mapToCategoryStats)
                .subscribeOn(Schedulers.io());
    }

    private CategoryStats mapToCategoryStats(List<CategoryStatsEntity> entityList) {
        Log.d(TAG, "mapToCategoryStats: " + entityList);
        CategoryStats categoryStats = CategoryStats.createDefault(getUserId());

        for (CategoryStatsEntity entity : entityList) {
            CategoryStatsData categoryStatsData = new CategoryStatsData.Builder()
                    .setCategoryIndex(entity.getCategoryIndex())
                    .setLastUpdated(entity.getLastUpdated())
                    .setCategoryName(entity.getCategoryName())
                    .setCurrentChapter(entity.getCurrentChapter())
                    .setNumberOfQuizzes(entity.getNumberOfQuizzes())
                    .setTotalQuestionsLearn(entity.getTotalQuestionsLearn())
                    .setCorrectAnswersLearn(entity.getCorrectAnswersLearn())
                    .setTotalQuestionsCompetitive(entity.getTotalQuestionsCompetitive())
                    .setCorrectAnswersCompetitive(entity.getCorrectAnswersCompetitive())
                    .setTotalTimeSpent(entity.getTotalTimeSpent())
                    .build();

            categoryStats.setCategoryStatsData(categoryStatsData.getCategoryName(), categoryStatsData);  // Assuming CategoryStats has an add method
        }
        Log.d(TAG, "mapToCategoryStats: " + categoryStats);
        return categoryStats;
    }



    @Override
    public void flushData() {

    }

    @Override
    public Completable deleteCategoryStatsLocal() {
        return null;
    }

    private CategoryStatsData mapToDomain (CategoryStatsEntity categoryStatsEntity) {
        return mapper.mapToDomain(categoryStatsEntity, CategoryStatsData.class);
    }

    private CategoryStatsEntity mapToEntity (CategoryStats domain) {
        return mapper.mapFromDomain(domain, CategoryStatsEntity.class);
    }



    // After
    public Single<CategoryStats> getCategoryStatsSingle2() {
        return Single.fromCallable(() -> new CategoryStats.Builder()
                        .setSoundWavesStats(mapToDomain(categoryStatsDao.getCategoryStatsByCategoryName("sound_waves")))
                        .setSynthesisStats(mapToDomain(categoryStatsDao.getCategoryStatsByCategoryName("synthesis")))
                        .setProductionStats(mapToDomain(categoryStatsDao.getCategoryStatsByCategoryName("production")))
                        .setProcessingStats(mapToDomain(categoryStatsDao.getCategoryStatsByCategoryName("processing")))
                        .setMixingStats(mapToDomain(categoryStatsDao.getCategoryStatsByCategoryName("mixing")))
                        .setMusicTheoryStats(mapToDomain(categoryStatsDao.getCategoryStatsByCategoryName("music_theory")))
                        .build())
                .subscribeOn(Schedulers.io()) // Perform database access on IO thread
                .observeOn(AndroidSchedulers.mainThread()); // Observe on main thread (if transformation is light)
    }

}