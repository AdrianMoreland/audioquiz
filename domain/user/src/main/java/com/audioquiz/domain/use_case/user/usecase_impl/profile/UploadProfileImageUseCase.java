package com.adrian.usecase.user.usecase_impl.profile;


import javax.inject.Inject;

public class UploadProfileImageUseCase {

    @Inject
    public UploadProfileImageUseCase() {
    }
//    private final UserProfileRepository userProfileRepository;
//    private final ImageSelectionUtil imageSelectionUtil;
//
//    public UploadProfileImageUseCase(UserProfileRepository userProfileRepository,
//                                      ImageSelectionUtil imageSelectionUtil) {
//        this.userProfileRepository = userProfileRepository;
//        this.imageSelectionUtil = imageSelectionUtil;
//    }
//
//    public void execute(Uri imageUri, UserStatisticsViewModel.OnImageUploadListener onImageUploadListener) {
//        userProfileRepository.uploadProfileImage(imageUri, onImageUploadListener);
//    }
//
//    public void selectImage(ActivityResultLauncher<Intent> launcher) {
//        ImageSelectionUtil.selectImage(launcher);
//    }
//
//    public void handleImageSelectionResult(ActivityResult result, OnImageSelectedListener listener) {
//        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
//            Uri selectedImageUri = result.getData().getData();
//            listener.onImageSelected(selectedImageUri);
//        }
//    }
//
//    public void handleCropResult(ActivityResult result, UserStatisticsViewModel.OnImageUploadListener listener) {
//        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
//            Uri croppedImageUri = UCrop.getOutput(result.getData());
//            if (croppedImageUri != null) {
//                userProfileRepository.uploadProfileImage(croppedImageUri, listener);
//            }
//        }
//    }
//
//    public void startUCrop(Uri selectedImageUri, ActivityResultLauncher<Intent> uCropActivityResultLauncher) {
//        imageSelectionUtil.startUCrop(selectedImageUri, uCropActivityResultLauncher);
//    }
//
//    public interface OnImageSelectedListener {
//        void onImageSelected(Uri uri);
//    }
//
//    public interface OnImageUploadListener {
//        void onImageUploadSuccess(String downloadUrl);
//        void onImageUploadFailure(Exception e);
//    }
}
