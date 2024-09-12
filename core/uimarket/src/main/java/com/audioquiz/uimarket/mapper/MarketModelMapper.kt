package com.audioquiz.uimarket.mapper

import com.audioquiz.domain.model.Market
import com.audioquiz.uimarket.model.MarketModel

fun MarketModel.toMarket() = Market(
    id = id,
    name = name,
    symbol = symbol,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    imageUrl = imageUrl,
    isFavorite = isFavorite,
)

fun Market.toMarketModel() = MarketModel(
    id = id,
    name = name,
    symbol = symbol,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    imageUrl = imageUrl,
    isFavorite = isFavorite,
)
