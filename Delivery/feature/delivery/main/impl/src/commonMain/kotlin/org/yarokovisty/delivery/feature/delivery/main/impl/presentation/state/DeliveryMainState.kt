package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state

import org.yarokovisty.delivery.common.presentation.State
import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.DeliveryPoint
import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.ParcelType

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
)

internal data class TrackerContent(
    val inputIdParcel: String,
) {
    val trackEnabled: Boolean
        get() = inputIdParcel.isNotEmpty()
}
