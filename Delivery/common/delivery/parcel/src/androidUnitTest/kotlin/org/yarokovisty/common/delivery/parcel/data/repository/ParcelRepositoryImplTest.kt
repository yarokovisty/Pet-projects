package org.yarokovisty.common.delivery.parcel.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.common.delivery.parcel.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.common.delivery.parcel.data.model.PackageTypeListResponse
import org.yarokovisty.common.delivery.parcel.data.model.PackageTypeResponse
import org.yarokovisty.common.delivery.parcel.domain.entity.PackageType
import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ParcelRepositoryImplTest {

    private val remoteDataSource: DeliveryRemoteDataSource = mockk()
    private val repository = ParcelRepositoryImpl(remoteDataSource)

    private val typePackageListResponse = PackageTypeListResponse(
        packages = listOf(
            PackageTypeResponse(
                id = "envelope",
                name = "name0",
                length = 1,
                width = 1,
                height = 1,
                weight = 1,
            ),
            PackageTypeResponse(
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

        val actual = repository.getParcelInfoList()

        assertEquals(expected, actual)
    }

    @Test
    fun `get parcel types EXPECT invoke get parcel types by remote data source`() = runTest {
        coEvery { remoteDataSource.getPackageTypes() } returns typePackageListResponse

        repository.getParcelInfoList()

        coVerify { remoteDataSource.getPackageTypes() }
    }

    @Test
    fun `get parcel types and id not exist in package type EXEPECT error`() = runTest {
        val response = PackageTypeListResponse(
            packages = listOf(
                PackageTypeResponse(
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

        assertFails { repository.getParcelInfoList() }
    }
}
