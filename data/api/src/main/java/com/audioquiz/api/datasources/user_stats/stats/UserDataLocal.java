package com.audioquiz.api.datasources.user_stats.stats;

import com.audioquiz.core.model.user.stats.UserStats;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Single;

public interface UserDataLocal {
    Completable insertUserData(UserStats userStats);

    Single<UserStats> getUserDataSingle();

    Completable saveUserStats(UserStats userStats);

    CompletableSource deleteUserStats(String userId);
}
