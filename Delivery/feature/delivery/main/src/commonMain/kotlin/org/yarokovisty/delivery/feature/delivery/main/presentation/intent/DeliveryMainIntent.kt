package org.yarokovisty.delivery.feature.delivery.main.presentation.intent

import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface DeliveryMainIntent : Intent {
    data object LoadData : org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent

    data object SelectDeliveryPointFrom :
        org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
    data class SelectAlternativeDeliveryPointFrom(val pointName: String) :
        org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
    data object SelectDeliveryPointTo :
        org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
    data class SelectAlternativeDeliveryPointTo(val pointName: String) :
        org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent

    data object OpenParcelTypeScreen :
        org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
    data object CloseParcelTypeScreen :
        org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
    data class SelectParcelType(val parcelInfo: ParcelInfo) :
        org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent

    data object CalculateDelivery :
        org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent

    data class ChangeInputParcelId(val id: String) :
        org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
    data object TrackParcel : org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
}
