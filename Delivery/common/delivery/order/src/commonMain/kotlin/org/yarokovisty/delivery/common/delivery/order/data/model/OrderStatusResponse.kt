package org.yarokovisty.delivery.common.delivery.order.data.model

internal object OrderStatusResponse {
    const val CREATED = 0
    const val WAITING = 1
    const val DELIVERING = 2
    const val DELIVERED = 3
    const val CANCELLED = 4
}
