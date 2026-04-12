package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryRouter
import org.yarokovisty.delivery.feature.direction.navigation.DirectionDestination
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

internal class DeliveryRouterImpl(private val globalBackStack: GlobalBackStack) : DeliveryRouter {

    override fun openDirectionScreen(directionType: DirectionType) {
        globalBackStack.push(DirectionDestination(directionType))
    }
}
