package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.delivery.point.navigation.ReceiverAddressDestination
import org.yarokovisty.delivery.feature.delivery.point.navigation.SenderAddressRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class SenderAddressRouterImpl(private val globalBackStack: GlobalBackStack) : SenderAddressRouter {

    override fun openReceiverAddress() {
        globalBackStack.push(ReceiverAddressDestination)
    }

    override fun back() {
        globalBackStack.pop()
    }
}
