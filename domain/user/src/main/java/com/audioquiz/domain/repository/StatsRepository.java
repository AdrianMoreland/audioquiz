package com.adrian.domain.user;

import com.adrian.model.stats.UserStats;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface StatsRepository {

    void init();
     Completable saveUserStats(UserStats userStatistics);
     Completable deleteUserStats(String userId);

    Single<Boolean> sync();

    Single<UserStats> getUserStatsSingle();


    int getUserLives(UserLivesObserver observer);

    interface UserLivesObserver {
        void onUserLivesChanged(int userLives);
        void onError(Exception e);
    }

}
