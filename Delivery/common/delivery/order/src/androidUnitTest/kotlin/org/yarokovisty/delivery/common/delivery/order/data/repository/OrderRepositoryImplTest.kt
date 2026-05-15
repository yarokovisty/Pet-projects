package org.yarokovisty.delivery.common.delivery.order.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.common.delivery.direction.data.model.DeliveryPointResponse
import org.yarokovisty.delivery.common.delivery.order.data.datasource.OrderRemoteDataSource
import org.yarokovisty.delivery.common.delivery.order.data.model.CreateOrderResponse
import org.yarokovisty.delivery.common.delivery.order.data.model.OrderResponse
import org.yarokovisty.delivery.common.delivery.order.data.model.OrderStatusResponse
import org.yarokovisty.delivery.common.delivery.order.domain.entity.OrderStatus
import org.yarokovisty.delivery.common.delivery.parcel.data.model.PackageTypeResponse
import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.common.delivery.person.data.model.PersonInfoResponse
import org.yarokovisty.delivery.common.delivery.point.data.model.AddressResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OrderRepositoryImplTest {

    private val remoteDataSource: OrderRemoteDataSource = mockk()
    private val repository = OrderRepositoryImpl(remoteDataSource)

    private val orderResponse = OrderResponse(
        id = "order-1",
        price = 500.0,
        packageType = PackageTypeResponse(
            id = "BOX_S",
            name = "Small Box",
            length = 20,
            width = 15,
            height = 10,
            weight = 5
        ),
        option = "DEFAULT",
        senderPoint = DeliveryPointResponse(
            id = "sp-1",
            name = "Sender Point",
            latitude = 55.75,
            longitude = 37.62
        ),
        senderAddress = AddressResponse(
            street = "Lenina",
            house = "10",
            apartment = "5",
            comment = "",
            nonContacted = false
        ),
        sender = PersonInfoResponse(
            firstname = "Ivan",
            lastname = "Ivanov",
            middlename = "Ivanovich",
            phone = "+79991234567"
        ),
        receiverPoint = DeliveryPointResponse(
            id = "rp-1",
            name = "Receiver Point",
            latitude = 59.93,
            longitude = 30.32
        ),
        receiverAddress = AddressResponse(
            street = "Pushkina",
            house = "20",
            apartment = "15",
            comment = "Door code 123",
            nonContacted = true
        ),
        receiver = PersonInfoResponse(
            firstname = "Petr",
            lastname = "Petrov",
            middlename = "Petrovich",
            phone = "+79997654321"
        ),
        payer = "SENDER",
        status = OrderStatusResponse.CREATED,
        cancellable = true
    )

    private val createOrderResponse = CreateOrderResponse(order = orderResponse)

    @Test
    fun `create order EXPECT correct order id`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals("order-1", result.id)
    }

    @Test
    fun `create order EXPECT correct price`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals(500.0, result.price)
    }

    @Test
    fun `create order EXPECT correct option type`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals(OptionType.DEFAULT, result.optionType)
    }

    @Test
    fun `create order EXPECT correct sender firstname`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals("Ivan", result.sender.firstname)
    }

    @Test
    fun `create order EXPECT correct receiver firstname`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals("Petr", result.receiver.firstname)
    }

    @Test
    fun `create order EXPECT correct sender address street`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals("Lenina", result.senderAddress.street)
    }

    @Test
    fun `create order EXPECT correct receiver address street`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals("Pushkina", result.receiverAddress.street)
    }

    @Test
    fun `create order EXPECT correct sender point name`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals("Sender Point", result.senderPoint.name)
    }

    @Test
    fun `create order EXPECT correct receiver point name`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals("Receiver Point", result.receiverPoint.name)
    }

    @Test
    fun `create order EXPECT correct payer`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals(Payer.SENDER, result.payer)
    }

    @Test
    fun `create order EXPECT correct status`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals(OrderStatus.CREATED, result.status)
    }

    @Test
    fun `create order EXPECT correct cancellable flag`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals(true, result.cancellable)
    }

    @Test
    fun `create order EXPECT correct parcel info name`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        val result = repository.createOrder(mockk(relaxed = true))

        assertEquals("Small Box", result.parcelInfo.name)
    }

    @Test
    fun `create order EXPECT data source called`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } returns createOrderResponse

        repository.createOrder(mockk(relaxed = true))

        coVerify(exactly = 1) { remoteDataSource.createOrder(any()) }
    }

    @Test
    fun `create order when data source throws exception EXPECT exception propagated`() = runTest {
        coEvery { remoteDataSource.createOrder(any()) } throws RuntimeException("Network error")

        assertFailsWith<RuntimeException> {
            repository.createOrder(mockk(relaxed = true))
        }
    }
}
