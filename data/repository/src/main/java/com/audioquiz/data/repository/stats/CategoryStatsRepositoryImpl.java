package com.audioquiz.data.repository.stats;

import android.content.Context;
import android.util.Log;

import com.audioquiz.api.datasources.user_stats.CategoryStatsDataSource;
import com.audioquiz.api.exceptions.DataNotFoundTypeException;
import com.audioquiz.api.util.NetworkUtils;
import com.audioquiz.core.domain.repository.user.CategoryStatsRepository;
import com.audioquiz.core.model.user.stats.CategoryStats;
import com.audioquiz.core.model.user.stats.CategoryStatsData;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryStatsRepositoryImpl implements CategoryStatsRepository {
    private static final String TAG = "CategoryStatsRepository";
    private static final long CACHE_INVALIDATION_TIME = 5 * 60 * 1000;

    private final CategoryStatsDataSource.Remote remote;
    private final CategoryStatsDataSource.Local local;
    private final Context context;


    @Inject
    public CategoryStatsRepositoryImpl(Context context, CategoryStatsDataSource.Remote remote, CategoryStatsDataSource.Local local) {
        this.context = context;
        this.remote = remote;
        this.local = local;
    }

    public Completable init() {
        Log.d(TAG, "init: ");
        // Sync data on initialization
        return Completable.fromAction(() -> getCategoryStats().subscribe())
                .subscribeOn(Schedulers.io());
    }


    @Override
    public Single<CategoryStats> getCategoryStats() {
        return local.getCategoryStatsSingle()
                .flatMap(cachedStats -> {
                    // Always serve cached data first
                    if (NetworkUtils.isNetworkAvailable(context)) {
                        return checkStaleWhileRevalidate(cachedStats);
                    } else {
                        // No network, serve only cached data
                        Log.d(TAG, "No network, serving cached data.");
                        return Single.just(cachedStats);
                    }
                })
                .onErrorResumeNext(error -> fetchFreshCategoryStatsFromApi()) // Handle cache failure by fetching fresh data
                .subscribeOn(Schedulers.io()) // Ensure operations are on a background thread
                .observeOn(AndroidSchedulers.mainThread()); // Return results on main thread
    }

    /**
     * Check whether the cached data is stale and revalidate in the background.
     * Serve the cached data immediately, while fetching fresh data asynchronously.
     */
    private Single<CategoryStats> checkStaleWhileRevalidate(CategoryStats cachedStats) {
        if (isCacheValid(cachedStats)) {
            // Cache is valid, serve the data and fetch fresh in the background
            refreshCacheInBackground();
            return Single.just(cachedStats);
        } else {
            // Cache is expired, fetch fresh data
            return fetchFreshCategoryStatsFromApi();
        }
    }

    /**
     * Helper method to fetch fresh stats from Firestore and save them to local cache.*
     * Fetch fresh data from API and update the local cache.
     */
    private Single<CategoryStats> fetchFreshCategoryStatsFromApi() {
        return remote.getCategoryStats()
                .onErrorResumeNext(error -> {
                    if (error instanceof DataNotFoundTypeException) {
                        // Document doesn't exist, create it
                        CategoryStats defaultStats  = createDefaultData(((DataNotFoundTypeException) error).getDocId());
                        return remote.saveCategoryStats(defaultStats)
                                .andThen(Single.just(defaultStats ));
                    } else {
                        // Other error, re-throw
                        return Single.error(error);
                    }
                })
                .flatMap(freshStats ->
                        saveCategoryStatsLocally(freshStats)
                                .toSingleDefault(freshStats))
                .subscribeOn(Schedulers.io());
    }

    private CategoryStats createDefaultData(String userId) {
        return CategoryStats.createDefault(userId);
    }


    /**
     * Check if the cached data is still valid based on both time and server-side freshness.
     */
    private boolean isCacheValid(CategoryStats cachedStats) {
        long currentTime = new Date().getTime();
        long cachedTime = cachedStats.getLastUpdated().getTime();

        // Ensure cached data is within allowed local cache time
        boolean isTimeValid = currentTime - cachedTime <= CACHE_INVALIDATION_TIME;
        // Alternative check for freshness (modify as per available properties)
        boolean isDataValid = cachedStats.getAllCategoryStatsData() != null;

        // Check if the server's metadata (e.g., lastUpdated from API) indicates freshness
        return isTimeValid && isDataValid;
    }

    /**
     * Background task to refresh cache.
     */
    private void refreshCacheInBackground() {
        Disposable disposable = fetchFreshCategoryStatsFromApi()
                .subscribe(freshStats -> {
                    Log.d(TAG, "Cache refreshed with fresh data.");
                }, throwable -> {
                    Log.e(TAG, "Failed to refresh cache.", throwable);
                });

        // Optionally log or manage the disposable if needed
        Log.d(TAG, "Subscription created: " + disposable);
    }

    @Override
    public Single<CategoryStatsData> getCategoryStatsData(String category) {
        Log.d(TAG, "getCategoryStatsData for category: " + category);
        return getCategoryStats().map(categoryStats ->
                categoryStats.getAllCategoryStatsData().get(category));
    }

    @Override
    public void sync() {

    }

    @Override
    public Completable saveCategoryStats(CategoryStats categoryStats) {
        Log.d(TAG, "saveCategoryStats: ");
        return local.insert(categoryStats)
                .andThen(remote.saveCategoryStats(categoryStats));
    }

    private Completable saveCategoryStatsLocally(CategoryStats categoryStats) {
        Log.d(TAG, "saveCategoryStatsLocally: ");
        return local.insert(categoryStats);
    }

    @Override
    public Completable deleteCategoryStats() {
        Log.d(TAG, "deleteCategoryStats: ");
        return local.deleteCategoryStatsLocal()
                .andThen(remote.deleteCategoryStats());
    }


}
