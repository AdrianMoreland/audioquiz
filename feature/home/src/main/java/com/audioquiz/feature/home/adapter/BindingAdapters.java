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
        if (state != null && state.categories() != null) {
            for (int i = 0; i < state.categories().size(); i++) {
                Timber.tag(TAG).d("Setting badge for category: %s", state.categories().get(i).getName());
                HomeViewContract.CategoryCardState categoryCardState = state.categories().get(i);
                ImageView badgeView = (ImageView) badgesContainer.getChildAt(i);
                if (badgeView != null) {
                    if (categoryCardState.isCompleted()) {
                        Timber.tag(TAG).d("Setting badge for category: " + categoryCardState.getName() + ", badgeResId: " + categoryCardState.getBadgeResId());
                        badgeView.setImageResource(categoryCardState.getBadgeResId());
                    } else {
                        badgeView.setImageResource(com.audioquiz.designsystem.R.drawable.ic_badge_empty);
                    }
                }
            }
        }
    }
}
