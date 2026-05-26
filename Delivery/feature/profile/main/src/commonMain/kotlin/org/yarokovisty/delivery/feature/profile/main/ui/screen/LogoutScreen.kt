package org.yarokovisty.delivery.feature.profile.main.ui.screen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.screen.BottomSheetScreen
import org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent
import org.yarokovisty.delivery.feature.profile.main.ui.component.LogoutContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LogoutScreen(
    visible: Boolean,
    onIntent: (ProfileIntent) -> Unit
) {
    BottomSheetScreen(
        visible = visible,
        containerColor = DeliveryTheme.colorScheme.bgPrimary,
        shape = RoundedCornerShape(16.dp),
        onDismissRequest = { onIntent(ProfileIntent.CloseLogoutScreen) }
    ) {
        LogoutContent(onIntent = onIntent)
    }
}
