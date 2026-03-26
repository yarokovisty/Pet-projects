package org.yarokovisty.delivery.feature.direction.impl.presentation.intent

import org.yarokovisty.delivery.common.presentation.Intent
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint

internal sealed interface DirectionIntent : Intent {
    object Back : DirectionIntent
    object LoadData : DirectionIntent
    data class SelectDeliveryPoint(val point: DeliveryPoint) : DirectionIntent
}
