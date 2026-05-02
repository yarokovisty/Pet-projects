package org.yarokovisty.delivery.feature.delivery.person.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.libs.navigation.destination.Screen

@Serializable
data object ReceiverDestination : Screen

@Serializable
data object SenderDestination : Screen
