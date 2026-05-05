package org.yarokovisty.delivery.common.delivery.point.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.point.data.datasource.AddressLocalDataSource
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AddressRepositoryImplTest {

    private val localDataSource: AddressLocalDataSource = mockk(relaxUnitFun = true)
    private val repository = AddressRepositoryImpl(localDataSource)

    private val address = Address(
        street = "Main Street",
        house = "12",
        apartment = "34",
        comment = "Ring the bell",
        nonContacted = false
    )

    @Test
    fun `get sender when sender is set EXPECT address`() = runTest {
        coEvery { localDataSource.getSender() } returns address

        val actual = repository.getSender()

        assertEquals(address, actual)
    }

    @Test
    fun `get sender when sender is not set EXPECT null`() = runTest {
        coEvery { localDataSource.getSender() } returns null

        val actual = repository.getSender()

        assertNull(actual)
    }

    @Test
    fun `set sender EXPECT invoke local data source`() = runTest {
        repository.setSender(address)

        coVerify { localDataSource.setSender(address) }
    }

    @Test
    fun `get receiver when receiver is set EXPECT address`() = runTest {
        coEvery { localDataSource.getReceiver() } returns address

        val actual = repository.getReceiver()

        assertEquals(address, actual)
    }

    @Test
    fun `get receiver when receiver is not set EXPECT null`() = runTest {
        coEvery { localDataSource.getReceiver() } returns null

        val actual = repository.getReceiver()

        assertNull(actual)
    }

    @Test
    fun `set receiver EXPECT invoke local data source`() = runTest {
        repository.setReceiver(address)

        coVerify { localDataSource.setReceiver(address) }
    }
}
