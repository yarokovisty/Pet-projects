package org.yarokovisty.delivery.presentation.router

import org.yarokovisty.delivery.feature.delivery.main.api.navigation.DeliveryTab
import org.yarokovisty.delivery.feature.login.api.navigation.LoginDestination
import org.yarokovisty.delivery.feature.profile.main.api.navigation.ProfileTab
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import org.yarokovisty.delivery.navigation.HistoryTab

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
