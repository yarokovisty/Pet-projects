package org.yarokovisty.delivery.feature.history.details.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.order.domain.usecase.CancelOrderUseCase
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.GetOrderUseCase
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.history.details.navigation.OrderDetailsRouter
import org.yarokovisty.delivery.feature.history.details.presentation.intent.OrderDetailsIntent
import org.yarokovisty.delivery.feature.history.details.presentation.state.OrderDetailsError
import org.yarokovisty.delivery.feature.history.details.presentation.state.OrderDetailsState
import org.yarokovisty.delivery.feature.history.details.presentation.state.cancellationSuccess
import org.yarokovisty.delivery.feature.history.details.presentation.state.content
import org.yarokovisty.delivery.feature.history.details.presentation.state.error
import org.yarokovisty.delivery.feature.history.details.presentation.state.initial
import org.yarokovisty.delivery.feature.history.details.presentation.state.loading
import org.yarokovisty.delivery.feature.history.details.presentation.state.updateCancellationScreenVisibility

internal class OrderDetailsViewModel(
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val router: OrderDetailsRouter,
    private val orderId: String,
) : BaseViewModel<OrderDetailsState, OrderDetailsIntent, Nothing>(initial()) {

    init {
        loadData()
    }

    override fun onIntent(intent: OrderDetailsIntent) {
        when (intent) {
            OrderDetailsIntent.Back -> back()
            OrderDetailsIntent.CloseCancellationScreen -> changeCancellationScreenVisibility(false)
            OrderDetailsIntent.ConfirmCancellation -> cancelOrder()
            OrderDetailsIntent.OpenCancellationScreen -> changeCancellationScreenVisibility(true)
            OrderDetailsIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
        updateState { loading() }

        launchTrying {
            val order = getOrderUseCase(orderId)
            updateState { content(order) }
        } handle { handleError(OrderDetailsError.LOAD) }
    }

    private fun cancelOrder() {
        updateState {
            copy(loading = true, cancellationScreenVisible = false)
        }

        launchTrying {
            cancelOrderUseCase(orderId)
            updateState { cancellationSuccess() }
        } handle { handleError(OrderDetailsError.CANCEL) }
    }

    private fun handleError(error: OrderDetailsError) {
        updateState { error(error) }
    }

    private fun changeCancellationScreenVisibility(visible: Boolean) {
        updateState { updateCancellationScreenVisibility(visible) }
    }

    private fun back() {
        router.back()
    }
}
