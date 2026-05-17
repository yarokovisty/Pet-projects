package org.yarokovisty.delivery.feature.delivery.person.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.libs.navigation.destination.Screen

@Serializable
data class ReceiverDestination(val type: PersonScreenType) : Screen

@Serializable
data class SenderDestination(val type: PersonScreenType) : Screen
