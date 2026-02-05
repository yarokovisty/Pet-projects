package org.yarokovisty.delivery.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DeliveryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = deliveryColorScheme,
        content = content
    )
}
