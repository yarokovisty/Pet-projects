package org.yarokovisty.delivery.feature.direction.impl.presentation.state

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.core.common.presentation.State
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DirectionType

internal data class DirectionState(
    val directionType: DirectionType,
    val loading: Boolean,
    val error: Boolean,
    val content: DirectionContentState?
) : State

internal data class DirectionContentState(
    val deliveryPoints: List<DeliveryPoint>
)
