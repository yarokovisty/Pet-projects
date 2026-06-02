package org.yarokovisty.delivery.feature.history.main.presentation.state

import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order

internal fun initial(): HistoryMainState =
    HistoryMainState(
        loading = false,
        error = null,
        orders = emptyList(),
    )

internal fun HistoryMainState.loading(): HistoryMainState =
    copy(error = null, loading = true)

internal fun HistoryMainState.error(error: Error): HistoryMainState =
    copy(loading = false, error = error)

internal fun HistoryMainState.content(orders: List<Order>): HistoryMainState =
    copy(loading = false, orders = orders)
