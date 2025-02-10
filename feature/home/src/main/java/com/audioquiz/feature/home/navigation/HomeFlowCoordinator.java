package com.audioquiz.feature.home.navigation;


import android.os.Bundle;

import com.audioquiz.feature.home.domain.HomeViewContract;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * LoginFlowCoordinator
 * Manages the login flow (e.g., login, signup, forgot password).
 * Handles events related to the login process.
 * Emits NavigationEvent for cross-module navigation (e.g., after successful login).
 */
public class HomeFlowCoordinator {
    private static final String TAG = "HomeFlowCoordinator";
    private final HomeNavigation homeNavigation;

    @Inject
    public HomeFlowCoordinator(HomeNavigation homeNavigation) {
        this.homeNavigation = homeNavigation;
    }

    public boolean onEvent(HomeViewContract.Effect event) {
        Timber.tag(TAG).d("onEvent Called with: %s", event);
        if (event instanceof HomeViewContract.Effect.NavigateToSettings) {
            Timber.tag(TAG).d("OnEvent: Settings flow");
            homeNavigation.navigateHomeToSettings();
            return true;
        } else if (event instanceof HomeViewContract.Effect.ShowCategoryBottomSheet) {
            Timber.tag(TAG).d("OnEvent: Category flow");
            return true;
        } else if (event instanceof HomeViewContract.Effect.NavigateToQuiz navigateToQuiz) {
            Bundle bundle = new Bundle();
            bundle.putString("category", navigateToQuiz.getCategory());
            bundle.putInt("chapter", navigateToQuiz.getCurrentChapter());
            Timber.tag(TAG).d("onEvent: Bundle received from ViewModel: %s", bundle);
            homeNavigation.navigateHomeToQuiz(bundle);
            return true;
        } else {
            return false;
        }
    }
}
