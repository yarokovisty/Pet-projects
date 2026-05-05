package org.yarokovisty.delivery.feature.delivery.calculator.presentation.state

import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.presentation.StepState

internal fun initial(currentStep: Int, maxSteps: Int) =
    CalculatorState(
        skeleton = false,
        error = false,
        stepState = StepState(
            progress = currentStep,
            maxProgress = maxSteps
        ),
        options = emptyList()
    )

internal fun CalculatorState.loadingState() =
    copy(skeleton = true, error = false)

internal fun CalculatorState.errorState() =
    copy(skeleton = false, error = true)

internal fun CalculatorState.contentState(options: List<Option>) =
    copy(skeleton = false, options = options)
