package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryTab
import org.yarokovisty.delivery.feature.delivery.order.navigation.SuccessOrderRouter
import org.yarokovisty.delivery.feature.history.main.navigation.HistoryTab
import org.yarokovisty.delivery.feature.login.navigation.LoginDestination
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.destination.MainDestination

class SuccessOrderRouterImpl(
    private val globalBackStack: GlobalBackStack,
    private val bottomBarBackStack: BottomBarBackStack,
) : SuccessOrderRouter {

    override fun backToMain() {
        globalBackStack.newRoot(MainDestination)
        bottomBarBackStack.newRoot(DeliveryTab)
    }

    override fun openLoginScreen() {
        globalBackStack.newChain(MainDestination, LoginDestination)
        bottomBarBackStack.newRoot(DeliveryTab)
    }

    override fun openHistoryMainScreen() {
        globalBackStack.newRoot(MainDestination)
        bottomBarBackStack.newRoot(HistoryTab)
    }
}
