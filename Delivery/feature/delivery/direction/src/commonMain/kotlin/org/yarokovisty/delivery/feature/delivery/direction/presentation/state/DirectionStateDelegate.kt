package org.yarokovisty.delivery.feature.delivery.direction.presentation.state

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.common.delivery.direction.domain.entity.DirectionType

internal fun initial(directionType: DirectionType) =
    DirectionState(
        directionType = directionType,
        loading = false,
        error = false,
        content = null
    )

internal fun DirectionState.loadingState() =
    copy(loading = true, error = false)

internal fun DirectionState.errorState() =
    copy(loading = false, error = true)

internal fun DirectionState.contentState(deliveryPoints: List<DeliveryPoint>) =
    copy(
        loading = false,
        content = DirectionContentState(deliveryPoints)
    )
