package org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint

internal class GetAlternativeDeliveryPointsUseCase {

    private companion object {
        const val ALTERNATIVE_POINTS_LIMIT = 3
    }

    operator fun invoke(points: List<DeliveryPoint>): List<DeliveryPoint> = runCatching {
        points.subList(0, ALTERNATIVE_POINTS_LIMIT)
    }.getOrNull() ?: emptyList()
}
