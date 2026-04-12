package org.yarokovisty.delivery.feature.profile.main.impl.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface ProfileIntent : Intent {

    data object LoadData : ProfileIntent

    data class InputLastname(val lastname: String) : ProfileIntent
    data class InputFirstname(val firstname: String) : ProfileIntent
    data class InputMiddlename(val middlename: String) : ProfileIntent
    data object ClickCity : ProfileIntent
    data class InputEmail(val email: String) : ProfileIntent

    data object ClickUpdateData : ProfileIntent
}
