package com.audioquiz.feature.home.adapter;


import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;

import com.audioquiz.feature.home.domain.HomeViewContract;

import timber.log.Timber;

public class BindingAdapters {
    private static final String TAG = "BindingAdapters";

    private BindingAdapters() {
        // Prevent instantiation
    }

    @BindingAdapter("android:src")
    public static void setCategoryImage(ImageView imageView, int imageResId) {
        Timber.tag(TAG).d("Setting image for resource: %s", imageResId);
        imageView.setImageResource(imageResId);
    }

    @BindingAdapter("categoryBadges")
    public static void setCategoryBadges(LinearLayout badgesContainer, HomeViewContract.State state) {
        if (state != null && state.getCategories() != null) {
            for (int i = 0; i < state.getCategories().size(); i++) {
                Timber.tag(TAG).d("Setting badge for category: %s", state.getCategories().get(i).name);
                HomeViewContract.CategoryUi categoryUi = state.getCategories().get(i);
                ImageView badgeView = (ImageView) badgesContainer.getChildAt(i);
                if (badgeView != null) {
                    if (categoryUi.isCompleted()) {
                        Timber.tag(TAG).d("Setting badge for category: " + categoryUi.name + ", badgeResId: " + categoryUi.badgeResId);
                        badgeView.setImageResource(categoryUi.badgeResId);
                    } else {
                        badgeView.setImageResource(com.audioquiz.designsystem.R.drawable.ic_badge_empty);
                    }
                }
            }
        }
    }
}
