package org.yarokovisty.delivery.feature.delivery.calculator.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.libs.navigation.destination.Screen

@Serializable
data class CalculatorDestination(
    val parcelInfo: ParcelInfo,
    val senderPoint: DeliveryPoint,
    val receiverPoint: DeliveryPoint
) : Screen
