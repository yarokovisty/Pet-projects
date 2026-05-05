package org.yarokovisty.delivery.feature.delivery.point.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface SenderAddressIntent : Intent {

    data class InputStreet(val street: String) : SenderAddressIntent

    data class InputHouse(val house: String) : SenderAddressIntent

    data class InputApartment(val apartment: String) : SenderAddressIntent

    data class InputComment(val comment: String) : SenderAddressIntent

    data object ClickContinue : SenderAddressIntent

    data object Back : SenderAddressIntent
}
