package org.yarokovisty.delivery.feature.history.main.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.history.main.navigation.HistoryMainRouter
import org.yarokovisty.delivery.feature.history.main.presentation.intent.HistoryMainIntent
import org.yarokovisty.delivery.feature.history.main.presentation.state.HistoryMainState
import org.yarokovisty.delivery.feature.history.main.presentation.state.content
import org.yarokovisty.delivery.feature.history.main.presentation.state.error
import org.yarokovisty.delivery.feature.history.main.presentation.state.initial
import org.yarokovisty.delivery.feature.history.main.presentation.state.loading

internal class HistoryMainViewModel(
    private val orderRepository: OrderRepository,
    private val router: HistoryMainRouter,
) : BaseViewModel<HistoryMainState, HistoryMainIntent, Nothing>(initial()) {

    override fun onIntent(intent: HistoryMainIntent) {
        when (intent) {
            is HistoryMainIntent.LoadData -> loadData()
            is HistoryMainIntent.OpenOrderDetails -> openOrderDetails(intent.orderId)
        }
    }

    private fun loadData() {
        updateState { loading() }

        launchTrying {
            val historyOrders = orderRepository.getHistory()
            updateState { content(historyOrders) }
        } handle { handleError() }
    }

    // TODO добавить обработку Unauthorized
    private fun handleError() {
        updateState { error() }
    }

    private fun openOrderDetails(orderId: String) {
        router.openOrderDetailsScreen(orderId)
    }
}
