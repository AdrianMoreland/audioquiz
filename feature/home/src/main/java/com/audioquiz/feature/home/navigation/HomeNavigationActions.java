package com.audioquiz.feature.home.navigation;

import androidx.navigation.NavController;

public interface HomeNavigationActions {
    void navigateHomeToSettings();

    NavController getNavController();
}
