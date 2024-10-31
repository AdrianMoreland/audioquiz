package com.audioquiz.presentation.navigation;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.audioquiz.R;
import com.audioquiz.feature.home.navigation.HomeNavigation;
import com.audioquiz.feature.login.presentation.navigation.LoginNavigation;
import com.audioquiz.feature.settings.presentation.navigation.SettingsNavigation;
import com.audioquiz.ui.MainViewModel;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;
import timber.log.Timber;

public class Navigator implements GlobalNavigation, StartNavigation, LoginNavigation, HomeNavigation, SettingsNavigation {
    private static final String TAG = "Navigator";

    private NavController navController;
    private MainViewModel mainViewModel;

    @Inject
    public Navigator(NavController navController,
                     @ActivityContext Context context) {
        this.navController = navController;
        init(context);
    }

    private void init (@ActivityContext Context context) {
        Timber.d("Navigator initialized with NavController: %s", navController);
        AppCompatActivity activity = (AppCompatActivity) context;
        mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
        observeNavigationChanges();
    }

    private void observeNavigationChanges() {
        mainViewModel.getNavControllerLiveData().observeForever(controller -> {
            if (controller != null) {
                Timber.tag(TAG).d("NavController updated: %s", controller);
                navController = controller;
            }
        });
    }

    @Override
    public NavController getNavController() {
        Timber.d("getNavController: %s", navController);
        return navController;
    }

    private void navigate (int actionId) {
        if (navController != null) {
            Timber.d("Navigating to actionId: " + actionId + " with NavController: " + navController);
            navController.navigate(actionId);
            return;
        }
        Timber.e("NavController is null when trying to navigate");
    }

    @Override
    public void navigateToAuthorizedGraph() {
        Timber.d("navigateToAuthorizedGraph");
         navigate(R.id.navigateToAuthorizedGraph);
    }

    @Override
    public void navigateToUnauthorizedGraph() {
        Timber.d("navigateToUnauthorizedGraph");
        navigate(R.id.navigateToUnauthorizedGraph);
    }

    @Override
    public void navigateHomeToSettings() {
        navigate(R.id.navigateFromHomeToSettings);
    }

    @Override
    public void navigateToHome() {
        Timber.d("navigateToHome");
        navigate(R.id.navigateToAuthorizedGraph);
    }

    @Override
    public void navigateToPlay() {
//        navigate(R.id.navigateFromHomeToPlay);
    }

    @Override
    public void navigateToStats() {
        navigate(R.id.navigateFromHomeToStats);
    }

    @Override
    public void navigateToRank() {
        navigate(R.id.navigateFromHomeToRank);
    }

    @Override
    public void navigateFromSettingsToWelcome() {
        navigate(R.id.navigateFromSettingsToWelcome);
    }
}
