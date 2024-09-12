@file:Suppress("PackageNaming", "PackageName")

package com.audioquiz.domain.use_case

import com.audioquiz.domain.model.Market
import com.audioquiz.domain.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMarketListUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    operator fun invoke(): Flow<List<Market>> = repository.getFavoriteMarketList()
}
