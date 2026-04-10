package org.yarokovisty.delivery.design.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun FullScreen(
    containerColor: Color = DeliveryTheme.colorScheme.bgPrimary,
    paddingValues: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(containerColor)
    ) {
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            content()
        }
    }
}
