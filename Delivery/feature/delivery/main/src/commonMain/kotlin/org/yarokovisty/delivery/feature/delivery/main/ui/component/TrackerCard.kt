package org.yarokovisty.delivery.feature.delivery.main.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.main.generated.resources.Res
import delivery.feature.delivery.main.generated.resources.tracker_card_button_find
import delivery.feature.delivery.main.generated.resources.tracker_card_hint_item
import delivery.feature.delivery.main.generated.resources.tracker_card_title
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.input.TextInput
import org.yarokovisty.delivery.design.uikit.text.TitleH2
import org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.TrackerContent

@Composable
internal fun TrackerCard(
    state: TrackerContent,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    ContentCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TitleH2(text = stringResource(Res.string.tracker_card_title))

            ParcelIdInput(
                text = state.inputIdParcel,
                onTextChange = { onIntent(DeliveryMainIntent.ChangeInputParcelId(it)) }
            )

            FindButton(
                enabled = state.trackEnabled,
                onClick = { onIntent(DeliveryMainIntent.TrackParcel) }
            )
        }
    }
}

@Composable
private fun ParcelIdInput(
    text: String,
    onTextChange: (String) -> Unit
) {
    TextInput(
        text = text,
        hint = stringResource(Res.string.tracker_card_hint_item),
        modifier = Modifier.fillMaxWidth(),
        onTextChange = onTextChange
    )
}

@Composable
private fun FindButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    PrimaryButton(
        text = stringResource(Res.string.tracker_card_button_find),
        enabled = enabled,
        onClick = onClick
    )
}

@Preview
@Composable
private fun TrackerCardPreview() {
    val state = TrackerContent("")

    DeliveryTheme {
        TrackerCard(state) {}
    }
}
