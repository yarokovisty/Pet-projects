package org.yarokovisty.delivery.feature.delivery.calculator.presentation.intent

import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface CalculatorIntent : Intent {

    data object Back : CalculatorIntent
    data object LoadData : CalculatorIntent
    data class SelectOption(
        val option: Option
    ) : CalculatorIntent
}
