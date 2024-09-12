package com.audioquiz.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.audioquiz.data.repository.MarketRepositoryImpl
import com.audioquiz.domain.repository.MarketRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindMarketRepository(
        marketsRepository: MarketRepositoryImpl,
    ): MarketRepository
}
