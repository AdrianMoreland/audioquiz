package com.audioquiz.di.activity;

import com.audioquiz.ui.component.bottom_nav_bar.BottomNavigationComponent;
import com.audioquiz.ui.component.bottom_nav_bar.BottomNavigationComponentApi;
import com.audioquiz.ui.component.toolbar.ToolbarComponent;
import com.audioquiz.ui.component.toolbar.ToolbarComponentImpl;
import com.audioquiz.ui.component.toolbar.ToolbarConfiguration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class UiComponentModule {

    @Provides
    public static ToolbarConfiguration provideToolbarConfiguration() {
        return new ToolbarConfiguration.Builder()
                .setVisible(true)
                .setTitle("Default Title")
                .setHasBackNavIcon(true)
                .setHasLogo(false)
                .build();
    }

    @Binds
    public abstract ToolbarComponent bindToolbarComponent(ToolbarComponentImpl impl);

    @Binds
    abstract BottomNavigationComponentApi bindBottomNavigationComponentApi(BottomNavigationComponent bottomNavigationComponent);

}