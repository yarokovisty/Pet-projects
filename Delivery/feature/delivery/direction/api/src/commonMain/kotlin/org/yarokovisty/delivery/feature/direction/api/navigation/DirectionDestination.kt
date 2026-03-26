package org.yarokovisty.delivery.feature.direction.api.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DirectionType
import org.yarokovisty.delivery.libs.navigation.destination.Screen

@Serializable
data class DirectionDestination(val directionType: DirectionType) : Screen
