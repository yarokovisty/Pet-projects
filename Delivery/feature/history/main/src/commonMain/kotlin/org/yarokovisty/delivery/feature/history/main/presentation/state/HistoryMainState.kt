package org.yarokovisty.delivery.feature.history.main.presentation.state

import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.core.common.presentation.State

internal data class HistoryMainState(
    val loading: Boolean,
    val error: Error?,
    val orders: List<Order>,
) : State

internal sealed interface Error {

    object Unauthorized : Error
    object Unknown : Error
}
