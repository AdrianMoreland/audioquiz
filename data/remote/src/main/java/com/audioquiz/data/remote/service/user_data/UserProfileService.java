package com.audioquiz.data.remote.service.user_data;

import static com.audioquiz.data.remote.util.FirestoreConstants.USER_PROFILE_COLLECTION;

import android.util.Log;

import com.audioquiz.api.datasources.user.UserProfileDataSource;
import com.audioquiz.core.model.user.UserProfile;
import com.audioquiz.data.remote.datasource.FirestoreDataSource;
import com.audioquiz.data.remote.dto.UserProfileDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserProfileService implements UserProfileDataSource.Remote {
    private static final String TAG = "UserProfileService";
    private final FirestoreDataSource<UserProfileDto> firestoreDataSource;

    @Inject
    public UserProfileService(FirestoreDataSource<UserProfileDto> firestoreDataSource) {
        this.firestoreDataSource = firestoreDataSource;
    }

    // CREATE
    @Override
    public void createUserProfileDocument(UserProfile userProfile) {
        saveUserProfile(userProfile);
        firestoreDataSource.setDocument(
                USER_PROFILE_COLLECTION,
                mapFromDomain(userProfile));
    }


    @Override
    public Observable<UserProfile> observeUserProfile() {
        return null;
    }

    @Override
    public Single<UserProfile> getUserProfile() {
        return Single.<UserProfile>create(emitter ->
                        firestoreDataSource.getDocument(USER_PROFILE_COLLECTION)
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


    private void handleDocumentSnapshot(DocumentSnapshot documentSnapshot, SingleEmitter<UserProfile> emitter) {
        if (documentSnapshot.exists()) {
            UserProfileDto dto = documentSnapshot.toObject(UserProfileDto.class);
            if (dto != null) {
                emitter.onSuccess(mapToDomain(dto));
            } else {
                emitter.onError(new NullPointerException("UserProfile object is null"));
            }
        } else {
            emitter.onError(new RuntimeException("No UserProfile document found"));
        }
    }

    public Completable saveUserProfile(UserProfile userProfile) {
        Log.d(TAG, "saveGeneralStats() called with: generalStats = [" + userProfile + "]");
        return Completable.create(emitter ->
                        firestoreDataSource.setDocument(
                                        USER_PROFILE_COLLECTION,
                                        mapFromDomain(userProfile))
                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable deleteUserProfile() {
        Log.d(TAG, "deleteGeneralStatsLocal() called");
        return Completable.create(emitter ->
                        firestoreDataSource.deleteDocument(USER_PROFILE_COLLECTION)
                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    // UPDATE
    @Override
    public void updateUsernameInFirestore(String newUsername) {
        //TODO: implement this
    }

    public Completable updateUserProfile(UserProfile userProfile) {
        UserProfileDto dto = mapFromDomain(userProfile);
        return Completable.create(emitter ->
                firestoreDataSource.updateDocument(
                                USER_PROFILE_COLLECTION,
                                dto)
                        .addOnSuccessListener(aVoid -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError));
    }

    public Task<Void> updateUserProfileDocument(String userId, UserProfile userProfile) {
        UserProfileDto dto = mapFromDomain(userProfile);
        return firestoreDataSource.updateDocument(USER_PROFILE_COLLECTION, dto);
    }


    // DELETE

    // MAPPER
    private UserProfile mapToDomain(UserProfileDto userProfileDto) {
        return NetworkMapper.map(userProfileDto, UserProfile.class);
    }

    private UserProfileDto mapFromDomain(UserProfile userProfile) {
        return NetworkMapper.map(userProfile, UserProfileDto.class);
    }

}
