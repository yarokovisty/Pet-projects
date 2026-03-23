package org.yarokovisty.delivery.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import delivery.composeapp.generated.resources.Res
import delivery.composeapp.generated.resources.ic_calculate
import delivery.composeapp.generated.resources.ic_time
import delivery.composeapp.generated.resources.ic_user
import delivery.composeapp.generated.resources.tab_text_delivery
import delivery.composeapp.generated.resources.tab_text_history
import delivery.composeapp.generated.resources.tab_text_profile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.Tabbar
import org.yarokovisty.delivery.presentation.state.MainTab

@Composable
internal fun BottomBar(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DeliveryTheme.colorScheme.borderLight
        )

        NavigationBar(containerColor = DeliveryTheme.colorScheme.bgPrimary) {
            MainTab.entries.forEach { tab ->
                BottomBarItem(
                    selected = tab == selectedTab,
                    text = stringResource(tab.textRes),
                    icon = painterResource(tab.iconRes),
                    onClick = { onTabSelected(tab) },
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomBarItem(
    selected: Boolean,
    text: String,
    icon: Painter,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        icon = {
            Icon(painter = icon, contentDescription = null)
        },
        label = {
            Tabbar(text)
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = DeliveryTheme.colorScheme.brandPrimary,
            selectedTextColor = DeliveryTheme.colorScheme.brandPrimary,
            indicatorColor = Color.Transparent,
            unselectedIconColor = DeliveryTheme.colorScheme.textTertiary,
            unselectedTextColor = DeliveryTheme.colorScheme.textTertiary,
        ),
        onClick = {
            if (!selected) {
                onClick()
            }
        }
    )
}

private val MainTab.textRes: StringResource
    get() = when (this) {
        MainTab.DELIVERY -> Res.string.tab_text_delivery
        MainTab.HISTORY -> Res.string.tab_text_history
        MainTab.PROFILE -> Res.string.tab_text_profile
    }

private val MainTab.iconRes: DrawableResource
    get() = when (this) {
        MainTab.DELIVERY -> Res.drawable.ic_calculate
        MainTab.HISTORY -> Res.drawable.ic_time
        MainTab.PROFILE -> Res.drawable.ic_user
    }
