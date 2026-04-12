package org.yarokovisty.delivery.feature.login.impl.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface LoginIntent : Intent {

    data class InputPhoneNumber(val phone: String) : LoginIntent

    data class InputOtpCode(val code: String) : LoginIntent

    data object RetrySendOtp : LoginIntent

    data object ClickLogin : LoginIntent

    data object Back : LoginIntent
}
