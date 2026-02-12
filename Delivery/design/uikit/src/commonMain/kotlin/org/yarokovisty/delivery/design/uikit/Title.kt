package org.yarokovisty.delivery.design.uikit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun TitleH1(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        style = DeliveryTheme.typography.titleH1,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Composable
fun TitleH2(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.titleH2,
        modifier = modifier
    )
}

@Composable
fun TitleH3(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.titleH3,
        modifier = modifier
    )
}

@Composable
fun TitleSubtitle(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        style = DeliveryTheme.typography.titleSubtitle,
        modifier = modifier
    )
}

@Preview
@Composable
private fun TitleH1Preview() {
    DeliveryTheme {
        TitleH1(text = "TitleH1")
    }
}

@Preview
@Composable
private fun TitleH2Preview() {
    DeliveryTheme {
        TitleH2(text = "TitleH2")
    }
}

@Preview
@Composable
private fun Title3Preview() {
    DeliveryTheme {
        TitleH3(text = "TitleH3")
    }
}

@Preview
@Composable
private fun TitleSubtitlePreview() {
    DeliveryTheme {
        TitleSubtitle(text = "TitleSubtitle")
    }
}
