package org.yarokovisty.delivery.feature.delivery.main.domain.usecase

import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAlternativeDeliveryPointesUseCaseTest {

    private val getAlternativeDeliveryPointsUseCase =
        org.yarokovisty.delivery.feature.delivery.main.domain.usecase.GetAlternativeDeliveryPointsUseCase()

    @Test
    fun `invoke EXPECT sublist of delivery points`() {
        val expected = listOf(
            DeliveryPoint("0", "name", 0.0, 0.0),
            DeliveryPoint("1", "name1", 1.0, 1.0),
            DeliveryPoint("2", "name2", 2.0, 2.0)
        )
        val points = listOf(
            DeliveryPoint("0", "name", 0.0, 0.0),
            DeliveryPoint("1", "name1", 1.0, 1.0),
            DeliveryPoint("2", "name2", 2.0, 2.0),
            DeliveryPoint("3", "name3", 3.0, 4.0),
            DeliveryPoint("4", "name4", 4.0, 4.0)
        )

        val actual = getAlternativeDeliveryPointsUseCase(points)

        assertEquals(expected, actual)
    }

    @Test
    fun `points has size less than 3 EXPECT empty list`() {
        val expected: List<DeliveryPoint> = emptyList()
        val cases = listOf(
            emptyList(),
            listOf(DeliveryPoint("0", "name", 0.0, 0.0)),
            listOf(
                DeliveryPoint("0", "name", 0.0, 0.0),
                DeliveryPoint("1", "name1", 1.0, 1.0),
            )
        )

        cases.forEach { case ->
            val actual = getAlternativeDeliveryPointsUseCase(case)

            assertEquals(expected, actual)
        }
    }
}
