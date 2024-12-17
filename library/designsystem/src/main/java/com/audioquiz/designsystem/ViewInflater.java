package com.audioquiz.designsystem;

import android.view.LayoutInflater;
import android.view.ViewGroup;

@FunctionalInterface
public interface ViewInflater<T> {
    T inflate(LayoutInflater inflater, ViewGroup container, boolean attachToRoot);
}