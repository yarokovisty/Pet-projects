package org.yarokovisty.delivery.presentation.router

import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryTab
import org.yarokovisty.delivery.feature.history.main.navigation.HistoryTab
import org.yarokovisty.delivery.feature.login.navigation.LoginDestination
import org.yarokovisty.delivery.feature.profile.main.navigation.ProfileTab
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class MainRouter(
    val globalBackStack: GlobalBackStack,
    val bottomBarBackStack: BottomBarBackStack
) {

    fun openDeliveryTab() {
        bottomBarBackStack.push(DeliveryTab)
    }

    fun openHistoryTab() {
        bottomBarBackStack.push(HistoryTab)
    }

    fun openProfileTab() {
        bottomBarBackStack.push(ProfileTab)
    }

    fun openLoginScreen() {
        globalBackStack.push(LoginDestination)
    }

    fun back() {
        bottomBarBackStack.pop()
    }
}
