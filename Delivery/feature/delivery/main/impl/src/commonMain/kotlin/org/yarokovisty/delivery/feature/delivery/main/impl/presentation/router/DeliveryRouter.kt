package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.router

import org.yarokovisty.delivery.feature.direction.api.domain.entity.DirectionType
import org.yarokovisty.delivery.feature.direction.api.navigation.DirectionDestination
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

internal class DeliveryRouter(private val globalBackStack: GlobalBackStack) {

    fun openDirectionScreen(directionType: DirectionType) {
        globalBackStack.push(DirectionDestination(directionType))
    }
}
