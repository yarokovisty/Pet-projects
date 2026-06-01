package org.yarokovisty.delivery.feature.history.details.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.design.resources.generated.resources.ic_question
import delivery.feature.history.details.generated.resources.Res
import delivery.feature.history.details.generated.resources.cancellation_order_cancel_button
import delivery.feature.history.details.generated.resources.cancellation_order_not_cancel_button
import delivery.feature.history.details.generated.resources.cancellation_order_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.button.OutlinedButton
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.screen.BottomSheetScreen
import org.yarokovisty.delivery.design.uikit.text.TitleH3
import org.yarokovisty.delivery.feature.history.details.presentation.intent.OrderDetailsIntent
import delivery.design.resources.generated.resources.Res as designRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CancellationScreen(
    visible: Boolean,
    onIntent: (OrderDetailsIntent) -> Unit
) {
    BottomSheetScreen(
        visible = visible,
        shape = RoundedCornerShape(16.dp),
        onDismissRequest = { onIntent(OrderDetailsIntent.CloseCancellationScreen) }
    ) {
        CancellationContent(onIntent)
    }
}

@Composable
private fun CancellationContent(
    onIntent: (OrderDetailsIntent) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(designRes.drawable.ic_question),
            contentDescription = null
        )

        VerticalGap(24.dp)

        TitleH3(text = stringResource(Res.string.cancellation_order_title))

        VerticalGap(24.dp)

        OutlinedButton(
            text = stringResource(Res.string.cancellation_order_cancel_button),
            onClick = { onIntent(OrderDetailsIntent.ConfirmCancellation) }
        )

        VerticalGap(16.dp)

        PrimaryButton(
            text = stringResource(Res.string.cancellation_order_not_cancel_button),
            onClick = { onIntent(OrderDetailsIntent.CloseCancellationScreen) }
        )
    }
}
