package org.yarokovisty.delivery.feature.history.details.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.history.details.generated.resources.Res
import delivery.feature.history.details.generated.resources.history_details_order_cancel_button
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.common.delivery.order.domain.entity.OrderStatus
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton

@Composable
internal fun DetailsCard(
    order: Order,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = DeliveryTheme.colorScheme.bgPrimary,
        border = BorderStroke(1.dp, DeliveryTheme.colorScheme.borderExtraLight),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            StatusContent(order.status)

            AddressContent(order.receiverPoint, order.receiverAddress)

            TariffContent(order.optionType)

            if (order.status != OrderStatus.CANCELLED) {
                PrimaryButton(
                    text = stringResource(Res.string.history_details_order_cancel_button),
                    onClick = onCancel
                )
            }
        }
    }
}
