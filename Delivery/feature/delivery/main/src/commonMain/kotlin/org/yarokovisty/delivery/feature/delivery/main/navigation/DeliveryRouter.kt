package org.yarokovisty.delivery.feature.delivery.main.navigation

import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DirectionType

interface DeliveryRouter {

    fun openDirectionScreen(directionType: DirectionType)

    fun openCalculatorScreen(
        parcelInfo: ParcelInfo,
        senderPoint: DeliveryPoint,
        receiverPoint: DeliveryPoint
    )
}
