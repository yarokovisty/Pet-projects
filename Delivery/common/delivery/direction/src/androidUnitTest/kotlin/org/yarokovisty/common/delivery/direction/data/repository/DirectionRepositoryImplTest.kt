package org.yarokovisty.common.delivery.direction.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.common.delivery.direction.data.datasource.DirectionRemoteDataSource
import org.yarokovisty.common.delivery.direction.data.model.DeliveryPointListResponse
import org.yarokovisty.common.delivery.direction.data.model.DeliveryPointResponse
import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import kotlin.test.Test
import kotlin.test.assertEquals

class DirectionRepositoryImplTest {

    private val remoteDataSource: DirectionRemoteDataSource = mockk()
    private val repository = DirectionRepositoryImpl(remoteDataSource)

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
        coEvery { remoteDataSource.getDeliveryPoints() } returns deliveryPointListResponse

        val actual = repository.getDeliveryPointList()

        assertEquals(expected, actual)
    }

    @Test
    fun `get delivery points EXPECT invoke get delivery points by remote data source`() = runTest {
        coEvery { remoteDataSource.getDeliveryPoints() } returns deliveryPointListResponse

        repository.getDeliveryPointList()

        coVerify { remoteDataSource.getDeliveryPoints() }
    }
}
