package org.yarokovisty.delivery.feature.delivery.person.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.person.generated.resources.Res
import delivery.feature.delivery.person.generated.resources.person_continue_button
import delivery.feature.delivery.person.generated.resources.person_firstname_input_hint
import delivery.feature.delivery.person.generated.resources.person_input_empty_error
import delivery.feature.delivery.person.generated.resources.person_lastname_input_hint
import delivery.feature.delivery.person.generated.resources.person_middlename_input_hint
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.validation.error.NameValidationError
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.input.TextInput
import org.yarokovisty.delivery.feature.delivery.person.presentation.intent.PersonIntent
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.ContentState
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.NameFieldStatus
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.NameState

@Composable
internal fun PersonContent(
    state: ContentState,
    onIntent: (PersonIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        NameInput(
            state = state.lastname,
            hintText = stringResource(Res.string.person_lastname_input_hint),
            onTextChange = { onIntent(PersonIntent.InputLastname(it)) }
        )

        NameInput(
            state = state.firstname,
            hintText = stringResource(Res.string.person_firstname_input_hint),
            onTextChange = { onIntent(PersonIntent.InputFirstname(it)) }
        )

        MiddlenameInput(
            text = state.middlename,
            onTextChange = { onIntent(PersonIntent.InputMiddlename(it)) }
        )

        PhoneInput(
            state = state.phoneNumber,
            onIntent = onIntent
        )

        PrimaryButton(
            text = stringResource(Res.string.person_continue_button),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            onClick = { onIntent(PersonIntent.ClickContinue) }
        )
    }
}

@Composable
private fun NameInput(
    state: NameState,
    hintText: String,
    onTextChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardOptions = remember {
        KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    }

    TextInput(
        text = state.value,
        onTextChange = onTextChange,
        hint = hintText,
        keyboardOptions = keyboardOptions,
        errorText = state.fieldStatus.getText(),
        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
    )
}

@Composable
private fun MiddlenameInput(
    text: String,
    onTextChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardOptions = remember {
        KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    }

    TextInput(
        text = text,
        onTextChange = onTextChange,
        hint = stringResource(Res.string.person_middlename_input_hint),
        keyboardOptions = keyboardOptions,
        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
    )
}

@Composable
private fun NameFieldStatus.getText(): String? =
    if (this is NameFieldStatus.Invalid) {
        this.error.toText()
    } else {
        null
    }

@Composable
private fun NameValidationError.toText(): String =
    when (this) {
        NameValidationError.EMPTY -> stringResource(Res.string.person_input_empty_error)
    }
