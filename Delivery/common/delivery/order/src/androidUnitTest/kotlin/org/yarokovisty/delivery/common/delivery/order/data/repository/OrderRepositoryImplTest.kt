package org.yarokovisty.delivery.common.delivery.order.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.common.delivery.direction.data.model.DeliveryPointResponse
import org.yarokovisty.delivery.common.delivery.order.data.datasource.OrderRemoteDataSource
import org.yarokovisty.delivery.common.delivery.order.data.model.CreateOrderResponse
import org.yarokovisty.delivery.common.delivery.order.data.model.OrderListResponse
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

    private val secondOrderResponse = OrderResponse(
        id = "order-2",
        price = 750.0,
        packageType = PackageTypeResponse(
            id = "BOX_M",
            name = "Medium Box",
            length = 30,
            width = 25,
            height = 20,
            weight = 10
        ),
        option = "EXPRESS",
        senderPoint = DeliveryPointResponse(
            id = "sp-2",
            name = "Another Sender Point",
            latitude = 56.85,
            longitude = 35.92
        ),
        senderAddress = AddressResponse(
            street = "Gagarina",
            house = "5",
            apartment = "12",
            comment = "Ring twice",
            nonContacted = false
        ),
        sender = PersonInfoResponse(
            firstname = "Sergey",
            lastname = "Sergeev",
            middlename = "Sergeevich",
            phone = "+79998765432"
        ),
        receiverPoint = DeliveryPointResponse(
            id = "rp-2",
            name = "Another Receiver Point",
            latitude = 60.01,
            longitude = 31.45
        ),
        receiverAddress = AddressResponse(
            street = "Kirova",
            house = "8",
            apartment = "3",
            comment = "",
            nonContacted = false
        ),
        receiver = PersonInfoResponse(
            firstname = "Anna",
            lastname = "Annova",
            middlename = "Annovna",
            phone = "+79991112233"
        ),
        payer = "RECEIVER",
        status = OrderStatusResponse.DELIVERED,
        cancellable = false
    )

    private val orderListResponse = OrderListResponse(
        orders = listOf(orderResponse, secondOrderResponse)
    )

    private val emptyOrderListResponse = OrderListResponse(orders = emptyList())

    private val singleOrderListResponse = OrderListResponse(orders = listOf(orderResponse))

    @Test
    fun `create order EXPECT correct order id`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals("order-1", result.id)
    }

    @Test
    fun `create order EXPECT correct price`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals(500.0, result.price)
    }

    @Test
    fun `create order EXPECT correct option type`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals(OptionType.DEFAULT, result.optionType)
    }

    @Test
    fun `create order EXPECT correct sender firstname`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals("Ivan", result.sender.firstname)
    }

    @Test
    fun `create order EXPECT correct receiver firstname`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals("Petr", result.receiver.firstname)
    }

    @Test
    fun `create order EXPECT correct sender address street`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals("Lenina", result.senderAddress.street)
    }

    @Test
    fun `create order EXPECT correct receiver address street`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals("Pushkina", result.receiverAddress.street)
    }

    @Test
    fun `create order EXPECT correct sender point name`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals("Sender Point", result.senderPoint.name)
    }

    @Test
    fun `create order EXPECT correct receiver point name`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals("Receiver Point", result.receiverPoint.name)
    }

    @Test
    fun `create order EXPECT correct payer`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals(Payer.SENDER, result.payer)
    }

    @Test
    fun `create order EXPECT correct status`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals(OrderStatus.CREATED, result.status)
    }

    @Test
    fun `create order EXPECT correct cancellable flag`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals(true, result.cancellable)
    }

    @Test
    fun `create order EXPECT correct parcel info name`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        val result = repository.create(mockk(relaxed = true))

        assertEquals("Small Box", result.parcelInfo.name)
    }

    @Test
    fun `create order EXPECT data source called`() = runTest {
        coEvery { remoteDataSource.create(any()) } returns createOrderResponse

        repository.create(mockk(relaxed = true))

        coVerify(exactly = 1) { remoteDataSource.create(any()) }
    }

    @Test
    fun `create order when data source throws exception EXPECT exception propagated`() = runTest {
        coEvery { remoteDataSource.create(any()) } throws RuntimeException("Network error")

        assertFailsWith<RuntimeException> {
            repository.create(mockk(relaxed = true))
        }
    }

    @Test
    fun `get history EXPECT correct first order id`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns orderListResponse

        val result = repository.getHistory("test-token")

        assertEquals("order-1", result[0].id)
    }

    @Test
    fun `get history EXPECT correct second order id`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns orderListResponse

        val result = repository.getHistory("test-token")

        assertEquals("order-2", result[1].id)
    }

    @Test
    fun `get history EXPECT correct first order price`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns orderListResponse

        val result = repository.getHistory("test-token")

        assertEquals(500.0, result[0].price)
    }

    @Test
    fun `get history EXPECT correct second order price`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns orderListResponse

        val result = repository.getHistory("test-token")

        assertEquals(750.0, result[1].price)
    }

    @Test
    fun `get history EXPECT correct first order status`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns orderListResponse

        val result = repository.getHistory("test-token")

        assertEquals(OrderStatus.CREATED, result[0].status)
    }

    @Test
    fun `get history EXPECT correct second order status`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns orderListResponse

        val result = repository.getHistory("test-token")

        assertEquals(OrderStatus.DELIVERED, result[1].status)
    }

    @Test
    fun `get history EXPECT correct first order payer`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns orderListResponse

        val result = repository.getHistory("test-token")

        assertEquals(Payer.SENDER, result[0].payer)
    }

    @Test
    fun `get history EXPECT correct second order payer`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns orderListResponse

        val result = repository.getHistory("test-token")

        assertEquals(Payer.RECEIVER, result[1].payer)
    }

    @Test
    fun `get history EXPECT data source called with token`() = runTest {
        val token = "auth-token-123"
        coEvery { remoteDataSource.getHistory(token) } returns orderListResponse

        repository.getHistory(token)

        coVerify(exactly = 1) { remoteDataSource.getHistory(token) }
    }

    @Test
    fun `get history when data source throws exception EXPECT exception propagated`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } throws RuntimeException("Network error")

        assertFailsWith<RuntimeException> {
            repository.getHistory("test-token")
        }
    }

    @Test
    fun `get history with multiple orders EXPECT correct list size`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns orderListResponse

        val result = repository.getHistory("test-token")

        assertEquals(2, result.size)
    }

    @Test
    fun `get history with empty list EXPECT empty result`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns emptyOrderListResponse

        val result = repository.getHistory("test-token")

        assertEquals(0, result.size)
    }

    @Test
    fun `get history with single order EXPECT list size one`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns singleOrderListResponse

        val result = repository.getHistory("test-token")

        assertEquals(1, result.size)
    }

    @Test
    fun `get history with single order EXPECT correct order id`() = runTest {
        coEvery { remoteDataSource.getHistory(any()) } returns singleOrderListResponse

        val result = repository.getHistory("test-token")

        assertEquals("order-1", result[0].id)
    }
}
