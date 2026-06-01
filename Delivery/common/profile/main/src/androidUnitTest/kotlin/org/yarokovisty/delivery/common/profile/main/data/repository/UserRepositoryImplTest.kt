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
import org.yarokovisty.delivery.common.profile.main.data.model.UserDto
import org.yarokovisty.delivery.common.profile.main.data.model.UserRequest
import org.yarokovisty.delivery.common.profile.main.data.model.UserSessionResponse
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import kotlin.test.Test
import kotlin.test.assertEquals

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
    }

    // region get

    @Test
    fun `get when local user exists EXPECT local user returned`() = runTest {
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

        val actual = repository.get()

        assertEquals(cachedUser, actual)
    }

    @Test
    fun `get when local user exists EXPECT remote data source not called`() = runTest {
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

        repository.get()

        coVerify(exactly = 0) { remoteDataSource.get() }
    }

    @Test
    fun `get when local is null EXPECT remote user returned`() = runTest {
        val sessionResponse = UserSessionResponse(
            user = UserDto(
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
        coEvery { localDataSource.get() } returns null
        coEvery { remoteDataSource.get() } returns sessionResponse
        coEvery { localDataSource.save(any()) } just runs

        val actual = repository.get()

        assertEquals(expectedUser, actual)
    }

    @Test
    fun `get when local is null EXPECT remote data source called`() = runTest {
        val sessionResponse = UserSessionResponse(
            user = UserDto(
                id = TEST_ID,
                phone = TEST_PHONE,
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            )
        )
        coEvery { localDataSource.get() } returns null
        coEvery { remoteDataSource.get() } returns sessionResponse
        coEvery { localDataSource.save(any()) } just runs

        repository.get()

        coVerify { remoteDataSource.get() }
    }

    @Test
    fun `get when local is null EXPECT remote user saved to local`() = runTest {
        val expectedUser = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        val sessionResponse = UserSessionResponse(
            user = UserDto(
                id = TEST_ID,
                phone = TEST_PHONE,
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            )
        )
        coEvery { localDataSource.get() } returns null
        coEvery { remoteDataSource.get() } returns sessionResponse
        coEvery { localDataSource.save(any()) } just runs

        repository.get()

        coVerify { localDataSource.save(expectedUser) }
    }

    @Test
    fun `get when local is null and response has null fields EXPECT user with nulls returned`() = runTest {
        val sessionResponse = UserSessionResponse(
            user = UserDto(
                id = TEST_ID,
                phone = TEST_PHONE
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
        coEvery { localDataSource.get() } returns null
        coEvery { remoteDataSource.get() } returns sessionResponse
        coEvery { localDataSource.save(any()) } just runs

        val actual = repository.get()

        assertEquals(expectedUser, actual)
    }

    // endregion

    // region set

    @Test
    fun `set EXPECT local data source save called`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { localDataSource.save(any()) } just runs

        repository.set(user)

        coVerify { localDataSource.save(user) }
    }

    // endregion

    // region update

    @Test
    fun `update EXPECT remote update called with correct request`() = runTest {
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
            user = UserDto(
                id = TEST_ID,
                phone = TEST_PHONE,
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            )
        )
        coEvery { remoteDataSource.update(any()) } returns mockResponse
        coEvery { localDataSource.save(any()) } just runs

        repository.update(user)

        coVerify { remoteDataSource.update(expectedRequest) }
    }

    @Test
    fun `update EXPECT user saved to local`() = runTest {
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
            user = UserDto(
                id = TEST_ID,
                phone = TEST_PHONE,
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            )
        )
        coEvery { remoteDataSource.update(any()) } returns mockResponse
        coEvery { localDataSource.save(any()) } just runs

        repository.update(user)

        coVerify { localDataSource.save(user) }
    }

    @Test
    fun `update with null fields EXPECT remote called with nulls`() = runTest {
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
            user = UserDto(
                id = TEST_ID,
                phone = TEST_PHONE
            )
        )
        coEvery { remoteDataSource.update(any()) } returns mockResponse
        coEvery { localDataSource.save(any()) } just runs

        repository.update(user)

        coVerify { remoteDataSource.update(expectedRequest) }
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
