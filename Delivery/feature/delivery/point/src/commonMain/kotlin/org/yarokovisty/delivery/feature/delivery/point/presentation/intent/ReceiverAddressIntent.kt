package org.yarokovisty.delivery.feature.delivery.point.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface ReceiverAddressIntent : Intent {

    data class InputStreet(val street: String) : ReceiverAddressIntent

    data class InputHouse(val house: String) : ReceiverAddressIntent

    data class InputApartment(val apartment: String) : ReceiverAddressIntent

    data class InputComment(val comment: String) : ReceiverAddressIntent

    data object ClickContinue : ReceiverAddressIntent

    data object ClickNonContactedCheckbox : ReceiverAddressIntent

    data object ClickNonContactedTip : ReceiverAddressIntent

    data object Back : ReceiverAddressIntent
}
