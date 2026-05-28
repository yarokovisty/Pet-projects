package org.yarokovisty.delivery.feature.history.main.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import delivery.feature.history.main.generated.resources.Res
import delivery.feature.history.main.generated.resources.history_main_order_canceled_status
import delivery.feature.history.main.generated.resources.history_main_order_created_status
import delivery.feature.history.main.generated.resources.history_main_order_delivered_status
import delivery.feature.history.main.generated.resources.history_main_order_delivering_status
import delivery.feature.history.main.generated.resources.history_main_order_status
import delivery.feature.history.main.generated.resources.history_main_order_waiting_status
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.order.domain.entity.OrderStatus
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.HorizontalGap
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.text.Paragraph12Regular
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular

@Composable
internal fun OrderStatusItem(status: OrderStatus) {
    Column {
        Paragraph12Regular(
            text = stringResource(Res.string.history_main_order_status),
            color = DeliveryTheme.colorScheme.textTertiary
        )

        VerticalGap(2.dp)

        Status(status)
    }
}

@Composable
private fun Status(status: OrderStatus) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        StatusIndicator(status)

        HorizontalGap(12.dp)

        Paragraph16Regular(text = status.getDescription())
    }
}

@Composable
private fun StatusIndicator(status: OrderStatus) {
    val indicatorColor = status.getIndicatorColor()

    Canvas(modifier = Modifier.size(10.dp)) {
        drawCircle(color = indicatorColor, radius = size.minDimension / 2)
    }
}

@Composable
private fun OrderStatus.getIndicatorColor(): Color =
    when (this) {
        OrderStatus.CREATED,
        OrderStatus.WAITING,
        OrderStatus.DELIVERING -> DeliveryTheme.colorScheme.indicatorAttention

        OrderStatus.DELIVERED -> DeliveryTheme.colorScheme.indicatorPositive
        OrderStatus.CANCELLED -> DeliveryTheme.colorScheme.indicatorError
    }

@Composable
private fun OrderStatus.getDescription(): String {
    val resId = when (this) {
        OrderStatus.CREATED -> Res.string.history_main_order_created_status
        OrderStatus.WAITING -> Res.string.history_main_order_waiting_status
        OrderStatus.DELIVERING -> Res.string.history_main_order_delivering_status
        OrderStatus.DELIVERED -> Res.string.history_main_order_delivered_status
        OrderStatus.CANCELLED -> Res.string.history_main_order_canceled_status
    }

    return stringResource(resId)
}
