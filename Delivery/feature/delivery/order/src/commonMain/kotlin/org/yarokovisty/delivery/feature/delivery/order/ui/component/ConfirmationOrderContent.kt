package org.yarokovisty.delivery.feature.delivery.order.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.order.generated.resources.Res
import delivery.feature.delivery.order.generated.resources.order_checkout_button
import delivery.feature.delivery.order.generated.resources.order_delivery_amount_work_days
import delivery.feature.delivery.order.generated.resources.order_delivery_cost
import delivery.feature.delivery.order.generated.resources.order_delviery_one_work_day
import delivery.feature.delivery.order.generated.resources.order_delviery_some_work_days
import delivery.feature.delivery.order.generated.resources.order_selected_tariff
import delivery.feature.delivery.order.generated.resources.order_tariff_default
import delivery.feature.delivery.order.generated.resources.order_tariff_express
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular
import org.yarokovisty.delivery.design.uikit.text.TitleH3
import org.yarokovisty.delivery.feature.delivery.order.presentation.intent.ConfirmationOrderIntent
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.ContentState
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.DetailType

@Composable
internal fun ConfirmationOrderContent(
    state: ContentState,
    onIntent: (ConfirmationOrderIntent) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        state.details.forEach { detail ->
            DetailCard(
                item = detail,
                onEditClick = { onIntent(detail.type.getIntent()) }
            )

            VerticalGap(24.dp)
        }

        Summary(state.confirmationOrder.option.price)

        VerticalGap(16.dp)

        Tariff(state.confirmationOrder.option.type)

        VerticalGap(4.dp)

        Arrives(state.confirmationOrder.option.days)

        VerticalGap(24.dp)

        CheckoutButton(
            loading = state.loading,
            onClick = { onIntent(ConfirmationOrderIntent.CheckoutOrder) }
        )

        VerticalGap(32.dp)
    }
}

private fun DetailType.getIntent(): ConfirmationOrderIntent =
    when (this) {
        DetailType.RECEIVER ->
            ConfirmationOrderIntent.EditReceiver

        DetailType.SENDER ->
            ConfirmationOrderIntent.EditSender

        DetailType.SENDER_ADDRESS ->
            ConfirmationOrderIntent.EditSenderAddress

        DetailType.RECEIVER_ADDRESS ->
            ConfirmationOrderIntent.EditReceiverAddress
    }

@Composable
private fun Summary(price: Double) {
    TitleH3(
        text = stringResource(Res.string.order_delivery_cost, price),
        color = DeliveryTheme.colorScheme.textPrimary
    )
}

@Composable
private fun Tariff(type: OptionType) {
    val text = when (type) {
        OptionType.DEFAULT -> stringResource(Res.string.order_tariff_default)
        OptionType.EXPRESS -> stringResource(Res.string.order_tariff_express)
    }

    Paragraph16Regular(
        text = stringResource(Res.string.order_selected_tariff, text),
        color = DeliveryTheme.colorScheme.textSecondary
    )
}

private const val ONE_WORK_DAY = 1

@Composable
private fun Arrives(days: Int) {
    val text = if (days == ONE_WORK_DAY) {
        stringResource(Res.string.order_delviery_one_work_day, days)
    } else {
        stringResource(Res.string.order_delviery_some_work_days, days)
    }

    Paragraph16Regular(
        text = stringResource(Res.string.order_delivery_amount_work_days, text),
        color = DeliveryTheme.colorScheme.textSecondary
    )
}

@Composable
private fun CheckoutButton(
    loading: Boolean,
    onClick: () -> Unit
) {
    PrimaryButton(onClick = onClick) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = DeliveryTheme.colorScheme.textInvert
            )
        } else {
            Text(
                text = stringResource(Res.string.order_checkout_button),
                color = DeliveryTheme.colorScheme.textInvert,
                style = DeliveryTheme.typography.buttonSemibold,
            )
        }
    }
}
