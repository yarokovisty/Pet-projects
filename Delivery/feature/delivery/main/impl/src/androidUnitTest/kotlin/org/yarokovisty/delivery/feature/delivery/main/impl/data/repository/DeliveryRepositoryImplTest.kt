package org.yarokovisty.delivery.feature.delivery.main.impl.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.PackageType
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelInfo
import org.yarokovisty.delivery.feature.delivery.main.impl.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.TypePackageListResponse
import org.yarokovisty.delivery.feature.delivery.main.impl.data.model.TypePackageResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class DeliveryRepositoryImplTest {

    private val remoteDataSource: DeliveryRemoteDataSource = mockk()
    private val repository = DeliveryRepositoryImpl(remoteDataSource)

    private val typePackageListResponse = TypePackageListResponse(
        packages = listOf(
            TypePackageResponse(
                id = "envelope",
                name = "name0",
                length = 1,
                width = 1,
                height = 1,
                weight = 1,
            ),
            TypePackageResponse(
                id = "box-s",
                name = "name1",
                length = 2,
                width = 2,
                height = 2,
                weight = 2,
            ),
        )
    )

    @Test
    fun `get parcel types EXPECT parcel types`() = runTest {
        val expected = listOf(
            ParcelInfo(
                id = "envelope",
                type = PackageType.ENVELOPE,
                name = "name0",
                length = 1,
                width = 1,
                height = 1,
                weight = 1,
            ),
            ParcelInfo(
                id = "box-s",
                type = PackageType.BOX_S,
                name = "name1",
                length = 2,
                width = 2,
                height = 2,
                weight = 2,
            )
        )
        coEvery { remoteDataSource.getPackageTypes() } returns typePackageListResponse

        val actual = repository.getParcelTypes()

        assertEquals(expected, actual)
    }

    @Test
    fun `get parcel types EXPECT invoke get parcel types by remote data source`() = runTest {
        coEvery { remoteDataSource.getPackageTypes() } returns typePackageListResponse

        repository.getParcelTypes()

        coVerify { remoteDataSource.getPackageTypes() }
    }

    @Test
    fun `get parcel types and id not exist in package type EXEPECT error`() = runTest {
        val response = TypePackageListResponse(
            packages = listOf(
                TypePackageResponse(
                    id = "unknown",
                    name = "name0",
                    length = 1,
                    width = 1,
                    height = 1,
                    weight = 1,
                )
            )
        )
        coEvery { remoteDataSource.getPackageTypes() } returns response

        assertFails { repository.getParcelTypes() }
    }
}
