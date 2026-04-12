package org.yarokovisty.delivery.feature.profile.main.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface ProfileIntent : Intent {

    data object LoadData : org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent

    data class InputLastname(val lastname: String) :
        org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent
    data class InputFirstname(val firstname: String) :
        org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent
    data class InputMiddlename(val middlename: String) :
        org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent
    data object ClickCity : org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent
    data class InputEmail(val email: String) :
        org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent

    data object ClickUpdateData : org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent
}
