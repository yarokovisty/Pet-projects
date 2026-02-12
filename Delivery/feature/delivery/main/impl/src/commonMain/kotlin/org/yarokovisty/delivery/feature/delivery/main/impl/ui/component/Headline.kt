package org.yarokovisty.delivery.feature.delivery.main.impl.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.main.impl.generated.resources.Res
import delivery.feature.delivery.main.impl.generated.resources.subtitle_screen
import delivery.feature.delivery.main.impl.generated.resources.title_screen
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.TitleH1
import org.yarokovisty.delivery.design.uikit.TitleSubtitle
import org.yarokovisty.delivery.design.uikit.VerticalGap

@Composable
internal fun Headline() {
    Column {
        TitleH1(
            text = stringResource(Res.string.title_screen),
            color = DeliveryTheme.colorScheme.textPrimary
        )

        VerticalGap(8.dp)

        TitleSubtitle(
            text = stringResource(Res.string.subtitle_screen),
            color = DeliveryTheme.colorScheme.textTertiary
        )
    }
}

@Preview
@Composable
private fun HeadlinePreview() {
    DeliveryTheme {
        Headline()
    }
}
