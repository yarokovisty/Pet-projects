package org.pet.project.rickandmorty.app.ui.view

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.core.navigation.navigator.LocalNestedNavigator
import org.pet.project.rickandmorty.core.navigation.destination.Tab

@Composable
internal fun AppNavigationBar(onClick: (Tab) -> Unit) {
    val nestedNavController = LocalNestedNavigator.current
    val navBackStackEntry by nestedNavController.navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        AppNavigationBarItem.entries.forEach { item ->

            val selected = currentDestination?.hierarchy?.any { it.route == item.tab.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = { if (!selected) onClick(item.tab) },
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