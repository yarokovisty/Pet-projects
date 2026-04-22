package org.yarokovisty.delivery.feature.login.ui.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import delivery.feature.login.generated.resources.Res
import delivery.feature.login.generated.resources.login_input_phone_empty_error
import delivery.feature.login.generated.resources.login_input_phone_hint
import delivery.feature.login.generated.resources.login_input_phone_length_error
import delivery.feature.login.generated.resources.login_input_phone_title
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.design.uikit.input.PhoneInputTransformation
import org.yarokovisty.delivery.design.uikit.input.TextInput
import org.yarokovisty.delivery.feature.login.presentation.intent.LoginIntent
import org.yarokovisty.delivery.feature.login.presentation.state.PhoneFieldStatus
import org.yarokovisty.delivery.feature.login.presentation.state.PhoneNumberState

@Composable
internal fun PhoneInput(
    state: PhoneNumberState,
    onIntent: (LoginIntent) -> Unit
) {
    val keyboardOptions = remember {
        KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done)
    }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextInput(
        text = state.phoneNumber,
        title = getTitleText(state.showTitle),
        hint = stringResource(Res.string.login_input_phone_hint),
        keyboardOptions = keyboardOptions,
        inputTransformation = remember { PhoneInputTransformation() },
        errorText = state.fieldStatus.toText(),
        onTextChange = { onIntent(LoginIntent.InputPhoneNumber(it)) },
        onImeAction = {
            focusManager.clearFocus()
            keyboardController?.hide()
            onIntent(LoginIntent.ClickLogin)
        }
    )
}

@Composable
private fun getTitleText(showTitle: Boolean): String? =
    if (showTitle) {
        stringResource(Res.string.login_input_phone_title)
    } else {
        null
    }

@Composable
private fun PhoneFieldStatus.toText(): String? =
    if (this is PhoneFieldStatus.Invalid) {
        error.toText()
    } else {
        null
    }

@Composable
private fun PhoneValidationError.toText(): String =
    when (this) {
        PhoneValidationError.EMPTY -> stringResource(Res.string.login_input_phone_empty_error)
        PhoneValidationError.INVALID_LENGTH -> stringResource(Res.string.login_input_phone_length_error)
    }
