package org.yarokovisty.delivery.common.delivery.order.domain.entity

import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address

data class Order(
    val id: String,
    val price: Double,
    val optionType: OptionType,
    val parcelInfo: ParcelInfo,
    val sender: PersonInfo,
    val senderAddress: Address,
    val senderPoint: DeliveryPoint,
    val receiver: PersonInfo,
    val receiverAddress: Address,
    val receiverPoint: DeliveryPoint,
    val payer: Payer,
    val status: OrderStatus,
    val cancellable: Boolean,
)
