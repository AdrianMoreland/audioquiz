package com.audioquiz.data.mapper

import com.audioquiz.db.MarketEntity
import com.audioquiz.domain.model.Market
import com.audioquiz.localdatasource.database.FALSE
import com.audioquiz.localdatasource.database.TRUE

fun MarketEntity.toMarket(): Market = Market(
    id = id,
    name = name,
    symbol = symbol,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    imageUrl = imageUrl,
    isFavorite = isFavorite == TRUE,
)

fun Market.toMarketEntity(): MarketEntity = MarketEntity(
    id = id,
    name = name,
    symbol = symbol,
    currentPrice = currentPrice,
    priceChangePercentage24h = priceChangePercentage24h,
    imageUrl = imageUrl,
    isFavorite = if (isFavorite) TRUE else FALSE,
)
