package org.yarokovisty.delivery.common.profile.main.data.datasource

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserLocalDataSourceTest {

    private val storage: PreferencesStorage = mockk()
    private val dataSource = UserLocalDataSource(storage)

    private companion object {
        const val USER_KEY = "user"
        const val TEST_ID = "user123"
        const val TEST_PHONE = "79123456789"
        const val TEST_FIRSTNAME = "Ivan"
        const val TEST_LASTNAME = "Ivanov"
        const val TEST_MIDDLENAME = "Ivanovich"
        const val TEST_EMAIL = "ivan@example.com"
        const val TEST_CITY = "Moscow"
    }

    @Test
    fun `get when user exists EXPECT user returned`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { storage.getObject(USER_KEY, User.serializer()) } returns user

        val actual = dataSource.get()

        assertEquals(user, actual)
    }

    @Test
    fun `get when user does not exist EXPECT null returned`() = runTest {
        coEvery { storage.getObject(USER_KEY, User.serializer()) } returns null

        val actual = dataSource.get()

        assertNull(actual)
    }

    @Test
    fun `save EXPECT storage put object called with correct parameters`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { storage.putObject(USER_KEY, user, User.serializer()) } just runs

        dataSource.save(user)

        coVerify { storage.putObject(USER_KEY, user, User.serializer()) }
    }

    @Test
    fun `remove EXPECT storage remove called with user key`() = runTest {
        coEvery { storage.remove(USER_KEY) } just runs

        dataSource.remove()

        coVerify { storage.remove(USER_KEY) }
    }
}
