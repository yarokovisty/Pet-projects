package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.feature.delivery.calculator.navigation.CalculatorDestination
import org.yarokovisty.delivery.feature.delivery.direction.navigation.DirectionDestination
import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

internal class DeliveryRouterImpl(private val globalBackStack: GlobalBackStack) : DeliveryRouter {

    override fun openDirectionScreen(directionType: DirectionType) {
        globalBackStack.push(
            DirectionDestination(
                directionType
            )
        )
    }

    override fun openCalculatorScreen(
        parcelInfo: ParcelInfo,
        senderPoint: DeliveryPoint,
        receiverPoint: DeliveryPoint
    ) {
        globalBackStack.push(CalculatorDestination(parcelInfo, senderPoint, receiverPoint))
    }
}
