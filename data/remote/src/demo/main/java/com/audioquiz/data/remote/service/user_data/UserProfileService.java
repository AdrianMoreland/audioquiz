package com.audioquiz.data.remote.service.user_data;

import static com.audioquiz.data.remote.util.FirestoreConstants.USER_DATA_COLLECTION;
import static com.audioquiz.data.remote.util.FirestoreConstants.USER_PROFILE_DOCUMENT;

import android.util.Log;

import com.audioquiz.api.datasources.user.UserProfileApi;
import com.audioquiz.data.remote.datasource.FirestoreDataSource;
import com.audioquiz.data.remote.dto.UserProfileDto;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.audioquiz.core.model.user.UserProfile;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserProfileService implements UserProfileApi {
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
                USER_DATA_COLLECTION,
                USER_PROFILE_DOCUMENT,
                mapFromDomain(userProfile));
    }


    @Override
    public Observable<UserProfile> observeUserProfile() {
        return null;
    }

    @Override
    public Single<UserProfile> getUserProfile() {
        return Single.<UserProfile>create(emitter ->
                        firestoreDataSource.getDocumentSnapshot(
                                        USER_DATA_COLLECTION,
                                        USER_PROFILE_DOCUMENT)
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
                        firestoreDataSource.setDocument(USER_DATA_COLLECTION, USER_PROFILE_DOCUMENT, mapFromDomain(userProfile))
                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable deleteUserProfile() {
        Log.d(TAG, "deleteGeneralStatsLocal() called");
        return Completable.create(emitter ->
                        firestoreDataSource.deleteDocument(USER_DATA_COLLECTION, USER_PROFILE_DOCUMENT)
                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    @Override
    public void setupUserProfileListener(UserProfileCallback userProfileCallback) {
        //TODO: implement this
    }


    // UPDATE
    @Override
    public void updateUsernameInFirestore(String newUsername, CompletionCallback completionCallback) {
        //TODO: implement this
    }

    public Completable updateUserProfile(String userId, UserProfile userProfile) {
        UserProfileDto dto = mapFromDomain(userProfile);
        return Completable.create(emitter ->
                firestoreDataSource.updateDocument(userId, "user_profile", dto)
                        .addOnSuccessListener(aVoid -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError));
    }

    public Task<Void> updateUserProfileDocument(String userId, UserProfile userProfile) {
        UserProfileDto dto = mapFromDomain(userProfile);
        return firestoreDataSource.updateDocument(USER_DATA_COLLECTION, USER_PROFILE_DOCUMENT, dto);
    }


    // DELETE

    // MAPPER
    private UserProfile mapToDomain(UserProfileDto userProfileDto) {
        return NetworkMapper.map(userProfileDto, UserProfile.class);
    }

    private UserProfileDto mapFromDomain(UserProfile userProfile) {
        return NetworkMapper.map(userProfile, UserProfileDto.class);
    }

    /*    public Task<UserProfile> getUserProfileee() {
        return userProfileDataSource.getDocumentSnapshot(userProfileDataSource.getUserId()).continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    UserProfileDto userProfileDto = document.toObject(UserProfileDto.class);
                    return mapToDomain(userProfileDto);
                } else {
                    throw new Exception("No document found");
                }
            } else {
                throw task.getException();
            }
        });
    }

      @Override
    public void updateUserProfile(UserProfile userProfile, CompletionCallback callback) {
        userProfileDataSource.updateUserProfileDocument(mapFromDomain(userProfile))
                .addOnSuccessListener(aVoid -> {
                    notifyUserProfileChanged(userProfile);
                    callback.onSuccess();
                })
                .addOnFailureListener(callback::onError);
    }*/

}
