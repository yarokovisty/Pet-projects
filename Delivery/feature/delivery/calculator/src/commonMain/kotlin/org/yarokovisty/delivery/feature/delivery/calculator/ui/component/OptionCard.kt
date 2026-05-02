package org.yarokovisty.delivery.feature.delivery.calculator.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
internal fun OptionCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = DeliveryTheme.colorScheme.bgPrimary,
        border = BorderStroke(width = 1.dp, color = DeliveryTheme.colorScheme.borderExtraLight),
        modifier = modifier,
        onClick = { onClick?.invoke() },
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            content = { content() }
        )
    }
}
