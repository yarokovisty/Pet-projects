package org.yarokovisty.delivery.design.uikit.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun FullScreen(
    containerColor: Color = DeliveryTheme.colorScheme.bgPrimary,
    windowInsets: WindowInsets = WindowInsets.safeDrawing,
    paddingValues: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(containerColor)
            .windowInsetsPadding(windowInsets)
            .padding(paddingValues)
    ) {
        content()
    }
}
