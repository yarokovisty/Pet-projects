package org.yarokovisty.delivery.design.uikit.lines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.HorizontalGap
import org.yarokovisty.delivery.design.uikit.VerticalGap

@Composable
fun TwoLine(
    title: String,
    titleStyle: TextStyle,
    subtitle: String,
    subtitleStyle: TextStyle,
    modifier: Modifier = Modifier,
    startIcon: Painter? = null,
    startIconColor: Color = Color.Unspecified,
    endIcon: Painter? = null,
    endIconColor: Color = Color.Unspecified,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        if (startIcon != null) {
            StartIcon(startIcon, startIconColor)

            HorizontalGap(16.dp)
        }

        Content(title, titleStyle, subtitle, subtitleStyle)

        if (endIcon != null) {
            EndIcon(endIcon, endIconColor)
        }
    }
}

@Composable
private fun StartIcon(
    icon: Painter,
    color: Color
) {
    Box(
        modifier = Modifier.size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(painter = icon, contentDescription = null, tint = color)
    }
}

@Composable
private fun RowScope.Content(
    title: String,
    titleStyle: TextStyle,
    subtitle: String,
    subtitleStyle: TextStyle
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(text = title, style = titleStyle)

        VerticalGap(2.dp)

        Text(text = subtitle, style = subtitleStyle)
    }
}

@Composable
private fun EndIcon(
    icon: Painter,
    color: Color
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
private fun TwoLinePreview() {
    DeliveryTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DeliveryTheme.colorScheme.bgPrimary)
        ) {
            TwoLine(
                title = "Title",
                titleStyle = DeliveryTheme.typography.paragraph12Regular.copy(
                    color = DeliveryTheme.colorScheme.textTertiary
                ),
                subtitle = "Subtitle",
                subtitleStyle = DeliveryTheme.typography.paragraph16Regular.copy(
                    color = DeliveryTheme.colorScheme.textPrimary
                )
            )
        }
    }
}
