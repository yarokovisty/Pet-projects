package org.yarokovisty.delivery.design.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun FullScreen(
    containerColor: Color = DeliveryTheme.colorScheme.bgPrimary,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(containerColor)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        content = { content() }
    )
}
