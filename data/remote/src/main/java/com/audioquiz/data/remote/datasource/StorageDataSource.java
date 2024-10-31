package com.audioquiz.data.remote.datasource;

import android.net.Uri;

import com.audioquiz.api.datasources.firebase.StorageApi;
import com.audioquiz.data.remote.provider.StorageProvider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;

public class StorageDataSource {
    private final FirebaseStorage mStorage;

    @Inject
    public StorageDataSource(StorageProvider storageProvider) {
        mStorage = storageProvider.getStorage();
    }

    public FirebaseStorage getStorage() {
        return mStorage;
    }

    public StorageReference getStorageReference() {
        return mStorage.getReference();
    }

    public void uploadImageToStorage(Uri imageUri, StorageApi.ImageUploadCallback callback) {
        //StorageReference storageReference = getStorage().getReference().child("profile_images/" + UUID.randomUUID().toString());
        StorageReference storageReference = getStorageReference();
        storageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl()
                        .addOnSuccessListener(uri -> callback.onSuccess(uri.toString()))
                        .addOnFailureListener(callback::onError))
                .addOnFailureListener(callback::onError);
    }

}
