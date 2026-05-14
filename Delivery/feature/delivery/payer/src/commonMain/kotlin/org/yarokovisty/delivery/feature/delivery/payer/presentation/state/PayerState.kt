package org.yarokovisty.delivery.feature.delivery.payer.presentation.state

import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.core.common.presentation.State

internal data class PayerState(
    val stepState: StepState,
    val content: ContentState,
) : State

internal data class ContentState(
    val selectedPayer: Payer,
    val payers: List<Payer>,
)
