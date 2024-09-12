@file:Suppress("TopLevelPropertyNaming")

package com.audioquiz.remotedatasource.api

import com.audioquiz.network.ApiResponse
import com.audioquiz.remotedatasource.dto.MarketChartResponse
import com.audioquiz.remotedatasource.dto.MarketDetailResponse
import com.audioquiz.remotedatasource.dto.MarketResponse

interface MarketsApi {
    suspend fun getMarkets(
        currency: String,
        order: String,
        perPage: Int,
        page: Int,
        sparkline: Boolean,
    ): ApiResponse<List<MarketResponse>>

    suspend fun getMarketChart(
        id: String,
        currency: String,
        days: Int,
    ): ApiResponse<MarketChartResponse>

    suspend fun getMarketDetail(id: String): ApiResponse<MarketDetailResponse>
}
