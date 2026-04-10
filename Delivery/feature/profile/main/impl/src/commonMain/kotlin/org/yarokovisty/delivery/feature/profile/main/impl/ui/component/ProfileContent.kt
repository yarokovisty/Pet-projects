package org.yarokovisty.delivery.feature.profile.main.impl.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import delivery.feature.profile.main.impl.generated.resources.Res
import delivery.feature.profile.main.impl.generated.resources.ic_arrow_drop_down
import delivery.feature.profile.main.impl.generated.resources.profile_city_title
import delivery.feature.profile.main.impl.generated.resources.profile_email_not_match_pattern_error
import delivery.feature.profile.main.impl.generated.resources.profile_email_title
import delivery.feature.profile.main.impl.generated.resources.profile_firstname_title
import delivery.feature.profile.main.impl.generated.resources.profile_lastname_title
import delivery.feature.profile.main.impl.generated.resources.profile_middlename_title
import delivery.feature.profile.main.impl.generated.resources.profile_phone_title
import delivery.feature.profile.main.impl.generated.resources.profile_update_data_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.PrimaryButton
import org.yarokovisty.delivery.design.uikit.SelectCategory
import org.yarokovisty.delivery.design.uikit.TextInput
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.intent.ProfileIntent
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.ContentState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.EmailFieldState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.EmailFieldStatus

@Composable
internal fun ProfileContent(
    state: ContentState,
    onIntent: (ProfileIntent) -> Unit
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        LasnameInput(state.lastname, onTextChange = { onIntent(ProfileIntent.InputLastname(it)) })

        FirstnameInput(state.firstname, onTextChange = { onIntent(ProfileIntent.InputFirstname(it)) })

        MiddlenameInput(state.middlename, onTextChange = { onIntent(ProfileIntent.InputMiddlename(it)) })

        CityField(state.city, onClick = { onIntent(ProfileIntent.ClickCity) })

        PhoneInput(state.phone)

        EmailInput(state.email, onIntent)

        UpdateDataButton(state.downloadingDataUpdate) {
            focusManager.clearFocus()
            keyboardController?.hide()
            onIntent(ProfileIntent.ClickUpdateData)
        }
    }
}

@Composable
private fun FirstnameInput(
    firstname: String,
    onTextChange: (String) -> Unit
) {
    val keyboardOptions = remember { KeyboardOptions(capitalization = KeyboardCapitalization.Sentences) }

    TextInput(
        text = firstname,
        onTextChange = onTextChange,
        title = stringResource(Res.string.profile_firstname_title),
        hint = stringResource(Res.string.profile_firstname_title),
        keyboardOptions = keyboardOptions
    )
}

@Composable
private fun LasnameInput(
    lastname: String,
    onTextChange: (String) -> Unit
) {
    val keyboardOptions = remember { KeyboardOptions(capitalization = KeyboardCapitalization.Sentences) }

    TextInput(
        text = lastname,
        onTextChange = onTextChange,
        title = stringResource(Res.string.profile_lastname_title),
        hint = stringResource(Res.string.profile_lastname_title),
        keyboardOptions = keyboardOptions
    )
}

@Composable
private fun MiddlenameInput(
    middlename: String,
    onTextChange: (String) -> Unit
) {
    val keyboardOptions = remember { KeyboardOptions(capitalization = KeyboardCapitalization.Sentences) }

    TextInput(
        text = middlename,
        onTextChange = onTextChange,
        title = stringResource(Res.string.profile_middlename_title),
        hint = stringResource(Res.string.profile_middlename_title),
        keyboardOptions = keyboardOptions
    )
}

@Composable
private fun CityField(
    city: String,
    onClick: () -> Unit
) {
    SelectCategory(
        text = city,
        defaultText = stringResource(Res.string.profile_city_title),
        label = stringResource(Res.string.profile_city_title),
        endIcon = painterResource(Res.drawable.ic_arrow_drop_down),
        onClick = onClick
    )
}

@Composable
private fun PhoneInput(phone: String) {
    TextInput(
        text = phone,
        title = stringResource(Res.string.profile_phone_title),
        onTextChange = {},
        enabled = false
    )
}

@Composable
private fun EmailInput(
    state: EmailFieldState,
    onIntent: (ProfileIntent) -> Unit
) {
    TextInput(
        text = state.text,
        title = stringResource(Res.string.profile_email_title),
        hint = stringResource(Res.string.profile_email_title),
        errorText = state.status.toText(),
        onTextChange = { onIntent(ProfileIntent.InputEmail(it)) },
    )
}

@Composable
private fun UpdateDataButton(
    loading: Boolean,
    onClick: () -> Unit
) {
    PrimaryButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        onClick = {
            if (!loading) {
                onClick()
            }
        }
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = DeliveryTheme.colorScheme.textInvert
            )
        } else {
            Text(
                text = stringResource(Res.string.profile_update_data_button),
                color = DeliveryTheme.colorScheme.textInvert,
                style = DeliveryTheme.typography.buttonSemibold,
            )
        }
    }
}

@Composable
private fun EmailFieldStatus.toText(): String? =
    if (this is EmailFieldStatus.Invalid) {
        reason.toText()
    } else {
        null
    }

@Composable
private fun EmailValidationError.toText(): String? =
    when (this) {
        EmailValidationError.NOT_MATCH_THE_PATTERN -> stringResource(Res.string.profile_email_not_match_pattern_error)
        else -> null
    }
