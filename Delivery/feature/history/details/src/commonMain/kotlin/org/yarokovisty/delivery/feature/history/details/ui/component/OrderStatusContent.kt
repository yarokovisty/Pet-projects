package org.yarokovisty.delivery.feature.history.details.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import delivery.feature.history.details.generated.resources.Res
import delivery.feature.history.details.generated.resources.history_details_order_status
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.order.domain.entity.OrderStatus
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.text.Paragraph12Regular
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular
import org.yarokovisty.delivery.feature.history.details.ui.model.OrderStatusUi
import org.yarokovisty.delivery.feature.history.details.ui.model.getStatusIcons
import org.yarokovisty.delivery.feature.history.details.ui.model.toUi

@Composable
internal fun StatusContent(status: OrderStatus) {
    val statusUi = status.toUi()

    Column {
        Paragraph12Regular(
            text = stringResource(Res.string.history_details_order_status),
            color = DeliveryTheme.colorScheme.textTertiary
        )

        VerticalGap(2.dp)

        Paragraph16Regular(statusUi.description)

        VerticalGap(2.dp)

        Progress(statusUi)
    }
}

@Composable
private fun Progress(statusUi: OrderStatusUi) {
    val icons = getStatusIcons(statusUi.iconColors)
    val lineColors = statusUi.lineColors

    Canvas(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
    ) {
        drawStatusLines(lineColors)
        drawStatusIcons(icons)
    }
}

private const val LINE_ON_WIDTH = 20f
private const val LINE_OFF_WIDTH = 16f
private const val ICON_SIZE_DP = 32

private fun DrawScope.drawStatusLines(lineColors: List<Color>) {
    val iconSize = ICON_SIZE_DP.dp.toPx()
    val step = (size.width - iconSize) / (lineColors.size)
    val padding = 4.dp.toPx()
    val centerY = size.height / 2
    val strokeWidth = 3.dp.toPx()
    val intervals = floatArrayOf(LINE_ON_WIDTH, LINE_OFF_WIDTH)

    lineColors.forEachIndexed { index, color ->
        val startX = index * step + iconSize + padding
        val endX = (index + 1) * step - padding

        drawLine(
            color = color,
            start = Offset(startX, centerY),
            end = Offset(endX, centerY),
            strokeWidth = strokeWidth,
            pathEffect = PathEffect.dashPathEffect(intervals)
        )
    }
}

private fun DrawScope.drawStatusIcons(icons: List<Painter>) {
    val iconSize = ICON_SIZE_DP.dp.toPx()
    val step = (size.width - iconSize) / (icons.size - 1)

    icons.forEachIndexed { index, painter ->
        translate(
            left = index * step,
            top = (size.height - iconSize) / 2
        ) {
            painter.run {
                draw(size = Size(iconSize, iconSize))
            }
        }
    }
}
