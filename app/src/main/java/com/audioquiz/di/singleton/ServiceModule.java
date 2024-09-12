package com.audioquiz.di.singleton;

import com.adrian.usecase.service.FrequencyPlayer;
import com.adrian.common.services.FrequencyPlayerImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ServiceModule {

    @Provides
    static FrequencyPlayer provideFrequencyPlayer() {
        return new FrequencyPlayerImpl();
    }

}
