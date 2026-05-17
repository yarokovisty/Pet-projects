package org.yarokovisty.delivery.common.delivery.order.data.mapper

import org.yarokovisty.delivery.common.delivery.direction.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.order.data.model.ConfirmationOrderRequest
import org.yarokovisty.delivery.common.delivery.order.data.model.OrderResponse
import org.yarokovisty.delivery.common.delivery.order.data.model.OrderStatusResponse
import org.yarokovisty.delivery.common.delivery.order.domain.entity.ConfirmationOrder
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.common.delivery.order.domain.entity.OrderStatus
import org.yarokovisty.delivery.common.delivery.parcel.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.person.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.person.data.mapper.toRequest
import org.yarokovisty.delivery.common.delivery.point.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.point.data.mapper.toRequest
import org.yarokovisty.delivery.util.kotlin.enums.toEnum

internal fun OrderResponse.toItem(): Order =
    Order(
        id = id,
        price = price,
        optionType = option.toEnum(),
        parcelInfo = packageType.toItem(),
        sender = sender.toItem(),
        senderAddress = senderAddress.toItem(),
        senderPoint = senderPoint.toItem(),
        receiver = receiver.toItem(),
        receiverAddress = receiverAddress.toItem(),
        receiverPoint = receiverPoint.toItem(),
        payer = payer.toEnum(),
        status = status.toOrderStatus(),
        cancellable = cancellable
    )

private fun Int.toOrderStatus(): OrderStatus =
    when (this) {
        OrderStatusResponse.CREATED -> OrderStatus.CREATED
        OrderStatusResponse.WAITING -> OrderStatus.WAITING
        OrderStatusResponse.DELIVERING -> OrderStatus.DELIVERING
        OrderStatusResponse.DELIVERED -> OrderStatus.DELIVERED
        OrderStatusResponse.CANCELLED -> OrderStatus.CANCELLED
        else -> error("Order status $this is unknown")
    }

internal fun ConfirmationOrder.toRequest(): ConfirmationOrderRequest =
    ConfirmationOrderRequest(
        packageId = packageId,
        optionType = option.type.name,
        senderPointId = senderPointId,
        senderAddress = senderAddress.toRequest(),
        sender = sender.toRequest(),
        receiverPointId = receiverPointId,
        receiverAddress = receiverAddress.toRequest(),
        receiver = receiver.toRequest(),
        payer = payer.name
    )
