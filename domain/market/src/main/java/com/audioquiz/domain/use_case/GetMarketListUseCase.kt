@file:Suppress("PackageNaming", "PackageName")

package com.audioquiz.domain.use_case

import com.audioquiz.domain.model.Market
import com.audioquiz.domain.repository.MarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMarketListUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    suspend operator fun invoke(): Flow<List<Market>> {
        repository.syncMarketList()
        return repository.getMarketList()
    }
}
