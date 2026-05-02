package org.yarokovisty.delivery.feature.delivery.calculator.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.calculator.domain.repository.CalculatorRepository
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.calculator.navigation.CalculatorRouter
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.intent.CalculatorIntent
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.state.CalculatorState
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.state.contentState
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.state.errorState
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.state.initial
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.state.loadingState

internal class CalculatorViewModel(
    private val calculatorRepository: CalculatorRepository,
    private val router: CalculatorRouter,
    private val parcelInfo: ParcelInfo,
    private val senderPoint: DeliveryPoint,
    private val receiverPoint: DeliveryPoint,
    maxSteps: Int
) : BaseViewModel<CalculatorState, CalculatorIntent, Nothing>(
    initial(CURRENT_STEP, maxSteps)
) {

    private companion object {

        const val CURRENT_STEP = 1
    }

    init {
        loadData()
    }

    override fun onIntent(intent: CalculatorIntent) {
        when (intent) {
            is CalculatorIntent.Back -> handleBack()
            is CalculatorIntent.LoadData -> loadData()
            is CalculatorIntent.SelectOption -> selectOption(intent.option)
        }
    }

    private fun handleBack() {
        router.back()
    }

    private fun loadData() {
        updateState { loadingState() }

        launchTrying {
            val options = calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
            updateState { contentState(options) }
        } handle { handleCalculateError() }
    }

    private fun handleCalculateError() {
        updateState { errorState() }
    }

    private fun selectOption(option: Option) {
        launch {
            calculatorRepository.setOption(option)
            router.openReceiverScreen()
        }
    }
}
