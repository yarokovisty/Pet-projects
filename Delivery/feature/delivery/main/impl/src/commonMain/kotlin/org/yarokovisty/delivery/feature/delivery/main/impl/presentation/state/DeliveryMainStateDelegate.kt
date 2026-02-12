package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state

import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.DeliveryPoint
import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.ParcelType

internal fun DeliveryMainState.loadingState(): DeliveryMainState =
    copy(loading = true, error = false)

internal fun DeliveryMainState.errorState(): DeliveryMainState =
    copy(loading = false, error = true)

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
