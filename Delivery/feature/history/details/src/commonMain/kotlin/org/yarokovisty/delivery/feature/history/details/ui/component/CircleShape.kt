package org.yarokovisty.delivery.feature.history.details.ui.component

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize

internal class CircleShape(
    private val size: DpSize,
    private val color: Color,
    density: Density,
) : Painter() {

    override val intrinsicSize: Size =
        with(density) {
            Size(width = size.width.toPx(), height = size.height.toPx())
        }

    override fun DrawScope.onDraw() {
        drawCircle(color)
    }
}
