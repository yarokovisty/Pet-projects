package org.yarokovisty.delivery.feature.history.details.ui.component

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawTransform
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.isUnspecified

internal class OverlayPainter(
    private val backImage: Painter,
    private val frontImage: Painter,
    private val frontImageTint: Color = Color.Unspecified,
    private val frontImageSize: DpSize = DpSize.Unspecified,
) : Painter() {

    override val intrinsicSize: Size
        get() = backImage.intrinsicSize

    override fun DrawScope.onDraw() {
        drawBackImage()
        drawFrontImage()
    }

    private fun DrawScope.drawBackImage() {
        backImage.run { draw(size) }
    }

    private fun DrawScope.drawFrontImage() {
        frontImage.run {
            withTransform(getFrontTransformations()) {
                draw(size = getFrontImageSize(), colorFilter = getColorFilterOrNull())
            }
        }
    }

    private fun DrawScope.getFrontTransformations(): DrawTransform.() -> Unit {
        val leftOffset = (size.width - frontImageSize.width.toPx()) / 2
        val topOffset = (size.height - frontImageSize.height.toPx()) / 2

        return {
            translate(leftOffset, topOffset)
        }
    }

    private fun DrawScope.getFrontImageSize(): Size =
        if (frontImageSize.isUnspecified) {
            size
        } else {
            frontImageSize.toSize()
        }

    private fun getColorFilterOrNull(): ColorFilter? =
        ColorFilter.tint(frontImageTint).takeIf { frontImageTint.isSpecified }
}
