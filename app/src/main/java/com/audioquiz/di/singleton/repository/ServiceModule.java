package com.audioquiz.di.singleton.repository;

import com.audioquiz.core.domain.service.FrequencyPlayer;
import com.audioquiz.data.services.FrequencyPlayerImpl;

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
