@file:Suppress("PackageNaming", "PackageName")

package com.audioquiz.domain.use_case

import com.audioquiz.domain.repository.MarketRepository
import javax.inject.Inject

class SyncMarketListUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    suspend operator fun invoke() = repository.syncMarketList()
}
