package org.yarokovisty.delivery.navigation.router

import org.yarokovisty.delivery.feature.delivery.calculator.navigation.CalculatorRouter
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack

class CalculatorRouterImpl(private val globalBackStack: GlobalBackStack) : CalculatorRouter {

    override fun back() {
        globalBackStack.pop()
    }
}
