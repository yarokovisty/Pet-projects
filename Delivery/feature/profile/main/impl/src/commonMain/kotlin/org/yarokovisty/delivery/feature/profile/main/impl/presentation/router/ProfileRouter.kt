package org.yarokovisty.delivery.feature.profile.main.impl.presentation.router

import org.yarokovisty.delivery.feature.direction.api.domain.entity.DirectionType
import org.yarokovisty.delivery.feature.direction.api.navigation.DirectionDestination
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

internal class ProfileRouter(private val globalBackStack: GlobalBackStack) {

    fun openDirectionScreen() {
        globalBackStack.push(DirectionDestination(DirectionType.FROM))
    }
}
