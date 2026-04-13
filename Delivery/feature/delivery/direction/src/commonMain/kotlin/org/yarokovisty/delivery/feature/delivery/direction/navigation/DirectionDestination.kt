package org.yarokovisty.delivery.feature.delivery.direction.navigation

import kotlinx.serialization.Serializable
import org.yarokovisty.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.delivery.libs.navigation.destination.Screen

@Serializable
data class DirectionDestination(val directionType: DirectionType) : Screen
