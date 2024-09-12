package com.audioquiz.di.activity.navigation;

import com.adrian.audioquiz.presentation.navigation.GlobalNavigation;
import com.adrian.audioquiz.presentation.navigation.Navigator;
import com.adrian.audioquiz.presentation.navigation.StartNavigation;
import com.adrian.audioquiz.presentation.navigation.impl.GlobalNavigationImpl;
import com.adrian.home.presentation.navigation.HomeNavigation;
import com.adrian.login.presentation.navigation.LoginNavigation;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class NavigationActionsModule {

    @Binds
    public abstract GlobalNavigation bindGlobalNavigationActions(GlobalNavigationImpl navigator);

    @Binds
    abstract StartNavigation bindStartNavigation(Navigator navigator);

    @Binds
    abstract LoginNavigation bindLoginNavigation(Navigator navigator);

    @Binds
    abstract HomeNavigation bindHomeNavigation(Navigator navigator);

}
