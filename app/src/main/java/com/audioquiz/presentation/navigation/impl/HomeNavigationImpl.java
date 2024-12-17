package com.audioquiz.presentation.navigation.impl;

import androidx.navigation.NavController;

import com.audioquiz.R;
import com.audioquiz.presentation.navigation.NavControllerProvider;

import javax.inject.Inject;

import timber.log.Timber;

public class HomeNavigationImpl {

    @Inject
    NavControllerProvider navControllerProvider;

    @Inject
    public HomeNavigationImpl() {
        Timber.tag("HomeNavigationImpl").d("HomeNavigationImpl initialized");
    }

    public void navigateHomeToSettings() {
        NavController navController = navControllerProvider.getNavController();
        navController.navigate(R.id.navigateFromHomeToSettings);
    }

}
