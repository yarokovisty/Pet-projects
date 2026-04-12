package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent

import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface DeliveryMainIntent : Intent {
    data object LoadData : DeliveryMainIntent

    data object SelectDeliveryPointFrom : DeliveryMainIntent
    data class SelectAlternativeDeliveryPointFrom(val pointName: String) : DeliveryMainIntent
    data object SelectDeliveryPointTo : DeliveryMainIntent
    data class SelectAlternativeDeliveryPointTo(val pointName: String) : DeliveryMainIntent

    data object OpenParcelTypeScreen : DeliveryMainIntent
    data object CloseParcelTypeScreen : DeliveryMainIntent
    data class SelectParcelType(val parcelInfo: ParcelInfo) : DeliveryMainIntent

    data object CalculateDelivery : DeliveryMainIntent

    data class ChangeInputParcelId(val id: String) : DeliveryMainIntent
    data object TrackParcel : DeliveryMainIntent
}
