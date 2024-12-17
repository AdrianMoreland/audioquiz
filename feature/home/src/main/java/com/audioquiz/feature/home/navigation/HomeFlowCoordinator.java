package com.audioquiz.feature.home.navigation;


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

    public boolean onEvent(HomeCoordinatorEvent event) {
        Timber.tag(TAG).d("onEvent Called with: %s", event);
        if (event instanceof HomeCoordinatorEvent.OnSettingsButtonPressed) {
            Timber.tag(TAG).d("OnEvent: Settings flow");
            homeNavigation.navigateHomeToSettings();
            return true;
        } else if (event instanceof HomeCoordinatorEvent.OnCategoryClicked) {
            Timber.tag(TAG).d("Navigating to bottom sheet category");
           // getNavController().navigate(R.id.action_homeFragment_to_bottomSheetCategoryFragment);
            return true;
        } else {
            return false;
        }
    }

    private boolean toCategoryBottomSheetFlow() {
        Timber.tag(TAG).d("Navigating to bottom sheet category");
      ///  getNavController().navigate(R.id.action_homeFragment_to_bottomSheetCategoryFragment);
        return true;
    }
}
