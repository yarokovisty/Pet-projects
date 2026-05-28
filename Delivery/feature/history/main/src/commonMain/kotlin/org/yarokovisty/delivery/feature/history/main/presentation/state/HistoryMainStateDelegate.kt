package org.yarokovisty.delivery.feature.history.main.presentation.state

import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order

internal fun initial(): HistoryMainState =
    HistoryMainState(
        loading = false,
        error = false,
        orders = emptyList(),
    )

internal fun HistoryMainState.loading(): HistoryMainState =
    copy(error = false, loading = true)

internal fun HistoryMainState.error(): HistoryMainState =
    copy(loading = false, error = true)

internal fun HistoryMainState.content(orders: List<Order>): HistoryMainState =
    copy(loading = false, orders = orders)
