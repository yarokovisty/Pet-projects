package org.yarokovisty.delivery.design.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun InputFieldDefault(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val textStyle = DeliveryTheme.typography.paragraph16Regular.copy(color = DeliveryTheme.colorScheme.textSecondary)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Hint(hint) },
        textStyle = textStyle,
        enabled = enabled,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DeliveryTheme.colorScheme.borderLight,
            unfocusedBorderColor = DeliveryTheme.colorScheme.borderLight,
        ),
        modifier = modifier
    )
}

@Composable
private fun Hint(text: String) {
    Paragraph16Regular(text, color = DeliveryTheme.colorScheme.textTertiary)
}

@Preview
@Composable
private fun InputDefaultPreview() {
    DeliveryTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            InputFieldDefault(value = "", onValueChange = {}, hint = "Hint")
            InputFieldDefault(value = "Value", onValueChange = {})
        }
    }
}
