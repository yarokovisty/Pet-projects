package org.yarokovisty.delivery.feature.direction.impl.presentation.router

import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

internal class DirectionRouter(private val globalBackStack: GlobalBackStack) {

    fun back() {
        globalBackStack.pop()
    }
}
