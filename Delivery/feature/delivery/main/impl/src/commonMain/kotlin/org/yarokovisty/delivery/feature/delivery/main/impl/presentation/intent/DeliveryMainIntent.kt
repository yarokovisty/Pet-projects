package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent

import org.yarokovisty.delivery.common.presentation.Intent
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType

internal sealed interface DeliveryMainIntent : Intent {
    object LoadData : DeliveryMainIntent

    object SelectDeliveryPointFrom : DeliveryMainIntent
    class SelectAlternativeDeliveryPointFrom(val pointName: String) : DeliveryMainIntent
    object SelectDeliveryPointTo : DeliveryMainIntent
    class SelectAlternativeDeliveryPointTo(val pointName: String) : DeliveryMainIntent

    object OpenParcelTypeScreen : DeliveryMainIntent
    object CloseParcelTypeScreen : DeliveryMainIntent
    class SelectParcelType(val parcelType: ParcelType) : DeliveryMainIntent

    object CalculateDelivery : DeliveryMainIntent

    class ChangeInputParcelId(val id: String) : DeliveryMainIntent
    object TrackParcel : DeliveryMainIntent
}
