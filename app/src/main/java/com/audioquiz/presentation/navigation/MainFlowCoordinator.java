package com.audioquiz.presentation.navigation;

import com.audioquiz.presentation.events.MainCoordinatorEvent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * MainFlowCoordinator
 * Manages top-level navigation flows (e.g., auth, home, quiz).
 * Handles events related to switching between these top-level flows.
 * Emits NavigationEvent for cross-module navigation.
 * Should not have direct access to FeatureNavigator.
 */
public class MainFlowCoordinator {
    private static final String TAG = "MainFlowCoordinator";
    private final StartNavigation startNavigation;

    @Inject
    public MainFlowCoordinator(StartNavigation startNavigation) {
        this.startNavigation = startNavigation;
        Timber.tag(TAG).d("MainFlowCoordinator initialized");
    }

    public boolean onEvent(MainCoordinatorEvent event) {
        Timber.tag(TAG).d("onEvent Called with: %s", event);
        if (event instanceof MainCoordinatorEvent.OnContinueNotLoggedIn) {
            Timber.tag(TAG).d("onEvent: OnContinueNotLoggedIn");
            startNavigation.navigateToUnauthorizedGraph();
            return true;
        } else if (event instanceof MainCoordinatorEvent.OnContinueLoggedIn) {
            Timber.tag(TAG).d("onEvent: OnContinueLoggedIn");
            startNavigation.navigateToAuthorizedGraph();
            return true;
        } else {
            Timber.tag(TAG).d("No event found for: %s", event);
            return false;
        }
    }
}