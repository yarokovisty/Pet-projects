package org.yarokovisty.delivery.feature.delivery.point.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import delivery.feature.delivery.point.generated.resources.Res
import delivery.feature.delivery.point.generated.resources.address_receiver_non_contact_checkbox_text
import delivery.feature.delivery.point.generated.resources.address_receiver_non_contact_hint_text
import delivery.feature.delivery.point.generated.resources.address_receiver_non_contact_hint_title
import delivery.feature.delivery.point.generated.resources.ic_circle_question
import delivery.feature.delivery.point.generated.resources.ic_s_cross
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.theme.Inter
import org.yarokovisty.delivery.design.uikit.HorizontalGap
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.selector.CheckBox
import org.yarokovisty.delivery.design.uikit.text.Paragraph12Regular
import org.yarokovisty.delivery.feature.delivery.point.presentation.intent.ReceiverAddressIntent
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.NonContactedState
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.ReceiverAddressContentState

@Composable
internal fun ReceiverAddressContent(
    state: ReceiverAddressContentState,
    onIntent: (ReceiverAddressIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        AddressInputsComponent(
            street = state.street,
            onStreetChange = { onIntent(ReceiverAddressIntent.InputStreet(it)) },
            house = state.house,
            onHouseChange = { onIntent(ReceiverAddressIntent.InputHouse(it)) },
            apartment = state.apartment,
            onApartmentChange = { onIntent(ReceiverAddressIntent.InputApartment(it)) },
            comment = state.comment,
            onCommentChange = { onIntent(ReceiverAddressIntent.InputComment(it)) }
        )

        NonContactedCheckBox(state.nonContactedState, onIntent)

        ContinueButton(
            onClick = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onIntent(ReceiverAddressIntent.ClickContinue)
            }
        )
    }
}

@Composable
private fun NonContactedCheckBox(
    state: NonContactedState,
    onIntent: (ReceiverAddressIntent) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CheckBox(
            checked = state.checked,
            text = stringResource(Res.string.address_receiver_non_contact_checkbox_text),
            onCheckedChange = { onIntent(ReceiverAddressIntent.ClickNonContactedCheckbox) }
        )

        HorizontalGap(8.dp)

        Tip(
            showing = state.tipShowing,
            onToggle = { onIntent(ReceiverAddressIntent.ClickNonContactedTip) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Tip(
    showing: Boolean,
    onToggle: () -> Unit
) {
    val tooltipState = rememberTooltipState(isPersistent = true)

    LaunchedEffect(showing) {
        if (showing) {
            tooltipState.show()
        } else {
            tooltipState.dismiss()
        }
    }

    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Below,
            spacingBetweenTooltipAndAnchor = 10.dp
        ),
        state = tooltipState,
        onDismissRequest = onToggle,
        tooltip = {
            RichTooltip(
                caretShape = TooltipDefaults.caretShape(),
                maxWidth = 240.dp,
                colors = TooltipDefaults.richTooltipColors()
                    .copy(containerColor = DeliveryTheme.colorScheme.bgPrimary)
            ) {
                TipContent(
                    onCloseClick = onToggle
                )
            }
        }
    ) {
        TipIcon(
            showing = showing,
            onClick = onToggle
        )
    }
}

@Composable
private fun TipContent(onCloseClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TipTitle(onCloseClick)

        VerticalGap(4.dp)

        TipDescription()
    }
}

@Composable
private fun TipTitle(onCloseClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.address_receiver_non_contact_hint_title),
            color = DeliveryTheme.colorScheme.textPrimary,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.W600,
            fontFamily = Inter(),
            modifier = Modifier.weight(1f)
        )

        CloseIcon(onClick = onCloseClick)
    }
}

@Composable
private fun TipDescription() {
    Paragraph12Regular(
        text = stringResource(Res.string.address_receiver_non_contact_hint_text),
        color = DeliveryTheme.colorScheme.textTertiary
    )
}

@Composable
private fun CloseIcon(onClick: () -> Unit) {
    Icon(
        painter = painterResource(Res.drawable.ic_s_cross),
        contentDescription = null,
        tint = DeliveryTheme.colorScheme.indicatorLight,
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun TipIcon(
    showing: Boolean,
    onClick: () -> Unit
) {
    val iconTint = if (showing) {
        DeliveryTheme.colorScheme.brandIndicator
    } else {
        DeliveryTheme.colorScheme.indicatorLight
    }

    IconButton(
        modifier = Modifier.size(20.dp),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_circle_question),
            contentDescription = null,
            tint = iconTint
        )
    }
}
