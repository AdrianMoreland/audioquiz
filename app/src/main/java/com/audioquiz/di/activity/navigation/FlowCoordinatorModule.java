package com.audioquiz.di.activity.navigation;

import com.audioquiz.feature.home.navigation.HomeFlowCoordinator;
import com.audioquiz.feature.home.navigation.HomeNavigation;
import com.audioquiz.feature.login.presentation.navigation.LoginFlowCoordinator;
import com.audioquiz.feature.login.presentation.navigation.LoginNavigation;
import com.audioquiz.presentation.navigation.MainFlowCoordinator;
import com.audioquiz.presentation.navigation.StartNavigation;

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
