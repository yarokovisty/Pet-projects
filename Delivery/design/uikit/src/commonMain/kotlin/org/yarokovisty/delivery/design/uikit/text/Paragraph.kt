package org.yarokovisty.delivery.design.uikit.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun Paragraph12Regular(
    text: String,
    color: Color = DeliveryTheme.colorScheme.textPrimary,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.paragraph12Regular,
        modifier = modifier
    )
}

@Composable
fun Paragraph14Regular(
    text: String,
    color: Color = DeliveryTheme.colorScheme.textPrimary,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.paragraph14Regular,
        modifier = modifier
    )
}

@Composable
fun Paragraph14Medium(
    text: String,
    color: Color = DeliveryTheme.colorScheme.textPrimary,
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
fun Paragraph14RegularUnderline(
    text: String,
    color: Color = DeliveryTheme.colorScheme.textPrimary,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.paragraph14RegularUnderline,
        modifier = modifier
    )
}

@Composable
fun Paragraph16Regular(
    text: String,
    color: Color = DeliveryTheme.colorScheme.textPrimary,
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

@Composable
fun Paragraph16Medium(
    text: String,
    color: Color = DeliveryTheme.colorScheme.textPrimary,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.paragraph16Medium,
        modifier = modifier
    )
}

@Composable
fun Paragraph16MediumUnderline(
    text: String,
    color: Color = DeliveryTheme.colorScheme.textPrimary,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.paragraph16MediumUnderline,
        modifier = modifier
    )
}

@Preview
@Composable
private fun Paragraph12RegularPreview() {
    DeliveryTheme {
        Paragraph12Regular(text = "Paragraph12Regular")
    }
}

@Preview
@Composable
private fun Paragraph14RegularPreview() {
    DeliveryTheme {
        Paragraph14Regular(text = "Paragraph14Regular")
    }
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
        Paragraph14RegularUnderline(text = "Paragraph14Underline")
    }
}

@Preview
@Composable
private fun Paragraph16MediumUnderlinePreview() {
    DeliveryTheme {
        Paragraph16MediumUnderline(text = "Paragraph16MediumUnderline")
    }
}

@Preview
@Composable
private fun Paragraph16RegularPreview() {
    DeliveryTheme {
        Paragraph16Regular(text = "Paragraph16Regular")
    }
}
