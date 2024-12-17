package com.audioquiz.data.remote.service.user_data;

import android.os.Bundle;
import android.util.Log;

import com.audioquiz.api.datasources.user_stats.stats.UserStatsApi;
import com.audioquiz.data.remote.datasource.AuthDataSource;
import com.audioquiz.data.remote.datasource.FirestoreDataSource;
import com.audioquiz.data.remote.dto.UserStatsDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.audioquiz.core.model.user.stats.UserStats;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class UserStatsService implements UserStatsApi {
    private final FirebaseAnalytics firebaseAnalytics;
    private final AuthDataSource authDataSource;

    @Inject
    FirestoreDataSource<UserStatsDto> userStatsDataSource;

    @Inject
    public UserStatsService(FirebaseAnalytics firebaseAnalytics,
                            AuthDataSource authDataSource) {
        this.firebaseAnalytics = firebaseAnalytics;
        this.authDataSource = authDataSource;
    }

    @Override
    public Single<UserStats> getUserStatsSingle() {
        return Single.create(emitter -> {
            FirebaseUser currentUser = authDataSource.getAuth().getCurrentUser();
            if (currentUser != null) {
                userStatsDataSource.getDocumentSnapshot(authDataSource.getFirebaseUserId(), "user_statistics").addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        UserStatsDto userStatsDto = task.getResult().toObject(UserStatsDto.class);
                        if (userStatsDto != null) {
                            emitter.onSuccess(NetworkMapper.map(userStatsDto, UserStats.class));
                        } else{
                            emitter.onError(new UserStatsNotFoundException("User statistics not found in Firestore"));
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                });
            } else {
                emitter.onError(new IllegalStateException("No user is currently logged in"));
            }
        });
    }


    @Override
    public Completable saveUserStats(UserStats userStats) {
        Log.d("UserStatsService", "Uploading user stats to Firestore");
        userStatsDataSource.add(userStats.getUserId(), "user_statistics",
                NetworkMapper.map(userStats, UserStatsDto.class));
        return Completable.complete();
    }

    @Override
    public Completable deleteUserStats(String userId) {
        return null;
    }

    @Override
    public void logEventAnalytics(String userStatsUpdated, Bundle bundle) {
        firebaseAnalytics.logEvent(userStatsUpdated, bundle);
    }

    // In your UserStatsApi implementation (e.g., FirestoreUserStatsApi)
    @Override
    public Single<Boolean> checkUserStatsDocumentExists(String userId) {
        return Single.create(emitter -> {
            userStatsDataSource.getDocumentSnapshot(userId, "user_statistics")
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            emitter.onSuccess(documentSnapshot.exists());
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }


    @Override
    public Completable createUserStatsInFirestore(UserStats userStats) {
        Log.d("UserStatsService", "Creating user stats in Firestore");
        userStatsDataSource.updateDocument(
                authDataSource.getFirebaseUserId(),
                "user_statistics",
                NetworkMapper.map(userStats, UserStatsDto.class));
        return Completable.complete();
    }

    @Override
    public void setupUserStatsListener(UserStatsCallback userStatsCallback) {

    }


    private static class UserStatsNotFoundException extends Exception {
        public UserStatsNotFoundException() {
            super();
        }
        public UserStatsNotFoundException(String message) {
            super(message);
        }
        public UserStatsNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }


}
