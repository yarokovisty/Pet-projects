package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent

import org.yarokovisty.delivery.common.presentation.Intent

internal sealed interface DeliveryMainIntent : Intent {
    object SelectDeliveryPointFrom : DeliveryMainIntent
    class SelectAlternativeDeliveryPointFrom(val name: String) : DeliveryMainIntent
    object SelectDeliveryPointTo : DeliveryMainIntent
    class SelectAlternativeDeliveryPointTo(val name: String) : DeliveryMainIntent
    object SelectParcelType : DeliveryMainIntent
    object CalculateDelivery : DeliveryMainIntent

    class ChangeInputParcelId(val id: String) : DeliveryMainIntent
    object TrackParcel : DeliveryMainIntent

    object RepeatLoad : DeliveryMainIntent
}
