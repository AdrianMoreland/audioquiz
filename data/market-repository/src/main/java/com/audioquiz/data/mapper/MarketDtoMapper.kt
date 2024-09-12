package com.audioquiz.data.mapper

import com.audioquiz.domain.model.Market
import com.audioquiz.localdatasource.dto.RemoteMarketDto
import com.audioquiz.remotedatasource.dto.MarketResponse

fun MarketResponse.toRemoteMarketDto(): RemoteMarketDto = RemoteMarketDto(
    id = id,
    name = name,
    symbol = symbol,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    imageUrl = imageUrl,
)

fun MarketResponse.toMarket(): Market = Market(
    id = id,
    name = name,
    symbol = symbol,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    imageUrl = imageUrl,
)
