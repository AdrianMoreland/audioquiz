package com.audioquiz.navigation;
/*

import com.audioquiz.data.remote.datasource.BaseFirestoreDataSource;
import com.audioquiz.data.remote.datasource.auth.AuthDataSource;
import com.audioquiz.data.remote.dto.UserProfileDto;
import com.audioquiz.data.remote.mapper.NetworkMapper;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class UserProfileDataSource extends BaseFirestoreDataSource {
    private static final String TAG = "UserStatsDataSource";
    private final FirebaseFirestore mFirestore;
    private final AuthDataSource authDataSource;
    private UserProfileDto userProfileDto;

    @Inject
    public UserProfileDataSource(FirestoreProvider firestoreProvider,
                                 AuthDataSource authDataSource,
                                 AuthProvider authProvider) {
        super(firestoreProvider, authProvider);
        this.authDataSource = authDataSource;
        mFirestore = firestoreProvider.getFirestore();
        mFirestore.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .build());
    }

    public String getUserId() {
        return authDataSource.getFirebaseUserId();
    }

    protected CollectionReference getCollection() {
        return getFirestore().collection("user_profile");
    }

    public void createUserProfileDocument(UserProfileDto userProfileDto) {
        setDocument(getUserId(), userProfileDto);
    }

    public Task<DocumentSnapshot> getUserProfileDocument() {
        return getDocument(getUserId());
    }

    public Task<UserProfileDto> getUserProfileDto() {
        return getDocument(getUserId()).continueWith(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            return documentSnapshot.toObject(UserProfileDto.class);
        });
    }

    public Task<Void> updateUserProfileDocument(UserProfileDto userProfileDto) {
        Map<String, Object> dataMap = getDocument(getUserId()).getResult().getData();
        return updateDocument(getUserId(), dataMap);
    }


    public Task<Void> updateUserProfile(String userId, UserProfileDto userProfileDto) {
        return updateDocument(userId, NetworkMapper.map(userProfileDto, HashMap.class));
    }

    public Task<Void> deleteUserProfileDocument() {
        return deleteDocument(getUserId());
    }

    public void createAndSetUser(FirebaseUser user) {

    }
}*/
