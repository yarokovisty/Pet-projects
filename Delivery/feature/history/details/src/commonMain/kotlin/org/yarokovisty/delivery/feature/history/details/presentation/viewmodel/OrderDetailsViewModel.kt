package org.yarokovisty.delivery.feature.history.details.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository
import org.yarokovisty.delivery.core.common.error.NetworkException
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.history.details.navigation.OrderDetailsRouter
import org.yarokovisty.delivery.feature.history.details.presentation.intent.OrderDetailsIntent
import org.yarokovisty.delivery.feature.history.details.presentation.state.Error
import org.yarokovisty.delivery.feature.history.details.presentation.state.OrderDetailsState
import org.yarokovisty.delivery.feature.history.details.presentation.state.cancellationSuccess
import org.yarokovisty.delivery.feature.history.details.presentation.state.content
import org.yarokovisty.delivery.feature.history.details.presentation.state.error
import org.yarokovisty.delivery.feature.history.details.presentation.state.initial
import org.yarokovisty.delivery.feature.history.details.presentation.state.loading
import org.yarokovisty.delivery.feature.history.details.presentation.state.updateCancellationScreenVisibility

internal class OrderDetailsViewModel(
    private val orderRepository: OrderRepository,
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
            OrderDetailsIntent.OpenLoginScreen -> openLoginScreen()
        }
    }

    private fun loadData() {
        updateState { loading() }

        launchTrying {
            val order = orderRepository.get(orderId)
            updateState { content(order) }
        } handle ::handleLoadError
    }

    private fun handleLoadError(throwable: Throwable) {
        val error = when (throwable) {
            is NetworkException.Unauthorized -> Error.Unauthorized
            else -> Error.Load
        }
        updateState { error(error) }
    }

    private fun cancelOrder() {
        updateState {
            copy(loading = true, cancellationScreenVisible = false)
        }

        launchTrying {
            orderRepository.cancel(orderId)
            updateState { cancellationSuccess() }
        } handle { handleCancelError() }
    }

    private fun handleCancelError() {
        updateState { error(Error.Cancel) }
    }

    private fun changeCancellationScreenVisibility(visible: Boolean) {
        updateState { updateCancellationScreenVisibility(visible) }
    }

    private fun back() {
        router.back()
    }

    private fun openLoginScreen() {
        router.openLoginScreen()
    }
}
