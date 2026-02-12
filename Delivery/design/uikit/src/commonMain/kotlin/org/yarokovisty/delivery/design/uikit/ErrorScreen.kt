package org.yarokovisty.delivery.design.uikit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.design.uikit.generated.resources.Res
import delivery.design.uikit.generated.resources.ic_close
import delivery.design.uikit.generated.resources.img_sorry
import org.jetbrains.compose.resources.painterResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun ErrorScreen(
    message: String,
    buttonText: String,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    onCloseClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                top = 12.dp,
                end = 16.dp,
                bottom = 32.dp
            )
    ) {
        if (onCloseClick != null) {
            CloseIcon(onClick = onCloseClick)
        }

        ErrorContent(message)

        PrimaryButton(
            text = buttonText,
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun CloseIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(Res.drawable.ic_close),
            contentDescription = null,
            tint = DeliveryTheme.colorScheme.indicatorLight,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun BoxScope.ErrorContent(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.img_sorry),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalGap(12.dp)

        TitleH3(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    DeliveryTheme {
        ErrorScreen(
            message = "Сервис временно недоступен",
            buttonText = "Повторить",
            onButtonClick = {},
            onCloseClick = {}
        )
    }
}
