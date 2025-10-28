package org.pet.project.rickandmorty.core.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination


fun NavController.navigateRootTo(graph: Graph) {
    navigate(graph) {
        popUpTo(this@navigateRootTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.back() { popBackStack() }
