package org.yarokovisty.delivery.common.delivery.parcel.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.parcel.data.datasource.DeliveryLocalDataSource
import org.yarokovisty.delivery.common.delivery.parcel.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.delivery.common.delivery.parcel.data.model.PackageTypeListResponse
import org.yarokovisty.delivery.common.delivery.parcel.data.model.PackageTypeResponse
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.PackageType
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull

class ParcelRepositoryImplTest {

    private val localDataSource: DeliveryLocalDataSource = mockk(relaxed = true)
    private val remoteDataSource: DeliveryRemoteDataSource = mockk()
    private val repository = ParcelRepositoryImpl(localDataSource, remoteDataSource)

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

    private val parcelInfo = ParcelInfo(
        id = "envelope",
        type = PackageType.ENVELOPE,
        name = "name0",
        length = 1,
        width = 1,
        height = 1,
        weight = 1
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

    @Test
    fun `get selected parcel EXPECT parcel from local data source`() = runTest {
        coEvery { localDataSource.getParcel() } returns parcelInfo

        val actual = repository.getSelectedParcel()

        assertEquals(parcelInfo, actual)
    }

    @Test
    fun `get selected parcel when no data EXPECT null`() = runTest {
        coEvery { localDataSource.getParcel() } returns null

        val actual = repository.getSelectedParcel()

        assertNull(actual)
    }

    @Test
    fun `save selected parcel EXPECT local data source save parcel called`() = runTest {
        repository.saveSelectedParcel(parcelInfo)

        coVerify { localDataSource.saveParcel(parcelInfo) }
    }

    @Test
    fun `clear selected parcel EXPECT local data source clear parcel called`() = runTest {
        repository.clearSelectedParcel()

        coVerify { localDataSource.clearParcel() }
    }
}
