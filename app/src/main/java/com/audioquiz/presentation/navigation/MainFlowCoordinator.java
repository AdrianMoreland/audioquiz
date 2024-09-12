package com.audioquiz.presentation.navigation;

import android.util.Log;

import com.adrian.audioquiz.presentation.events.MainCoordinatorEvent;

import javax.inject.Inject;

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
        Log.d(TAG, "MainFlowCoordinator initialized");
    }

    public boolean onEvent(MainCoordinatorEvent event) {
        Log.d(TAG, "onEvent Called with: " + event);
        if (event instanceof MainCoordinatorEvent.OnContinueNotLoggedIn) {
            Log.d(TAG, "onEvent: OnContinueNotLoggedIn");
            startNavigation.navigateToUnauthorizedGraph();
            return true;
        } else if (event instanceof MainCoordinatorEvent.OnContinueLoggedIn) {
            Log.d(TAG, "onEvent: OnContinueLoggedIn");
            startNavigation.navigateToAuthorizedGraph();
            return true;
        } else {
            Log.d(TAG, "No event found for: " + event);
            return false;
        }
    }
}