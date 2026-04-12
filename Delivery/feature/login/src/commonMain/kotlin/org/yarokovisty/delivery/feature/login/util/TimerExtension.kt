package org.yarokovisty.delivery.feature.login.util

private const val MILLISECONDS_PER_SECOND = 1000L

internal fun Long.toSecondRounded(): Long {
    return this / MILLISECONDS_PER_SECOND
}
