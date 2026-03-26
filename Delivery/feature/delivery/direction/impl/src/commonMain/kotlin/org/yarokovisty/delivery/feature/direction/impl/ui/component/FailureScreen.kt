package org.yarokovisty.delivery.feature.direction.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import delivery.feature.delivery.direction.impl.generated.resources.Res
import delivery.feature.delivery.direction.impl.generated.resources.error_button_repeat
import delivery.feature.delivery.direction.impl.generated.resources.error_message_unavailable_service
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.ErrorScreen
import org.yarokovisty.delivery.feature.direction.impl.presentation.intent.DirectionIntent

@Composable
internal fun FailureScreen(onIntent: (DirectionIntent) -> Unit) {
    ErrorScreen(
        message = stringResource(Res.string.error_message_unavailable_service),
        buttonText = stringResource(Res.string.error_button_repeat),
        onButtonClick = { onIntent(DirectionIntent.LoadData) }
    )
}

@Preview
@Composable
private fun FailureScreenPreview() {
    DeliveryTheme {
        FailureScreen {}
    }
}
