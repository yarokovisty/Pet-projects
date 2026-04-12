package org.yarokovisty.delivery.feature.profile.main.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.feature.profile.main.domain.entity.User
import org.yarokovisty.delivery.feature.profile.main.domain.repository.UserRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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
    fun `invoke with valid token EXPECT user returned`() = runTest {
        val expected = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { authRepository.getToken() } returns TEST_TOKEN
        coEvery { userRepository.getUser(TEST_TOKEN) } returns expected

        val actual = useCase()

        assertEquals(expected, actual)
    }

    @Test
    fun `invoke EXPECT auth repository called`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { authRepository.getToken() } returns TEST_TOKEN
        coEvery { userRepository.getUser(TEST_TOKEN) } returns user

        useCase()

        coVerify { authRepository.getToken() }
    }

    @Test
    fun `invoke EXPECT user repository called with token`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { authRepository.getToken() } returns TEST_TOKEN
        coEvery { userRepository.getUser(TEST_TOKEN) } returns user

        useCase()

        coVerify { userRepository.getUser(TEST_TOKEN) }
    }

    @Test
    fun `invoke when token is null EXPECT error thrown`() = runTest {
        coEvery { authRepository.getToken() } returns null

        assertFailsWith<IllegalStateException> {
            useCase()
        }
    }

    @Test
    fun `invoke when token is null EXPECT user repository not called`() = runTest {
        coEvery { authRepository.getToken() } returns null

        assertFailsWith<IllegalStateException> { useCase() }

        coVerify(exactly = 0) { userRepository.getUser(any()) }
    }
}
