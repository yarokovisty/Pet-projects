package org.yarokovisty.delivery.feature.delivery.point.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import delivery.feature.delivery.point.generated.resources.Res
import delivery.feature.delivery.point.generated.resources.address_continue_button
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton

@Composable
internal fun ContinueButton(onClick: () -> Unit) {
    PrimaryButton(
        text = stringResource(Res.string.address_continue_button),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    )
}
