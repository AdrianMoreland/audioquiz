package com.audioquiz.feature.login.presentation.navigation;


import androidx.navigation.NavController;

import com.audioquiz.feature.login.R;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * LoginFlowCoordinator
 * Manages the login flow (e.g., login, signup, forgot password).
 * Handles events related to the login process.
 * Emits NavigationEvent for cross-module navigation (e.g., after successful login).
 */
public class LoginFlowCoordinator {
    private static final String TAG = "LoginFlowCoordinator";

    private final LoginNavigation loginNavigation;

    @Inject
    public LoginFlowCoordinator(LoginNavigation loginNavigation) {
        this.loginNavigation = loginNavigation;
    }

    private NavController getNavController() {
        Timber.tag(TAG).d("getNavController: %s", loginNavigation.getNavController());
        return loginNavigation.getNavController();
    }

    public void onEvent(LoginCoordinatorEvent event) {
        Timber.tag(TAG).d("onEvent Called with: %s", event);
        if (event instanceof LoginCoordinatorEvent.OnLoginSuccess) {
            Timber.tag(TAG).d("OnEvent: Login success");
            loginNavigation.navigateToAuthorizedGraph();
        } else if (event instanceof LoginCoordinatorEvent.OnSignupClicked) {
            Timber.tag(TAG).d("OnEvent: Onboarding flow");
            getNavController().navigate(R.id.signupFragment);
        } else {
            Timber.tag(TAG).d("OnEvent: Unknown event");
        }
    }
}
