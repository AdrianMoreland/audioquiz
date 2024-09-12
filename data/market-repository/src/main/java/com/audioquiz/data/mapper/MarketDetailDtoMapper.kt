package com.audioquiz.data.mapper

import com.audioquiz.domain.model.MarketDetail
import com.audioquiz.domain.model.MarketDetail.MarketData.High24h
import com.audioquiz.domain.model.MarketDetail.MarketData.Low24h
import com.audioquiz.domain.model.MarketDetail.MarketData.MarketCap
import com.audioquiz.remotedatasource.dto.MarketDetailResponse
import com.audioquiz.remotedatasource.dto.MarketDetailResponse.MarketData

fun MarketDetailResponse.toDetail(): MarketDetail = MarketDetail(
    id = id,
    name = name,
    marketData = marketData?.toMarketData(),
    marketCapRank = marketCapRank,
)

fun MarketData.toMarketData(): MarketDetail.MarketData = MarketDetail.MarketData(
    high24h = high24h?.toHigh24(),
    low24h = low24h?.toLow24(),
    marketCap = marketCap?.toMarketCap(),
    marketCapRank = marketCapRank,
)

fun MarketData.High24h.toHigh24(): High24h = High24h(
    usd = usd,
)

fun MarketData.Low24h.toLow24(): Low24h = Low24h(
    usd = usd,
)

fun MarketData.MarketCap.toMarketCap(): MarketCap = MarketCap(
    usd = usd,
)
