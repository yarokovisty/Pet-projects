package org.pet.project.rickandmorty.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Suppress("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEffect(
    action: suspend CoroutineScope.(T) -> Unit
) {
    LaunchedEffect(Unit) {
        collect { action(it) }
    }
}