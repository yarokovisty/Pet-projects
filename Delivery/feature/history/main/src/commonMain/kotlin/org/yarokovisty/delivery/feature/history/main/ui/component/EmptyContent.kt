package org.yarokovisty.delivery.feature.history.main.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.design.resources.generated.resources.img_empty
import delivery.feature.history.main.generated.resources.Res
import delivery.feature.history.main.generated.resources.history_main_empty_content
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular
import delivery.design.resources.generated.resources.Res as designR

@Composable
internal fun EmptyContent() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
    ) {
        Image(
            painter = painterResource(designR.drawable.img_empty),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Paragraph16Regular(
            text = stringResource(Res.string.history_main_empty_content),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun EmptyContentPreview() {
    DeliveryTheme {
        Box(modifier = Modifier.background(DeliveryTheme.colorScheme.bgPrimary)) {
            EmptyContent()
        }
    }
}
