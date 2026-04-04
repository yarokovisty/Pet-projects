package org.yarokovisty.delivery.feature.direction.impl.presentation.intent

import org.yarokovisty.delivery.common.presentation.Intent
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint

internal sealed interface DirectionIntent : Intent {
    data object Back : DirectionIntent
    data object LoadData : DirectionIntent
    data class SelectDeliveryPoint(val point: DeliveryPoint) : DirectionIntent
}
