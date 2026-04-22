package org.yarokovisty.delivery.feature.delivery.main.presentation.state

import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo

internal fun initial() =
    DeliveryMainState(
        loading = false,
        error = false,
        deliveryCalculatorContent = null,
        trackerContent = TrackerContent(
            ""
        ),
        showSelectParcelType = false
    )

internal fun loadingState() =
    initial().copy(loading = true)

internal fun errorState() =
    initial().copy(error = true)

internal fun DeliveryMainState.contentState(
    deliveryPoints: List<DeliveryPoint>,
    alternativePointsFrom: List<String>,
    alternativePointsTo: List<String>,
    parcelInfoList: List<ParcelInfo>
): DeliveryMainState {
    val deliveryCalculatorContent = DeliveryCalculatorContent(
        points = deliveryPoints,
        selectedPointFrom = null,
        alternativePointsFrom = alternativePointsFrom,
        selectedPointTo = null,
        alternativePointsTo = alternativePointsTo,
        parcelInfoList = parcelInfoList,
        selectedParcelInfo = null
    )

    return copy(
        loading = false,
        deliveryCalculatorContent = deliveryCalculatorContent,
    )
}

internal fun DeliveryMainState.selectDeliveryPointFrom(point: DeliveryPoint) =
    copy(deliveryCalculatorContent = deliveryCalculatorContent?.copy(selectedPointFrom = point))

internal fun DeliveryMainState.selectDeliveryPointTo(point: DeliveryPoint) =
    copy(deliveryCalculatorContent = deliveryCalculatorContent?.copy(selectedPointTo = point))

internal fun DeliveryMainState.showSelectParcelTypeScreen() =
    copy(showSelectParcelType = true)

internal fun DeliveryMainState.closeSelectParcelTypeScreen() =
    copy(showSelectParcelType = false)

internal fun DeliveryMainState.selectParcelType(parcelInfo: ParcelInfo) =
    copy(
        deliveryCalculatorContent = deliveryCalculatorContent?.copy(selectedParcelInfo = parcelInfo),
        showSelectParcelType = false
    )

internal fun DeliveryMainState.changeTracker(parcelId: String) =
    copy(trackerContent = trackerContent.copy(inputIdParcel = parcelId))
