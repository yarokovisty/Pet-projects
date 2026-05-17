package org.yarokovisty.delivery.common.delivery.direction.data.datasource

import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage

internal class DirectionLocalDataSource(private val storage: PreferencesStorage) {

    private companion object {

        const val POINT_FROM_KEY = "point_from"
        const val POINT_TO_KEY = "point_to"
    }

    suspend fun getPointFrom(): DeliveryPoint? =
        storage.getObject(POINT_FROM_KEY, DeliveryPoint.serializer())

    suspend fun savePointFrom(point: DeliveryPoint) {
        storage.putObject(POINT_FROM_KEY, point, DeliveryPoint.serializer())
    }

    suspend fun clearPointFrom() {
        storage.remove(POINT_FROM_KEY)
    }

    suspend fun getPointTo(): DeliveryPoint? =
        storage.getObject(POINT_TO_KEY, DeliveryPoint.serializer())

    suspend fun savePointTo(point: DeliveryPoint) {
        storage.putObject(POINT_TO_KEY, point, DeliveryPoint.serializer())
    }

    suspend fun clearPointTo() {
        storage.remove(POINT_TO_KEY)
    }
}
