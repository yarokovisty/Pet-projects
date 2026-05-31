package org.yarokovisty.delivery.feature.delivery.main.presentation.state

import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.core.common.presentation.State

internal data class DeliveryMainState(
    val loading: Boolean,
    val error: Boolean,
    val deliveryCalculatorContent: DeliveryCalculatorContent?,
    val trackerContent: TrackerContent,
    val showSelectParcelType: Boolean
) : State

internal data class DeliveryCalculatorContent(
    val points: List<DeliveryPoint>,
    val selectedPointFrom: DeliveryPoint?,
    val alternativePointsFrom: List<String>,
    val selectedPointTo: DeliveryPoint?,
    val alternativePointsTo: List<String>,
    val parcelInfoList: List<ParcelInfo>,
    val selectedParcelInfo: ParcelInfo?,
) {
    val calculateButtonEnabled: Boolean
        get() = selectedPointFrom != null && selectedPointTo != null && selectedParcelInfo != null
}

internal data class TrackerContent(
    val orderId: String,
) {
    val trackEnabled: Boolean
        get() = orderId.isNotEmpty()
}
