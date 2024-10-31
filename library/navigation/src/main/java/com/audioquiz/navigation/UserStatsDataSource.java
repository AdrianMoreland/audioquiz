package com.audioquiz.navigation;
/*

import android.util.Log;

import com.audioquiz.api.datasources.user_stats.stats.UserStatsApi;
import com.audioquiz.data.remote.datasource.BaseFirestoreDataSource;
import com.audioquiz.data.remote.dto.UserStatsDto;
import com.audioquiz.data.remote.mapper.NetworkMapper;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;
import com.audioquiz.core.model.user.stats.UserStats;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class UserStatsDataSource extends BaseFirestoreDataSource {
    private static final String TAG = "UserStatsDataSource";
    private final FirebaseFirestore mFirestore;

    @Inject
    public UserStatsDataSource(FirestoreProvider firestoreProvider,
                               AuthProvider authProvider) {
        super(firestoreProvider, authProvider);
        mFirestore = firestoreProvider.getFirestore();
        mFirestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .build());
    }

    @Override
    protected CollectionReference getCollection() {
        return getFirestore().collection("user_statistics");
    }

    public DocumentReference getDoc(String userId) {
        Log.d(TAG, "getDoc: " + userId);
        return getCollection().document(userId);
    }

    public Task<UserStatsDto> fetchUserStatisticsDto(String userId) {
        Log.d(TAG, "Fetching user statistics for user: " + userId);
        return getDocument(userId).continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    return document.toObject(UserStatsDto.class);
                } else {
                    throw new UserStatsNotFoundException("User statistics not found for user: " + userId);
                }
            } else {
                throw new RuntimeException("Error fetching user statistics", task.getException());
            }
        });
    }

    public Task<Void> setUserStatistics(String userId, Map<String, Object> userStatistics) {
        return getDoc(userId).set(userStatistics);
    }

    public Task<Void> updateUserStatistics(Map<String, Object> userStatistics) {
        return updateDocument(getFirebaseUserId(), userStatistics);
    }

    public Task<Void> deleteUserStatistics(String userId) {
        return deleteDocument(userId);
    }

    public void setupUserStatisticsListener(String userId, UserStatisticsCallback callback) {
        DocumentReference docRef = getFirestore().collection("user_statistics").document(userId);
        docRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                callback.onError(e);
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: " + snapshot.getData());
                UserStatsDto userStatsDto = NetworkMapper.map(snapshot, UserStatsDto.class);
                callback.onUserStatisticsChanged(userStatsDto);
            } else {
                Log.d(TAG, "Current data: null");
                callback.onUserStatisticsEmpty();
            }
        });
    }

    public void uploadUserStatisticsToFirestore(String userId, UserStatsDto userStatsDto) {

          // Upload the user statistics map to Firestore
        getDoc(userId)
                .set(NetworkMapper.map(userStatsDto, HashMap.class))
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "User statistics updated successfully for user -> " + userId + ": " + userStatsDto.toString());
                 })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating user statistics for user -> " + userId + ": error -> " + e);
                 });
    }

    public Single<UserStats> fetchUserStatsSingle(String userId) {
        return Single.create(emitter -> fetchUserStatisticsDto(userId).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                    UserStats userStats = NetworkMapper.map(task.getResult(), UserStats.class);
                    if (userStats == null) {
                        emitter.onError(new UserStatsNotFoundException("User statistics not found in Firestore"));
                    }
                    emitter.onSuccess(NetworkMapper.map(task.getResult(), UserStats.class));
            } else {
                emitter.onError(task.getException()); // Emit Firestore exception
            }
        }));
    }

    public Task<Boolean> userStatsDocumentExists(String userId) {
        Log.d(TAG, "userStatsDocumentExists: checking if document exists for user: " + userId);
        return getDoc(userId).get().continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                Log.d(TAG, "userStatsDocumentExists: task successful, document exists: " + documentSnapshot.exists());
                return documentSnapshot.exists();
            } else {
                Log.e(TAG, "userStatsDocumentExists: task failed", task.getException());
                return false; // Return false if the task failed
            }
        });
    }

     public void checkUserStatisticsDocumentExistence(UserStatsApi.CheckDocCompletionCallback completionCallback) {
        Log.d(TAG, "Checking user statistics document existence");
        String currentUserId = getFirebaseUserId();
        if (currentUserId != null) {
            getFirestore().collection("user_statistics")
                    .document(currentUserId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User statistics document exists: " + task.getResult().exists());
                            boolean exists = task.getResult().exists();
                            completionCallback.onSuccess(exists);
                        } else {
                            completionCallback.onError(task.getException());
                            Log.e(TAG, "Error checking user statistics document existence", task.getException());
                        }
                    });
        } else {
            Log.e(TAG, "No user is currently logged in");

            completionCallback.onError(new IllegalStateException("No user is currently logged in"));
        }
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


    public interface CompletionCallback {
        void onSuccess(Boolean b);
        void onError(Exception e);
    }
    public interface UserStatisticsCallback {
        void onUserStatisticsChanged(UserStatsDto userStatsDto);
        void onUserStatisticsEmpty();
        void onError(Exception e);
    }
}*/
