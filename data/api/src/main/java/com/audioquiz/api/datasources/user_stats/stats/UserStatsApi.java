package com.audioquiz.api.datasources.user_stats.stats;

  import android.os.Bundle;

  import com.audioquiz.core.model.user.stats.UserStats;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserStatsApi {
    Single<UserStats> getUserStatsSingle();
    Completable createUserStatsInFirestore(UserStats userStats);
    Completable saveUserStats(UserStats userStats);

    void setupUserStatsListener(UserStatsCallback userStatsCallback);

    Completable deleteUserStats(String userId);

    void logEventAnalytics(String userStatsUpdated, Bundle bundle);

    Single<Boolean> checkUserStatsDocumentExists(String userId);

    interface CheckDocCompletionCallback {
        void onSuccess(boolean exists);
        void onError(Exception e);
    }

    interface UserStatsCallback {
        void onUserStatsChanged(UserStats documentSnapshot);
        void onUserStatsEmpty();
        void onError(Exception e);
    }

}
