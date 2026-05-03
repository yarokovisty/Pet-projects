package org.yarokovisty.delivery.common.delivery.point.data.datasource

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AddressLocalDataSourceTest {

    private val storage: PreferencesStorage = mockk()
    private val dataSource = AddressLocalDataSource(storage)

    private companion object {

        const val SENDER_ADDRESS_KEY = "senderAddress"
        const val RECEIVER_ADDRESS_KEY = "receiverAddress"

        val TEST_ADDRESS = Address(
            street = "Main Street",
            house = "12",
            apartment = "34",
            comment = "Ring the bell",
            isNonContact = false
        )
    }

    @Test
    fun `get sender EXPECT address from storage`() = runTest {
        coEvery { storage.getObject(SENDER_ADDRESS_KEY, Address.serializer()) } returns TEST_ADDRESS

        val actual = dataSource.getSender()

        assertEquals(TEST_ADDRESS, actual)
    }

    @Test
    fun `get sender EXPECT invoke get object by storage with sender key`() = runTest {
        coEvery { storage.getObject(any(), any<kotlinx.serialization.KSerializer<Address>>()) } returns TEST_ADDRESS

        dataSource.getSender()

        coVerify { storage.getObject(SENDER_ADDRESS_KEY, Address.serializer()) }
    }

    @Test
    fun `get sender when storage returns null EXPECT null`() = runTest {
        coEvery { storage.getObject(SENDER_ADDRESS_KEY, Address.serializer()) } returns null

        val actual = dataSource.getSender()

        assertNull(actual)
    }

    @Test
    fun `set sender EXPECT invoke put object by storage with sender key`() = runTest {
        coEvery { storage.putObject(any(), any<Address>(), any()) } returns Unit

        dataSource.setSender(TEST_ADDRESS)

        coVerify { storage.putObject(SENDER_ADDRESS_KEY, TEST_ADDRESS, Address.serializer()) }
    }

    @Test
    fun `get receiver EXPECT address from storage`() = runTest {
        coEvery { storage.getObject(RECEIVER_ADDRESS_KEY, Address.serializer()) } returns TEST_ADDRESS

        val actual = dataSource.getReceiver()

        assertEquals(TEST_ADDRESS, actual)
    }

    @Test
    fun `get receiver EXPECT invoke get object by storage with receiver key`() = runTest {
        coEvery { storage.getObject(any(), any<kotlinx.serialization.KSerializer<Address>>()) } returns TEST_ADDRESS

        dataSource.getReceiver()

        coVerify { storage.getObject(RECEIVER_ADDRESS_KEY, Address.serializer()) }
    }

    @Test
    fun `get receiver when storage returns null EXPECT null`() = runTest {
        coEvery { storage.getObject(RECEIVER_ADDRESS_KEY, Address.serializer()) } returns null

        val actual = dataSource.getReceiver()

        assertNull(actual)
    }

    @Test
    fun `set receiver EXPECT invoke put object by storage with receiver key`() = runTest {
        coEvery { storage.putObject(any(), any<Address>(), any()) } returns Unit

        dataSource.setReceiver(TEST_ADDRESS)

        coVerify { storage.putObject(RECEIVER_ADDRESS_KEY, TEST_ADDRESS, Address.serializer()) }
    }
}
