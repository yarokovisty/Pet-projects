package org.yarokovisty.delivery.user.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.user.domain.repository.UserRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoveUserPhoneNumberUseCaseTest {

    private val repository: UserRepository = mockk()
    private val useCase = RemoveUserPhoneNumberUseCase(repository)

    @Test
    fun `invoke EXPECT invoke remove user phone number by repository`() = runTest {
        coEvery { repository.removeUserPhoneNumber() } returns Unit

        useCase()

        coVerify { repository.removeUserPhoneNumber() }
    }

    @Test
    fun `invoke EXPECT unit returned`() = runTest {
        coEvery { repository.removeUserPhoneNumber() } returns Unit

        val actual = useCase()

        assertEquals(Unit, actual)
    }
}
