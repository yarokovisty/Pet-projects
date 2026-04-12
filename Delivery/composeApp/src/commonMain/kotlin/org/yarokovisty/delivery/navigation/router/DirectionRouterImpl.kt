package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.direction.navigation.DirectionRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class DirectionRouterImpl(private val globalBackStack: GlobalBackStack) : DirectionRouter {

    override fun back() {
        globalBackStack.pop()
    }
}
