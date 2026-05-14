package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.delivery.payer.navigation.PayerRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class PayerRouterImpl(private val globalBackStack: GlobalBackStack) : PayerRouter {

    override fun back() {
        globalBackStack.pop()
    }

    override fun openConfirmationScreen() {
        TODO("Not yet implemented")
    }
}
