package org.yarokovisty.delivery.navigation.router

import io.mockk.mockk
import io.mockk.verify
import org.yarokovisty.delivery.feature.history.details.navigation.OrderDetailsDestination
import org.yarokovisty.delivery.feature.login.navigation.LoginDestination
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import kotlin.test.Test

class HistoryMainRouterImplTest {

    private val globalBackStack: GlobalBackStack = mockk(relaxed = true)
    private val router = HistoryMainRouterImpl(globalBackStack)

    // region openOrderDetailsScreen

    @Test
    fun `open order details screen EXPECT push OrderDetailsDestination to global back stack`() {
        val orderId = "order-123"

        router.openOrderDetailsScreen(orderId)

        verify { globalBackStack.push(OrderDetailsDestination(orderId)) }
    }

    // endregion

    // region openLoginScreen

    @Test
    fun `open login screen EXPECT push LoginDestination to global back stack`() {
        router.openLoginScreen()

        verify { globalBackStack.push(LoginDestination) }
    }

    // endregion
}
