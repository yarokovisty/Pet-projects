package org.yarokovisty.delivery.feature.delivery.calculator.presentation.state

import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.core.common.presentation.State

internal data class CalculatorState(
    val skeleton: Boolean,
    val error: Boolean,
    val stepState: StepState,
    val options: List<Option>
) : State

internal data class StepState(
    val progress: Int,
    val maxProgress: Int
)
