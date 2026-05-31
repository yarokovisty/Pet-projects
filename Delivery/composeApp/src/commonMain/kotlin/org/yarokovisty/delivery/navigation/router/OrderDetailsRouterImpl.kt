package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.history.details.navigation.OrderDetailsRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class OrderDetailsRouterImpl(private val globalBackStack: GlobalBackStack) : OrderDetailsRouter {

    override fun back() {
        globalBackStack.pop()
    }
}
