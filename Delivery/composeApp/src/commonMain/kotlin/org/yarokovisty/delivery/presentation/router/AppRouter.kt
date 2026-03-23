package org.yarokovisty.delivery.presentation.router

import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class AppRouter(val globalBackStack: GlobalBackStack) {

    fun back() {
        globalBackStack.pop()
    }
}
