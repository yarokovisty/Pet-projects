package org.yarokovisty.delivery.feature.delivery.main.impl.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.main.impl.generated.resources.Res
import delivery.feature.delivery.main.impl.generated.resources.tracker_card_button_find
import delivery.feature.delivery.main.impl.generated.resources.tracker_card_hint_item
import delivery.feature.delivery.main.impl.generated.resources.tracker_card_title
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.InputFieldDefault
import org.yarokovisty.delivery.design.uikit.PrimaryButton
import org.yarokovisty.delivery.design.uikit.PrimaryCard
import org.yarokovisty.delivery.design.uikit.TitleH2
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.TrackerContent

@Composable
internal fun TrackerCard(
    state: TrackerContent,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    PrimaryCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TitleH2(text = stringResource(Res.string.tracker_card_title), color = DeliveryTheme.colorScheme.textPrimary)

            InputFieldDefault(
                value = state.inputIdParcel,
                hint = stringResource(Res.string.tracker_card_hint_item),
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { id -> onIntent(DeliveryMainIntent.ChangeInputParcelId(id)) }
            )

            PrimaryButton(
                text = stringResource(Res.string.tracker_card_button_find),
                enabled = state.trackEnabled,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onIntent(DeliveryMainIntent.TrackParcel) }
            )
        }
    }
}

@Preview
@Composable
private fun TrackerCardPreview() {
    val state = TrackerContent("")

    DeliveryTheme {
        TrackerCard(state) {}
    }
}
