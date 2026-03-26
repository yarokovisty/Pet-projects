package org.yarokovisty.delivery.feature.direction.impl.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.direction.impl.data.model.DeliveryPointListResponse
import org.yarokovisty.delivery.feature.direction.impl.data.model.DeliveryPointResponse
import org.yarokovisty.delivery.feature.direction.impl.data.repository.DirectionRepositoryImpl
import org.yarokovisty.delivery.feature.direction.impl.data.service.DirectionService
import kotlin.test.Test
import kotlin.test.assertEquals

class DirectionRepositoryImplTest {

    private val service: DirectionService = mockk()
    private val repository = DirectionRepositoryImpl(service)

    private val deliveryPointListResponse = DeliveryPointListResponse(
        points = listOf(
            DeliveryPointResponse(
                id = "0",
                name = "name0",
                latitude = 0.0,
                longitude = 0.0
            ),
            DeliveryPointResponse(
                id = "1",
                name = "name1",
                latitude = 1.0,
                longitude = 1.0
            )
        )
    )

    @Test
    fun `get delivery points EXPECT delivery points`() = runTest {
        val expected = listOf(
            DeliveryPoint(
                id = "0",
                name = "name0",
                latitude = 0.0,
                longitude = 0.0
            ),
            DeliveryPoint(
                id = "1",
                name = "name1",
                latitude = 1.0,
                longitude = 1.0
            )
        )
        coEvery { service.getDeliveryPoints() } returns deliveryPointListResponse

        val actual = repository.getDeliveryPoints()

        assertEquals(expected, actual)
    }

    @Test
    fun `get delivery points EXPECT invoke get delivery points by service`() = runTest {
        coEvery { service.getDeliveryPoints() } returns deliveryPointListResponse

        repository.getDeliveryPoints()

        coVerify { service.getDeliveryPoints() }
    }
}
