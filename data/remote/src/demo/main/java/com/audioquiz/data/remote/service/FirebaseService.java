package com.audioquiz.data.remote.service;

import android.app.Application;

import com.audioquiz.api.datasources.firebase.FirebaseApi;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;
import com.audioquiz.data.remote.provider.StorageProvider;

import javax.inject.Inject;

public class FirebaseService implements FirebaseApi {
    private final AuthProvider authProvider;
    private final FirestoreProvider firestoreProvider;
    private final StorageProvider storageProvider;


    @Inject
    public FirebaseService(AuthProvider authProvider,
                           FirestoreProvider firestoreProvider,
                           StorageProvider storageProvider) {
        this.authProvider = authProvider;
        this.firestoreProvider = firestoreProvider;
        this.storageProvider = storageProvider;
    }

    @Override
    public void initializeFirebase(Application application) {
        authProvider.init();
        firestoreProvider.init();
        storageProvider.init();
        // Enable Firestore logging
        firestoreProvider.setLoggingEnabled(true);
    }

}
