package org.yarokovisty.delivery.feature.delivery.main.navigation

import org.yarokovisty.common.delivery.direction.domain.entity.DirectionType

interface DeliveryRouter {

    fun openDirectionScreen(directionType: DirectionType)
}
