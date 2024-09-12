package com.audioquiz.domain.model

import kotlinx.collections.immutable.PersistentList

data class Chart(
    val prices: PersistentList<Pair<Int, Double>>,
)
