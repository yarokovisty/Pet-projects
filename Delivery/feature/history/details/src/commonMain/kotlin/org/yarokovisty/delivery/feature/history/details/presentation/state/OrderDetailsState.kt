package org.yarokovisty.delivery.feature.history.details.presentation.state

import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.core.common.presentation.State

internal data class OrderDetailsState(
    val loading: Boolean,
    val error: OrderDetailsError?,
    val order: Order?,
    val successfulScreenVisible: Boolean,
    val cancellationScreenVisible: Boolean,
) : State

internal enum class OrderDetailsError {
    LOAD,
    CANCEL
}
