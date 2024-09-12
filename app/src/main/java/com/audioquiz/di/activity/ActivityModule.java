package com.audioquiz.di.activity;


import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public abstract class ActivityModule {

    @Provides
    @ActivityScoped
    public static Context provideActivityContext(Activity activity) {
        return activity;
    }
}

