package org.yarokovisty.delivery.feature.login.impl.presentation.state

import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.core.common.presentation.State
import org.yarokovisty.delivery.feature.login.impl.domain.validator.OtpCodeFormatValidationError

internal data class LoginState(
    val phoneNumberState: PhoneNumberState,
    val otpCodeState: OtpCodeState?,
    val otpRetryTimerState: OtpRetryTimerState
) : State

internal data class PhoneNumberState(
    val showTitle: Boolean,
    val phoneNumber: String,
    val fieldStatus: PhoneFieldStatus,
)

internal sealed interface PhoneFieldStatus {

    data object NotValidated : PhoneFieldStatus

    data object Valid : PhoneFieldStatus

    data class Invalid(val error: PhoneValidationError) : PhoneFieldStatus
}

internal data class OtpCodeState(
    val code: String,
    val fieldStatus: OtpFieldStatus
)

internal sealed interface OtpFieldStatus {

    data object NotValidated : OtpFieldStatus

    data object Valid : OtpFieldStatus

    data class InvalidFormat(val error: OtpCodeFormatValidationError) : OtpFieldStatus

    data object InvalidCode : OtpFieldStatus
}

internal data class OtpRetryTimerState(
    val secondsLeft: Long?
)
