package org.yarokovisty.delivery.feature.delivery.payer.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.common.delivery.payer.domain.repository.PayerRepository
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.payer.navigation.PayerRouter
import org.yarokovisty.delivery.feature.delivery.payer.presentation.intent.PayerIntent
import org.yarokovisty.delivery.feature.delivery.payer.presentation.state.PayerState
import org.yarokovisty.delivery.feature.delivery.payer.presentation.state.changePayer
import org.yarokovisty.delivery.feature.delivery.payer.presentation.state.initial

internal class PayerViewModel(
    private val payerRepository: PayerRepository,
    private val router: PayerRouter,
    maxSteps: Int,
) : BaseViewModel<PayerState, PayerIntent, Nothing>(
    initial(currentStep = CURRENT_STEP, maxSteps = maxSteps)
) {

    private companion object {

        const val CURRENT_STEP = 6
    }

    override fun onIntent(intent: PayerIntent) {
        when (intent) {
            is PayerIntent.Back -> back()
            is PayerIntent.ClickContinue -> nextStep()
            is PayerIntent.SelectPayer -> selectPayer(intent.payer)
        }
    }

    private fun back() {
        router.back()
    }

    private fun nextStep() {
        val payer = stateValue.content.selectedPayer

        launch {
            payerRepository.setPayer(payer)
            router.openConfirmationOrderScreen()
        }
    }

    private fun selectPayer(payer: Payer) {
        updateState { changePayer(payer) }
    }
}
