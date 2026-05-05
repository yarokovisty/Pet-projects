package org.yarokovisty.delivery.feature.delivery.point.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.libs.navigation.destination.Screen

@Serializable
data object SenderAddressDestination : Screen

@Serializable
data object ReceiverAddressDestination : Screen
