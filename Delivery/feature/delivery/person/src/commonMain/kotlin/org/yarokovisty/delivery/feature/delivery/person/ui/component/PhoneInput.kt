package org.yarokovisty.delivery.feature.delivery.person.ui.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import delivery.feature.delivery.person.generated.resources.Res
import delivery.feature.delivery.person.generated.resources.person_input_empty_error
import delivery.feature.delivery.person.generated.resources.person_input_phone_length_error
import delivery.feature.delivery.person.generated.resources.person_phone_number_input_hint
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.design.uikit.input.PhoneInputTransformation
import org.yarokovisty.delivery.design.uikit.input.TextInput
import org.yarokovisty.delivery.feature.delivery.person.presentation.intent.PersonIntent
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.PhoneFieldStatus
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.PhoneNumberState

@Composable
internal fun PhoneInput(
    state: PhoneNumberState,
    onIntent: (PersonIntent) -> Unit
) {
    val keyboardOptions = remember {
        KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done)
    }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextInput(
        text = state.value,
        hint = stringResource(Res.string.person_phone_number_input_hint),
        keyboardOptions = keyboardOptions,
        inputTransformation = remember { PhoneInputTransformation() },
        errorText = state.fieldStatus.getText(),
        onTextChange = { onIntent(PersonIntent.InputPhoneNumber(it)) },
        onImeAction = {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    )
}

@Composable
private fun PhoneFieldStatus.getText(): String? =
    if (this is PhoneFieldStatus.Invalid) {
        this.error.toText()
    } else {
        null
    }

@Composable
private fun PhoneValidationError.toText(): String =
    when (this) {
        PhoneValidationError.EMPTY -> stringResource(Res.string.person_input_empty_error)
        PhoneValidationError.INVALID_LENGTH -> stringResource(Res.string.person_input_phone_length_error)
    }
