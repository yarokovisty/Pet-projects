package org.yarokovisty.delivery.feature.history.details.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import delivery.design.resources.generated.resources.ic_box
import delivery.design.resources.generated.resources.ic_car
import delivery.design.resources.generated.resources.ic_check_circle
import delivery.design.resources.generated.resources.ic_time
import delivery.feature.history.details.generated.resources.Res
import delivery.feature.history.details.generated.resources.history_details_order_canceled_status
import delivery.feature.history.details.generated.resources.history_details_order_created_status
import delivery.feature.history.details.generated.resources.history_details_order_delivered_status
import delivery.feature.history.details.generated.resources.history_details_order_delivering_status
import delivery.feature.history.details.generated.resources.history_details_order_waiting_status
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.order.domain.entity.OrderStatus
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.feature.history.details.ui.component.CircleShape
import org.yarokovisty.delivery.feature.history.details.ui.component.OverlayPainter
import delivery.design.resources.generated.resources.Res as designRes

internal data class OrderStatusUi(
    val description: String,
    val iconColors: List<Color>,
    val lineColors: List<Color>
)

@Composable
internal fun OrderStatus.toUi(): OrderStatusUi = when (this) {
    OrderStatus.CREATED -> getCreatedStatusUi()
    OrderStatus.WAITING -> getWaitingStatusUi()
    OrderStatus.DELIVERING -> getDeliveringStatusUi()
    OrderStatus.DELIVERED -> getDeliveredStatusUi()
    OrderStatus.CANCELLED -> getCancelledStatusUi()
}

@Composable
private fun getCreatedStatusUi(): OrderStatusUi =
    OrderStatusUi(
        description = stringResource(Res.string.history_details_order_created_status),
        iconColors = listOf(
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorLight,
            DeliveryTheme.colorScheme.indicatorLight,
            DeliveryTheme.colorScheme.indicatorLight
        ),
        lineColors = listOf(
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorLight,
            DeliveryTheme.colorScheme.indicatorLight
        )
    )

@Composable
private fun getWaitingStatusUi(): OrderStatusUi =
    OrderStatusUi(
        description = stringResource(Res.string.history_details_order_waiting_status),
        iconColors = listOf(
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorLight,
            DeliveryTheme.colorScheme.indicatorLight
        ),
        lineColors = listOf(
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorLight
        )
    )

@Composable
private fun getDeliveringStatusUi(): OrderStatusUi =
    OrderStatusUi(
        description = stringResource(Res.string.history_details_order_delivering_status),
        iconColors = listOf(
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorLight
        ),
        lineColors = listOf(
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorAttention,
            DeliveryTheme.colorScheme.indicatorAttention
        )
    )

private const val ORDER_STATUS_ICONS_AMOUNT = 4
private const val ORDER_STATUS_LINES_AMOUNT = 3

@Composable
private fun getDeliveredStatusUi(): OrderStatusUi =
    OrderStatusUi(
        description = stringResource(Res.string.history_details_order_delivered_status),
        iconColors = List(ORDER_STATUS_ICONS_AMOUNT) { DeliveryTheme.colorScheme.indicatorPositive },
        lineColors = List(ORDER_STATUS_LINES_AMOUNT) { DeliveryTheme.colorScheme.indicatorPositive }
    )

@Composable
private fun getCancelledStatusUi(): OrderStatusUi =
    OrderStatusUi(
        description = stringResource(Res.string.history_details_order_canceled_status),
        iconColors = List(ORDER_STATUS_ICONS_AMOUNT) { DeliveryTheme.colorScheme.indicatorError },
        lineColors = List(ORDER_STATUS_LINES_AMOUNT) { DeliveryTheme.colorScheme.indicatorError }
    )

@Composable
internal fun getStatusIcons(colors: List<Color>): List<Painter> {
    val density = LocalDensity.current

    return listOf(
        painterResource(designRes.drawable.ic_time),
        painterResource(designRes.drawable.ic_box),
        painterResource(designRes.drawable.ic_car),
        painterResource(designRes.drawable.ic_check_circle)
    ).mapIndexed { index, painter ->
        OverlayPainter(
            backImage = CircleShape(
                size = DpSize(32.dp, 32.dp),
                color = colors[index],
                density = density
            ),
            frontImage = painter,
            frontImageTint = Color.White,
            frontImageSize = DpSize(20.dp, 20.dp)
        )
    }
}
