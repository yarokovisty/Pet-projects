package org.yarokovisty.delivery.presentation.router

import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.navigation.DeliveryTab
import org.yarokovisty.delivery.navigation.HistoryTab
import org.yarokovisty.delivery.navigation.ProfileTab

class MainRouter(val bottomBarBackStack: BottomBarBackStack) {

    fun openDeliveryTab() {
        bottomBarBackStack.push(DeliveryTab)
    }

    fun openHistoryTab() {
        bottomBarBackStack.push(HistoryTab)
    }

    fun openProfileTab() {
        bottomBarBackStack.push(ProfileTab)
    }

    fun back() {
        bottomBarBackStack.pop()
    }
}
