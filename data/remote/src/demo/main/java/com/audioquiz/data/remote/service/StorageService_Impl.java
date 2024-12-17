package com.audioquiz.data.remote.service;

import android.net.Uri;

import com.audioquiz.api.datasources.firebase.StorageApi;
import com.audioquiz.data.remote.datasource.StorageDataSource;
import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;

public class StorageService_Impl implements StorageApi {
    private final StorageDataSource storageDataSource;

    @Inject
    public StorageService_Impl(StorageDataSource storageDataSource) {
        this.storageDataSource = storageDataSource;
    }

    @Override
    public void uploadImageToStorage(Uri imageUri, StorageApi.ImageUploadCallback callback) {
        storageDataSource.uploadImageToStorage(imageUri, callback);
    }

    public StorageReference getStorageReference() {
        return storageDataSource.getStorage().getReference();
    }

}
