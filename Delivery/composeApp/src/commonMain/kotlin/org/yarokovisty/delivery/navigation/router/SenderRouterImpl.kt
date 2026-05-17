package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.delivery.person.navigation.SenderRouter
import org.yarokovisty.delivery.feature.delivery.point.navigation.AddressScreenType
import org.yarokovisty.delivery.feature.delivery.point.navigation.SenderAddressDestination
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class SenderRouterImpl(private val globalBackStack: GlobalBackStack) : SenderRouter {

    override fun openSenderAddressScreen() {
        globalBackStack.push(SenderAddressDestination(AddressScreenType.NEW))
    }

    override fun back() {
        globalBackStack.pop()
    }
}
