package org.yarokovisty.delivery.feature.delivery.point.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.feature.delivery.point.presentation.intent.SenderAddressIntent
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.SenderAddressContentState

@Composable
internal fun SenderAddressContent(
    state: SenderAddressContentState,
    onIntent: (SenderAddressIntent) -> Unit
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
            onStreetChange = { onIntent(SenderAddressIntent.InputStreet(it)) },
            house = state.house,
            onHouseChange = { onIntent(SenderAddressIntent.InputHouse(it)) },
            apartment = state.apartment,
            onApartmentChange = { onIntent(SenderAddressIntent.InputApartment(it)) },
            comment = state.comment,
            onCommentChange = { onIntent(SenderAddressIntent.InputComment(it)) }
        )

        ContinueButton(
            onClick = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onIntent(SenderAddressIntent.ClickContinue)
            }
        )
    }
}
