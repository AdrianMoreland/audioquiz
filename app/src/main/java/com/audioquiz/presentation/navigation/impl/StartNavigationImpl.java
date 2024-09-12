package com.audioquiz.presentation.navigation.impl;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.adrian.audioquiz.R;
import com.adrian.audioquiz.presentation.navigation.NavControllerProvider;
import com.adrian.audioquiz.presentation.viewmodel.MainViewModel;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ActivityContext;

public class StartNavigationImpl {

    private final NavControllerProvider navControllerProvider;
    private NavController navController;
    private final MainViewModel mainViewModel;


    @Inject
    public StartNavigationImpl(NavControllerProvider navControllerProvider,
                               @ActivityContext Context context) {
        this.navControllerProvider = navControllerProvider;
        AppCompatActivity activity = (AppCompatActivity) context;
        mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);
     //   observeNavigationChanges();
    }


/*    private void observeNavigationChanges() {
        mainViewModel.getNavControllerLiveData().observeForever(controller -> {
            if (controller != null) {
                Log.d("StartNavigationImpl", "NavController updated: " + controller);
                navController = controller;
            }
        });
    }*/

    public void navigateToAuthorizedGraph() {
    //    NavController navController = navControllerProvider.getNavController();
        if (navController == null) {
            Log.e("StartNavigationImpl", "NavController is null when trying to navigateToAuthorizedGraph");
            return;
        }
        Log.d("StartNavigationImpl", "Navigating to authorized graph with NavController: " + navController);
        navController.navigate(R.id.navigateToAuthorizedGraph);
    }



    public void navigateToUnauthorizedGraph() {
        navController.navigate(R.id.navigateToUnauthorizedGraph);
    }

}