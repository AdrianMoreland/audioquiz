package com.audioquiz.di.activity.navigation;

import com.adrian.audioquiz.presentation.navigation.StartNavigation;
import com.adrian.home.presentation.navigation.HomeFlowCoordinator;
import com.adrian.home.presentation.navigation.HomeNavigation;
import com.adrian.login.presentation.navigation.LoginFlowCoordinator;
import com.adrian.login.presentation.navigation.LoginNavigation;
import com.adrian.audioquiz.presentation.navigation.MainFlowCoordinator;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class FlowCoordinatorModule {

    @Provides
    public static MainFlowCoordinator provideMainFlowCoordinator(StartNavigation startNavigation) {
        return new MainFlowCoordinator(startNavigation);
    }

    @Provides
    public static LoginFlowCoordinator provideLoginFlowCoordinator(LoginNavigation loginNavigation) {
        return new LoginFlowCoordinator(loginNavigation);
    }

    @Provides
    public static HomeFlowCoordinator provideHomeFlowCoordinator(HomeNavigation homeNavigation) {
        return new HomeFlowCoordinator(homeNavigation);
    }
}
