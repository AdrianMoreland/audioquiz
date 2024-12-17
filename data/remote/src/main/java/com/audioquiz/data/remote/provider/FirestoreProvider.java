package com.audioquiz.data.remote.provider;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.PersistentCacheSettings;

import javax.inject.Inject;


public class FirestoreProvider {
    private FirebaseFirestore mFirestore;

    @Inject
    public FirestoreProvider() {
        // Empty constructor
    }

    public FirebaseFirestore getFirestore() {
        if (mFirestore == null) {
            init();
        }
        return mFirestore;
    }

    public void setLoggingEnabled(boolean enable) {
        FirebaseFirestore.setLoggingEnabled(enable);
    }

    public void init() {
        setFirestore();
    }

    private void setFirestore() {
        this.mFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings =
                new FirebaseFirestoreSettings.Builder(mFirestore.getFirestoreSettings())
                        // Use memory-only cache
                        .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                        // Use persistent disk cache (default)
                        .setLocalCacheSettings(PersistentCacheSettings.newBuilder()
                                .build())
                        .build();
        mFirestore.setFirestoreSettings(settings);
    }
}
