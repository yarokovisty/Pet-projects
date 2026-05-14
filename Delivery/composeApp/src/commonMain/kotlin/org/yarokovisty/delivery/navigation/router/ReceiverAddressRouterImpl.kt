package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.delivery.payer.navigation.PayerDestination
import org.yarokovisty.delivery.feature.delivery.point.navigation.ReceiverAddressRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class ReceiverAddressRouterImpl(private val globalBackStack: GlobalBackStack) : ReceiverAddressRouter {

    override fun openPayerScreen() {
        globalBackStack.push(PayerDestination)
    }

    override fun back() {
        globalBackStack.pop()
    }
}
