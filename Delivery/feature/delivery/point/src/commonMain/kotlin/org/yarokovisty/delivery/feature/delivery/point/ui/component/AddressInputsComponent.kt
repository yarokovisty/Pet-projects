package org.yarokovisty.delivery.feature.delivery.point.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import delivery.feature.delivery.point.generated.resources.Res
import delivery.feature.delivery.point.generated.resources.address_apartment_input_hint
import delivery.feature.delivery.point.generated.resources.address_comment_input_hint
import delivery.feature.delivery.point.generated.resources.address_house_input_hint
import delivery.feature.delivery.point.generated.resources.address_input_empty_error
import delivery.feature.delivery.point.generated.resources.address_street_input_hint
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.validation.error.AddressValidationError
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.input.TextInput
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.FieldStatus
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.InputState

@Composable
internal fun AddressInputsComponent(
    street: InputState,
    onStreetChange: (String) -> Unit,
    house: InputState,
    onHouseChange: (String) -> Unit,
    apartment: InputState,
    onApartmentChange: (String) -> Unit,
    comment: String,
    onCommentChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AddressInput(
            state = street,
            hint = stringResource(Res.string.address_street_input_hint),
            onTextChange = onStreetChange
        )

        InputGap(street.fieldStatus is FieldStatus.Invalid)

        AddressInput(
            state = house,
            hint = stringResource(Res.string.address_house_input_hint),
            onTextChange = onHouseChange
        )

        InputGap(house.fieldStatus is FieldStatus.Invalid)

        AddressInput(
            state = apartment,
            hint = stringResource(Res.string.address_apartment_input_hint),
            onTextChange = onApartmentChange
        )

        InputGap(apartment.fieldStatus is FieldStatus.Invalid)

        CommentInput(
            text = comment,
            onTextChange = onCommentChange
        )
    }
}

@Composable
private fun AddressInput(
    state: InputState,
    hint: String,
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
        hint = hint,
        keyboardOptions = keyboardOptions,
        errorText = state.fieldStatus.getText(),
        onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
    )
}

@Composable
private fun CommentInput(
    text: String,
    onTextChange: (String) -> Unit
) {
    TextInput(
        text = text,
        onTextChange = onTextChange,
        hint = stringResource(Res.string.address_comment_input_hint),
        singleLine = false
    )
}

@Composable
private fun InputGap(error: Boolean) {
    VerticalGap(
        height = if (error) {
            7.dp
        } else {
            24.dp
        }
    )
}

@Composable
private fun FieldStatus.getText(): String? =
    if (this is FieldStatus.Invalid) {
        this.error.toText()
    } else {
        null
    }

@Composable
private fun AddressValidationError.toText(): String =
    when (this) {
        AddressValidationError.EMPTY -> stringResource(Res.string.address_input_empty_error)
    }
