package com.audioquiz.feature.settings.presentation.navigation;


import javax.inject.Inject;

import timber.log.Timber;

/**
 * LoginFlowCoordinator
 * Manages the login flow (e.g., login, signup, forgot password).
 * Handles events related to the login process.
 * Emits NavigationEvent for cross-module navigation (e.g., after successful login).
 */
public class SettingsFlowCoordinator {
    private static final String TAG = "SettingsFlowCoordinator";
    private final SettingsNavigation actions;

    @Inject
    public SettingsFlowCoordinator(SettingsNavigation actions) {
        this.actions = actions;
    }

    public boolean onEvent(SettingsCoordinatorEvent event) {
        Timber.tag(TAG).d("onEvent Called with: %s", event);
        if (event instanceof SettingsCoordinatorEvent.OnSuccessfulLogout) {
            Timber.tag(TAG).d("Navigating to welcome screen");
            actions.navigateFromSettingsToWelcome();
            return true;
        } else if (event instanceof SettingsCoordinatorEvent.OnDeleteAccountClicked) {
            Timber.tag(TAG).d("Navigating to bottom sheet category");
            return true;
        } else {
            return false;
        }
    }
}
