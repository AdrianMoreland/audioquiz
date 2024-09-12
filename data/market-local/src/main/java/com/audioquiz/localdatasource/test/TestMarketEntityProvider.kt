package com.audioquiz.localdatasource.test

import com.audioquiz.db.MarketEntity
import com.audioquiz.localdatasource.database.FALSE
import com.audioquiz.localdatasource.database.TRUE
import com.audioquiz.localdatasource.dto.RemoteMarketDto

val marketEntity = MarketEntity(
    id = "id",
    name = "name",
    symbol = "symbol",
    currentPrice = 100000.0,
    priceChangePercentage24h = 100000.0,
    imageUrl = "some_shit_url.png",
    isFavorite = FALSE,
)

val favoriteMarketEntity = MarketEntity(
    id = "id",
    name = "name",
    symbol = "symbol",
    currentPrice = 100000.0,
    priceChangePercentage24h = 100000.0,
    imageUrl = "some_shit_url.png",
    isFavorite = TRUE,
)

val remoteMarketDto = RemoteMarketDto(
    id = "id",
    name = "name",
    symbol = "symbol",
    currentPrice = 100000.0,
    priceChangePercentage24h = 100000.0,
    imageUrl = "some_shit_url.png",
)
