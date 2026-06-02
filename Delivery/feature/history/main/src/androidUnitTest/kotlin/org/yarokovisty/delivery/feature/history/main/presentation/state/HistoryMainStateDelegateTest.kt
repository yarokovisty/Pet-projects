package org.yarokovisty.delivery.feature.history.main.presentation.state

import io.mockk.mockk
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class HistoryMainStateDelegateTest {

    // region initial

    @Test
    fun `initial EXPECT loading is false`() {
        val state = initial()

        assertFalse(state.loading)
    }

    @Test
    fun `initial EXPECT error is null`() {
        val state = initial()

        assertNull(state.error)
    }

    @Test
    fun `initial EXPECT orders is empty list`() {
        val state = initial()

        assertEquals(emptyList(), state.orders)
    }

    // endregion

    // region loading

    @Test
    fun `loading EXPECT loading is true`() {
        val state = initial()

        val result = state.loading()

        assertTrue(result.loading)
    }

    @Test
    fun `loading EXPECT error is null`() {
        val state = initial().copy(error = Error.Unknown)

        val result = state.loading()

        assertNull(result.error)
    }

    @Test
    fun `loading EXPECT orders are preserved`() {
        val orders = listOf(mockk<Order>(relaxed = true))
        val state = initial().copy(orders = orders)

        val result = state.loading()

        assertEquals(orders, result.orders)
    }

    // endregion

    // region error

    @Test
    fun `error with Unauthorized EXPECT error is Unauthorized`() {
        val state = initial()

        val result = state.error(Error.Unauthorized)

        assertEquals(Error.Unauthorized, result.error)
    }

    @Test
    fun `error with Unknown EXPECT error is Unknown`() {
        val state = initial()

        val result = state.error(Error.Unknown)

        assertEquals(Error.Unknown, result.error)
    }

    @Test
    fun `error EXPECT loading is false`() {
        val state = initial().copy(loading = true)

        val result = state.error(Error.Unknown)

        assertFalse(result.loading)
    }

    @Test
    fun `error EXPECT orders are preserved`() {
        val orders = listOf(mockk<Order>(relaxed = true))
        val state = initial().copy(orders = orders)

        val result = state.error(Error.Unknown)

        assertEquals(orders, result.orders)
    }

    // endregion

    // region content

    @Test
    fun `content EXPECT orders are set`() {
        val orders = listOf(mockk<Order>(relaxed = true), mockk<Order>(relaxed = true))
        val state = initial()

        val result = state.content(orders)

        assertEquals(orders, result.orders)
    }

    @Test
    fun `content EXPECT loading is false`() {
        val state = initial().copy(loading = true)

        val result = state.content(emptyList())

        assertFalse(result.loading)
    }

    @Test
    fun `content EXPECT error is preserved`() {
        val state = initial().copy(error = Error.Unknown)

        val result = state.content(emptyList())

        assertEquals(Error.Unknown, result.error)
    }

    // endregion
}
