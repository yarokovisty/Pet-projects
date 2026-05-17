package org.yarokovisty.delivery.feature.delivery.point.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.libs.navigation.destination.Screen

@Serializable
data class SenderAddressDestination(val screenType: AddressScreenType) : Screen

@Serializable
data class ReceiverAddressDestination(val screenType: AddressScreenType) : Screen
