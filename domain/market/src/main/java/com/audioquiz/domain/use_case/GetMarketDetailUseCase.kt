@file:Suppress("PackageNaming", "PackageName", "ktlint:standard:annotation")

package com.audioquiz.domain.use_case

import com.audioquiz.domain.model.MarketDetail
import com.audioquiz.domain.repository.MarketRepository
import com.audioquiz.network.Errors
import com.audioquiz.network.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetMarketDetailUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    open operator fun invoke(id: String): Flow<Resource<MarketDetail, Errors>> = repository.fetchDetail(id = id)
}
