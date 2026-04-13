package org.yarokovisty.delivery.feature.delivery.direction.presentation.intent

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface DirectionIntent : Intent {
    data object Back : org.yarokovisty.delivery.feature.delivery.direction.presentation.intent.DirectionIntent
    data object LoadData : org.yarokovisty.delivery.feature.delivery.direction.presentation.intent.DirectionIntent
    data class SelectDeliveryPoint(val point: DeliveryPoint) :
        org.yarokovisty.delivery.feature.delivery.direction.presentation.intent.DirectionIntent
}
