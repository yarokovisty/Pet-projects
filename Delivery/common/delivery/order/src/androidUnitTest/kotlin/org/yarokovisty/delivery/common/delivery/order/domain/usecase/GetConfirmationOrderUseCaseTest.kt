package org.yarokovisty.delivery.common.delivery.order.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.common.delivery.calculator.domain.repository.CalculatorRepository
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.direction.domain.repository.DirectionRepository
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.PackageType
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.common.delivery.parcel.domain.repository.ParcelRepository
import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.common.delivery.payer.domain.repository.PayerRepository
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.common.delivery.person.domain.repository.PersonRepository
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.common.delivery.point.domain.repository.AddressRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetConfirmationOrderUseCaseTest {

    private val calculatorRepository: CalculatorRepository = mockk()
    private val directionRepository: DirectionRepository = mockk()
    private val parcelRepository: ParcelRepository = mockk()
    private val personRepository: PersonRepository = mockk()
    private val addressRepository: AddressRepository = mockk()
    private val payerRepository: PayerRepository = mockk()
    private val useCase = GetConfirmationOrderUseCase(
        calculatorRepository = calculatorRepository,
        directionRepository = directionRepository,
        parcelRepository = parcelRepository,
        personRepository = personRepository,
        addressRepository = addressRepository,
        payerRepository = payerRepository
    )

    private val parcelInfo = ParcelInfo(
        id = "envelope",
        type = PackageType.ENVELOPE,
        name = "Envelope",
        length = 1,
        width = 1,
        height = 1,
        weight = 1
    )

    private val senderPoint = DeliveryPoint(
        id = "point-from-1",
        name = "Moscow",
        latitude = 55.75,
        longitude = 37.61
    )

    private val receiverPoint = DeliveryPoint(
        id = "point-to-1",
        name = "Saint Petersburg",
        latitude = 59.93,
        longitude = 30.31
    )

    private val option = Option(
        id = "opt-1",
        price = 500.0,
        days = 3,
        type = OptionType.DEFAULT
    )

    private val sender = PersonInfo(
        firstname = "Ivan",
        lastname = "Ivanov",
        middlename = "Ivanovich",
        phone = "+79991234567"
    )

    private val receiver = PersonInfo(
        firstname = "Petr",
        lastname = "Petrov",
        middlename = null,
        phone = "+79997654321"
    )

    private val senderAddress = Address(
        street = "Lenina",
        house = "10",
        apartment = "5",
        comment = "",
        nonContacted = false
    )

    private val receiverAddress = Address(
        street = "Pushkina",
        house = "20",
        apartment = "15",
        comment = "Door code 123",
        nonContacted = true
    )

    private val payer = Payer.SENDER

    private fun setupAllMocks() {
        coEvery { parcelRepository.getSelectedParcel() } returns parcelInfo
        coEvery { calculatorRepository.getOption() } returns option
        coEvery { directionRepository.getSelectedPointFrom() } returns senderPoint
        coEvery { directionRepository.getSelectedPointTo() } returns receiverPoint
        coEvery { personRepository.getSender() } returns sender
        coEvery { addressRepository.getSender() } returns senderAddress
        coEvery { personRepository.getReceiver() } returns receiver
        coEvery { addressRepository.getReceiver() } returns receiverAddress
        coEvery { payerRepository.getPayer() } returns payer
    }

    @Test
    fun `invoke when all data is present EXPECT correct packageId`() = runTest {
        setupAllMocks()

        val result = useCase()

        assertEquals(parcelInfo.id, result.packageId)
    }

    @Test
    fun `invoke when all data is present EXPECT correct option`() = runTest {
        setupAllMocks()

        val result = useCase()

        assertEquals(option, result.option)
    }

    @Test
    fun `invoke when all data is present EXPECT correct senderPointId`() = runTest {
        setupAllMocks()

        val result = useCase()

        assertEquals(senderPoint.id, result.senderPointId)
    }

    @Test
    fun `invoke when all data is present EXPECT correct sender`() = runTest {
        setupAllMocks()

        val result = useCase()

        assertEquals(sender, result.sender)
    }

    @Test
    fun `invoke when all data is present EXPECT correct sender address`() = runTest {
        setupAllMocks()

        val result = useCase()

        assertEquals(senderAddress, result.senderAddress)
    }

    @Test
    fun `invoke when all data is present EXPECT correct receiverPointId`() = runTest {
        setupAllMocks()

        val result = useCase()

        assertEquals(receiverPoint.id, result.receiverPointId)
    }

    @Test
    fun `invoke when all data is present EXPECT correct receiver`() = runTest {
        setupAllMocks()

        val result = useCase()

        assertEquals(receiver, result.receiver)
    }

    @Test
    fun `invoke when all data is present EXPECT correct receiver address`() = runTest {
        setupAllMocks()

        val result = useCase()

        assertEquals(receiverAddress, result.receiverAddress)
    }

    @Test
    fun `invoke when all data is present EXPECT correct payer`() = runTest {
        setupAllMocks()

        val result = useCase()

        assertEquals(payer, result.payer)
    }

    @Test
    fun `invoke when parcel is null EXPECT IllegalStateException`() = runTest {
        coEvery { parcelRepository.getSelectedParcel() } returns null
        coEvery { calculatorRepository.getOption() } returns option
        coEvery { directionRepository.getSelectedPointFrom() } returns senderPoint
        coEvery { directionRepository.getSelectedPointTo() } returns receiverPoint
        coEvery { personRepository.getSender() } returns sender
        coEvery { addressRepository.getSender() } returns senderAddress
        coEvery { personRepository.getReceiver() } returns receiver
        coEvery { addressRepository.getReceiver() } returns receiverAddress
        coEvery { payerRepository.getPayer() } returns payer

        assertFailsWith<IllegalStateException> { useCase() }
    }

    @Test
    fun `invoke when option is null EXPECT IllegalStateException`() = runTest {
        coEvery { parcelRepository.getSelectedParcel() } returns parcelInfo
        coEvery { calculatorRepository.getOption() } returns null
        coEvery { directionRepository.getSelectedPointFrom() } returns senderPoint
        coEvery { directionRepository.getSelectedPointTo() } returns receiverPoint
        coEvery { personRepository.getSender() } returns sender
        coEvery { addressRepository.getSender() } returns senderAddress
        coEvery { personRepository.getReceiver() } returns receiver
        coEvery { addressRepository.getReceiver() } returns receiverAddress
        coEvery { payerRepository.getPayer() } returns payer

        assertFailsWith<IllegalStateException> { useCase() }
    }

    @Test
    fun `invoke when sender point is null EXPECT IllegalStateException`() = runTest {
        coEvery { parcelRepository.getSelectedParcel() } returns parcelInfo
        coEvery { calculatorRepository.getOption() } returns option
        coEvery { directionRepository.getSelectedPointFrom() } returns null
        coEvery { directionRepository.getSelectedPointTo() } returns receiverPoint
        coEvery { personRepository.getSender() } returns sender
        coEvery { addressRepository.getSender() } returns senderAddress
        coEvery { personRepository.getReceiver() } returns receiver
        coEvery { addressRepository.getReceiver() } returns receiverAddress
        coEvery { payerRepository.getPayer() } returns payer

        assertFailsWith<IllegalStateException> { useCase() }
    }

    @Test
    fun `invoke when sender is null EXPECT IllegalStateException`() = runTest {
        coEvery { parcelRepository.getSelectedParcel() } returns parcelInfo
        coEvery { calculatorRepository.getOption() } returns option
        coEvery { directionRepository.getSelectedPointFrom() } returns senderPoint
        coEvery { directionRepository.getSelectedPointTo() } returns receiverPoint
        coEvery { personRepository.getSender() } returns null
        coEvery { addressRepository.getSender() } returns senderAddress
        coEvery { personRepository.getReceiver() } returns receiver
        coEvery { addressRepository.getReceiver() } returns receiverAddress
        coEvery { payerRepository.getPayer() } returns payer

        assertFailsWith<IllegalStateException> { useCase() }
    }

    @Test
    fun `invoke when sender address is null EXPECT IllegalStateException`() = runTest {
        coEvery { parcelRepository.getSelectedParcel() } returns parcelInfo
        coEvery { calculatorRepository.getOption() } returns option
        coEvery { directionRepository.getSelectedPointFrom() } returns senderPoint
        coEvery { directionRepository.getSelectedPointTo() } returns receiverPoint
        coEvery { personRepository.getSender() } returns sender
        coEvery { addressRepository.getSender() } returns null
        coEvery { personRepository.getReceiver() } returns receiver
        coEvery { addressRepository.getReceiver() } returns receiverAddress
        coEvery { payerRepository.getPayer() } returns payer

        assertFailsWith<IllegalStateException> { useCase() }
    }

    @Test
    fun `invoke when receiver point is null EXPECT IllegalStateException`() = runTest {
        coEvery { parcelRepository.getSelectedParcel() } returns parcelInfo
        coEvery { calculatorRepository.getOption() } returns option
        coEvery { directionRepository.getSelectedPointFrom() } returns senderPoint
        coEvery { directionRepository.getSelectedPointTo() } returns null
        coEvery { personRepository.getSender() } returns sender
        coEvery { addressRepository.getSender() } returns senderAddress
        coEvery { personRepository.getReceiver() } returns receiver
        coEvery { addressRepository.getReceiver() } returns receiverAddress
        coEvery { payerRepository.getPayer() } returns payer

        assertFailsWith<IllegalStateException> { useCase() }
    }

    @Test
    fun `invoke when receiver is null EXPECT IllegalStateException`() = runTest {
        coEvery { parcelRepository.getSelectedParcel() } returns parcelInfo
        coEvery { calculatorRepository.getOption() } returns option
        coEvery { directionRepository.getSelectedPointFrom() } returns senderPoint
        coEvery { directionRepository.getSelectedPointTo() } returns receiverPoint
        coEvery { personRepository.getSender() } returns sender
        coEvery { addressRepository.getSender() } returns senderAddress
        coEvery { personRepository.getReceiver() } returns null
        coEvery { addressRepository.getReceiver() } returns receiverAddress
        coEvery { payerRepository.getPayer() } returns payer

        assertFailsWith<IllegalStateException> { useCase() }
    }

    @Test
    fun `invoke when receiver address is null EXPECT IllegalStateException`() = runTest {
        coEvery { parcelRepository.getSelectedParcel() } returns parcelInfo
        coEvery { calculatorRepository.getOption() } returns option
        coEvery { directionRepository.getSelectedPointFrom() } returns senderPoint
        coEvery { directionRepository.getSelectedPointTo() } returns receiverPoint
        coEvery { personRepository.getSender() } returns sender
        coEvery { addressRepository.getSender() } returns senderAddress
        coEvery { personRepository.getReceiver() } returns receiver
        coEvery { addressRepository.getReceiver() } returns null
        coEvery { payerRepository.getPayer() } returns payer

        assertFailsWith<IllegalStateException> { useCase() }
    }

    @Test
    fun `invoke when payer is null EXPECT IllegalStateException`() = runTest {
        coEvery { parcelRepository.getSelectedParcel() } returns parcelInfo
        coEvery { calculatorRepository.getOption() } returns option
        coEvery { directionRepository.getSelectedPointFrom() } returns senderPoint
        coEvery { directionRepository.getSelectedPointTo() } returns receiverPoint
        coEvery { personRepository.getSender() } returns sender
        coEvery { addressRepository.getSender() } returns senderAddress
        coEvery { personRepository.getReceiver() } returns receiver
        coEvery { addressRepository.getReceiver() } returns receiverAddress
        coEvery { payerRepository.getPayer() } returns null

        assertFailsWith<IllegalStateException> { useCase() }
    }
}
