package org.yarokovisty.delivery.feature.profile.main.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import delivery.feature.profile.main.generated.resources.Res
import delivery.feature.profile.main.generated.resources.ic_check_circle
import delivery.feature.profile.main.generated.resources.ic_error_circle
import org.jetbrains.compose.resources.painterResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.text.Paragraph14Regular

@Composable
internal fun ProfileSnacbarHost(
    successSnackbarHostState: SnackbarHostState,
    errorSnackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = successSnackbarHostState,
        modifier = modifier
    ) { snackbarData ->
        _root_ide_package_.org.yarokovisty.delivery.feature.profile.main.ui.component.ProfileSnackbar(
            text = snackbarData.visuals.message,
            icon = painterResource(Res.drawable.ic_check_circle),
            iconTint = DeliveryTheme.colorScheme.indicatorPositive
        )
    }

    SnackbarHost(
        hostState = errorSnackbarHostState,
        modifier = modifier
    ) { snackbarData ->
        _root_ide_package_.org.yarokovisty.delivery.feature.profile.main.ui.component.ProfileSnackbar(
            text = snackbarData.visuals.message,
            icon = painterResource(Res.drawable.ic_error_circle),
            iconTint = DeliveryTheme.colorScheme.indicatorError
        )
    }
}

@Composable
private fun ProfileSnackbar(
    text: String,
    icon: Painter,
    iconTint: Color
) {
    Snackbar(
        containerColor = DeliveryTheme.colorScheme.bgSecondary,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Paragraph14Regular(
                text = text,
                color = DeliveryTheme.colorScheme.textPrimary,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = icon,
                contentDescription = null,
                tint = iconTint
            )
        }
    }
}
