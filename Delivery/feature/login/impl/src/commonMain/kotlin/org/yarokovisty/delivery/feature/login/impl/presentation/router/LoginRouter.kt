package org.yarokovisty.delivery.feature.login.impl.presentation.router

import org.yarokovisty.delivery.feature.profile.main.api.navigation.ProfileTab
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

internal class LoginRouter(
    private val globalBackStack: GlobalBackStack,
    private val bottomBarBackStack: BottomBarBackStack
) {

    fun openProfileScreen() {
        bottomBarBackStack.push(ProfileTab)
        globalBackStack.pop()
    }

    fun back() {
        globalBackStack.pop()
    }
}
