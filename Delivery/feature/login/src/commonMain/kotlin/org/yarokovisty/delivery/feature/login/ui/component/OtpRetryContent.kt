package org.yarokovisty.delivery.feature.login.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import delivery.feature.login.generated.resources.Res
import delivery.feature.login.generated.resources.login_otp_code_retry_text
import delivery.feature.login.generated.resources.login_otp_code_timer_text
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.text.Paragraph14Regular
import org.yarokovisty.delivery.design.uikit.text.Paragraph16MediumUnderline
import org.yarokovisty.delivery.feature.login.presentation.intent.LoginIntent
import org.yarokovisty.delivery.feature.login.presentation.state.OtpRetryTimerState

@Composable
internal fun OtpRetryContent(
    state: OtpRetryTimerState,
    onIntent: (LoginIntent) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (state.secondsLeft == null) {
            OtpRetryText(onClick = { onIntent(LoginIntent.RetrySendOtp) })
        } else {
            OtpRetryTimerText(state.secondsLeft)
        }
    }
}

@Composable
private fun OtpRetryText(onClick: () -> Unit) {
    Paragraph16MediumUnderline(
        text = stringResource(Res.string.login_otp_code_retry_text),
        color = DeliveryTheme.colorScheme.textPrimary,
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun OtpRetryTimerText(time: Long) {
    Paragraph14Regular(
        text = stringResource(Res.string.login_otp_code_timer_text, time),
        color = DeliveryTheme.colorScheme.textTertiary
    )
}
