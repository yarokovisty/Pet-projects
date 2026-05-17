package org.yarokovisty.delivery.feature.delivery.order.presentation.state

import org.yarokovisty.delivery.common.delivery.order.domain.entity.ConfirmationOrder
import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.core.common.presentation.State

internal data class ConfirmationOrderState(
    val stepState: StepState,
    val content: ContentState?
) : State

internal data class ContentState(
    val confirmationOrder: ConfirmationOrder,
    val details: List<DetailItem>,
    val loading: Boolean,
)

internal data class DetailItem(
    val type: DetailType,
    val title: String,
    val subtitle1: String,
    val description1: String,
    val subtitle2: String,
    val description2: String
)

internal enum class DetailType {
    RECEIVER,
    SENDER,
    SENDER_ADDRESS,
    RECEIVER_ADDRESS
}
