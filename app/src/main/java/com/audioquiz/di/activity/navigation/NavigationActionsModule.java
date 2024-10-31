package com.audioquiz.di.activity.navigation;

import com.audioquiz.feature.home.navigation.HomeNavigation;
import com.audioquiz.feature.login.presentation.navigation.LoginNavigation;
import com.audioquiz.feature.settings.presentation.navigation.SettingsNavigation;
import com.audioquiz.presentation.navigation.GlobalNavigation;
import com.audioquiz.presentation.navigation.Navigator;
import com.audioquiz.presentation.navigation.StartNavigation;
import com.audioquiz.presentation.navigation.impl.GlobalNavigationImpl;

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

    @Binds
    abstract SettingsNavigation bindSettingsNavigation(Navigator navigator);

}
