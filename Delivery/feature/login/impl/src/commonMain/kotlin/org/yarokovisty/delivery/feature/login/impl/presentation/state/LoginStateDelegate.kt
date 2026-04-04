package org.yarokovisty.delivery.feature.login.impl.presentation.state

import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.feature.login.impl.domain.validator.OtpCodeFormatValidationError

internal fun initial() =
    LoginState(
        phoneNumberState = PhoneNumberState(
            showTitle = true,
            phoneNumber = "",
            fieldStatus = PhoneFieldStatus.NotValidated
        ),
        otpCodeState = null,
        otpRetryTimerState = OtpRetryTimerState(
            secondsLeft = null
        )
    )

internal fun LoginState.changePhoneNumber(phoneNumber: String) =
    copy(
        phoneNumberState = phoneNumberState.copy(
            showTitle = true,
            phoneNumber = phoneNumber,
            fieldStatus = PhoneFieldStatus.NotValidated
        ),
        otpCodeState = null,
        otpRetryTimerState = otpRetryTimerState.copy(
            secondsLeft = null
        )
    )

internal fun LoginState.invalidPhoneNumber(reason: PhoneValidationError) =
    copy(phoneNumberState = phoneNumberState.copy(fieldStatus = PhoneFieldStatus.Invalid(reason)))

internal fun LoginState.validPhoneNumber() =
    copy(phoneNumberState = phoneNumberState.copy(fieldStatus = PhoneFieldStatus.Valid))

internal fun LoginState.showOtpInput() =
    copy(
        phoneNumberState = phoneNumberState.copy(
            showTitle = false,
        ),
        otpCodeState = OtpCodeState(
            code = "",
            fieldStatus = OtpFieldStatus.NotValidated
        )
    )

internal fun LoginState.changeOtpCode(code: String) =
    copy(
        otpCodeState = otpCodeState?.copy(
            code = code,
            fieldStatus = OtpFieldStatus.NotValidated
        )
    )

internal fun LoginState.invalidFormatOtpCode(reason: OtpCodeFormatValidationError) =
    copy(otpCodeState = otpCodeState?.copy(fieldStatus = OtpFieldStatus.InvalidFormat(reason)))

internal fun LoginState.invalidOtpCode() =
    copy(otpCodeState = otpCodeState?.copy(fieldStatus = OtpFieldStatus.InvalidCode))

internal fun LoginState.validOtpCode() =
    copy(otpCodeState = otpCodeState?.copy(fieldStatus = OtpFieldStatus.Valid))

internal fun LoginState.tickOtpRetryTimer(time: Long) =
    copy(otpRetryTimerState = otpRetryTimerState.copy(secondsLeft = time))

internal fun LoginState.clearOtpRetryTimer() =
    copy(otpRetryTimerState = otpRetryTimerState.copy(secondsLeft = null))
