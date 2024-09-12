package com.audioquiz.data.mapper

import com.audioquiz.domain.model.Chart
import com.audioquiz.remotedatasource.dto.MarketChartResponse
import kotlinx.collections.immutable.toPersistentList

fun MarketChartResponse.toChart(): Chart = Chart(
    prices = prices.map { pair -> Pair(pair[0].toInt(), pair[1]) }.toPersistentList(),
)
