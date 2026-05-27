package org.yarokovisty.delivery.feature.delivery.payer.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.payer.generated.resources.Res
import delivery.feature.delivery.payer.generated.resources.payer_continue_button
import delivery.feature.delivery.payer.generated.resources.payer_question_select_text
import delivery.feature.delivery.payer.generated.resources.payer_receiver_text
import delivery.feature.delivery.payer.generated.resources.payer_sender_text
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.selector.RadioGroup
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular
import org.yarokovisty.delivery.feature.delivery.payer.presentation.intent.PayerIntent
import org.yarokovisty.delivery.feature.delivery.payer.presentation.state.ContentState

@Composable
internal fun PayerContent(
    state: ContentState,
    onIntent: (PayerIntent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Paragraph16Regular(text = stringResource(Res.string.payer_question_select_text))

        PayerRadioGroup(
            selectedPayer = state.selectedPayer,
            payers = state.payers,
            onSelect = { onIntent(PayerIntent.SelectPayer(it)) }
        )

        ContinueButton(onClick = { onIntent(PayerIntent.ClickContinue) })
    }
}

@Composable
private fun PayerRadioGroup(
    selectedPayer: Payer,
    payers: List<Payer>,
    onSelect: (Payer) -> Unit
) {
    val payerMap = payers.associateBy { it.getText() }

    RadioGroup(
        selected = selectedPayer.getText(),
        group = payerMap.keys.toList(),
        onSelect = { text ->
            payerMap[text]?.let(onSelect)
        },
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun ContinueButton(onClick: () -> Unit) {
    PrimaryButton(
        text = stringResource(Res.string.payer_continue_button),
        onClick = onClick,
        modifier = Modifier.padding(vertical = 16.dp),
    )
}

@Composable
private fun Payer.getText(): String =
    when (this) {
        Payer.RECEIVER -> stringResource(Res.string.payer_receiver_text)
        Payer.SENDER -> stringResource(Res.string.payer_sender_text)
    }
