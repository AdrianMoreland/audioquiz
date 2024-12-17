package com.audioquiz.designsystem.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.audioquiz.designsystem.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;


// ImageSelectionUtil.java
public class ImageSelectionUtil {
    private final Context applicationContext; // Use Application Context to avoid issues with fragment lifecycle

    @Inject
    public ImageSelectionUtil(@ApplicationContext Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void selectAndCropImage( Fragment fragment, Uri uri, ActivityResultLauncher<Intent> cropLauncher, Consumer<Uri> croppedImageUriCallback ) {
        if (uri != null) {
            startUCrop(fragment, uri, cropLauncher, croppedImageUriCallback);
        }
    }

    private void startUCrop( Fragment fragment,  Uri sourceUri, ActivityResultLauncher<Intent> cropLauncher, Consumer<Uri> croppedImageUriCallback ) {
        Uri destinationUri = Uri.fromFile(new File(fragment.requireContext().getCacheDir(), "temp.jpeg"));
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        uCrop.withOptions(getOptions());
        Intent uCropIntent = uCrop.getIntent(fragment.requireContext());

        cropLauncher.launch(uCropIntent);
    }

    private UCrop.Options getOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(70);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);

        options.setToolbarColor(androidx.core.content.ContextCompat.getColor(applicationContext, R.color.md_theme_primary));
        options.setStatusBarColor(androidx.core.content.ContextCompat.getColor(applicationContext, R.color.md_theme_primary));
    //    options.setActiveWidgetColor(androidx.core.content.ContextCompat.getColor(applicationContext, R.color.md_theme_primary));
        return options;
    }

}

