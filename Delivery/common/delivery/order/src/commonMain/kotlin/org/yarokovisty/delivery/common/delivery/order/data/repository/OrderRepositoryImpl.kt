package org.yarokovisty.delivery.common.delivery.order.data.repository

import org.yarokovisty.delivery.common.delivery.order.data.datasource.OrderRemoteDataSource
import org.yarokovisty.delivery.common.delivery.order.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.order.data.mapper.toRequest
import org.yarokovisty.delivery.common.delivery.order.domain.entity.ConfirmationOrder
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.common.delivery.order.domain.repository.OrderRepository

internal class OrderRepositoryImpl(
    private val remoteDataSource: OrderRemoteDataSource,
) : OrderRepository {

    override suspend fun create(confirmation: ConfirmationOrder): Order {
        val request = confirmation.toRequest()
        val response = remoteDataSource.create(request)

        return response.order.toItem()
    }

    override suspend fun getHistory(token: String): List<Order> =
        remoteDataSource.getHistory(token).toItem()
}
