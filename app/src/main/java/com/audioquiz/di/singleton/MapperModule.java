package com.audioquiz.di.singleton;

import com.adrian.data.mapper.INetworkMapper;
import com.adrian.data.mapper.NetworkMapper;
import com.adrian.database.mapper.DatabaseMapper;
import com.adrian.database.mapper.IDatabaseMapper;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class MapperModule {

    @Binds
    abstract IDatabaseMapper bindDatabaseMapper(DatabaseMapper impl);

    @Binds
    abstract INetworkMapper bindNetworkMapper(NetworkMapper impl);

}
