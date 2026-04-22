package org.yarokovisty.delivery.util.kotlin.number

private const val NUM_OF_KOPECKS_IN_RUB = 100

fun Int.toRubles(): Double =
    this.toDouble() / NUM_OF_KOPECKS_IN_RUB
