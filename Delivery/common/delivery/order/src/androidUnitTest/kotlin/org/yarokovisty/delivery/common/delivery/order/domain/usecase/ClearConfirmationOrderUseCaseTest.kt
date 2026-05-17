package org.yarokovisty.delivery.common.delivery.order.domain.usecase

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.calculator.domain.repository.CalculatorRepository
import org.yarokovisty.delivery.common.delivery.direction.domain.repository.DirectionRepository
import org.yarokovisty.delivery.common.delivery.parcel.domain.repository.ParcelRepository
import org.yarokovisty.delivery.common.delivery.payer.domain.repository.PayerRepository
import org.yarokovisty.delivery.common.delivery.person.domain.repository.PersonRepository
import org.yarokovisty.delivery.common.delivery.point.domain.repository.AddressRepository
import kotlin.test.Test
import kotlin.test.assertTrue

class ClearConfirmationOrderUseCaseTest {

    private val addressRepository: AddressRepository = mockk(relaxed = true)
    private val calculatorRepository: CalculatorRepository = mockk(relaxed = true)
    private val directionRepository: DirectionRepository = mockk(relaxed = true)
    private val parcelRepository: ParcelRepository = mockk(relaxed = true)
    private val payerRepository: PayerRepository = mockk(relaxed = true)
    private val personRepository: PersonRepository = mockk(relaxed = true)
    private val useCase = ClearConfirmationOrderUseCase(
        addressRepository = addressRepository,
        calculatorRepository = calculatorRepository,
        directionRepository = directionRepository,
        parcelRepository = parcelRepository,
        payerRepository = payerRepository,
        personRepository = personRepository
    )

    @Test
    fun `invoke EXPECT address receiver cleared`() = runTest {
        useCase()

        coVerify { addressRepository.clearReceiver() }
    }

    @Test
    fun `invoke EXPECT address sender cleared`() = runTest {
        useCase()

        coVerify { addressRepository.clearSender() }
    }

    @Test
    fun `invoke EXPECT calculator option cleared`() = runTest {
        useCase()

        coVerify { calculatorRepository.clearOption() }
    }

    @Test
    fun `invoke EXPECT direction point from cleared`() = runTest {
        useCase()

        coVerify { directionRepository.clearSelectedPointFrom() }
    }

    @Test
    fun `invoke EXPECT direction point to cleared`() = runTest {
        useCase()

        coVerify { directionRepository.clearSelectedPointTo() }
    }

    @Test
    fun `invoke EXPECT parcel cleared`() = runTest {
        useCase()

        coVerify { parcelRepository.clearSelectedParcel() }
    }

    @Test
    fun `invoke EXPECT payer cleared`() = runTest {
        useCase()

        coVerify { payerRepository.clearPayer() }
    }

    @Test
    fun `invoke EXPECT person receiver cleared`() = runTest {
        useCase()

        coVerify { personRepository.clearReceiver() }
    }

    @Test
    fun `invoke EXPECT person sender cleared`() = runTest {
        useCase()

        coVerify { personRepository.clearSender() }
    }

    @Test
    fun `invoke EXPECT success result`() = runTest {
        val result = useCase()

        assertTrue(result.isSuccess)
    }
}
