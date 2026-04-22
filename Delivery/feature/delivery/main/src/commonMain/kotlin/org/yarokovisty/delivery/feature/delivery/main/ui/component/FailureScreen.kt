package org.yarokovisty.delivery.feature.delivery.main.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import delivery.feature.delivery.main.generated.resources.Res
import delivery.feature.delivery.main.generated.resources.error_button_repeat
import delivery.feature.delivery.main.generated.resources.error_message_unavailable_service
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.screen.ErrorScreen
import org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent

@Composable
internal fun FailureScreen(onIntent: (DeliveryMainIntent) -> Unit) {
    ErrorScreen(
        message = stringResource(Res.string.error_message_unavailable_service),
        buttonText = stringResource(Res.string.error_button_repeat),
        onButtonClick = { onIntent(DeliveryMainIntent.LoadData) }
    )
}

@Preview
@Composable
private fun FailureScreenPreview() {
    DeliveryTheme {
        FailureScreen {}
    }
}
