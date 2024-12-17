package com.audioquiz.designsystem.di;


import com.audioquiz.designsystem.adapters.BindingImageFromUrl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UiModule {

    @Provides
    public BindingImageFromUrl provideBindingImageFromUrl() {
        return new BindingImageFromUrl();
    }

}