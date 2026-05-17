package org.yarokovisty.delivery.feature.delivery.order.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.ClearConfirmationOrderUseCase
import org.yarokovisty.delivery.common.delivery.order.domain.usecase.GetConfirmationOrderUseCase
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.order.navigation.ConfirmationOrderRouter
import org.yarokovisty.delivery.feature.delivery.order.presentation.event.ConfirmationOrderEvent
import org.yarokovisty.delivery.feature.delivery.order.presentation.intent.ConfirmationOrderIntent
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.ConfirmationOrderState
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.error
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.getDetails
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.initial
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.loading
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.setContent
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter

internal class ConfirmationOrderViewModel(
    private val orderRepository: OrderRepository,
    private val getConfirmationOrderUseCase: GetConfirmationOrderUseCase,
    private val clearConfirmationOrderUseCase: ClearConfirmationOrderUseCase,
    private val phoneNumberFormatter: PhoneNumberFormatter,
    private val router: ConfirmationOrderRouter,
    maxSteps: Int
) : BaseViewModel<ConfirmationOrderState, ConfirmationOrderIntent, ConfirmationOrderEvent>(
    initial(currentStep = CURRENT_STEP, maxSteps = maxSteps)
) {

    private companion object {

        const val CURRENT_STEP = 7
    }

    override fun onIntent(intent: ConfirmationOrderIntent) {
        when (intent) {
            is ConfirmationOrderIntent.Back -> back()
            is ConfirmationOrderIntent.CheckoutOrder -> checkoutOrder()
            is ConfirmationOrderIntent.EditReceiver -> openReceiverScreen()
            is ConfirmationOrderIntent.EditReceiverAddress -> openReceiverAddressScreen()
            is ConfirmationOrderIntent.EditSender -> openSenderScreen()
            is ConfirmationOrderIntent.EditSenderAddress -> openSenderAddressScreen()
            is ConfirmationOrderIntent.LoadData -> loadData()
        }
    }

    private fun back() {
        launch {
            clearConfirmationOrderUseCase()
            router.back()
        }
    }

    private fun checkoutOrder() {
        updateState { loading() }

        launchTrying {
            val content = stateValue.content ?: return@launchTrying
            val confirmationOrder = content.confirmationOrder

            orderRepository.createOrder(confirmationOrder)
            clearConfirmationOrderUseCase()
        } handle { handleError() }
    }

    private fun handleError() {
        updateState { error() }

        launch {
            emitEvent(ConfirmationOrderEvent.CheckoutOrderError)
        }
    }

    private fun openReceiverScreen() {
        router.openReceiverScreen()
    }

    private fun openReceiverAddressScreen() {
        router.openReceiverAddressScreen()
    }

    private fun openSenderScreen() {
        router.openSenderScreen()
    }

    private fun openSenderAddressScreen() {
        router.openSenderAddressScreen()
    }

    private fun loadData() {
        launch {
            val confirmationOrder = getConfirmationOrderUseCase()
            val details = confirmationOrder.getDetails(phoneNumberFormatter)

            updateState { setContent(confirmationOrder, details) }
        }
    }
}
