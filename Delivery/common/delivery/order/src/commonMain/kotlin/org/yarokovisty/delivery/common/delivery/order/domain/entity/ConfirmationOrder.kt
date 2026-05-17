package org.yarokovisty.delivery.common.delivery.order.domain.entity

import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address

data class ConfirmationOrder(
    val packageId: String,
    val option: Option,
    val senderPointId: String,
    val sender: PersonInfo,
    val senderAddress: Address,
    val receiverPointId: String,
    val receiver: PersonInfo,
    val receiverAddress: Address,
    val payer: Payer
)
