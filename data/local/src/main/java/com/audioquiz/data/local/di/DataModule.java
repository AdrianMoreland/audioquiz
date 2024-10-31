package com.audioquiz.data.local.di;


import android.content.Context;

import com.audioquiz.api.datasources.session.SessionLocal;
import com.audioquiz.data.local.cache.SessionCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Singleton
    @Provides
    SessionLocal providesSessionLocal(@ApplicationContext Context context) {
        return new SessionCache(context);
    }

}
