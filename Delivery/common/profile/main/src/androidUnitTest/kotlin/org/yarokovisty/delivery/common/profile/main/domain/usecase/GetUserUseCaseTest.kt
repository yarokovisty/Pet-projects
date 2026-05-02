package org.yarokovisty.delivery.common.profile.main.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetUserUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val useCase =
        GetUserUseCase(authRepository, userRepository)

    private companion object {
        const val TEST_TOKEN = "token_abc123"
        const val TEST_ID = "user123"
        const val TEST_PHONE = "79123456789"
        const val TEST_FIRSTNAME = "Ivan"
        const val TEST_EMAIL = "ivan@example.com"
    }

    @Test
    fun `invoke when local user exists EXPECT local user returned`() = runTest {
        val localUser = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { userRepository.getUserFromLocal() } returns localUser

        val actual = useCase()

        assertEquals(localUser, actual)
    }

    @Test
    fun `invoke when local user exists EXPECT network not called`() = runTest {
        val localUser = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { userRepository.getUserFromLocal() } returns localUser

        useCase()

        coVerify(exactly = 0) { userRepository.getUserFromNetwork(any()) }
    }

    @Test
    fun `invoke when local user exists EXPECT auth repository not called`() = runTest {
        val localUser = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { userRepository.getUserFromLocal() } returns localUser

        useCase()

        coVerify(exactly = 0) { authRepository.getToken() }
    }

    @Test
    fun `invoke when local is null and token exists EXPECT network user returned`() = runTest {
        val networkUser = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { userRepository.getUserFromLocal() } returns null
        coEvery { authRepository.getToken() } returns TEST_TOKEN
        coEvery { userRepository.getUserFromNetwork(TEST_TOKEN) } returns networkUser

        val actual = useCase()

        assertEquals(networkUser, actual)
    }

    @Test
    fun `invoke when local is null and token exists EXPECT network called with token`() = runTest {
        val networkUser = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { userRepository.getUserFromLocal() } returns null
        coEvery { authRepository.getToken() } returns TEST_TOKEN
        coEvery { userRepository.getUserFromNetwork(TEST_TOKEN) } returns networkUser

        useCase()

        coVerify { userRepository.getUserFromNetwork(TEST_TOKEN) }
    }

    @Test
    fun `invoke when local is null and token is null EXPECT null returned`() = runTest {
        coEvery { userRepository.getUserFromLocal() } returns null
        coEvery { authRepository.getToken() } returns null

        val actual = useCase()

        assertNull(actual)
    }

    @Test
    fun `invoke when local is null and token is null EXPECT network not called`() = runTest {
        coEvery { userRepository.getUserFromLocal() } returns null
        coEvery { authRepository.getToken() } returns null

        useCase()

        coVerify(exactly = 0) { userRepository.getUserFromNetwork(any()) }
    }
}
