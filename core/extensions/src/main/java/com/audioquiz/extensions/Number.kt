

package com.audioquiz.extensions

fun Number.roundToTwoDecimalPlaces(): String {
    return String.format("%.2f", this)
}
