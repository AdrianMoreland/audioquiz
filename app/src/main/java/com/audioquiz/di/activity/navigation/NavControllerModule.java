package com.audioquiz.di.activity.navigation;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.adrian.audioquiz.R;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;

@Module
@InstallIn(ActivityComponent.class)
public class NavControllerModule {

    @Provides
    public NavHostFragment provideNavHostFragment(@ActivityContext Context context) {
        NavHostFragment navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment == null) {
            throw new IllegalStateException("NavHostFragment not found");
        }
        return navHostFragment;
    }

    @Provides
    public NavController provideNavController(NavHostFragment navHostFragment) {
        return navHostFragment.getNavController();
    }
}