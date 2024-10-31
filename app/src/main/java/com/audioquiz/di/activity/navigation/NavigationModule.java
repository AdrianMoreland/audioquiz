package com.audioquiz.di.activity.navigation;

import com.audioquiz.presentation.navigation.NavControllerProvider;
import com.audioquiz.ui.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public abstract class NavigationModule {

    @Binds
    @ActivityScoped
    abstract NavControllerProvider bindNavControllerProvider(MainActivity mainActivity);

}
