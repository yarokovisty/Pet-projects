package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state

import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint

internal fun loadingState(): DeliveryMainState =
    DeliveryMainState.INITIAL.copy(loading = true)

internal fun errorState(): DeliveryMainState =
    DeliveryMainState.INITIAL.copy(error = true)

internal fun DeliveryMainState.contentState(
    deliveryPoints: List<DeliveryPoint>,
    alternativePointsFrom: List<String>,
    alternativePointsTo: List<String>,
    parcelTypes: List<ParcelType>
): DeliveryMainState {
    val deliveryCalculatorContent = DeliveryCalculatorContent(
        points = deliveryPoints,
        selectedPointFrom = null,
        alternativePointsFrom = alternativePointsFrom,
        selectedPointTo = null,
        alternativePointsTo = alternativePointsTo,
        parcelTypes = parcelTypes,
        selectedParcelType = null
    )

    return copy(
        loading = false,
        deliveryCalculatorContent = deliveryCalculatorContent
    )
}

internal fun DeliveryMainState.changeTrackerState(parcelId: String): DeliveryMainState =
    copy(trackerContent = trackerContent.copy(inputIdParcel = parcelId))
