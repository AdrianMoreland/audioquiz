package com.audioquiz.api.datasources.firebase;

import android.net.Uri;

public interface StorageApi {
    void uploadImageToStorage(Uri imageUri, ImageUploadCallback callback);

    interface ImageUploadCallback {
        void onSuccess(String imageUrl);
        void onError(Exception e);
    }
}
