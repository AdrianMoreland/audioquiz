package com.audioquiz.remotedatasource.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.audioquiz.remotedatasource.api.MarketsApi
import com.audioquiz.remotedatasource.api.MarketsApiImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    abstract fun bindMarketsApi(marketsApi: MarketsApiImpl): MarketsApi
}
