package org.yarokovisty.delivery.common.logout.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.auth.domain.repository.AuthRepository
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository
import kotlin.test.Test

class LogoutUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val useCase = LogoutUseCase(
        authRepository = authRepository,
        userRepository = userRepository
    )

    @Test
    fun `invoke EXPECT auth repository clear token called`() = runTest {
        coEvery { authRepository.clearToken() } just runs
        coEvery { userRepository.clear() } just runs

        useCase()

        coVerify { authRepository.clearToken() }
    }

    @Test
    fun `invoke EXPECT user repository clear called`() = runTest {
        coEvery { authRepository.clearToken() } just runs
        coEvery { userRepository.clear() } just runs

        useCase()

        coVerify { userRepository.clear() }
    }

    @Test
    fun `invoke EXPECT auth repository cleared before user repository`() = runTest {
        coEvery { authRepository.clearToken() } just runs
        coEvery { userRepository.clear() } just runs

        useCase()

        coVerify(ordering = io.mockk.Ordering.ORDERED) {
            authRepository.clearToken()
            userRepository.clear()
        }
    }
}
