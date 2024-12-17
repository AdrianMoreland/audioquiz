package com.audioquiz.data.remote.provider;

import com.google.firebase.storage.FirebaseStorage;

public class StorageProvider {
    private FirebaseStorage mStorage;

    public FirebaseStorage getStorage() {
        return mStorage;
    }

    public void init() {
        setStorage();
    }

    public void setStorage() {
        this.mStorage = FirebaseStorage.getInstance();
    }
}