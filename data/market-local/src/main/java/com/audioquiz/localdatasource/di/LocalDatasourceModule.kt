package com.audioquiz.localdatasource.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.audioquiz.db.MarketDatabase
import com.audioquiz.localdatasource.database.MarketDao
import com.audioquiz.localdatasource.database.MarketDaoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatasourceModule {

    @Singleton
    @Provides
    fun provideMarketsDatabase(app: Application): MarketDatabase {
        val driver: SqlDriver = AndroidSqliteDriver(MarketDatabase.Schema, app, "MarketDatabase")
        return MarketDatabase(driver)
    }

    @Provides
    fun provideMarketsDao(dao: MarketDaoImpl): MarketDao = dao
}
