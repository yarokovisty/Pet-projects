package org.yarokovisty.delivery.common.profile.main.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository
import kotlin.test.Test
import kotlin.test.assertFailsWith

class UpdateUserUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val useCase =
        UpdateUserUseCase(authRepository, userRepository)

    private companion object {
        const val TEST_TOKEN = "token_abc123"
        const val TEST_ID = "user123"
        const val TEST_PHONE = "79123456789"
        const val TEST_FIRSTNAME = "Ivan"
        const val TEST_EMAIL = "ivan@example.com"
        const val TEST_CITY = "Moscow"
    }

    @Test
    fun `invoke with valid token EXPECT user repository called`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { authRepository.getToken() } returns TEST_TOKEN
        coEvery { userRepository.update(user, TEST_TOKEN) } returns Unit

        useCase(user)

        coVerify { userRepository.update(user, TEST_TOKEN) }
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
            city = TEST_CITY
        )
        coEvery { authRepository.getToken() } returns TEST_TOKEN
        coEvery { userRepository.update(user, TEST_TOKEN) } returns Unit

        useCase(user)

        coVerify { authRepository.getToken() }
    }

    @Test
    fun `invoke when token is null EXPECT error thrown`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { authRepository.getToken() } returns null

        assertFailsWith<IllegalStateException> {
            useCase(user)
        }
    }

    @Test
    fun `invoke when token is null EXPECT user repository not called`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { authRepository.getToken() } returns null

        assertFailsWith<IllegalStateException> { useCase(user) }

        coVerify(exactly = 0) { userRepository.update(any(), any()) }
    }

    @Test
    fun `invoke with user containing null fields EXPECT repository called with same user`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        coEvery { authRepository.getToken() } returns TEST_TOKEN
        coEvery { userRepository.update(user, TEST_TOKEN) } returns Unit

        useCase(user)

        coVerify { userRepository.update(user, TEST_TOKEN) }
    }
}
