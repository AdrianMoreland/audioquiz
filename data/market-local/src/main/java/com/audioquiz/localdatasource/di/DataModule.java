package com.adrian.audioquiz.di.singleton.database;

import android.content.Context;

import com.adrian.api.data.datasources.session.SessionLocal;
import com.adrian.database.cache.SessionCache;

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
