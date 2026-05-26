package org.yarokovisty.delivery.design.uikit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.text.TitleH2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    navigationIcon: Painter? = null,
    onClickNavIcon: () -> Unit = {},
) {
    TopAppBar(
        title = {
            TitleH2(text = title)
        },
        navigationIcon = {
            if (navigationIcon != null) {
                NavIcon(
                    icon = navigationIcon,
                    onClick = onClickNavIcon
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DeliveryTheme.colorScheme.bgPrimary
        )
    )
}

@Composable
private fun NavIcon(
    icon: Painter,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = DeliveryTheme.colorScheme.indicatorLight
        )
    }
}
