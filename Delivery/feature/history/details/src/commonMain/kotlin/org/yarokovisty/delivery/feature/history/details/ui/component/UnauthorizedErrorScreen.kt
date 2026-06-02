package org.yarokovisty.delivery.feature.history.details.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import delivery.feature.history.details.generated.resources.Res
import delivery.feature.history.details.generated.resources.error_login_button
import delivery.feature.history.details.generated.resources.error_message_unauthorized
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.screen.ErrorScreen

@Composable
internal fun UnauthorizedErrorScreen(
    onBack: () -> Unit,
    onLoginClick: () -> Unit
) {
    ErrorScreen(
        message = stringResource(Res.string.error_message_unauthorized),
        buttonText = stringResource(Res.string.error_login_button),
        onButtonClick = onLoginClick,
        onCloseClick = onBack
    )
}

@Preview
@Composable
private fun UnauthorizedErrorScreenPreview() {
    DeliveryTheme {
        UnauthorizedErrorScreen(onBack = {}, onLoginClick = {})
    }
}
