package org.yarokovisty.delivery.feature.delivery.calculator.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import delivery.feature.delivery.calculator.generated.resources.Res
import delivery.feature.delivery.calculator.generated.resources.error_button_repeat
import delivery.feature.delivery.calculator.generated.resources.error_message_unavailable_service
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.screen.ErrorScreen
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.intent.CalculatorIntent

@Composable
internal fun FailureScreen(onIntent: (CalculatorIntent) -> Unit) {
    ErrorScreen(
        message = stringResource(Res.string.error_message_unavailable_service),
        buttonText = stringResource(Res.string.error_button_repeat),
        onButtonClick = { onIntent(CalculatorIntent.LoadData) }
    )
}

@Preview
@Composable
private fun FailureScreenPreview() {
    DeliveryTheme {
        FailureScreen {}
    }
}
