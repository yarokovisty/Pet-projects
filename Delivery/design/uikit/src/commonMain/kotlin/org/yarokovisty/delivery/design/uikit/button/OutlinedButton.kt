package org.yarokovisty.delivery.design.uikit.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        contentPadding = PaddingValues(16.dp),
        border = BorderStroke(2.dp, DeliveryTheme.colorScheme.borderLight),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = DeliveryTheme.colorScheme.bgPrimary),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            color = DeliveryTheme.colorScheme.textSecondary,
            style = DeliveryTheme.typography.buttonSemibold,
        )
    }
}

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        contentPadding = PaddingValues(16.dp),
        border = BorderStroke(2.dp, DeliveryTheme.colorScheme.borderLight),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = DeliveryTheme.colorScheme.bgPrimary),
        modifier = modifier.fillMaxWidth(),
        content = content
    )
}

@Preview
@Composable
private fun OutlinedButtonPreview() {
    DeliveryTheme {
        OutlinedButton(text = "Button", onClick = {})
    }
}
