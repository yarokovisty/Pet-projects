package org.yarokovisty.delivery.user.data.datasource

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserLocalDataSourceTest {

    private val storage: PreferencesStorage = mockk()
    private val dataSource = UserLocalDataSource(storage)

    private companion object {

        const val TEST_PHONE_NUMBER = "+1234567890"
        const val USER_PHONE_NUMBER_KEY = "user_phone_number"
    }

    @Test
    fun `save phone number EXPECT invoke put string by storage`() = runTest {
        coEvery { storage.putString(any(), any()) } returns Unit

        dataSource.savePhoneNumber(TEST_PHONE_NUMBER)

        coVerify { storage.putString(USER_PHONE_NUMBER_KEY, TEST_PHONE_NUMBER) }
    }

    @Test
    fun `fetch phone number EXPECT phone number from storage`() = runTest {
        coEvery { storage.getString(USER_PHONE_NUMBER_KEY) } returns TEST_PHONE_NUMBER

        val actual = dataSource.fetchPhoneNumber()

        assertEquals(TEST_PHONE_NUMBER, actual)
    }

    @Test
    fun `fetch phone number EXPECT invoke get string by storage`() = runTest {
        coEvery { storage.getString(any()) } returns TEST_PHONE_NUMBER

        dataSource.fetchPhoneNumber()

        coVerify { storage.getString(USER_PHONE_NUMBER_KEY) }
    }

    @Test
    fun `fetch phone number when storage returns null EXPECT null`() = runTest {
        coEvery { storage.getString(USER_PHONE_NUMBER_KEY) } returns null

        val actual = dataSource.fetchPhoneNumber()

        assertNull(actual)
    }

    @Test
    fun `clear phone number EXPECT invoke remove by storage`() = runTest {
        coEvery { storage.remove(any()) } returns Unit

        dataSource.clearPhoneNumber()

        coVerify { storage.remove(USER_PHONE_NUMBER_KEY) }
    }
}
