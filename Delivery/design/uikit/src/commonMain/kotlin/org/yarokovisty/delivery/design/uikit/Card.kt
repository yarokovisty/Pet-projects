package org.yarokovisty.delivery.design.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun PrimaryCard(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(color = DeliveryTheme.colorScheme.bgPrimary, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 32.dp),
        contentAlignment = contentAlignment,
        content = { content() }
    )
}

@Preview
@Composable
private fun PrimaryCardPreview() {
    DeliveryTheme {
        PrimaryCard {}
    }
}
