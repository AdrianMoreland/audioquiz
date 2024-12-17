package com.audioquiz.feature.home.di;

import com.audioquiz.feature.home.view.HomeFragment;
import com.audioquiz.feature.home.view.factory.HomeFragmentFactory;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.scopes.FragmentScoped;

@Module
@InstallIn(FragmentComponent.class) // Or FragmentComponent
public class HomeModule {

    @Provides
    @FragmentScoped
    HomeFragment provideHomeFragment() {
        return new HomeFragment();
    }

    @Provides
    public static HomeFragmentFactory provideHomeFragmentFactory(HomeFragmentFactory factory) {
        return factory;
    }
}
