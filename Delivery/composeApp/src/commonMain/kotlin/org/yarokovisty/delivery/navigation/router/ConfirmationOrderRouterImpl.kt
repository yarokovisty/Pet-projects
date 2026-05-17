package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.delivery.order.navigation.ConfirmationOrderRouter
import org.yarokovisty.delivery.feature.delivery.person.navigation.PersonScreenType
import org.yarokovisty.delivery.feature.delivery.person.navigation.ReceiverDestination
import org.yarokovisty.delivery.feature.delivery.person.navigation.SenderDestination
import org.yarokovisty.delivery.feature.delivery.point.navigation.AddressScreenType
import org.yarokovisty.delivery.feature.delivery.point.navigation.ReceiverAddressDestination
import org.yarokovisty.delivery.feature.delivery.point.navigation.SenderAddressDestination
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.destination.MainDestination

class ConfirmationOrderRouterImpl(private val globalBackStack: GlobalBackStack) : ConfirmationOrderRouter {

    override fun back() {
        globalBackStack.popTo(MainDestination)
    }

    override fun openReceiverScreen() {
        globalBackStack.push(ReceiverDestination(PersonScreenType.EDIT))
    }

    override fun openSenderScreen() {
        globalBackStack.push(SenderDestination(PersonScreenType.EDIT))
    }

    override fun openReceiverAddressScreen() {
        globalBackStack.push(ReceiverAddressDestination(AddressScreenType.EDIT))
    }

    override fun openSenderAddressScreen() {
        globalBackStack.push(SenderAddressDestination(AddressScreenType.EDIT))
    }
}
