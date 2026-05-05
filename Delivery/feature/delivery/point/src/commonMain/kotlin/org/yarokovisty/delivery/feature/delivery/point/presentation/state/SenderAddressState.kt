package org.yarokovisty.delivery.feature.delivery.point.presentation.state

import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.core.common.presentation.State

internal data class SenderAddressState(
    val stepState: StepState,
    val contentState: SenderAddressContentState
) : State

internal data class SenderAddressContentState(
    val street: InputState,
    val house: InputState,
    val apartment: InputState,
    val comment: String
)
