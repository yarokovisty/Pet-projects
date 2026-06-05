package org.yarokovisty.delivery.navigation.router

import io.mockk.mockk
import io.mockk.verify
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import kotlin.test.Test

class DirectionRouterImplTest {

    private val globalBackStack: GlobalBackStack = mockk(relaxed = true)
    private val router = DirectionRouterImpl(globalBackStack)

    // region back

    @Test
    fun `go back EXPECT pop global back stack`() {
        router.back()

        verify { globalBackStack.pop() }
    }

    // endregion
}
