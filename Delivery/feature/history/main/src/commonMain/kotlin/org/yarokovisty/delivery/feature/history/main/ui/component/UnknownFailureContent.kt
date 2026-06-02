package org.yarokovisty.delivery.feature.history.main.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import delivery.feature.history.main.generated.resources.Res
import delivery.feature.history.main.generated.resources.error_message_unavailable_service
import delivery.feature.history.main.generated.resources.error_repeat_button
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.screen.ErrorScreen

@Composable
internal fun UnknownFailureContent(onRefreshClick: () -> Unit) {
    ErrorScreen(
        message = stringResource(Res.string.error_message_unavailable_service),
        buttonText = stringResource(Res.string.error_repeat_button),
        onButtonClick = onRefreshClick
    )
}

@Preview
@Composable
private fun FailureContentPreview() {
    DeliveryTheme {
        UnknownFailureContent {}
    }
}
