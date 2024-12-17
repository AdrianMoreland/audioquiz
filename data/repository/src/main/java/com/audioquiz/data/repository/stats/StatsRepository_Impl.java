package com.audioquiz.data.repository.stats;

import android.util.Log;

import androidx.core.util.Pair;

import com.audioquiz.api.datasources.firebase_auth.AuthApi;
import com.audioquiz.api.datasources.user_stats.stats.UserDataLocal;
import com.audioquiz.api.datasources.user_stats.stats.UserStatsApi;
import com.audioquiz.core.domain.repository.user.StatsRepository;
import com.audioquiz.core.model.user.stats.UserStats;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StatsRepository_Impl implements StatsRepository {
    private static final String TAG = "UserStatsRepositoryImpl";

    private final UserStatsApi api;
    private final UserDataLocal cache;
    private final AuthApi authApi;

    private CompositeDisposable compositeDisposable;

    @Inject
    public StatsRepository_Impl(UserDataLocal cache,
                                UserStatsApi api,
                                AuthApi authApi) {
        this.cache = cache;
        this.api = api;
        this.authApi = authApi;
        //checkUserStatisticsDocumentExistence();
    }

    private CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    @Override
    public void init() {
        Log.d(TAG, "UserStatsRepositoryImpl initialized");
    }

    private String getUserId() {
        return authApi.getFirebaseUserId();
    }

    @Override
    public Single<Boolean> sync() {
        return Single.zip(
                        getRemoteUserStatsSingle(),
                        getLocalUserStatsSingle(),
                        Pair::create // Create a Pair of localStats and remoteStats
                )
                .flatMap(pair -> {
                    UserStats latestStats = getLatestUserStats(pair.first, pair.second);

                    if (latestStats == null) {
                        return Single.just(false);
                    } else if (latestStats.getLastUpdated().equals(pair.first.getLastUpdated())) {
                        return syncUserStatsWithApiAndFirebase(latestStats).toSingleDefault(true);
                    } else {
                        return saveUserStatsLocally(latestStats).toSingleDefault(true);
                    }
                });
    }

    private UserStats getLatestUserStats(UserStats local, UserStats remote) {
        if (local == null) return remote;
        if (remote == null) return local;
        return local.getLastUpdated().compareTo(remote.getLastUpdated()) >= 0 ? local : remote;
    }

    // GET
    @Override
    public Single<UserStats> getUserStatsSingle() {
        Log.d(TAG, "getUserStatsSingle called");
        return getLocalUserStatsSingle()
                .onErrorResumeNext(error -> getRemoteUserStatsSingle()
                        .flatMap(userStats -> saveUserStatsLocally(userStats).toSingleDefault(userStats))
                );
    }

    private Single<UserStats> getLocalUserStatsSingle() {
        Log.d(TAG, "getLocalUserStatsSingle called");
        return cache.getUserDataSingle()
                .map(userStats -> userStats);
    }

    private Single<UserStats> getRemoteUserStatsSingle() {
        Log.d(TAG, "getLocalUserStatsSingle called");
        return api.getUserStatsSingle()
                .map(userStats -> userStats);
    }

    // DELETE
    @Override
    public Completable deleteUserStats(String userId) {
        return api.deleteUserStats(userId)
                .andThen(cache.deleteUserStats(userId));
    }

    // SAVE
    @Override
    public Completable saveUserStats(UserStats userStats) {
        userStats.setLastUpdated(new Date());
        return api.saveUserStats(userStats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .andThen(saveUserStatsLocally(userStats))
                .doOnError(e -> Log.e(TAG, "Error saving user stats", e));
    }

    private Completable saveUserStatsLocally(UserStats userStats) {
        return cache.saveUserStats(userStats);
    }

    private Completable syncUserStatsWithApiAndFirebase(UserStats userStats) {
        userStats.setLastUpdated(new Date());
        return api.saveUserStats(userStats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .andThen(Completable.fromAction(() -> api.logEventAnalytics("user_stats_updated", null)))
                .andThen(saveUserStatsLocally(userStats))
                .doOnError(e -> Log.e(TAG, "Error syncing user stats", e));
    }


    // USER_STATISTICS_DATA_FETCHING_AND_LOGIC
    // --------------------------------------------------------------------------------------------
    // Check if a user_statistics Document exists for current user in Firestore
    private void checkUserStatisticsDocumentExistence() {
        getCompositeDisposable().add(
                api.checkUserStatsDocumentExists(getUserId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(exists -> {
                            if (exists) {
                                setupUserStatsListener();
                            } else {
                                createUserStatsDocument();
                            }
                        }, e -> createUserStatsDocument())
        );
    }

    // Create the initial user statistics document in Firestore for a new user
    private void createUserStatsDocument() {
        UserStats userStatistics = UserStats.createDefault(getUserId());
        getCompositeDisposable().add(
                api.createUserStatsInFirestore(userStatistics)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> { // OnSuccess
                                    saveUserStatsLocally(userStatistics);
                                //    checkUserStatisticsDocumentExistence();
                                    Log.d(TAG, "User statistics document created successfully");
                                },
                                e -> { // OnError
                                    Log.e(TAG, "Error creating user statistics document", e);
                                }
                        ));
    }

    // Setup Listener for user_statistics Document in Firestore for current user
    private void setupUserStatsListener() {
        api.setupUserStatsListener(new UserStatsApi.UserStatsCallback() {
            @Override
            public void onUserStatsChanged(UserStats userStats) {
                if (userStats != null) {
                    getCompositeDisposable().add(
                            saveUserStats(userStats)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            () -> notifyUserStatsChanged(userStats), // Success
                                            e -> notifyError((Exception) e) // Error
                                    ));
                } else {
                    notifyUserStatsEmpty();
                }
            }

            @Override
            public void onUserStatsEmpty() {
                notifyUserStatsEmpty();
            }

            @Override
            public void onError(Exception e) {
                notifyError(e);
            }
        });
    }

    // Method to notify external components about user statistics changes
    private void notifyUserStatsChanged(UserStats userStats) {
        Log.d(TAG, "User statistics changed: " + userStats.toString());
    }

    // Method to notify external components about empty user statistics document
    private void notifyUserStatsEmpty() {
        // Example: Notify a callback or listener outside this repository
        // Implement as needed based on your architecture (callbacks, interfaces, etc.)
        Log.d(TAG, "User statistics document is empty");
    }

    // Method to notify external components about errors
    private void notifyError(Exception e) {
        // Example: Notify a callback or listener outside this repository
        // Implement as needed based on your architecture (callbacks, interfaces, etc.)
        Log.e(TAG, "Error setting up user statistics listener", e);
    }


    @Override
    public int getUserLives(UserLivesObserver observer) {
        UserStats userStats = getUserStatsSingle().blockingGet();
        int userLives = 3;
        if (userStats != null) {
            userLives = userStats.getGeneralStats().getNumberOfLives();
            observer.onUserLivesChanged(userLives);
        } else {
            observer.onError(new NullPointerException("User statistics data is null!"));
        }
        return userLives;
    }
}
