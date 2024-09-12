package com.audioquiz.remotedatasource.api

import io.ktor.client.HttpClient
import io.ktor.http.appendPathSegments
import com.audioquiz.network.ApiResponse
import com.audioquiz.network.get
import com.audioquiz.remotedatasource.dto.MarketChartResponse
import com.audioquiz.remotedatasource.dto.MarketDetailResponse
import com.audioquiz.remotedatasource.dto.MarketResponse
import com.audioquiz.remotedatasource.util.HttpRoutes.COINS
import com.audioquiz.remotedatasource.util.HttpRoutes.DAYS
import com.audioquiz.remotedatasource.util.HttpRoutes.MARKETS
import com.audioquiz.remotedatasource.util.HttpRoutes.MARKET_CHART
import com.audioquiz.remotedatasource.util.HttpRoutes.ORDER
import com.audioquiz.remotedatasource.util.HttpRoutes.PAGE
import com.audioquiz.remotedatasource.util.HttpRoutes.PER_PAGE
import com.audioquiz.remotedatasource.util.HttpRoutes.SPARKLINE
import com.audioquiz.remotedatasource.util.HttpRoutes.VS_CURRENCY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarketsApiImpl @Inject constructor(
    private val httpClient: HttpClient,
) : MarketsApi {
    override suspend fun getMarkets(
        currency: String,
        order: String,
        perPage: Int,
        page: Int,
        sparkline: Boolean,
    ): ApiResponse<List<MarketResponse>> = withContext(Dispatchers.IO) {
        val response = httpClient.get<List<MarketResponse>> {
            url {
                appendPathSegments(COINS, MARKETS)
                parameters.append(VS_CURRENCY, currency)
                parameters.append(ORDER, order)
                parameters.append(PER_PAGE, perPage.toString())
                parameters.append(PAGE, page.toString())
                parameters.append(SPARKLINE, sparkline.toString())
            }
        }
        response
    }

    override suspend fun getMarketChart(
        id: String,
        currency: String,
        days: Int,
    ): ApiResponse<MarketChartResponse> = withContext(Dispatchers.IO) {
        val response = httpClient.get<MarketChartResponse> {
            url {
                appendPathSegments(COINS, id, MARKET_CHART)
                parameters.append(VS_CURRENCY, currency)
                parameters.append(DAYS, days.toString())
            }
        }
        response
    }

    override suspend fun getMarketDetail(id: String): ApiResponse<MarketDetailResponse> =
        withContext(Dispatchers.IO) {
            val response = httpClient.get<MarketDetailResponse> {
                url {
                    appendPathSegments(COINS, id)
                }
            }
            response
        }
}
