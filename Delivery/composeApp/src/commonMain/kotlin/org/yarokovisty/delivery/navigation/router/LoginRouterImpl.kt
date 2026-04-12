package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.login.navigation.LoginRouter
import org.yarokovisty.delivery.feature.profile.main.navigation.ProfileTab
import org.yarokovisty.delivery.libs.navigation.backstack.BottomBarBackStack
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

internal class LoginRouterImpl(
    private val globalBackStack: GlobalBackStack,
    private val bottomBarBackStack: BottomBarBackStack
) : LoginRouter {

    override fun openProfileScreen() {
        bottomBarBackStack.push(ProfileTab)
        globalBackStack.pop()
    }

    override fun back() {
        globalBackStack.pop()
    }
}
