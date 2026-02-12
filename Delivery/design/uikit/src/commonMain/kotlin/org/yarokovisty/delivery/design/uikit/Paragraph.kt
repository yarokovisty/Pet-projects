package org.yarokovisty.delivery.design.uikit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun Paragraph14Medium(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.paragraph14Medium,
        modifier = modifier
    )
}

@Composable
fun Paragraph14Underline(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.paragraph14Underline,
        modifier = modifier
    )
}

@Composable
fun Paragraph16Regular(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.paragraph16Regular,
        modifier = modifier
    )
}

@Preview
@Composable
private fun Paragraph14MediumPreview() {
    DeliveryTheme {
        Paragraph14Medium(text = "Paragraph14Medium")
    }
}

@Preview
@Composable
private fun Paragraph14UnderlinePreview() {
    DeliveryTheme {
        Paragraph14Underline(text = "Paragraph14Underline")
    }
}

@Preview
@Composable
private fun Paragraph16RegularPreview() {
    DeliveryTheme {
        Paragraph16Regular(text = "Paragraph16Regular")
    }
}
