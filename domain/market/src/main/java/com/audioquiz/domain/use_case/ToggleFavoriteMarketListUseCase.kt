@file:Suppress("PackageNaming", "PackageName", "ktlint:standard:annotation")

package com.audioquiz.domain.use_case

import com.audioquiz.domain.model.Market
import com.audioquiz.domain.repository.MarketRepository
import javax.inject.Inject

open class ToggleFavoriteMarketListUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    open suspend operator fun invoke(news: Market) = repository.toggleFavoriteMarket(news)
}
