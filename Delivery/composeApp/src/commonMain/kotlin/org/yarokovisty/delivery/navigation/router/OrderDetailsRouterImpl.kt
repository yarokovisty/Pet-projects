package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.history.details.navigation.OrderDetailsRouter
import org.yarokovisty.delivery.feature.login.navigation.LoginDestination
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.destination.MainDestination

class OrderDetailsRouterImpl(private val globalBackStack: GlobalBackStack) : OrderDetailsRouter {

    override fun openLoginScreen() {
        globalBackStack.newChain(MainDestination, LoginDestination)
    }

    override fun back() {
        globalBackStack.pop()
    }
}
