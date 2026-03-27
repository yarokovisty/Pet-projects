package org.yarokovisty.delivery.user.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.user.data.datasource.UserLocalDataSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserRepositoryImplTest {

    private val localDataSource: UserLocalDataSource = mockk()
    private val repository = UserRepositoryImpl(localDataSource)

    @Test
    fun `save user phone number EXPECT invoke save phone number by local data source`() = runTest {
        val phoneNumber = "+1234567890"
        coEvery { localDataSource.savePhoneNumber(phoneNumber) } returns Unit

        repository.saveUserPhoneNumber(phoneNumber)

        coVerify { localDataSource.savePhoneNumber(phoneNumber) }
    }

    @Test
    fun `remove user phone number EXPECT invoke clear phone number by local data source`() = runTest {
        coEvery { localDataSource.clearPhoneNumber() } returns Unit

        repository.removeUserPhoneNumber()

        coVerify { localDataSource.clearPhoneNumber() }
    }

    @Test
    fun `get auth phone number EXPECT phone number`() = runTest {
        val expected = "+1234567890"
        coEvery { localDataSource.fetchPhoneNumber() } returns expected

        val actual = repository.getAuthPhoneNumber()

        assertEquals(expected, actual)
    }

    @Test
    fun `get auth phone number EXPECT invoke fetch phone number by local data source`() = runTest {
        coEvery { localDataSource.fetchPhoneNumber() } returns "+1234567890"

        repository.getAuthPhoneNumber()

        coVerify { localDataSource.fetchPhoneNumber() }
    }

    @Test
    fun `get auth phone number when not saved EXPECT null`() = runTest {
        coEvery { localDataSource.fetchPhoneNumber() } returns null

        val actual = repository.getAuthPhoneNumber()

        assertNull(actual)
    }
}
