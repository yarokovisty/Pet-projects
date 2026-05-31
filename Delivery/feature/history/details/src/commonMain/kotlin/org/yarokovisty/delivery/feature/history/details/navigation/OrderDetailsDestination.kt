package org.yarokovisty.delivery.feature.history.details.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.libs.navigation.destination.Screen

@Serializable
data class OrderDetailsDestination(val orderId: String) : Screen
