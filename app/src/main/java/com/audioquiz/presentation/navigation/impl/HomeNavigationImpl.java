package com.audioquiz.presentation.navigation.impl;

import android.util.Log;

import androidx.navigation.NavController;

import com.audioquiz.R;
import com.audioquiz.presentation.navigation.NavControllerProvider;

import javax.inject.Inject;

public class HomeNavigationImpl {

    @Inject
    NavControllerProvider navControllerProvider;

    @Inject
    public HomeNavigationImpl() {
        Log.d("HomeNavigationImpl", "HomeNavigationImpl initialized");
    }

    public void navigateHomeToSettings() {
        NavController navController = navControllerProvider.getNavController();
        navController.navigate(R.id.navigateFromHomeToSettings);
    }

}
