package org.yarokovisty.delivery.feature.delivery.main.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint

class GetDeliveryPointByNameUseCase(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(points: List<DeliveryPoint>, pointName: String): DeliveryPoint =
        withContext(dispatcher) {
            points.find { point ->
                point.name == pointName
            } ?: error("Point $pointName not found")
        }
}
