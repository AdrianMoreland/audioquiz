package com.audioquiz.presentation.navigation;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.adrian.audioquiz.R;
import com.adrian.audioquiz.presentation.viewmodel.MainViewModel;
import com.adrian.home.presentation.navigation.HomeNavigation;
import com.adrian.login.presentation.navigation.LoginNavigation;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class Navigator implements GlobalNavigation, StartNavigation, LoginNavigation, HomeNavigation {
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
        Log.d(TAG, "Navigator initialized with NavController: " + navController);
        AppCompatActivity activity = (AppCompatActivity) context;
        mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
        observeNavigationChanges();
    }

    private void observeNavigationChanges() {
        mainViewModel.getNavControllerLiveData().observeForever(controller -> {
            if (controller != null) {
                Log.d(TAG, "NavController updated: " + controller);
                navController = controller;
            }
        });
    }

    @Override
    public NavController getNavController() {
        return navController;
    }

    private void navigate (int actionId) {
        if (navController != null) {
            Log.d(TAG, "Navigating to actionId: " + actionId + " with NavController: " + navController);
            navController.navigate(actionId);
            return;
        }
        Log.e(TAG, "NavController is null when trying to navigate");
    }

    @Override
    public void navigateToAuthorizedGraph() {
        Log.d(TAG, "navigateToAuthorizedGraph");
         navigate(R.id.navigateToAuthorizedGraph);
    }

    @Override
    public void navigateToUnauthorizedGraph() {
        Log.d(TAG, "navigateToUnauthorizedGraph");
        navigate(R.id.navigateToUnauthorizedGraph);
    }

    @Override
    public void navigateHomeToSettings() {
        navigate(R.id.navigateFromHomeToSettings);
    }

    @Override
    public void navigateToHome() {
        Log.d(TAG, "navigateToHome");
        navigate(R.id.navigateToAuthorizedGraph);
    }

    @Override
    public void navigateToPlay() {
        navigate(R.id.navigateFromHomeToPlay);
    }

    @Override
    public void navigateToStats() {
        navigate(R.id.navigateFromHomeToStats);
    }

    @Override
    public void navigateToRank() {
        navigate(R.id.navigateFromHomeToRank);
    }
}
