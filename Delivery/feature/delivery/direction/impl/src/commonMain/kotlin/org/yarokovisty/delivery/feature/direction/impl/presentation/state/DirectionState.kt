package org.yarokovisty.delivery.feature.direction.impl.presentation.state

import org.yarokovisty.delivery.common.presentation.State
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DirectionType

internal data class DirectionState(
    val directionType: DirectionType,
    val loading: Boolean,
    val error: Boolean,
    val content: DirectionContentState?
) : State {

    companion object {
        fun initial(directionType: DirectionType) =
            DirectionState(
                directionType = directionType,
                loading = false,
                error = false,
                content = null
            )
    }
}

internal data class DirectionContentState(
    val deliveryPoints: List<DeliveryPoint>
)
