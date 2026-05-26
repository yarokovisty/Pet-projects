package org.yarokovisty.delivery.feature.login.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.feature.login.generated.resources.Res
import delivery.feature.login.generated.resources.login_button
import delivery.feature.login.generated.resources.login_description
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular
import org.yarokovisty.delivery.feature.login.presentation.intent.LoginIntent
import org.yarokovisty.delivery.feature.login.presentation.state.LoginState
import org.yarokovisty.delivery.feature.login.presentation.state.initial

@Composable
internal fun LoginContent(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Paragraph16Regular(
            text = stringResource(Res.string.login_description),
            modifier = Modifier.fillMaxWidth(),
        )

        VerticalGap(24.dp)

        PhoneInput(state.phoneNumberState, onIntent)

        VerticalGap(24.dp)

        if (state.otpCodeState != null) {
            OtpCodeInput(state.otpCodeState, onIntent)

            VerticalGap(24.dp)
        }

        PrimaryButton(
            text = stringResource(Res.string.login_button),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            onClick = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onIntent(LoginIntent.ClickLogin)
            }
        )

        if (state.otpCodeState != null) {
            VerticalGap(24.dp)

            OtpRetryContent(state.otpRetryTimerState, onIntent)
        }
    }
}

@Preview
@Composable
private fun LoginContentPreview() {
    val state = initial()
    DeliveryTheme {
        LoginContent(state, onIntent = {})
    }
}
