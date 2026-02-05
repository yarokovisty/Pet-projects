package org.yarokovisty.delivery.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun DeliveryTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalDeliveryColorScheme provides deliveryColorScheme,
        LocalDeliveryTypography provides deliveryTypography,
        content = content,
    )
}

object DeliveryTheme {

    val colorScheme: DeliveryColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalDeliveryColorScheme.current

    val typography: DeliveryTypography
        @Composable @ReadOnlyComposable
        get() = LocalDeliveryTypography.current
}
