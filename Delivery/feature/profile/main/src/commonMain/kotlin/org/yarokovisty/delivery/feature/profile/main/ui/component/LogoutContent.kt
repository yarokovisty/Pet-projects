package org.yarokovisty.delivery.feature.profile.main.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.feature.profile.main.generated.resources.Res
import delivery.feature.profile.main.generated.resources.profile_logout_cancel_button
import delivery.feature.profile.main.generated.resources.profile_logout_confirm_button
import delivery.feature.profile.main.generated.resources.profile_logout_title
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.button.OutlinedButton
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.text.TitleH3
import org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent

@Composable
internal fun LogoutContent(onIntent: (ProfileIntent) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        TitleH3(
            text = stringResource(Res.string.profile_logout_title),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalGap(24.dp)

        OutlinedButton(
            text = stringResource(Res.string.profile_logout_cancel_button),
            onClick = { onIntent(ProfileIntent.CloseLogoutScreen) }
        )

        VerticalGap(16.dp)

        PrimaryButton(
            text = stringResource(Res.string.profile_logout_confirm_button),
            onClick = { onIntent(ProfileIntent.ConfirmLogout) }
        )
    }
}

@Preview
@Composable
private fun LogoutContentPreview() {
    DeliveryTheme {
        Box(modifier = Modifier.background(DeliveryTheme.colorScheme.bgPrimary)) {
            LogoutContent(onIntent = {})
        }
    }
}
