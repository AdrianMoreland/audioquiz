package com.audioquiz.remotedatasource.test

import com.audioquiz.network.ApiResponse
import com.audioquiz.remotedatasource.api.MarketsApi
import com.audioquiz.remotedatasource.dto.MarketChartResponse
import com.audioquiz.remotedatasource.dto.MarketDetailResponse
import com.audioquiz.remotedatasource.dto.MarketResponse

class FakeMarketsApi : MarketsApi {
    override suspend fun getMarkets(
        currency: String,
        order: String,
        perPage: Int,
        page: Int,
        sparkline: Boolean,
    ): ApiResponse<List<MarketResponse>> = ApiResponse.Success(listOf(marketDto))

    override suspend fun getMarketChart(
        id: String,
        currency: String,
        days: Int,
    ): ApiResponse<MarketChartResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getMarketDetail(id: String): ApiResponse<MarketDetailResponse> {
        TODO("Not yet implemented")
    }
}
