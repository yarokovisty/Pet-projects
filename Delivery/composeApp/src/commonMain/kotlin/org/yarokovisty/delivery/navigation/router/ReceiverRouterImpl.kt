package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.delivery.person.navigation.ReceiverRouter
import org.yarokovisty.delivery.feature.delivery.person.navigation.SenderDestination
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class ReceiverRouterImpl(private val globalBackStack: GlobalBackStack) : ReceiverRouter {

    override fun openSenderScreen() {
        globalBackStack.push(SenderDestination)
    }

    override fun back() {
        globalBackStack.pop()
    }
}
