package org.yarokovisty.delivery.feature.delivery.person.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface PersonIntent : Intent {

    data object Back : PersonIntent

    data class InputLastname(val lastname: String) : PersonIntent

    data class InputFirstname(val firstname: String) : PersonIntent

    data class InputMiddlename(val middlename: String) : PersonIntent

    data class InputPhoneNumber(val phoneNumber: String) : PersonIntent

    data object ClickContinue : PersonIntent
}
