package org.yarokovisty.delivery.feature.login.ui.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import delivery.feature.login.generated.resources.Res
import delivery.feature.login.generated.resources.login_input_otp_code_empty_error
import delivery.feature.login.generated.resources.login_input_otp_code_hint
import delivery.feature.login.generated.resources.login_input_otp_code_invalid
import delivery.feature.login.generated.resources.login_input_otp_code_length_error
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.uikit.TextInput
import org.yarokovisty.delivery.feature.login.domain.validator.OtpCodeFormatValidationError
import org.yarokovisty.delivery.feature.login.presentation.intent.LoginIntent
import org.yarokovisty.delivery.feature.login.presentation.state.OtpCodeState
import org.yarokovisty.delivery.feature.login.presentation.state.OtpFieldStatus

@Composable
internal fun OtpCodeInput(
    state: OtpCodeState,
    onIntent: (LoginIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardOptions = remember {
        KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
    }

    TextInput(
        text = state.code,
        hint = stringResource(Res.string.login_input_otp_code_hint),
        errorText = state.fieldStatus.toText(),
        keyboardOptions = keyboardOptions,
        onTextChange = { onIntent(LoginIntent.InputOtpCode(it)) },
        onImeAction = {
            focusManager.clearFocus()
            keyboardController?.hide()
            onIntent(LoginIntent.ClickLogin)
        }
    )
}

@Composable
private fun OtpFieldStatus.toText(): String? =
    when (this) {
        is OtpFieldStatus.InvalidCode -> stringResource(Res.string.login_input_otp_code_invalid)
        is OtpFieldStatus.InvalidFormat -> error.toText()
        else -> null
    }

@Composable
private fun OtpCodeFormatValidationError.toText(): String =
    when (this) {
        OtpCodeFormatValidationError.EMPTY -> stringResource(Res.string.login_input_otp_code_empty_error)
        OtpCodeFormatValidationError.INVALID_LENGTH -> stringResource(Res.string.login_input_otp_code_length_error)
    }
