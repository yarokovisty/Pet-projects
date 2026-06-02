package org.yarokovisty.delivery.feature.history.details.presentation.state

import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order

internal fun initial(): OrderDetailsState =
    OrderDetailsState(
        loading = false,
        error = null,
        order = null,
        successfulScreenVisible = false,
        cancellationScreenVisible = false,
    )

internal fun OrderDetailsState.loading(): OrderDetailsState =
    copy(loading = true, error = null)

internal fun OrderDetailsState.error(error: Error): OrderDetailsState =
    copy(loading = false, error = error)

internal fun OrderDetailsState.content(order: Order) =
    copy(loading = false, order = order)

internal fun OrderDetailsState.updateCancellationScreenVisibility(visible: Boolean): OrderDetailsState =
    copy(cancellationScreenVisible = visible)

internal fun OrderDetailsState.cancellationSuccess(): OrderDetailsState =
    copy(loading = false, successfulScreenVisible = true)
