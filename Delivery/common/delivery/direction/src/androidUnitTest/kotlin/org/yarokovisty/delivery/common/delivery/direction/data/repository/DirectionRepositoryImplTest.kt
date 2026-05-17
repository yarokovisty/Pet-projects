package org.yarokovisty.delivery.common.delivery.direction.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.direction.data.datasource.DirectionLocalDataSource
import org.yarokovisty.delivery.common.delivery.direction.data.datasource.DirectionRemoteDataSource
import org.yarokovisty.delivery.common.delivery.direction.data.model.DeliveryPointListResponse
import org.yarokovisty.delivery.common.delivery.direction.data.model.DeliveryPointResponse
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DirectionRepositoryImplTest {

    private val localDataSource: DirectionLocalDataSource = mockk(relaxed = true)
    private val remoteDataSource: DirectionRemoteDataSource = mockk()
    private val repository = DirectionRepositoryImpl(localDataSource, remoteDataSource)

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

    private val deliveryPoint = DeliveryPoint(
        id = "0",
        name = "name0",
        latitude = 0.0,
        longitude = 0.0
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

    @Test
    fun `get selected point from EXPECT point from local data source`() = runTest {
        coEvery { localDataSource.getPointFrom() } returns deliveryPoint

        val actual = repository.getSelectedPointFrom()

        assertEquals(deliveryPoint, actual)
    }

    @Test
    fun `get selected point from when no data EXPECT null`() = runTest {
        coEvery { localDataSource.getPointFrom() } returns null

        val actual = repository.getSelectedPointFrom()

        assertNull(actual)
    }

    @Test
    fun `set selected point from EXPECT local data source save point from called`() = runTest {
        repository.setSelectedPointFrom(deliveryPoint)

        coVerify { localDataSource.savePointFrom(deliveryPoint) }
    }

    @Test
    fun `clear selected point from EXPECT local data source clear point from called`() = runTest {
        repository.clearSelectedPointFrom()

        coVerify { localDataSource.clearPointFrom() }
    }

    @Test
    fun `get selected point to EXPECT point from local data source`() = runTest {
        coEvery { localDataSource.getPointTo() } returns deliveryPoint

        val actual = repository.getSelectedPointTo()

        assertEquals(deliveryPoint, actual)
    }

    @Test
    fun `get selected point to when no data EXPECT null`() = runTest {
        coEvery { localDataSource.getPointTo() } returns null

        val actual = repository.getSelectedPointTo()

        assertNull(actual)
    }

    @Test
    fun `set selected point to EXPECT local data source save point to called`() = runTest {
        repository.setSelectedPointTo(deliveryPoint)

        coVerify { localDataSource.savePointTo(deliveryPoint) }
    }

    @Test
    fun `clear selected point to EXPECT local data source clear point to called`() = runTest {
        repository.clearSelectedPointTo()

        coVerify { localDataSource.clearPointTo() }
    }
}
