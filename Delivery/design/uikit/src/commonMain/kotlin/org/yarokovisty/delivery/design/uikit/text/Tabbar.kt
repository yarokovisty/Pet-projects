package org.yarokovisty.delivery.design.uikit.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun Tabbar(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.tabbar,
        modifier = modifier
    )
}
