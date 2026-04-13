package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.delivery.feature.delivery.direction.navigation.DirectionDestination
import org.yarokovisty.delivery.feature.profile.main.navigation.ProfileRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

internal class ProfileRouterImpl(private val globalBackStack: GlobalBackStack) : ProfileRouter {

    override fun openDirectionScreen() {
        globalBackStack.push(
            org.yarokovisty.delivery.feature.delivery.direction.navigation.DirectionDestination(
                DirectionType.FROM
            )
        )
    }
}
