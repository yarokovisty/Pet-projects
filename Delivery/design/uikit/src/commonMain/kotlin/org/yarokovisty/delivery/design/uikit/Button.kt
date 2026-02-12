package org.yarokovisty.delivery.design.uikit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DeliveryTheme.colorScheme.brandPrimary
        ),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = DeliveryTheme.colorScheme.textInvert,
            style = DeliveryTheme.typography.buttonSemibold,
        )
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    DeliveryTheme {
        PrimaryButton(text = "PrimaryButton", onClick = {})
    }
}
