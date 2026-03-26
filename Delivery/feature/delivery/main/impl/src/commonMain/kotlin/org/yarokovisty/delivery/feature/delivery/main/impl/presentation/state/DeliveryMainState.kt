package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state

import org.yarokovisty.delivery.common.presentation.State
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint

internal data class DeliveryMainState(
    val loading: Boolean,
    val error: Boolean,
    val deliveryCalculatorContent: DeliveryCalculatorContent?,
    val trackerContent: TrackerContent,
) : State {

    companion object {

        val INITIAL = DeliveryMainState(
            loading = false,
            error = false,
            deliveryCalculatorContent = null,
            trackerContent = TrackerContent(""),
        )
    }
}

internal data class DeliveryCalculatorContent(
    val points: List<DeliveryPoint>,
    val selectedPointFrom: DeliveryPoint?,
    val alternativePointsFrom: List<String>,
    val selectedPointTo: DeliveryPoint?,
    val alternativePointsTo: List<String>,
    val parcelTypes: List<ParcelType>,
    val selectedParcelType: ParcelType?,
) {
    val calculateButtonEnabled: Boolean
        get() = selectedPointFrom != null && selectedPointTo != null && selectedParcelType != null
}

internal data class TrackerContent(
    val inputIdParcel: String,
) {
    val trackEnabled: Boolean
        get() = inputIdParcel.isNotEmpty()
}
