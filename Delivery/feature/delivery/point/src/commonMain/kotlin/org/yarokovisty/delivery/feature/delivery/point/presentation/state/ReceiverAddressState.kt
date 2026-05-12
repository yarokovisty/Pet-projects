package org.yarokovisty.delivery.feature.delivery.point.presentation.state

import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.core.common.presentation.State

internal data class ReceiverAddressState(
    val stepState: StepState,
    val contentState: ReceiverAddressContentState
) : State

internal data class ReceiverAddressContentState(
    val street: InputState,
    val house: InputState,
    val apartment: InputState,
    val comment: String,
    val nonContactedState: NonContactedState
)

internal data class NonContactedState(
    val checked: Boolean,
    val tipShowing: Boolean
)
