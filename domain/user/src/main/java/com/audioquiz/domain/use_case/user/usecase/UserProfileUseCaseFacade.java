package com.adrian.usecase.user.usecase;

import com.adrian.model.login.UserProfile;
//import com.sun.jndi.toolkit.url.Uri;
import io.reactivex.rxjava3.core.Single;

public interface UserProfileUseCaseFacade {
    Single<UserProfile> getUserProfileSingle();
    void preloadUserProfile();
//    void selectImage(ActivityResultLauncher<Intent> launcher);
//    void handleImageSelectionResult(ActivityResult result, UploadProfileImageUseCase.OnImageSelectedListener listener);
//    void handleCropResult(ActivityResult result, UploadProfileImageUseCase.OnImageUploadListener onImageUploadListener);
//    void startUCrop(Uri selectedImageUri, ActivityResultLauncher<Intent> uCropActivityResultLauncher);
//    void handleSelectedImageUri(Uri imageUri);

//    LiveData<String> getProfileImageUrl();
    String getUsernameLetter();

    String getProfileImageUrl();

//    void uploadProfileImage(java.net.URI imageUri, OnImageUploadListener onImageUploadListener);
}
