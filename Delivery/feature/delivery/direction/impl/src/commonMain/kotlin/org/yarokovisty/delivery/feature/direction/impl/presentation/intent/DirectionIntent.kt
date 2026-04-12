package org.yarokovisty.delivery.feature.direction.impl.presentation.intent

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface DirectionIntent : Intent {
    data object Back : DirectionIntent
    data object LoadData : DirectionIntent
    data class SelectDeliveryPoint(val point: DeliveryPoint) : DirectionIntent
}
