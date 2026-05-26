package org.yarokovisty.delivery.design.uikit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.text.Paragraph14Regular

@Composable
fun LinearStepIndicator(
    step: Int,
    maxSteps: Int,
    modifier: Modifier = Modifier,
    title: String? = null,
    contentColor: Color = DeliveryTheme.colorScheme.indicatorPositive,
    containerColor: Color = DeliveryTheme.colorScheme.indicatorLight,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (title != null) {
            Paragraph14Regular(text = title)

            VerticalGap(8.dp)
        }

        StepIndicator(step, maxSteps, contentColor, containerColor)
    }
}

@Composable
private fun StepIndicator(
    step: Int,
    maxSteps: Int,
    contentColor: Color,
    containerColor: Color
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
    ) {
        val indicatorWidth = size.width
        val indicatorHeight = size.height

        val progress = step.toFloat() / maxSteps.toFloat()
        val progressWidth = indicatorWidth * progress

        drawRoundRect(
            color = containerColor,
            size = Size(indicatorWidth, indicatorHeight),
            cornerRadius = CornerRadius(indicatorHeight / 2)
        )

        drawRoundRect(
            color = contentColor,
            size = Size(progressWidth, indicatorHeight),
            cornerRadius = CornerRadius(indicatorHeight / 2)
        )
    }
}

@Preview
@Composable
private fun LinearStepIndicatorPreview() {
    DeliveryTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DeliveryTheme.colorScheme.bgPrimary)
        ) {
            LinearStepIndicator(step = 1, maxSteps = 5, title = "Title")
        }
    }
}
