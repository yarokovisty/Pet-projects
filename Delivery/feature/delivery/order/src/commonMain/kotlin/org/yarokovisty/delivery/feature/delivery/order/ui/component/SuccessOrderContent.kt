package org.yarokovisty.delivery.feature.delivery.order.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.design.resources.generated.resources.success
import delivery.feature.delivery.order.generated.resources.Res
import delivery.feature.delivery.order.generated.resources.success_order_check_order_button
import delivery.feature.delivery.order.generated.resources.success_order_description
import delivery.feature.delivery.order.generated.resources.success_order_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular
import org.yarokovisty.delivery.design.uikit.text.TitleH3
import org.yarokovisty.delivery.feature.delivery.order.presentation.intent.SuccessOrderIntent
import delivery.design.resources.generated.resources.Res as designRes

@Composable
internal fun SuccessOrderContent(
    onIntent: (SuccessOrderIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(designRes.drawable.success),
            contentDescription = null,
        )

        VerticalGap(16.dp)

        TitleH3(text = stringResource(Res.string.success_order_title))

        VerticalGap(16.dp)

        Paragraph16Regular(
            text = stringResource(Res.string.success_order_description),
            color = DeliveryTheme.colorScheme.textSecondary,
            textAlign = TextAlign.Center,
        )

        VerticalGap(40.dp)

        PrimaryButton(
            text = stringResource(Res.string.success_order_check_order_button),
            onClick = { onIntent(SuccessOrderIntent.CheckStatus) }
        )
    }
}

@Preview
@Composable
private fun SuccessOrderContentPreview() {
    DeliveryTheme {
        Box(modifier = Modifier.background(DeliveryTheme.colorScheme.bgPrimary)) {
            SuccessOrderContent(
                onIntent = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
