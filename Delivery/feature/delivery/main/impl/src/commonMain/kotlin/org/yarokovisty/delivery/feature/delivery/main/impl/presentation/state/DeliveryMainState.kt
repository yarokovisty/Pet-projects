package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state

import org.yarokovisty.delivery.core.common.presentation.State
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelInfo
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint

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
    val parcelInfos: List<ParcelInfo>,
    val selectedParcelInfo: ParcelInfo?,
) {
    val calculateButtonEnabled: Boolean
        get() = selectedPointFrom != null && selectedPointTo != null && selectedParcelInfo != null
}

internal data class TrackerContent(
    val inputIdParcel: String,
) {
    val trackEnabled: Boolean
        get() = inputIdParcel.isNotEmpty()
}
