package com.audioquiz.presentation.navigation.impl;

import androidx.navigation.NavController;

import com.audioquiz.R;
import com.audioquiz.presentation.navigation.NavControllerProvider;

import javax.inject.Inject;

public class LoginNavigationImpl  {

    @Inject
    NavControllerProvider navControllerProvider;

    @Inject
    public LoginNavigationImpl() {}

     public NavController getNavController() {
        return navControllerProvider.getNavController();
    }

     public void navigateToAuthorizedGraph() {
        getNavController().navigate(R.id.navigateToAuthorizedGraph);
    }

}
