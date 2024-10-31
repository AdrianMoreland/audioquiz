package com.audioquiz.data.local.cache.user_data;

import android.util.Log;

import com.audioquiz.api.datasources.user.UserProfileApi;
import com.audioquiz.api.datasources.user.UserProfileLocal;
import com.audioquiz.api.datasources.user_stats.stats.UserDataLocal;
import com.audioquiz.api.datasources.user_stats.stats_category.CategoryStatsLocal;
import com.audioquiz.api.datasources.user_stats.stats_frequency.StatsFrequencyLocal;
import com.audioquiz.api.datasources.user_stats.stats_general.GeneralStatsLocal;
import com.audioquiz.core.model.user.UserProfile;
import com.audioquiz.core.model.user.stats.CategoryStats;
import com.audioquiz.core.model.user.stats.FrequencyStats;
import com.audioquiz.core.model.user.stats.GeneralStats;
import com.audioquiz.core.model.user.stats.UserStats;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserDataCache implements UserDataLocal {
    private static final String TAG = "UserDataCache";
     private final UserProfileApi userProfileApi;
    private final UserProfileLocal userProfileLocal;
    private final GeneralStatsLocal generalStatsLocal;
    private final StatsFrequencyLocal statsFrequencyLocal;
    private final CategoryStatsLocal categoryStatsLocal;


    @Inject
    public UserDataCache(UserProfileApi userProfileApi,
                         UserProfileLocal userProfileLocal,
                         GeneralStatsLocal generalStatsLocal,
                         StatsFrequencyLocal statsFrequencyLocal,
                         CategoryStatsLocal categoryStatsLocal) {
         this.userProfileApi = userProfileApi;
        this.userProfileLocal = userProfileLocal;
        this.generalStatsLocal = generalStatsLocal;
        this.statsFrequencyLocal = statsFrequencyLocal;
        this.categoryStatsLocal = categoryStatsLocal;
    }

    @Override
    public Completable insertUserData(UserStats userStats) {
        return Completable.mergeArray(
                userProfileLocal.insert(
                        userStats.getUserProfile()
                ).subscribeOn(Schedulers.io()),

                generalStatsLocal.insertGeneralStatsLocal(
                        userStats.getGeneralStats()
                ).subscribeOn(Schedulers.io()),

                categoryStatsLocal.insert(
                        userStats.getCategoryStats()
                ).subscribeOn(Schedulers.io()),

                statsFrequencyLocal.insert(
                        userStats.getFrequencyStats()
                ).subscribeOn(Schedulers.io())
        );
    }

    @Override
    public Single<UserStats> getUserDataSingle() {
        Log.d(TAG, "getUserDataSingle called");
        return Single.zip(
                        getUserProfileSingle(),
                        getGeneralStatsSingle(),
                        getCategoryStatsSingle(),
                        getFrequencyStatsSingle(),
                        (userProfile, generalStats, categoryStats, frequencyStats) -> {
                            // Combine the results into a UserStats object
                            return new UserStats.Builder()
                                    .setUserId(userProfile != null ? getUserId() : null) // Handle null userProfile
                                    .setLastUpdated(new Date())
                                    .setUserProfile(userProfile)
                                    .setGeneralStats(generalStats).setCategoryStats(categoryStats)
                                    .setFrequencyStats(frequencyStats)
                                    .build();
                        }
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private String getUserId() {
        return null;
    }

    @Override
    public Completable saveUserStats(UserStats userStats) {
        return Completable.mergeArray(
                userProfileLocal.insert(
                        userStats.getUserProfile()
                ).subscribeOn(Schedulers.io()),

                generalStatsLocal.insertGeneralStatsLocal(
                        userStats.getGeneralStats()
                ).subscribeOn(Schedulers.io()),

                categoryStatsLocal.insert(
                        userStats.getCategoryStats()
                ).subscribeOn(Schedulers.io()),

                statsFrequencyLocal.insert(
                        userStats.getFrequencyStats()
                ).subscribeOn(Schedulers.io())
        );
    }

    @Override
    public CompletableSource deleteUserStats(String userId) {
        return null;
    }

    private Single<UserProfile> getUserProfileSingle() {
        Log.d(TAG, "getUserProfileSingle called");
        return userProfileLocal.getUserProfileSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

     private Single<GeneralStats> getGeneralStatsSingle() {
        Log.d(TAG, "getGeneralStatsLocal called");
        return generalStatsLocal.getGeneralStatsLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

     private Single<CategoryStats> getCategoryStatsSingle() {
        Log.d(TAG, "getCategoryStatsSingle called");
        return categoryStatsLocal.getCategoryStatsSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

     private Single<FrequencyStats> getFrequencyStatsSingle() {
        Log.d(TAG, "getFrequencyStatsSingle called");
        return statsFrequencyLocal.getFrequencyStatsSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
