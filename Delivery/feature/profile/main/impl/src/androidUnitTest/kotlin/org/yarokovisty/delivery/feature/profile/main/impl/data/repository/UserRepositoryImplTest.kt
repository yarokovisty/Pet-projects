package org.yarokovisty.delivery.feature.profile.main.impl.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.feature.profile.main.api.domain.entity.User
import org.yarokovisty.delivery.feature.profile.main.impl.data.datasource.UserRemoteDataSource
import org.yarokovisty.delivery.feature.profile.main.impl.data.model.ProfileRequest
import org.yarokovisty.delivery.feature.profile.main.impl.data.model.UserRequest
import org.yarokovisty.delivery.feature.profile.main.impl.data.model.UserResponse
import org.yarokovisty.delivery.feature.profile.main.impl.data.model.UserSessionResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRepositoryImplTest {

    private val remoteDataSource: UserRemoteDataSource = mockk()
    private val repository = UserRepositoryImpl(remoteDataSource)

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

    @Test
    fun `getUser EXPECT user entity returned`() = runTest {
        val userResponse = UserResponse(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        val sessionResponse = UserSessionResponse(user = userResponse)
        val expected = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { remoteDataSource.getUser(TEST_TOKEN) } returns sessionResponse

        val actual = repository.getUser(TEST_TOKEN)

        assertEquals(expected, actual)
        coVerify { remoteDataSource.getUser(TEST_TOKEN) }
    }

    @Test
    fun `getUser with null fields EXPECT user entity with nulls returned`() = runTest {
        val userResponse = UserResponse(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        val sessionResponse = UserSessionResponse(user = userResponse)
        val expected = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        coEvery { remoteDataSource.getUser(TEST_TOKEN) } returns sessionResponse

        val actual = repository.getUser(TEST_TOKEN)

        assertEquals(expected, actual)
    }

    @Test
    fun `updateUser EXPECT remote data source called with correct request`() = runTest {
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

        repository.updateUser(user, TEST_TOKEN)

        coVerify { remoteDataSource.updateUser(expectedRequest, TEST_TOKEN) }
    }

    @Test
    fun `updateUser with null fields EXPECT remote data source called with nulls`() = runTest {
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

        repository.updateUser(user, TEST_TOKEN)

        coVerify { remoteDataSource.updateUser(expectedRequest, TEST_TOKEN) }
    }
}
