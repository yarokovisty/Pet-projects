package org.yarokovisty.delivery.feature.history.details.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import delivery.design.resources.generated.resources.ic_close
import delivery.design.resources.generated.resources.success
import delivery.feature.history.details.generated.resources.Res
import delivery.feature.history.details.generated.resources.cancellation_order_back_button
import delivery.feature.history.details.generated.resources.successful_cancellation_description
import delivery.feature.history.details.generated.resources.successful_cancellation_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular
import org.yarokovisty.delivery.design.uikit.text.TitleH3
import org.yarokovisty.delivery.feature.history.details.presentation.intent.OrderDetailsIntent
import delivery.design.resources.generated.resources.Res as designRes

@Composable
internal fun SuccessfulScreen(onIntent: (OrderDetailsIntent) -> Unit) {
    Column {
        TopBar(
            navigationIcon = painterResource(designRes.drawable.ic_close),
            onNavIconClick = { onIntent(OrderDetailsIntent.Back) }
        )

        SuccessfulContent(onBack = { onIntent(OrderDetailsIntent.Back) })
    }
}

@Composable
private fun SuccessfulContent(onBack: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(designRes.drawable.success),
            contentDescription = null
        )

        VerticalGap(16.dp)

        TitleH3(text = stringResource(Res.string.successful_cancellation_title))

        VerticalGap(16.dp)

        Paragraph16Regular(
            text = stringResource(Res.string.successful_cancellation_description),
            color = DeliveryTheme.colorScheme.textSecondary,
            textAlign = TextAlign.Center,
        )

        VerticalGap(40.dp)

        PrimaryButton(
            text = stringResource(Res.string.cancellation_order_back_button),
            onClick = onBack
        )
    }
}
