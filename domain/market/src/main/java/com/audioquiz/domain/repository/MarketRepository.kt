package com.audioquiz.domain.repository

import com.audioquiz.domain.model.Chart
import com.audioquiz.domain.model.Market
import com.audioquiz.domain.model.MarketDetail
import com.audioquiz.network.Errors
import com.audioquiz.network.Resource
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    fun getMarketList(): Flow<List<Market>>
    fun getFavoriteMarketList(): Flow<List<Market>>
    suspend fun syncMarketList()
    suspend fun toggleFavoriteMarket(oldMarket: Market)
    fun fetchChart(id: String): Flow<Resource<Chart, Errors>>
    fun fetchDetail(id: String): Flow<Resource<MarketDetail, Errors>>
}
