package org.pet.project.rickandmorty.app.ui.view

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AppNavigationBar(
    selectedIcon: Int,
    onClick: (Int) -> Unit
) {
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        AppNavigationBarItem.entries.forEachIndexed { index, item ->
            val selected = selectedIcon == index
            NavigationBarItem(
                selected = selected,
                onClick = { if (!selected) onClick(index) },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(item.title)) }
            )
        }
    }
}