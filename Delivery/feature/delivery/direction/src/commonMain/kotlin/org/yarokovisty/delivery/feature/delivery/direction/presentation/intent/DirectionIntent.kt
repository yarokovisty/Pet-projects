package org.yarokovisty.delivery.feature.delivery.direction.presentation.intent

import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface DirectionIntent : Intent {
    data object Back : DirectionIntent
    data object LoadData : DirectionIntent
    data class SelectDeliveryPoint(val point: DeliveryPoint) :
        DirectionIntent
}
