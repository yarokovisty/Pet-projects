package org.yarokovisty.delivery.common.profile.main.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.profile.main.data.datasource.UserLocalDataSource
import org.yarokovisty.delivery.common.profile.main.data.datasource.UserRemoteDataSource
import org.yarokovisty.delivery.common.profile.main.data.model.ProfileRequest
import org.yarokovisty.delivery.common.profile.main.data.model.UserRequest
import org.yarokovisty.delivery.common.profile.main.data.model.UserResponse
import org.yarokovisty.delivery.common.profile.main.data.model.UserSessionResponse
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserRepositoryImplTest {

    private val localDataSource: UserLocalDataSource = mockk()
    private val remoteDataSource: UserRemoteDataSource = mockk()
    private val repository = UserRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource
    )

    private companion object {
        const val TEST_ID = "user123"
        const val TEST_PHONE = "79123456789"
        const val TEST_FIRSTNAME = "Ivan"
        const val TEST_LASTNAME = "Ivanov"
        const val TEST_MIDDLENAME = "Ivanovich"
        const val TEST_EMAIL = "ivan@example.com"
        const val TEST_CITY = "Moscow"
        const val TEST_TOKEN = "token_abc123"
    }

    // region getUserFromLocal

    @Test
    fun `get user from local when data exists EXPECT user returned`() = runTest {
        val cachedUser = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { localDataSource.get() } returns cachedUser

        val actual = repository.getFromLocal()

        assertEquals(cachedUser, actual)
    }

    @Test
    fun `get user from local when no data EXPECT null returned`() = runTest {
        coEvery { localDataSource.get() } returns null

        val actual = repository.getFromLocal()

        assertNull(actual)
    }

    @Test
    fun `get user from local EXPECT local data source get called`() = runTest {
        coEvery { localDataSource.get() } returns null

        repository.getFromLocal()

        coVerify { localDataSource.get() }
    }

    // endregion

    // region getUserFromNetwork

    @Test
    fun `get user from network EXPECT remote data source called with token`() = runTest {
        val sessionResponse = UserSessionResponse(
            user = UserResponse(
                id = TEST_ID,
                phone = TEST_PHONE,
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            )
        )
        coEvery { remoteDataSource.getUser(TEST_TOKEN) } returns sessionResponse

        repository.getFromNetwork(TEST_TOKEN)

        coVerify { remoteDataSource.getUser(TEST_TOKEN) }
    }

    @Test
    fun `get user from network EXPECT mapped user returned`() = runTest {
        val sessionResponse = UserSessionResponse(
            user = UserResponse(
                id = TEST_ID,
                phone = TEST_PHONE,
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            )
        )
        val expectedUser = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { remoteDataSource.getUser(TEST_TOKEN) } returns sessionResponse

        val actual = repository.getFromNetwork(TEST_TOKEN)

        assertEquals(expectedUser, actual)
    }

    @Test
    fun `get user from network when response has null fields EXPECT user with nulls returned`() = runTest {
        val sessionResponse = UserSessionResponse(
            user = UserResponse(
                id = TEST_ID,
                phone = TEST_PHONE,
                firstname = null,
                lastname = null,
                middlename = null,
                email = null,
                city = null
            )
        )
        val expectedUser = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        coEvery { remoteDataSource.getUser(TEST_TOKEN) } returns sessionResponse

        val actual = repository.getFromNetwork(TEST_TOKEN)

        assertEquals(expectedUser, actual)
    }

    // endregion

    // region updateUser

    @Test
    fun `update user EXPECT remote update called with correct request`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        val expectedRequest = UserRequest(
            profile = ProfileRequest(
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            ),
            phone = TEST_PHONE
        )
        val mockResponse = UserSessionResponse(
            user = UserResponse(
                id = TEST_ID,
                phone = TEST_PHONE,
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            )
        )
        coEvery { remoteDataSource.updateUser(any(), any()) } returns mockResponse
        coEvery { localDataSource.save(any()) } just runs

        repository.update(user, TEST_TOKEN)

        coVerify { remoteDataSource.updateUser(expectedRequest, TEST_TOKEN) }
    }

    @Test
    fun `update user EXPECT user saved to local`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        val mockResponse = UserSessionResponse(
            user = UserResponse(
                id = TEST_ID,
                phone = TEST_PHONE,
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            )
        )
        coEvery { remoteDataSource.updateUser(any(), any()) } returns mockResponse
        coEvery { localDataSource.save(any()) } just runs

        repository.update(user, TEST_TOKEN)

        coVerify { localDataSource.save(user) }
    }

    @Test
    fun `update user with null fields EXPECT remote called with nulls`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        val expectedRequest = UserRequest(
            profile = ProfileRequest(
                firstname = null,
                lastname = null,
                middlename = null,
                email = null,
                city = null
            ),
            phone = TEST_PHONE
        )
        val mockResponse = UserSessionResponse(
            user = UserResponse(
                id = TEST_ID,
                phone = TEST_PHONE
            )
        )
        coEvery { remoteDataSource.updateUser(any(), any()) } returns mockResponse
        coEvery { localDataSource.save(any()) } just runs

        repository.update(user, TEST_TOKEN)

        coVerify { remoteDataSource.updateUser(expectedRequest, TEST_TOKEN) }
    }

    @Test
    fun `update user with null fields EXPECT user with nulls saved to local`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        val mockResponse = UserSessionResponse(
            user = UserResponse(
                id = TEST_ID,
                phone = TEST_PHONE
            )
        )
        coEvery { remoteDataSource.updateUser(any(), any()) } returns mockResponse
        coEvery { localDataSource.save(any()) } just runs

        repository.update(user, TEST_TOKEN)

        coVerify { localDataSource.save(user) }
    }

    // endregion

    // region clear

    @Test
    fun `clear EXPECT local data source remove called`() = runTest {
        coEvery { localDataSource.remove() } just runs

        repository.clear()

        coVerify { localDataSource.remove() }
    }

    // endregion
}
