package org.yarokovisty.delivery.feature.direction.presentation.state

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.delivery.core.common.presentation.State

internal data class DirectionState(
    val directionType: DirectionType,
    val loading: Boolean,
    val error: Boolean,
    val content: DirectionContentState?
) : State

internal data class DirectionContentState(
    val deliveryPoints: List<DeliveryPoint>
)
