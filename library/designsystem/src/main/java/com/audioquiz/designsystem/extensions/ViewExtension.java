package com.audioquiz.designsystem.extensions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewExtension {

    /**
     * Inflates a given layout resources and returns the inflated view.
     */
    public static View inflate(ViewGroup group, int layoutRes) {
        return LayoutInflater.from(group.getContext()).inflate(layoutRes, group, false);
    }

    /**
     * Method to retrieve a String from the appModule resources.
     */
    public static CharSequence getStringFromResources(View view, int stringResId) {
        return view.getResources().getString(stringResId);
    }

    public static void setVisible(View view, boolean isVisible, boolean invisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else if (invisible) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static void setTextIfAvailableOrHide(TextView textView, String text, View... otherViewsToBeHidden) {
        textView.setText(text);
        boolean isTextAvailable = text != null && !text.isEmpty();
        setVisible(textView, isTextAvailable, false);
        for (View view : otherViewsToBeHidden) {
            setVisible(view, isTextAvailable, false);
        }
    }

    /**
     * Method to make a View visible
     */
    public static void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * Method to make a View invisible
     */
    public static void setInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }
}