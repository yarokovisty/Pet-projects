package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.history.details.navigation.OrderDetailsDestination
import org.yarokovisty.delivery.feature.history.main.navigation.HistoryMainRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class HistoryMainRouterImpl(private val globalBackStack: GlobalBackStack) : HistoryMainRouter {

    override fun openOrderDetailsScreen(orderId: String) {
        globalBackStack.push(OrderDetailsDestination(orderId))
    }
}
