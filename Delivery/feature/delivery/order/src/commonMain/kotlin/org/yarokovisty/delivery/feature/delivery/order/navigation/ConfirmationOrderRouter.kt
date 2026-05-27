package org.yarokovisty.delivery.feature.delivery.order.navigation

interface ConfirmationOrderRouter {

    fun back()

    fun openReceiverScreen()

    fun openSenderScreen()

    fun openReceiverAddressScreen()

    fun openSenderAddressScreen()

    fun openSuccessOrderScreen()
}
