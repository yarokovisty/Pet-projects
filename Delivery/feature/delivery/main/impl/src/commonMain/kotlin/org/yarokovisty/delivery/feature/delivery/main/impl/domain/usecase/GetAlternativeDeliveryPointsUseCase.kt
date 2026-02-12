package org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase

import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.DeliveryPoint

internal class GetAlternativeDeliveryPointsUseCase {

    private companion object {
        const val ALTERNATIVE_POINTS_LIMIT = 3
    }

    operator fun invoke(points: List<DeliveryPoint>): List<DeliveryPoint> = runCatching {
        points.take(ALTERNATIVE_POINTS_LIMIT)
    }.getOrNull() ?: emptyList()
}
