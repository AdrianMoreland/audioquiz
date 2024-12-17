package com.audioquiz.designsystem.util;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class BlurViewUtils {

    public static void setupBlurView(Activity activity, BlurView blurView, float radius) {
        View decorView = activity.getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView, new RenderScriptBlur(activity))
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            blurView.setOutlineAmbientShadowColor(Color.TRANSPARENT);
            blurView.setOutlineSpotShadowColor(Color.TRANSPARENT);
        }

        blurView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                View childView = blurView.getChildAt(0); // Get the child view
                if (childView.getBackground() != null) {
                    childView.setClipToOutline(true);
                }
            }
        });
    }
}