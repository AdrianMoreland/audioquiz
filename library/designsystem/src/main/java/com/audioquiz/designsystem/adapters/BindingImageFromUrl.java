package com.audioquiz.designsystem.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class BindingImageFromUrl implements ImageLoader {
    @Override
    public void loadImage(String imageUrl, ImageView view) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(view.getContext())
                    .load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(com.audioquiz.designsystem.R.drawable.bg_rounded_8dp)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view);
        }
    }
}
