package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.delivery.point.navigation.ReceiverAddressRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class ReceiverAddressRouterImpl(private val globalBackStack: GlobalBackStack) : ReceiverAddressRouter {

    override fun openPayerScreen() {
        TODO("Not yet implemented")
    }

    override fun back() {
        globalBackStack.pop()
    }
}
