package com.audioquiz.admin.di;

import com.audioquiz.admin.data.MockData;
import com.audioquiz.admin.domain.IMockData;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AdminModule {
    @Binds
    abstract IMockData bindMockData(MockData mockData);
}
