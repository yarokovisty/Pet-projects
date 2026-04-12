package org.yarokovisty.delivery.feature.delivery.main.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetDeliveryPointByNameUseCaseTest {

    private val getDeliveryPointByNameUseCase =
        org.yarokovisty.delivery.feature.delivery.main.domain.usecase.GetDeliveryPointByNameUseCase(
            Dispatchers.Unconfined
        )

    @Test
    fun `invoke with existing point name EXPECT correct point returned`() = runTest {
        val points = listOf(
            DeliveryPoint("0", "name", 0.0, 0.0),
            DeliveryPoint("1", "name1", 1.0, 1.0),
            DeliveryPoint("2", "name2", 2.0, 2.0)
        )
        val expected = DeliveryPoint("1", "name1", 1.0, 1.0)

        val actual = getDeliveryPointByNameUseCase(points, "name1")

        assertEquals(expected, actual)
    }

    @Test
    fun `invoke with non-existing point name EXPECT error thrown`() = runTest {
        val points = listOf(
            DeliveryPoint("0", "name", 0.0, 0.0),
            DeliveryPoint("1", "name1", 1.0, 1.0)
        )

        assertFailsWith<IllegalStateException> {
            getDeliveryPointByNameUseCase(points, "nonExistingName")
        }
    }

    @Test
    fun `invoke with empty list EXPECT error thrown`() = runTest {
        val points = emptyList<DeliveryPoint>()

        assertFailsWith<IllegalStateException> {
            getDeliveryPointByNameUseCase(points, "anyName")
        }
    }

    @Test
    fun `invoke with first point name EXPECT first point returned`() = runTest {
        val points = listOf(
            DeliveryPoint("0", "name", 0.0, 0.0),
            DeliveryPoint("1", "name1", 1.0, 1.0),
            DeliveryPoint("2", "name2", 2.0, 2.0)
        )
        val expected = DeliveryPoint("0", "name", 0.0, 0.0)

        val actual = getDeliveryPointByNameUseCase(points, "name")

        assertEquals(expected, actual)
    }

    @Test
    fun `invoke with last point name EXPECT last point returned`() = runTest {
        val points = listOf(
            DeliveryPoint("0", "name", 0.0, 0.0),
            DeliveryPoint("1", "name1", 1.0, 1.0),
            DeliveryPoint("2", "name2", 2.0, 2.0)
        )
        val expected = DeliveryPoint("2", "name2", 2.0, 2.0)

        val actual = getDeliveryPointByNameUseCase(points, "name2")

        assertEquals(expected, actual)
    }
}
